package com.sevenbits.servlets.requests_sniffer;

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

    private Map<String, Boolean> conditions;
    private Map<String, String> fields;
    private Map<String, String[]> parameters;

    private Validator validator;

    @Override
    public void init() {
        conditions = null;
        fields = null;
        parameters = null;

        validator = new Validator();

        try {
            super.init();
        }
        catch(ServletException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, java.io.IOException {
        parameters = request.getParameterMap();

        fields = validator.validValueField(parameters, request.getParameter("rad"),
                request.getParameter("our"));
        conditions = validator.getConditions();
        logger.info("Radio: " + fields.get("radio"));
        logger.info("Checkbox: " + fields.get("check"));
        logger.info("Repeat: " + conditions.get("repeatRegistered"));

        request.getSession().setAttribute("field", fields);
        request.getSession().setAttribute("condition", conditions);

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException{
        doPost(request, response);
    }

}
