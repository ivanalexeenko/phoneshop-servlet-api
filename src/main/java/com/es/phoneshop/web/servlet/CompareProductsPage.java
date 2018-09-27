package com.es.phoneshop.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CompareProductsPage extends HttpServlet {
    private HttpSession session;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session = request.getSession();

        request.getRequestDispatcher("/WEB-INF/pages/compare.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        session = null;
    }
}
