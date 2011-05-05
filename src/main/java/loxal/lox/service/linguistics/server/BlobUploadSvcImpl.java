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

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import loxal.lox.service.meta.server.BlobSvcImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class BlobUploadSvcImpl extends HttpServlet {
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final Map<String, BlobKey> blobs = BlobstoreServiceFactory.getBlobstoreService().getUploadedBlobs(request);

        final User user = UserServiceFactory.getUserService().getCurrentUser();
        BlobSvcImpl.assignUser(user == null ? null : user.getUserId(), user == null ? null : user.getEmail(), blobs.get("file").getKeyString());
        response.sendRedirect("/linguistics/upload.jsp");
    }

}