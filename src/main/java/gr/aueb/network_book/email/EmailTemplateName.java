package gr.aueb.network_book.email;


/**
 * used to represent different email template names,
 * with the only defined value being ACTIVATION_ACCOUNT
 */

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account");
    //PASSWORD_RESET("password_reset"),
    //WELCOME_EMAIL("welcome_email");


    private final String name;

    EmailTemplateName(String name){
        this.name = name;
    }
}
