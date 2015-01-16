package com.sevenbits.servlets.sniffer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletSniffer extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, java.io.IOException {
        String name = request.getParameter("first");
        String surname = request.getParameter("last");

        response.setContentType("text/html");
        java.io.PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Registration</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h2>Registration is successful!</h2>");
        writer.println("Thanks you: ");
        writer.println((name == null || name.equals("")) ? "Unknown" : name);
        writer.println(" " + ((surname == null || surname.equals("")) ? "Unknown" : surname));
        writer.println("<p>SevenBits</p>");
        writer.println("</body>");
        writer.println("</html>");

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException{
        doPost(request, response);
    }

}
