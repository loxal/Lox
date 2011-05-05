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

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import loxal.lox.service.meta.client.dto.Task;
import loxal.lox.service.meta.client.tasksolver.TaskSvc;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class TaskSvcImpl extends RemoteServiceServlet implements TaskSvc {
    private static final long serialVersionUID = -7668467017948551535L;
    private final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
    private final static String entityName = "Task";

    @Override
    public void putTask(final Task task) {
        final Entity entity = new Entity(entityName);
        entity.setProperty("name", task.getName());
        entity.setProperty("category", task.getCategory());
        entity.setProperty("description", task.getDescription());
        entity.setProperty("priority", task.getPriority());
        final User user = UserServiceFactory.getUserService().getCurrentUser();
        if (user != null) entity.setProperty("userEmail", user.getEmail());
        else entity.setProperty("userEmail", null); // null must be set explicitly

        datastoreService.put(entity);
    }

    @Override
    public void deleteTasks(final ArrayList<String> entityKeys) { // TODO check if these tasks actually belong to the user
        final ArrayList<Key> keys = new ArrayList<Key>();

        for (String key : entityKeys) {
            keys.add(KeyFactory.createKey(entityName, Long.parseLong(key)));
        }

        datastoreService.delete(keys);
    }

    @Override
    public void updateTask(final Task task) { // TODO check if this task actually belongs to the user
        final Entity taskEntity;
        try {
            taskEntity = datastoreService.get(KeyFactory.createKey(entityName, task.getId()));

            taskEntity.setProperty("name", task.getName());
            taskEntity.setProperty("category", task.getCategory());
            taskEntity.setProperty("description", task.getDescription());
            taskEntity.setProperty("priority", task.getPriority());

            datastoreService.put(taskEntity);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Task> searchTasksWithName(final String taskName) {
        final ArrayList<Task> tasks = new ArrayList<Task>();

        final Query taskQuery = new Query(entityName);
        final String userEmail =
                UserServiceFactory.getUserService().getCurrentUser() == null ? null : UserServiceFactory.getUserService().getCurrentUser().getEmail();

        taskQuery.addFilter("userEmail", Query.FilterOperator.EQUAL, userEmail);
        taskQuery.addFilter("name", Query.FilterOperator.EQUAL, taskName);
        final Iterator<Entity> taskQueryResult = datastoreService.prepare(taskQuery).asIterator();
        while (taskQueryResult.hasNext()) {
            final Entity taskEntity = taskQueryResult.next();
            final Task task = new Task();

            task.setName(String.valueOf(taskEntity.getProperty("name")));
            task.setCategory(String.valueOf(taskEntity.getProperty("category")));
            task.setDescription(String.valueOf(taskEntity.getProperty("description")));
            task.setPriority(Integer.parseInt(taskEntity.getProperty("priority").toString()));
            task.setUserEmail(String.valueOf(taskEntity.getProperty("userEmail")));
            task.setId(taskEntity.getKey().getId());

            tasks.add(task);
        }
        return tasks;
    }

    @Override
    public Task getTask(final String taskId) {
        try {
            final Entity taskEntity = datastoreService.get(KeyFactory.createKey(entityName, Long.parseLong(taskId)));

            final Task task = new Task();
            final String userEmail = UserServiceFactory.getUserService().getCurrentUser() == null ? null : UserServiceFactory.getUserService().getCurrentUser().getEmail();
            if (taskEntity.getProperty("userEmail") == null || taskEntity.getProperty("userEmail").equals(userEmail)) { // check for NPE first 
                task.setUserEmail(String.valueOf(taskEntity.getProperty("userEmail")));
                task.setName(String.valueOf(taskEntity.getProperty("name")));
                task.setCategory(String.valueOf(taskEntity.getProperty("category")));
                task.setDescription(String.valueOf(taskEntity.getProperty("description")));
                task.setPriority(Integer.parseInt(taskEntity.getProperty("priority").toString()));
                task.setId(taskEntity.getKey().getId());
            }

            return task;
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Task> getTasks() {
        final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        final ArrayList<Task> tasks = new ArrayList<Task>();
        final Query taskQuery = new Query(entityName);

        final String userEmail = UserServiceFactory.getUserService().getCurrentUser() == null ? null : UserServiceFactory.getUserService().getCurrentUser().getEmail();
        taskQuery.addFilter("userEmail", Query.FilterOperator.EQUAL, userEmail); // to assure that only user-owned entities are fetched
        final Iterator<Entity> taskQueryResult = datastoreService.prepare(taskQuery).asIterator();
        while (taskQueryResult.hasNext()) {
            final Entity taskEntity = taskQueryResult.next();

            final Task task = new Task();
            task.setName(String.valueOf(taskEntity.getProperty("name")));
            task.setCategory(String.valueOf(taskEntity.getProperty("category")));
            task.setDescription(String.valueOf(taskEntity.getProperty("description")));
            task.setPriority(Integer.parseInt(taskEntity.getProperty("priority").toString()));
            task.setUserEmail(String.valueOf(taskEntity.getProperty("userEmail")));
            task.setId(taskEntity.getKey().getId());

            tasks.add(task);
        }

        return tasks;
    }

    public void checkLoggedIn() throws NotLoggedInException {
        if (UserServiceFactory.getUserService().getCurrentUser() == null) {
            throw new NotLoggedInException();
        }
    }
}
