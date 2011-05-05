/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>
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

package loxal.lox.service.meta.client.tasksolver;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import loxal.lox.service.meta.client.blobmgmt.BlobSvc;
import loxal.lox.service.meta.client.blobmgmt.BlobSvcAsync;
import loxal.lox.service.meta.client.blobmgmt.BlobUploadUrlSvc;
import loxal.lox.service.meta.client.blobmgmt.BlobUploadUrlSvcAsync;
import loxal.lox.service.meta.client.dto.Blob;
import loxal.lox.service.meta.client.dto.Task;
import loxal.lox.service.meta.client.meta.layout.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Task UI logic
 */
public class TaskMgmt extends Composite {
    private final TaskSvcAsync taskSvcAsync = GWT.create(TaskSvc.class);
    private final BlobUploadUrlSvcAsync uploadServiceAsync = GWT.create(BlobUploadUrlSvc.class);
    private final BlobSvcAsync blobSvcAsync = GWT.create(BlobSvc.class);

    interface Binder extends UiBinder<TabLayoutPanel, TaskMgmt> {
    }

    @UiField
    TextBox name;
    @UiField
    TextArea description;
    @UiField
    Button createTask;
    @UiField
    TextBox category;
    @UiField
    TextBox priority;
    @UiField
    TabLayoutPanel tabPanel;
    @UiField
    FormPanel uploadForm;
    @UiField
    FileUpload fileUpload;
    @UiField
    CellTable<Task> taskPager;
    @UiField
    CellTable<Blob> blobPager;
    @UiField
    VerticalPanel control;
    @UiField
    MenuBar menu;
    @UiField
    MenuItem taskItem;
    @UiField
    MenuItem deleteTask;
    @UiField
    VerticalPanel processTaskPanel;
    @UiField
    MenuItem closeTask;
    @UiField
    TextBox nameUpdate;
    @UiField
    TextBox categoryUpdate;
    @UiField
    TextBox priorityUpdate;
    @UiField
    FormPanel uploadFormUpdate;
    @UiField
    FileUpload fileUploadUpdate;
    @UiField
    TextArea descriptionUpdate;
    @UiField
    Button updateTask;
    @UiField
    Label taskId;
    @UiField
    Button showAllTasks;
    @UiField
    MenuItem placeholder;

    public TaskMgmt() {
        final Binder binder = GWT.create(Binder.class);
        initWidget(binder.createAndBindUi(this));

        createTask.setAccessKey('C');
        updateTask.setAccessKey('U');
        tabPanel.selectTab(0);

        getTasks();
//        loadBlobs();
        buildUploader();

        closeTask.setCommand(new Command() {
            @Override
            public void execute() {
                tabPanel.selectTab(1);
            }
        });

        tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(
                    SelectionEvent<Integer> integerSelectionEvent) {
            }
        });

    }

    void setUploadFormAction() {
        uploadServiceAsync.getUploadUrl(new AsyncCallback<String>() {
            @Override
            public void onFailure(final Throwable caught) {
                GWT.log(caught.getMessage());
            }

            @Override
            public void onSuccess(final String result) {
                uploadForm.setAction(result);
            }
        });
    }

    void buildUploader() {
        setUploadFormAction();
        loadBlobs();

        uploadForm.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(final FormPanel.SubmitEvent event) {
                if (fileUpload.getFilename().length() != 0) {
                    // getUploadUrl could be just a dummy method that do not have any real functionality here,
                    // however any method must be called
                    uploadServiceAsync.doPost(new AsyncCallback<Void>() { // actually assign BlobKey (NULL only yet) to User

                        @Override
                        public void onFailure(final Throwable caught) {
                            GWT.log(caught.getMessage());
                        }

                        @Override
                        public void onSuccess(final Void result) {
                            loadBlobs();
                        }
                    });
                } else {
                    event.cancel();
                }
            }
        });

        uploadForm
                .addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
                    @Override
                    public void onSubmitComplete(
                            FormPanel.SubmitCompleteEvent event) {
                        setUploadFormAction();
                    }
                });
    }

    private void loadTask(final String taskId) {
        taskSvcAsync.getTask(taskId, new AsyncCallback<Task>() {
            @Override
            public void onFailure(final Throwable caught) {
            }

            @Override
            public void onSuccess(final Task task) {
                displayTask(task);
            }
        });
    }

    private void displayTask(final Task task) {
        nameUpdate.setText(task.getName());
        categoryUpdate.setText(task.getCategory());
        priorityUpdate.setText(String.valueOf(task.getPriority()));
        descriptionUpdate.setText(task.getDescription());
        taskId.setText(task.getId().toString());
    }

    private void getTasks() {
        taskSvcAsync.getTasks(new AsyncCallback<ArrayList<Task>>() {
            @Override
            public void onFailure(final Throwable caught) {
            }

            @Override
            public void onSuccess(final ArrayList<Task> tasks) {
                displayTasks(tasks);

                { // SuggestBox / Oracle
                    final MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
                    for (final Task task : tasks) {
                        oracle.add(task.getName());
                    }

                    tabPanel.remove(4);
                    final TextBox searchBox = new TextBox();
                    final SuggestBox search = new SuggestBox(oracle, searchBox);

                    searchBox.addFocusHandler(new FocusHandler() {
                        @Override
                        public void onFocus(FocusEvent focusEvent) {
                            search.setText("");
                        }
                    });

                    searchBox.addBlurHandler(new BlurHandler() {
                        @Override
                        public void onBlur(BlurEvent blurEvent) {
                            search.setText("Search For a Task Name");
                        }
                    });

                    search.setText("Search For a Task Name");
                    search.setWidth("12em");
                    search.setAccessKey('O');
                    search.setTitle("[Access Key: O]");
                    search.setFocus(true);

                    search.addValueChangeHandler(new ValueChangeHandler<String>() {
                        @Override
                        public void onValueChange(
                                final ValueChangeEvent<String> stringValueChangeEvent) {
                            taskSvcAsync.searchTasksWithName(
                                    stringValueChangeEvent.getValue(),
                                    new AsyncCallback<ArrayList<Task>>() {
                                        @Override
                                        public void onFailure(
                                                final Throwable caught) {
                                        }

                                        @Override
                                        public void onSuccess(
                                                final ArrayList<Task> tasks) {
                                            displayTasks(tasks);
                                            tabPanel.selectTab(1);
                                            showAllTasks.setVisible(true);
                                        }
                                    });
                        }
                    });

                    tabPanel.add(new HTML("<hr>"), search);
                }

            }
        });
    }

    private boolean initConstruction; // TODO re-engineer this HACK: make this var
    // unnecessary >> MAYBE this is a workaround
    // (pay attention to the GET!):
    // LISTadapter.getList().add("Item " + i);

    private void displayTasks(ArrayList<Task> tasks) {
        final ListDataProvider<Task> listDataProvider = new ListDataProvider<Task>();
        listDataProvider.addDataDisplay(taskPager);
//        final SelectionModel<Task> selectionModel = new SingleSelectionModel<Task>();
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
                    public String getValue(final Task object) {
                        return object.getId().toString();
                    }
                }, "ID");

        taskPager.addColumn(new TextColumn<Task>() {
                    @Override
                    public String getValue(final Task object) {
                        return object.getName();
                    }
                }, "Name");

        taskPager.addColumn(new TextColumn<Task>() {
                    @Override
                    public String getValue(final Task object) {
                        return object.getCategory();
                    }
                }, "Category");

        taskPager.addColumn(new TextColumn<Task>() {
                    @Override
                    public String getValue(final Task object) {
                        return String.valueOf(object.getPriority());
                    }
                }, "Priority");

        taskPager.addColumn(new TextColumn<Task>() {
                    @Override
                    public String getValue(final Task object) {
                        return object.getDescription();
                    }
                }, "Description");

        final Column<Task, String> edit = new Column<Task, String>(
                new ButtonCell()) {
            @Override
            public String getValue(Task object) {
                return "Edit";
            }
        };
        edit.setFieldUpdater(new FieldUpdater<Task, String>() {
            @Override
            public void update(final int index, final Task object,
                               final String value) {
                tabPanel.selectTab(3);
                loadTask(object.getId().toString());
                taskItem.setHTML("Task " + object.getId().toString());
                deleteTask.setCommand(new Command() {
                    @Override
                    public void execute() {
                        final ArrayList<String> taskIds = new ArrayList<String>();
                        taskIds.add(object.getId().toString());
                        deleteTasks(taskIds);
                        tabPanel.selectTab(1);
                    }
                });
            }
        });
        taskPager.addColumn(edit);

        final Column<Task, String> removeButton = new Column<Task, String>(
                new ButtonCell()) {
            @Override
            public String getValue(Task object) {
                return "X";
            }
        };
        removeButton.setFieldUpdater(new FieldUpdater<Task, String>() {
            @Override
            public void update(final int index, final Task object,
                               final String value) {
                final List<String> selectedTaskIds = new ArrayList<String>();
                selectedTaskIds.add(object.getId().toString());
                deleteTasks(selectedTaskIds);
                // deleteTasks(selectedTaskIds); // TODO native Longs caused
                // compilation errors in Scala implemented server-side class
            }
        });
        taskPager.addColumn(removeButton);

    }

    private Task declareTask() {
        final Task task = new Task();
        task.setName(name.getValue());
        task.setDescription(description.getValue());
        task.setCategory(category.getValue());
        if (priority.getValue().matches("\\d+"))
            task.setPriority(Integer.parseInt(priority.getValue()));

        return task;
    }

    private Task declareTaskUpdate() {
        final Task task = new Task();
        task.setName(nameUpdate.getValue());
        task.setDescription(descriptionUpdate.getValue());
        task.setCategory(categoryUpdate.getValue());
        if (priorityUpdate.getValue().matches("\\d+"))
            task.setPriority(Integer.parseInt(priorityUpdate.getValue()));
        task.setId(taskId.getText());

        return task;
    }

    private void putTask(final Task task) {
        taskSvcAsync.putTask(task, new AsyncCallback<Void>() {
            @Override
            public void onFailure(final Throwable caught) {
                Header.displayActionResult(
                        "Error Creating Task: " + caught.getMessage(), false);
            }

            @Override
            public void onSuccess(final Void ignore) {
                Header.displayActionResult("Task Created Successfully", true);
                getTasks();
            }
        });
    }

    private void deleteTasks(final List<String> selectedTaskIds) {
        final ArrayList<String> tasks = new ArrayList<String>(); // TODO
        // optimize
        // > don't
        // create
        // this
        // redundant
        // object,
        // use the
        // Task IDs
        // only
        for (final String taskId : selectedTaskIds) {
            tasks.add(taskId);
        }

        taskSvcAsync.deleteTasks(tasks, new AsyncCallback<Void>() {
            @Override
            public void onFailure(final Throwable caught) {
            }

            @Override
            public void onSuccess(final Void result) {
                getTasks();
            }
        });
    }

    private void deleteBlobs(final ArrayList<String> blobKeys) {
        blobSvcAsync.deleteBlobs(blobKeys, new AsyncCallback<Void>() {
            @Override
            public void onFailure(final Throwable caught) {
            }

            @Override
            public void onSuccess(final Void result) {
                loadBlobs();
            }
        });
    }

    private boolean initConstructionBlobs; // TODO re-engineer this HACK: make this var
    // unnecessary >> MAYBE this is a workaround
    // (pay attention to the GET!):
    // LISTadapter.getList().add("Item " + i);

    private void displayBlobs(final ArrayList<Blob> blobs) {
        final ListDataProvider<Blob> listDataProvider = new ListDataProvider<Blob>();
        listDataProvider.addDataDisplay(blobPager);
        final SelectionModel<Blob> selectionModel = new SingleSelectionModel<Blob>();
        blobPager.setSelectionModel(selectionModel);

        final ArrayList<Blob> blobDTOs = new ArrayList<Blob>();
        for (final Blob blob : blobs) {
            blobDTOs.add(blob);
        }
        listDataProvider.setList(blobDTOs);

        if (!initConstructionBlobs) {
            initTableColumnsOfBlobs();
            initConstructionBlobs = true;
        }
    }

    // TODO add delete button

    private void initTableColumnsOfBlobs() {
        blobPager.addColumn(new TextColumn<Blob>() {
                    @Override
                    public String getValue(final Blob object) {
                        return object.getContentType();
                    }
                }, "Content Type");

        blobPager.addColumn(new TextColumn<Blob>() {
                    @Override
                    public String getValue(final Blob object) {
                        Hyperlink hyperlink = new Hyperlink();
                        hyperlink.setText("my text");
                        Anchor anchor = new Anchor();
                        anchor.setText(object.getBlobKey());
                        anchor.setHref("/meta/serveBlob?blobKey=" + object.getBlobKey());
                        return "<a href='meta/serveBlob?blobKey=" + object.getBlobKey()
                                + "'>" + object.getFileName() + "</a>";
                    }
                }, "File Name");

        blobPager.addColumn(new TextColumn<Blob>() {
                    @Override
                    public String getValue(final Blob object) {
                        return object.getCreation().toString();
                    }
                }, "Date");

        blobPager.addColumn(new TextColumn<Blob>() {
                    @Override
                    public String getValue(final Blob object) {
                        return String.valueOf(object.getSize());
                    }
                }, "Size");

        final Column<Blob, String> removeButton = new Column<Blob, String>(
                new ButtonCell()) {
            @Override
            public String getValue(Blob object) {
                return "X";
            }
        };
        removeButton.setFieldUpdater(new FieldUpdater<Blob, String>() {
            @Override
            public void update(final int index, Blob object, String value) {
                final ArrayList<String> selectedBlobKeys = new ArrayList<String>();
                selectedBlobKeys.add(object.getBlobKey());
                deleteBlobs(selectedBlobKeys); // native Longs caused
                // compilation errors in Scala
                // implemented server-side class
            }
        });
        blobPager.addColumn(removeButton);
    }

    private void loadBlobs() {
        blobSvcAsync.getBlobs(new AsyncCallback<ArrayList<Blob>>() {
            @Override
            public void onFailure(final Throwable caught) {
                GWT.log(caught.getMessage());
            }

            @Override
            public void onSuccess(final ArrayList<Blob> result) {
                displayBlobs(result);
            }
        });
    }

    private void updateTask(final Task task) {
        taskSvcAsync.updateTask(task, new AsyncCallback<Void>() {
            @Override
            public void onFailure(final Throwable caught) {
                Header.displayActionResult(
                        "Error Updating Task: " + caught.getMessage(), false);
            }

            @Override
            public void onSuccess(final Void ignore) {
                Header.displayActionResult("Task Updated Successfully", true);
                getTasks();
            }
        });
    }

    @UiHandler("createTask")
    void addTask(final ClickEvent event) {
        uploadForm.submit();
        putTask(declareTask());
        name.setFocus(true);
        tabPanel.selectTab(1);
    }

    @UiHandler("updateTask")
    void addTaskUpdate(final ClickEvent event) {
        updateTask(declareTaskUpdate());
        tabPanel.selectTab(1);
    }

    @UiHandler("showAllTasks")
    void showAllTasks(final ClickEvent event) {
        showAllTasks.setVisible(false);
        getTasks();
    }
}
