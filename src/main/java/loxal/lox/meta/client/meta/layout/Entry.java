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

import com.google.api.gwt.client.impl.ClientGoogleApiRequestTransport;
import com.google.api.gwt.client.impl.ClientOAuth2Login;
import com.google.api.gwt.services.plus.shared.Plus;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import loxal.lox.meta.client.meta.shared.PlusBase;

public class Entry extends PlusBase implements EntryPoint {
    interface Binder extends UiBinder<Widget, Entry> {
    }

    private Binder binder = GWT.create(Binder.class);

    @Override
    protected Plus createPlus() {
        return GWT.create(Plus.class);
    }

    @Override
    protected void println(String value) {
        RootPanel.get().add(new Label(value));
    }

    @Override
    public void onModuleLoad() {
        Widget app = binder.createAndBindUi(this);
//        Window.setTitle("TEST123");
//        RootLayoutPanel.get().add(app);

        final Button b = new Button("Authenticate to get public activities");
        b.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                login(new ClientOAuth2Login(CLIENT_ID), new ClientGoogleApiRequestTransport());
                b.setVisible(false);
            }
        });
        RootPanel.get().add(b);
    }
}
