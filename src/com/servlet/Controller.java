package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DataDao;
import com.google.gson.Gson;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		try {
			String term = request.getParameter("term").toLowerCase();
			DataDao dataDao = new DataDao();
			List<String> addressVOList = dataDao.getFrameWork(request,
					response, term);
			String searchList = new Gson().toJson(addressVOList);
			response.getWriter().write(searchList);
		} catch (Exception e) {
			System.err.println("Exception Occured in Controller === "+e.getMessage());
		}
	}
}