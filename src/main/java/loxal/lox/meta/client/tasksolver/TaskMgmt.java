/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>
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

package loxal.lox.meta.client.tasksolver;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import loxal.lox.meta.client.dto.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskMgmt extends Composite {
    private TaskSvcAsync taskSvcAsync = GWT.create(TaskSvc.class);

    interface Binder extends UiBinder<Widget, TaskMgmt> {
    }

    @UiField
    CellTable<Task> taskPager;
    @UiField
    VerticalPanel control;
    @UiField
    Button showAllTasks;
    @UiField
    PageSizePager taskPageSizePager;
    @UiField
    SimplePager taskSimplePager;

    public TaskMgmt() {
        Binder binder = GWT.create(Binder.class);
        initWidget(binder.createAndBindUi(this));

        getTasks();

    }

    private void getTasks() {
        taskSvcAsync.getTasks(new AsyncCallback<ArrayList<Task>>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(ArrayList<Task> tasks) {
                displayTasks(tasks);
            }
        });
    }

    private boolean initConstruction; // TODO re-engineer this HACK: make this var

    private void displayTasks(ArrayList<Task> tasks) {
        ListDataProvider<Task> listDataProvider = new ListDataProvider<Task>();
        listDataProvider.addDataDisplay(taskPager);
        taskPageSizePager.setDisplay(taskPager);
        taskSimplePager.setDisplay(taskPager);
        SelectionModel<Task> selectionModel = new MultiSelectionModel<Task>(); // TODO not yet working (because the API isn't ready?)
        taskPager.setSelectionModel(selectionModel);

        ArrayList<Task> taskDTOs = new ArrayList<Task>();
        for (Task task : tasks) {
            taskDTOs.add(task);
        }
        listDataProvider.setList(taskDTOs);

        if (!initConstruction) {
            initTableColumnsOfTasks();
            initConstruction = true;
        }
    }

    private void initTableColumnsOfTasks() {
        taskPager.addColumn(new TextColumn<Task>() {
            @Override
            public String getValue(Task object) {
                return object.getId();
            }
        }, "ID");

        taskPager.addColumn(new TextColumn<Task>() {
            @Override
            public String getValue(Task object) {
                return object.getName();
            }
        }, "Name");

        taskPager.addColumn(new TextColumn<Task>() {
            @Override
            public String getValue(Task object) {
                return object.getCategory();
            }
        }, "Category");

        taskPager.addColumn(new TextColumn<Task>() {
            @Override
            public String getValue(Task object) {
                return String.valueOf(object.getPriority());
            }
        }, "Priority");

        taskPager.addColumn(new TextColumn<Task>() {
            @Override
            public String getValue(Task object) {
                return object.getDescription();
            }
        }, "Description");

        Column<Task, String> edit = new Column<Task, String>(
                new ButtonCell()) {
            @Override
            public String getValue(Task object) {
                return "Edit";
            }
        };
        taskPager.addColumn(edit);

        Column<Task, String> removeButton = new Column<Task, String>(
                new ButtonCell()) {
            @Override
            public String getValue(Task object) {
                return "X";
            }
        };
        removeButton.setFieldUpdater(new FieldUpdater<Task, String>() {
            @Override
            public void update(int index, Task object,
                               String value) {
                List<String> selectedTaskIds = new ArrayList<String>();
                selectedTaskIds.add(object.getId());
                deleteTasks(selectedTaskIds);
                // deleteTasks(selectedTaskIds); // TODO native Longs caused
                // compilation errors in Scala implemented server-side class
            }
        });
        taskPager.addColumn(removeButton);

    }

    private void deleteTasks(List<String> selectedTaskIds) {
        ArrayList<String> tasks = new ArrayList<String>(); // TODO
        // optimize > don't create this redundant object, use the Task IDs only
        for (String taskId : selectedTaskIds) {
            tasks.add(taskId);
        }

        taskSvcAsync.deleteTasks(tasks, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(Void result) {
                getTasks();
            }
        });
    }

    @UiHandler("showAllTasks")
    void showAllTasks(ClickEvent event) {
        showAllTasks.setVisible(false);
        getTasks();
    }
}
