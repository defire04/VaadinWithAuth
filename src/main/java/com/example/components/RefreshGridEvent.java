package com.example.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;


public class RefreshGridEvent extends ComponentEvent<UserEditor> {
    public RefreshGridEvent(UserEditor source) {
        super(source, false);
    }
}