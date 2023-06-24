package com.example.components;

import com.vaadin.flow.component.html.Div;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

public class UserForm extends Div {
    private TextField username;
    private TextField name;
    private EmailField email;


    public UserForm() {
        addClassName("user-form");
        username = new TextField("Username");
        name = new TextField("Name");
        email = new EmailField("Email");
//        verticalLayout= new VerticalLayout(username, name, email);
        add(username, name, email);
    }

    public TextField getUsernameTextField() {
        return username;
    }

    public TextField getNameTextField() {
        return name;
    }

    public EmailField getEmailTextField() {
        return email;
    }

    public String getUsername() {
        return username.getValue();
    }

    public String getName() {
        return name.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }



}
