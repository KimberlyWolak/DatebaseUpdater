package com.app.updatedatabases;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class UpdaterServlet
 */
public class UpdaterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdaterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String database = request.getParameter("database");
		String updater = request.getParameter("updater");
		String download = request.getParameter("download");
		
		HashMap<String,String> updateDBListMap = new HashMap<>();
			HashMap<String,JSONObject>  actualDBList =  new HashMap<>();
			JSONArray updateDBList = new JSONArray();		
			try {
				actualDBList = JsonUtil.readJsonFile(database);
				updateDBListMap = JsonUtil.extractJsonFileInfo(updater);
				updateDBList = JsonUtil.updateAndCreateJsonObject(updateDBListMap,actualDBList);
				JsonUtil.writeJsonFile(updateDBList,download);
				
			} catch (Exception e) {
				System.out.print(e);
			    throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
