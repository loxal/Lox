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

package loxal.lox.service.linguistics.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class Linguistics implements EntryPoint {
    interface Binder extends UiBinder<DockLayoutPanel, Linguistics> {
    }

    @UiField
    TabLayoutPanel tabBar;
    @UiField
    TextArea text;
    @UiField
    Label textCharNumber;
    @UiField
    Label textContent;
    @UiField
    Button estimatePrice;
    @UiField
    Button cloudProcess;
    @UiField
    Frame upload;
    @UiField
    RichTextArea text1;

    private final Binder binder = GWT.create(Binder.class);
    private final LinguisticsSvcAsync linguisticsSvcAsync = GWT.create(LinguisticsSvc.class);

//	TODO all linguistics-related libs from Google

    @Override
    public void onModuleLoad() {
        final DockLayoutPanel app = binder.createAndBindUi(this);
        RootLayoutPanel.get().add(app);
        cloudProcess.setAccessKey('P');
        estimatePrice.setAccessKey('D');

        text1.setText("blub");
        RichTextArea.Formatter formatter = text1.getFormatter();
//        formatter.insertHorizontalRule();
//        formatter.insertOrderedList();
//        formatter.insertUnorderedList();
//        formatter.toggleUnderline();


        text.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(final KeyPressEvent event) {
                textCharNumber.setText(String.valueOf(text.getCursorPos()));
            }
        });

    }

    public String test(final String test) {
        return test + "blub";
    }

    @UiHandler("estimatePrice")
    void estimatePrice(final ClickEvent event) {
        linguisticsSvcAsync.priceEstimation(text.getText(), new AsyncCallback<Double>() {
            @Override
            public void onFailure(final Throwable throwable) {
                GWT.log(throwable.getMessage());
            }

            @Override
            public void onSuccess(final Double aDouble) {
                System.out.println("aDouble = " + aDouble);
            }
        });
    }

    @UiHandler("cloudProcess")
    void cloudProcess(final ClickEvent event) {
        linguisticsSvcAsync.putWordsToDatastore(text.getText(), new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable throwable) {
            }

            @Override
            public void onSuccess(Void aVoid) {
            }
        });

    }

}
