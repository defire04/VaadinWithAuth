package com.example.views;

import com.example.components.RegistrationForm;
import com.example.components.RegistrationFormBinder;
import com.example.data.entity.User;
import com.example.data.service.UserService;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


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

//        registrationForm.getRegister().addClickListener(event -> {
//            try {
//                authService.register(new User(registrationForm.getUserForm().getUsername(), registrationForm.getPassword(),
//                        registrationForm.getUserForm().getName(), registrationForm.getUserForm().getEmail()));
//                UI.getCurrent().navigate("home");
//            } catch (RuntimeException e) {
//                Notification.show(e.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
//            }
//        });




//        add(registrationForm);

    }
}
