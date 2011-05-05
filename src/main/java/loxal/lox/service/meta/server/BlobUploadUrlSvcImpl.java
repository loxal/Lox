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

import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import loxal.lox.service.meta.client.blobmgmt.BlobUploadUrlSvc;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class BlobUploadUrlSvcImpl extends RemoteServiceServlet implements BlobUploadUrlSvc {
    @Override
    public void doPost() {
//        final User user = UserServiceFactory.getUserService().getCurrentUser(); // TODO blobKey cannot be obtained yet due to a callback bug in GWT: http://code.google.com/p/google-web-toolkit/issues/detail?id=5242
//        BlobSvcImpl.assignUser(user.getUserId(), user.getEmail(), blobKey); // TODO blobKey cannot be obtained yet due to a callback bug in GWT: http://code.google.com/p/google-web-toolkit/issues/detail?id=5242
    }

    @Override
    public String getUploadUrl() {
        return BlobstoreServiceFactory.getBlobstoreService().createUploadUrl("/");
    }
}
