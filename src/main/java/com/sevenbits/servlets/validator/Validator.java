package com.sevenbits.servlets.validator;

import org.apache.log4j.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private String first;
    private String last;
    private String email;
    private Pattern pattern;
    private Matcher matcher;
    private final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Logger logger = Logger.getLogger(Validator.class);

    public Validator(String first, String last, String email) {
        logger.info(getClass().getName());
        pattern = Pattern.compile(EMAIL_PATTERN);
        this.first = first;
        this.last = last;
        this.email = email;
    }

    public boolean isEmailValid() {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isNameValid() {
        if(first.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean isSurnameValid() {
        if(last.isEmpty()) {
            return false;
        }
        return true;
    }

}
