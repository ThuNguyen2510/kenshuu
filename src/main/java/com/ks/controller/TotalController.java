package com.ks.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ks.model.Count;
import com.ks.service.TotalService;
import com.ks.service.impl.TotalServiceImpl;

@WebServlet("/total")
public class TotalController extends HttpServlet {
	private static final Logger logger = Logger.getLogger(TotalController.class);
	private static final long serialVersionUID = 1L;

	private TotalService totalService;

	public TotalController() {
		totalService = new TotalServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("GET SUMMARY");
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/total.jsp");
		List<Count> listCount = totalService.getSummary();
		request.setAttribute("listCount", listCount);
		rd.forward(request, response);
	}

}
