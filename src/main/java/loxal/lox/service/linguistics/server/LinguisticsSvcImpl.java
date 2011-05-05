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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import loxal.lox.service.linguistics.client.LinguisticsSvc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LinguisticsSvcImpl extends RemoteServiceServlet implements LinguisticsSvc {
//    public void doGet111(HttpServletRequest req, HttpServletResponse resp) {
//        final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
//        final List<Entity> entities = new ArrayList<Entity>();
//
//        for (int i = 0; i < 400; i++) {
//            Entity entity = new Entity("Words1");
//            if (i % 2 == 0) {
//                if (i % 4 == 0) {
//                    entity.setProperty("skub", "Pro");
//                } else {
//                    entity.setProperty("skub", "Anti");
//                }
//            }
//            entities.add(entity);
//        }
//        datastoreService.put(entities);
//    }


    @Override
    public void putWordsToDatastore(final String text) {
        final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        final List<Entity> entities = new ArrayList<Entity>();


        System.out.println("öhmmmm = " + text);
        final Pattern pattern = Pattern.compile(".*(test)*");
        final Matcher matcher = pattern.matcher(text);

        System.out.println("text = " + text);
        System.out.println("text = " + matcher.groupCount());
//        System.out.println("text = " + matcher.group());
//        System.out.println("text = " + matcher.group(0));
//        System.out.println("text = " + matcher.group(1));

    }

    @Override
    public long countWords(final String text) {
        return text.split("\\s").length;
    }

    @Override
    public double priceEstimation(final String text) {
        final long wordCount = countWords(text);
        final double translationCostPerWord = .05;

        return wordCount * translationCostPerWord;
    }

}
