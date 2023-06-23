package com.example.views;

import com.example.components.LoginFormMy;
import com.example.data.entity.User;
import com.example.data.service.UserService;
import com.example.exeption.LoginFormException;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;

@Route(value = "login")
@Tag("login-view")
@PageTitle("Login")
public class LoginView extends Div {

    public LoginView(UserService authService) {
        addClassName("login-view");

        UI.getCurrent().getElement().getThemeList().add("dark");

        logoutIfUserLogIn();

        LoginFormMy loginFormMy = new LoginFormMy();
        LoginForm loginForm = loginFormMy.getLoginForm();

        setSizeFull();
        getStyle().setDisplay(Style.Display.FLEX)
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("min-height", "100vh")
        ;

        loginForm.addLoginListener(event -> {
            try {
                authService.login(event.getUsername(), event.getPassword());
                UI.getCurrent().navigate("home");
            } catch (LoginFormException e) {
                loginForm.setError(true);
//                Notification.show(e.getMessage());
            }
        });
        add(loginFormMy);
    }


    public void logoutIfUserLogIn() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session.getAttribute(User.class) != null) {
            UI.getCurrent().getPage().setLocation("login");
            session.close();
        }
    }
}