package com.example.views.register;

import com.example.components.RegistrationForm;
import com.example.data.service.UserService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "register")
@Tag("register-view")
@PageTitle("Register")
public class RegisterView extends VerticalLayout {

    private final UserService authService;

    public RegisterView(UserService authService) {
        this.authService = authService;
        addClassName("register-view");

        RegistrationForm registrationForm = new RegistrationForm();
        add(registrationForm);


        RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm, authService);
        registrationFormBinder.addBindingAndValidation();


    }
}
