package com.sevenbits.servlets.requests_sniffer;

import com.sevenbits.servlets.dbconnector.DBConnector;
import com.sevenbits.servlets.validator.Validator;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class RequestsSniffer extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RequestsSniffer.class);

    private boolean isName;
    private boolean isSurname;
    private boolean isMail;
    private boolean isSuccess;

    private Map<String, Boolean> conditions;
    private Map<String, String> field;

    private DBConnector DBConnector;

    @Override
    public void init() {
        isName = false;
        isSurname = false;
        isMail = false;
        isSuccess = false;

        conditions = new HashMap<String, Boolean>();
        field = new HashMap<String, String>();

        DBConnector = new DBConnector();
        DBConnector.createDBUserTable();

        try {
            super.init();
        }
        catch(ServletException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean repeatRegistration(DBConnector DBConnector, String email) {
        if(DBConnector.selectFromDB(email).get("name") != null) {
            return true;
        }

        return false;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, java.io.IOException {
        Map<String, String[]> parameters = request.getParameterMap();

        field.put("name", parameters.get("first")[0]);
        field.put("surname", parameters.get("last")[0]);
        field.put("mail", parameters.get("email")[0]);

        Validator validator = new Validator(field.get("name"),
                field.get("surname"), field.get("mail"));

        isName = validator.isNameValid();
        isSurname = validator.isSurnameValid();
        isMail = validator.isEmailValid();
        isSuccess = (isName && isSurname && isMail &&
                request.getParameter("rad") != null);

        if(isSuccess) {
            if(!repeatRegistration(DBConnector, field.get("mail"))) {
                DBConnector.insertIntoDB(field);
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

        field.put("comments", parameters.get("comments")[0]);

        if(request.getParameter("rad") != null &&
            request.getParameter("rad").equals("photo")) {
            field.put("radio", "photo");
        }
        else if(request.getParameter("rad") != null &&
                request.getParameter("rad").equals("video")){
            field.put("radio", "video");
        }
        else {
            field.put("radio", "fail");
        }

        if(request.getParameter("our") != null &&
                request.getParameter("our").equals("on")) {
            field.put("check", "on");
        }
        else {
            field.put("check", "off");
        }

        if(isSuccess && !conditions.get("repeatRegistered")) {
            field.put("name", "");
            field.put("surname", "");
            field.put("mail", "");
            field.put("comments", "");
        }

        request.getSession().setAttribute("field", field);
        request.getSession().setAttribute("condition", conditions);

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException{
        doPost(request, response);
    }

}
