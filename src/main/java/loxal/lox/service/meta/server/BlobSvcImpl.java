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

package loxal.lox.service.meta.server;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import loxal.lox.service.meta.client.blobmgmt.BlobSvc;
import loxal.lox.service.meta.client.dto.Blob;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public final class BlobSvcImpl extends RemoteServiceServlet implements BlobSvc {
    private final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    private static final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
    private static final String assignedBlobKind = "AssignedBlob";

    @Override
    public ArrayList<Blob> getBlobs() {
        final List<String> userBlobKeys = getUserBlobKeys();

        final BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
        final Iterator<BlobInfo> blobInfos = blobInfoFactory.queryBlobInfos();
        final ArrayList<Blob> blobs = new ArrayList<Blob>();

        while (blobInfos.hasNext()) {
            final BlobInfo blobInfo = blobInfos.next();
            if (userBlobKeys.contains(blobInfo.getBlobKey().getKeyString())) {
                final Blob blob = new Blob();

                blob.setBlobKey(blobInfo.getBlobKey().getKeyString());
                blob.setFileName(blobInfo.getFilename());
                blob.setContentType(blobInfo.getContentType());
                blob.setCreation(blobInfo.getCreation());
                blob.setSize(blobInfo.getSize());

                blobs.add(blob);
            }
        }
        return blobs;
    }

    private List<String> getUserBlobKeys() {
        final String userId =
                UserServiceFactory.getUserService().getCurrentUser() == null ? null : UserServiceFactory.getUserService().getCurrentUser().getUserId();

        final List<String> blobKeys = new ArrayList<String>();
        final Query userBlobKeys = new Query(assignedBlobKind);
        userBlobKeys.addFilter("userId", Query.FilterOperator.EQUAL, userId);
        final Iterator<Entity> userBlobKeysEntities = datastoreService.prepare(userBlobKeys).asIterator();

        while (userBlobKeysEntities.hasNext()) {
            blobKeys.add(userBlobKeysEntities.next().getProperty("blobKey").toString());
        }

        return blobKeys;
    }

    public static void assignUser(final String userId, final String userEmail, final String blobKey) {
        final Entity assignedBlob = new Entity(assignedBlobKind);
        assignedBlob.setProperty("userId", userId);
        assignedBlob.setProperty("userEmail", userEmail);
        assignedBlob.setProperty("blobKey", blobKey);
        datastoreService.put(assignedBlob);
    }

    private void deleteBlobKeyAssigningEntity(final List<String> blobKeys) {
        final String userId =
                UserServiceFactory.getUserService().getCurrentUser() == null ? null : UserServiceFactory.getUserService().getCurrentUser().getUserId();

        final Query userBlobKeys = new Query(assignedBlobKind);
        userBlobKeys.addFilter("userId", Query.FilterOperator.EQUAL, userId);
        final Iterator<Entity> userBlobKeysEntities = datastoreService.prepare(userBlobKeys).asIterator();

        while (userBlobKeysEntities.hasNext()) {
            final Entity userBlobKey = userBlobKeysEntities.next();
            if (blobKeys.contains(userBlobKey.getProperty("blobKey").toString())) {
                datastoreService.delete(userBlobKey.getKey());
            }
        }
    }

    @Override
    public void deleteBlobs(final ArrayList<String> keys) {
        final List<String> userBlobKeys = getUserBlobKeys();
        final List<String> blobKeysToDelete = new ArrayList<String>();

        for (final String blobKey : keys) {
            if (userBlobKeys.contains(blobKey)) {
                blobstoreService.delete(new BlobKey(blobKey));
                blobKeysToDelete.add(blobKey);
            }
        }
        deleteBlobKeyAssigningEntity(blobKeysToDelete);
    }

}
