package com.sevenbits.servlets.validator;

import com.sevenbits.servlets.dbconnector.DBConnector;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private Map<String, String> fields;
    private Map<String, Boolean> conditions;

    private boolean isSuccess;
    private DBConnector DBConnector;

    private Pattern pattern;
    private Matcher matcher;

    private boolean isName;
    private boolean isSurname;
    private boolean isMail;

    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Logger logger = Logger.getLogger(Validator.class);

    public Validator() {
        pattern = Pattern.compile(EMAIL_PATTERN);

        fields = new HashMap<String, String>();
        conditions = new HashMap<String, Boolean>();

        isSuccess = false;
        isName = false;
        isSurname = false;
        isMail = false;

        //Validator shouldn't collaborate with DB! Use service.
        DBConnector = new DBConnector();
        //It's not a responsibility of validator. Ever.
        DBConnector.createDBUserTable();
    }

    public boolean isEmailValid() {
        matcher = pattern.matcher(fields.get("mail"));
        return matcher.matches();
    }

    public boolean isNameValid() {
        if(fields.get("name").isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean isSurnameValid() {
        if(fields.get("surname").isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean repeatRegistration(DBConnector DBConnector, String email) {
        if(DBConnector.selectFromDB(email).get("name") != null) {
            return true;
        }

        return false;
    }

    public Map<String, Boolean> getConditions() {
        return conditions;
    }

    public Map<String, String> validValueField(Map<String, String[]> parameters,
                                                     String radio, String checkbox) {
        fields.put("name", parameters.get("first")[0]);
        fields.put("surname", parameters.get("last")[0]);
        fields.put("mail", parameters.get("email")[0]);
        fields.put("comments", parameters.get("comments")[0]);

        isName = isNameValid();
        isSurname = isSurnameValid();
        isMail = isEmailValid();

        isSuccess = (isName && isSurname && isMail &&
                radio != null);

        if(isSuccess) {
            if(!repeatRegistration(DBConnector, fields.get("mail"))) {
                DBConnector.insertIntoDB(fields);
                conditions.put("repeatRegistered", false);
            }
            else {
                conditions.put("repeatRegistered", true);
            }
        }
        else  {
            conditions.put("repeatRegistered", false);
        }

        conditions.put("success", isSuccess);
        conditions.put("first", isName);
        conditions.put("last", isSurname);
        conditions.put("email", isMail);

        if(radio != null && radio.equals("photo")) {
            fields.put("radio", "photo");
        }
        else if(radio != null && radio.equals("video")){
            fields.put("radio", "video");
        } else {
            fields.put("radio", "fail");
        }

        if(checkbox != null && checkbox.equals("on")) {
            fields.put("check", "on");
        }
        else {
            fields.put("check", "off");
        }

        if(isSuccess && !conditions.get("repeatRegistered")) {
            fields.put("name", "");
            fields.put("surname", "");
            fields.put("mail", "");
            fields.put("comments", "");
        }

        return fields;
    }
}
