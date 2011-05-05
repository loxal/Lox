/*
 * Copyright 2010 Alexander Orlov <alexander.orlov@loxal.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package loxal.lox.service.linguistics.server;

import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;

import java.io.*;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Iterator;

public class PGPCryptoImpl implements PGPCrypto {

    private String publicKeyPath;
    private String privateKeyPath;
    private String password;

    public PGPCryptoImpl() {
        Security.addProvider(new BouncyCastleProvider());
    }

    private PGPPublicKey readPublicKey(InputStream publichKey)
            throws IOException, PGPException {

        publichKey = PGPUtil.getDecoderStream(publichKey);

        final PGPPublicKeyRingCollection pgpPub = new PGPPublicKeyRingCollection(
                publichKey);

        PGPPublicKey key = null;

        // key ring iterator
        final Iterator keyRings = pgpPub.getKeyRings();
        while (key == null && keyRings.hasNext()) {
            final PGPPublicKeyRing kRing = (PGPPublicKeyRing) keyRings.next();
            Iterator kIt = kRing.getPublicKeys();

            while (key == null && kIt.hasNext()) {
                PGPPublicKey k = (PGPPublicKey) kIt.next();

                if (k.isEncryptionKey()) {
                    key = k;
                }
            }
        }

        if (key == null) {
            throw new IllegalArgumentException(
                    "Key ring not found.");
        }

        return key;
    }

    public File encryptFile(String plainData) throws PGPException {
        File outputFile;
        try {
            outputFile = File.createTempFile("~~~", ".tmp.gpg");
        } catch (IOException ioe) {
            throw new PGPException("Error creating tmp output file.", ioe);
        }

        encryptFile(plainData, outputFile.getAbsolutePath());

        return outputFile;
    }

    public void encryptFile(String plainData, String out)
            throws PGPException {

        PGPEncryptedDataGenerator dataGenerator = null;
        InputStream publicKey = null;
        OutputStream encryptedFile = null;
        OutputStream encryptedData = null;
        PGPCompressedDataGenerator compressedDataGenerator = null;

        try {

            publicKey = new FileInputStream(getPublicKeyFile());
            final PGPPublicKey pubKey = readPublicKey(publicKey);

            dataGenerator = new PGPEncryptedDataGenerator(
                    SymmetricKeyAlgorithmTags.CAST5, true, new SecureRandom(), "BC");
            dataGenerator.addMethod(pubKey);

//			encryptedFile =  new FileOutputStream(out); // TODO commented
            encryptedData = dataGenerator
                    .open(encryptedFile, new byte[1 << 16]);

            compressedDataGenerator = new PGPCompressedDataGenerator(
                    CompressionAlgorithmTags.ZIP);
            PGPUtil.writeFileToLiteralData(compressedDataGenerator
                    .open(encryptedData), PGPLiteralData.BINARY, new File(
                    plainData), new byte[1 << 16]);

        } catch (FileNotFoundException fnfe) {
            throw new PGPException(
                    "Could not find necessary file for encription.", fnfe);
        } catch (NoSuchProviderException nspe) {
            throw new PGPException("No provider for the encription.", nspe);
        } catch (IOException ioe) {
            throw new PGPException("Error while writing data.", ioe);
        } finally {

            try {
                if (compressedDataGenerator != null) {
                    compressedDataGenerator.close();
                }
                if (publicKey != null) {
                    publicKey.close();
                }
                if (dataGenerator != null) {
                    dataGenerator.close();
                }
                if (encryptedFile != null) {
                    encryptedData.close();
                }
                if (encryptedData != null) {
                    encryptedData.close();
                }
            } catch (IOException ioe) {
                throw new PGPException("Error closing Stream.", ioe);
            }

        }
    }

    private PGPPrivateKey findPrivateKey(InputStream privateKeyPath,
                                         long keyID, char[] pass) throws IOException, PGPException,
            NoSuchProviderException {
        final PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
                PGPUtil.getDecoderStream(privateKeyPath));
        final PGPSecretKey pgpSecKey = pgpSec.getSecretKey(keyID);

        if (pgpSecKey == null) {
            return null;
        }

        return pgpSecKey.extractPrivateKey(pass, "BC");
    }

    public File decryptFile(String in, String plainData)
            throws PGPException {

        InputStream encryptedFile = null;
        InputStream privateKey = null;
        InputStream clear = null;
//		FileOutputStream decryptedFileStream = null; // TODO commented
        InputStream decryptedInput = null;

        try {
            encryptedFile = new FileInputStream(in);
            privateKey = new FileInputStream(getPrivateKeyFile());

            final PGPObjectFactory pgpFactory = new PGPObjectFactory(
                    encryptedFile);
            final PGPEncryptedDataList encryptedDataList;
            final Object pgpObject = pgpFactory.nextObject();

            // the first object might be a PGP marker packet.
            if (pgpObject instanceof PGPEncryptedDataList) {
                encryptedDataList = (PGPEncryptedDataList) pgpObject;
            } else {
                encryptedDataList = (PGPEncryptedDataList) pgpFactory
                        .nextObject();
            }

            // find the secret key
            PGPPrivateKey secretKey = null;
            PGPPublicKeyEncryptedData encryptedData = null;
            for (Iterator encryptedDataObjects = encryptedDataList.getEncryptedDataObjects(); encryptedDataObjects
                    .hasNext();) {
                encryptedData = (PGPPublicKeyEncryptedData) encryptedDataObjects.next();

                secretKey = findPrivateKey(privateKey,
                        encryptedData.getKeyID(), getPassword().toCharArray());
            }
            if (secretKey == null) {
                throw new PGPException(
                        "No secret key in file");
            }

            clear = encryptedData.getDataStream(secretKey, "BC");
            final PGPObjectFactory plainFactory = new PGPObjectFactory(clear);
            final PGPCompressedData compressedData = (PGPCompressedData) plainFactory
                    .nextObject();
            final PGPObjectFactory pgpCompressedFactory = new PGPObjectFactory(
                    compressedData.getDataStream());
            final Object message = pgpCompressedFactory.nextObject();

            final File decryptedFile;
            if (message instanceof PGPLiteralData) {
                PGPLiteralData literalData = (PGPLiteralData) message;

                decryptedFile = new File(new File(plainData)
                        .getAbsolutePath()
                        + File.separator + literalData.getFileName());

//				decryptedFileStream = new FileOutputStream(decryptedFile); // TODO commented
                decryptedInput = literalData.getInputStream();
                int ch;
                while ((ch = decryptedInput.read()) >= 0) {
//					decryptedFileStream.write(ch);    // TODO commented
                }
            } else if (message instanceof PGPOnePassSignatureList) {
                throw new PGPException(
                        "Encrypted message contains a signed message - not literal data.");
            } else {
                throw new PGPException(
                        "Message is not a simple encrypted file - type unknown.");
            }

            if (encryptedData.isIntegrityProtected()) {
                if (!encryptedData.verify()) {
                    throw new PGPException("Integrity check failed.");
                }
            }

            return decryptedFile;

        } catch (IOException ioe) {
            throw new PGPException("Error while reading/writing data.", ioe);
        } catch (NoSuchProviderException nspe) {
            throw new PGPException("No provider for the encription.", nspe);
        } finally {
            try {
                if (encryptedFile != null) {
                    encryptedFile.close();
                }
                if (privateKey != null) {
                    privateKey.close();
                }
                if (clear != null) {
                    clear.close();
                }
//				if (decryptedFileStream != null) { // TODO commented
//					decryptedFileStream.close();
//				}
                if (decryptedInput != null) {
                    decryptedInput.close();
                }
            } catch (IOException ioe) {
                throw new PGPException("Error closing Stream.", ioe);
            }
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
    }

    public File getPrivateKeyFile() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public File getPublicKeyFile() {
        return null;
    }

}
