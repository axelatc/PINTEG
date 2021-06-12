package com.atc.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// source: https://stackoverflow.com/questions/9148490/how-to-redirect-a-logged-out-user-to-the-home-page-in-java-ee-jsf
public class SecurityFilter implements Filter {
    FilterConfig fc;

    public void init(FilterConfig filterConfig)throws ServletException {
        fc = filterConfig;
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException{
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);

        String pageRequested = req.getRequestURI().toString();
        if(session.getAttribute("loggedUser") == null && !pageRequested.contains("login.xhtml")){
            resp.sendRedirect("login.xhtml");
        }else{
            chain.doFilter(request, response);
        }
    }
    public void destroy(){

    }

}
