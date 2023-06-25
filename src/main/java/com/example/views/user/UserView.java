package com.example.views.user;


import com.example.data.entity.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;



public class UserView extends VerticalLayout {
    public UserView(){
        Button edit = new Button("Edit profile", e ->{

            User u = UI.getCurrent().getSession().getAttribute(User.class);
            UI.getCurrent().getPage().setLocation("edit");
        });


        add(edit);
    }
}
