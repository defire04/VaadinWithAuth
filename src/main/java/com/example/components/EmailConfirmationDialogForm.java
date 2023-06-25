package com.example.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.PasswordField;

public class EmailConfirmationDialogForm extends Dialog {

    private Button save;
    private Button cancel;
    private Paragraph emailParagraph;
    private PasswordField confirmPassword;

    public EmailConfirmationDialogForm(){

        setHeaderTitle("Email confirmation");
        emailParagraph = new Paragraph();
        save = new Button("Save", VaadinIcon.CHECK.create());
        cancel = new Button("Cancel", event -> close());
        confirmPassword = new PasswordField();


        add(emailParagraph, confirmPassword, save, cancel);
    }

    public Button getSave() {
        return save;
    }

    public void setSave(Button save) {
        this.save = save;
    }

    public Button getCancel() {
        return cancel;
    }

    public void setCancel(Button cancel) {
        this.cancel = cancel;
    }

    public Paragraph getEmailParagraph() {
        return emailParagraph;
    }

    public void setEmailParagraph(Paragraph emailParagraph) {
        this.emailParagraph = emailParagraph;
    }

    public PasswordField getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(PasswordField confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
