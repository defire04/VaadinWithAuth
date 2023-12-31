package com.example.views.login;

import com.example.components.LoginFormMy;
import com.example.components.NavigationBar;
import com.example.components.NotificationWarning;
import com.example.data.entity.User;
import com.example.data.service.UserService;
import com.example.data.exeption.LoginFormException;
import com.example.data.exeption.UserNotFoundException;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(value = "login")
@Tag("login-view")
@PageTitle("Login")
public class LoginView extends Div {

    public LoginView(UserService authService) {

        addClassName("login-view");
        logoutIfUserLogIn();

        LoginFormMy loginFormMy = new LoginFormMy();
        LoginForm loginForm = loginFormMy.getLoginForm();

        setStyles();
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.addLoginListener(event -> {
            try {
                authService.login(event.getUsername(), event.getPassword());
                UI.getCurrent().navigate("home");
            } catch (LoginFormException e) {
                loginForm.setError(true);
                loginForm.setForgotPasswordButtonVisible(true);
//                Notification.show(e.getMessage());
            }
            loginForm.addForgotPasswordListener(forgotPasswordEvent -> {
                try {
                    authService.sendTempPassword(event.getUsername());
                    Div text = new Div(
                            new Text("We have sent a temporary password to your email."),
                            new HtmlComponent("br"),
                            new Text("You can login using this password")
                    );
                    new NotificationWarning(text).show();

                } catch (UserNotFoundException userNotFoundException) {
                    Notification.show(userNotFoundException.getMessage())
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            });
        });
        add(loginFormMy);
    }

    private void setStyles(){
        UI.getCurrent().getElement().getThemeList().add("dark");
        setSizeFull();
        getStyle().setDisplay(Style.Display.FLEX)
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("min-height", "100vh")
        ;
    }

    public void logoutIfUserLogIn() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session.getAttribute(User.class) != null) {
            UI.getCurrent().getPage().setLocation("login");
            session.close();
        }
    }
}