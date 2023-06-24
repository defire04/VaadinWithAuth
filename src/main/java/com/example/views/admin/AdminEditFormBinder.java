package com.example.views.admin;

import com.example.components.AdminEditForm;
import com.example.data.entity.User;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;


public class AdminEditFormBinder {
    private final AdminEditForm adminEditForm;

    public AdminEditFormBinder(AdminEditForm adminEditForm) {
        this.adminEditForm = adminEditForm;

    }

    public void addBindingAndValidation() {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
        binder.bindInstanceFields(adminEditForm.getUserForm());

        binder.setStatusLabel(adminEditForm.getErrorMessageField());

        adminEditForm.getSave().addClickListener(event -> {
            try {
                User userBean = new User();
                binder.writeBean(userBean);

                showSuccess();
            } catch (ValidationException exception) {
                Notification.show(exception.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }
    private void showSuccess() {
        Notification notification =
                Notification.show("Data saved");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

    }
}
