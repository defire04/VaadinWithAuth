package com.example.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;

public class LoginFormMy extends FormLayout {
    private LoginForm loginForm;
    private VerticalLayout verticalLayout;
    private HorizontalLayout horizontalLayout;
    private Button register;
    private Div loginContainer;
    public LoginFormMy() {
        loginForm = new LoginForm();
        verticalLayout = new VerticalLayout();
        horizontalLayout = new HorizontalLayout();

        register = new Button("Register", event -> {
            UI.getCurrent().navigate("register");
        });

        horizontalLayout.add(register);
        verticalLayout.add(loginForm, horizontalLayout);

        loginContainer = new Div(loginForm, horizontalLayout);
        loginContainer.addClassName("login-container");

        setStyles();
        add(loginContainer);
    }
    public void setStyles() {
        loginForm.getStyle().clear()
                .setDisplay(Style.Display.FLEX)
                .setWidth("100%")
                .setHeight("100%")
                .set("border-radius", "50px")
                .set("justify-content", "center")
                ;

        register.getStyle()
                .setDisplay(Style.Display.FLEX)
                .setWidth("100%")
                .set("justify-content", "center")
        ;
    }
    public LoginForm getLoginForm() {
        return loginForm;
    }
    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }
    public VerticalLayout getVerticalLayout() {
        return verticalLayout;
    }
    public void setVerticalLayout(VerticalLayout verticalLayout) {
        this.verticalLayout = verticalLayout;
    }
    public HorizontalLayout getHorizontalLayout() {
        return horizontalLayout;
    }
    public void setHorizontalLayout(HorizontalLayout horizontalLayout) {
        this.horizontalLayout = horizontalLayout;
    }
    public Button getRegister() {
        return register;
    }
    public void setRegister(Button register) {
        this.register = register;
    }
    public Div getLoginContainer() {
        return loginContainer;
    }
    public void setLoginContainer(Div loginContainer) {
        this.loginContainer = loginContainer;
    }
}
