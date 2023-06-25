package com.example.views.register;

import com.example.components.NavigationBar;
import com.example.components.RegistrationForm;
import com.example.data.entity.User;
import com.example.data.service.PasswordService;
import com.example.data.service.UserService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;


@Route(value = "register")
@Tag("register-view")
@PageTitle("Register")
public class RegisterView extends VerticalLayout {

    public RegisterView(UserService authService) {

        addClassName("register-view");
        logoutIfUserLogIn();

        RegistrationForm registrationForm = new RegistrationForm();
        add(registrationForm);


        RegistrationFormEditor registrationFormEditor = new RegistrationFormEditor(registrationForm, authService);
        registrationFormEditor.addBindingAndValidation();
    }

    public void logoutIfUserLogIn() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session.getAttribute(User.class) != null) {
            UI.getCurrent().getPage().setLocation("register");
            session.close();
        }
    }
}
