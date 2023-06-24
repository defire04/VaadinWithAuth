package com.example.views.register;

import com.example.components.RegistrationForm;
import com.example.data.entity.User;
import com.example.data.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;

public class RegistrationFormBinder {

    private final RegistrationForm registrationForm;

    private final UserService authService;
    private boolean enablePasswordValidation;

    public RegistrationFormBinder(RegistrationForm registrationForm, UserService authService) {
        this.registrationForm = registrationForm;
        this.authService = authService;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
        binder.bindInstanceFields(registrationForm.getUserForm());

        binder.forField(registrationForm.getPasswordPasswordField())
                .withValidator(this::passwordValidator).bind("password");

        registrationForm.getPasswordConfirm().addValueChangeListener(e -> {
            enablePasswordValidation = true;
            binder.validate();
        });

        binder.setStatusLabel(registrationForm.getErrorMessageField());

        registrationForm.getRegister().addClickListener(event -> {
            try {
                User userBean = new User();
                binder.writeBean(userBean);

                authService.register(new User(userBean.getUsername(), userBean.getPassword(),
                        userBean.getName(), userBean.getEmail()));

                showSuccess(userBean);
            } catch (ValidationException exception) {
                Notification.show(exception.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }

    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {

        if (!enablePasswordValidation) {

            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = registrationForm.getPasswordConfirm().getValue();

        if (pass1 != null && pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }

    private void showSuccess(User userBean) {
        Notification notification =
                Notification.show("Data saved, welcome " + userBean.getName());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        UI.getCurrent().navigate("home");
    }
}
