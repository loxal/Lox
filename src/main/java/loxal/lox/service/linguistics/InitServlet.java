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

package loxal.lox.service.linguistics;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Add some test data for our mapper.
 *
 * @author frew@google.com (Fred Wulff)
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 6342428666976155895L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        List<Entity> entities = new ArrayList<Entity>();
        for (int i = 0; i < 400; i++) {
            Entity entity = new Entity("PBFVotes");
            if (i % 2 == 0) {
                if (i % 4 == 0) {
                    entity.setProperty("skub", "Pro");
                } else {
                    entity.setProperty("skub", "Anti");
                }
            }
            entities.add(entity);
        }
        ds.put(entities);
    }
}