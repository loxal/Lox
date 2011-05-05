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

import org.bouncycastle.openpgp.PGPException;

import java.io.File;

/**
 * PGP Cryptography
 */
public interface PGPCrypto {

    public void setPublicKeyPath(String publicKeyPath);

    public void setPrivateKeyPath(String privateKeyPath);

    public void setPassword(String password);

    public File encryptFile(String plainData) throws PGPException;

    /**
     * Encrypt a file
     *
     * @param plainData file to encrypt
     * @param out       destination where the encrypted file should be stored (*.bpg
     *                  file suffix is recommended)
     * @throws PGPException
     */
    public void encryptFile(String plainData, String out)
            throws PGPException;

    /**
     * Decrypt a file
     *
     * @param in        the path of the encrypted file that have to be decrypted
     * @param plainData folder location to write the decrypted file (the filename is
     *                  encrypted in the file)
     * @throws PGPException
     */
    public File decryptFile(String in, String plainData)
			throws PGPException;

}
