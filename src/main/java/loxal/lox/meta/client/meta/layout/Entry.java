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

package loxal.lox.meta.client.meta.layout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import loxal.lox.meta.client.tasksolver.TaskMgmt;

public class Entry implements EntryPoint {
    interface Binder extends UiBinder<Widget, Entry> {
    }

    private Binder binder = GWT.create(Binder.class);
    private I18n i18n = GWT.create(I18n.class);

    @UiField
    TaskMgmt taskMgmt;

    @Override
    public void onModuleLoad() {
        Widget app = binder.createAndBindUi(this);
        RootLayoutPanel.get().add(app);
        Window.setTitle(i18n.appTitle());
    }
}
