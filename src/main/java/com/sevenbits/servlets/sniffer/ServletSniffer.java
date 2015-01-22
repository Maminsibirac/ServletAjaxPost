package com.sevenbits.servlets.sniffer;

import com.sevenbits.servlets.validator.Validator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ServletSniffer extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ServletSniffer.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, java.io.IOException {
        logger.info(getClass().getName());
        Map<String, String[]> parameters = request.getParameterMap();
        Validator validator = new Validator(parameters.get("first")[0],
                parameters.get("last")[0], parameters.get("email")[0]);
        Map<String, Boolean> conditions = new HashMap<String, Boolean>();
        Map<String, String> field = new HashMap<String, String>();

        boolean isName = validator.isNameValid();
        boolean isSurname = validator.isSurnameValid();
        boolean isMail = validator.isEmailValid();

        conditions.put("success", (isName && isSurname && isMail));
        conditions.put("first", isName);
        conditions.put("last", isSurname);
        conditions.put("email", isMail);

        field.put("name", parameters.get("first")[0]);
        field.put("surname", parameters.get("last")[0]);
        field.put("mail", parameters.get("email")[0]);
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
