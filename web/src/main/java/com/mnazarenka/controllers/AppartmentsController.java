package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.service.AppartmentService;
import com.mnazarenka.service.impl.AppartmentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/appartments.html")
public class AppartmentsController extends HttpServlet {

    public static final String APPARTMENT_VIEW = "/WEB-INF/pages/appartments.jsp";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppartmentService appartmentService = new AppartmentServiceImpl();
        List<Appartment> entities = appartmentService.findAllAppartments();

        req.setAttribute("appartments", entities);
        req.getRequestDispatcher(APPARTMENT_VIEW).forward(req, resp);
    }
}
