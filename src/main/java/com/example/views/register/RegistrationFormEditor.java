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

public class RegistrationFormEditor {

    private final UserService userService;
    private RegistrationForm registrationForm;
    private boolean enablePasswordValidation;
    private String confirmEmailPassword;



    public RegistrationFormEditor(RegistrationForm registrationForm, UserService userService) {
        this.registrationForm = registrationForm;
        this.userService = userService;

    }

    public void addBindingAndValidation() {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
        binder.bindInstanceFields(registrationForm);

        binder.forField(registrationForm.getPasswordPasswordField())
                .withValidator(this::passwordValidator).bind("password");

        registrationForm.getPasswordConfirm().addValueChangeListener(e -> {
            enablePasswordValidation = true;
            binder.validate();
        });

        binder.setStatusLabel(registrationForm.getErrorMessageField());

        registrationForm.getRegister().addClickListener(event -> {
            User userBean = new User();
            try {
                binder.writeBean(userBean);
                confirmEmailPassword = userService.generateConfirmPasswordAndSendByEmail(userBean);
                registrationForm.getConfirmationDialogForm().open();


            } catch (ValidationException getMessage) {
                Notification.show("All fields must be filled").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }

        });

        registrationForm.getConfirmationDialogForm().getSave().addClickListener(event -> {
            try {
                User userBean = new User();
                binder.writeBean(userBean);

                if(confirmEmailPassword.equals(registrationForm.getConfirmationDialogForm().getConfirmPassword().getValue())){
                    userService.register(new User(userBean.getUsername(), userBean.getPassword(),
                            userBean.getName(), userBean.getEmail()));
                    showSuccess(userBean);
                    registrationForm.getConfirmationDialogForm().close();
                } else{
                    Notification.show("Confirm password does not match!").addThemeVariants(NotificationVariant.LUMO_ERROR);
                }

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
                Notification.show(" Profile created successfully");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        UI.getCurrent().navigate("home");
    }
}
