package com.servlet;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.dao.DBConnectionManager;

@WebListener
public class AppContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		long start = System.currentTimeMillis();
		ServletContext ctx = servletContextEvent.getServletContext();
		
		// initialize DB Connection
		String dbURL = ctx.getInitParameter("dbURL");
		String user = ctx.getInitParameter("dbUser");
		String pwd = ctx.getInitParameter("dbPassword");

		try {
			DBConnectionManager connectionManager = new DBConnectionManager(
					dbURL, user, pwd);
			ctx.setAttribute("DBConnection", connectionManager.getConnection());
			System.out.println("TEST DB Connection initialized successfully.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int size = 0;
		PreparedStatement ps;
		ResultSet rs;

		List<Character> possibleCharsListArray = new ArrayList<Character>();
		Character arrayOfCharsGroups[] = { '0', '1', '2', '3', '4', '5', '6',
				'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
				'v', 'w', 'x', 'y', 'z' };
		List<HashMap<String, List<String>>> finalAddressMapsList = new ArrayList<HashMap<String, List<String>>>();
		List<HashMap<String, List<String>>> finalCityMapsList = new ArrayList<HashMap<String, List<String>>>();
		
		possibleCharsListArray = Arrays.asList(arrayOfCharsGroups);

		try {
			for (int arrayIndex = 0; arrayIndex < possibleCharsListArray.size(); arrayIndex++) {
				Map<String, List<String>> indexedDataResultsMap = new HashMap<String, List<String>>();
				List<String> groupingAddressesList = new ArrayList<String>();
				Map<String, List<String>> indexedCityDataResultsMap = new HashMap<String, List<String>>();
				List<String> groupingCityList = new ArrayList<String>();
				if (Character.isDigit(possibleCharsListArray.get(arrayIndex))) {
					ps = ((Connection) ctx.getAttribute("DBConnection"))
							.prepareStatement("SELECT CONCAT(address, ', ',city) as address_city from address where address like '"
									+ possibleCharsListArray.get(arrayIndex)
									+ "%' order by address asc");
					//SELECT CONCAT(address, ', ',city) as address_city from address where address like '1%' order by address asc
					System.out.println("Digit QUERY ***** " +  possibleCharsListArray.get(arrayIndex));
					rs = ps.executeQuery();
					while (rs.next()) {
						size++;
						groupingAddressesList.add(rs.getString("address_city"));
						indexedDataResultsMap.put(String.valueOf(arrayIndex),
								groupingAddressesList);
					}
				} else {
					
					 ps = ((Connection) ctx.getAttribute("DBConnection"))
							.prepareStatement("select CONCAT(city, ', ', address) as city_address from address where ((city like '" 
+(possibleCharsListArray.get(arrayIndex)) + "%') OR (address like '"+(possibleCharsListArray
											.get(arrayIndex))+"%'))");
					//select city || ',      ' || address1 as city_address from store_locator_main where (upper(city) like upper('z') || '%') OR (upper(address1) like upper('z') || '%')
					System.out.println("Char QUERY ***** " + possibleCharsListArray
							.get(arrayIndex));
					rs = ps.executeQuery();
					while (rs.next()) {
						size++;
						groupingCityList.add(rs.getString("city_address"));
						indexedCityDataResultsMap.put(String.valueOf(possibleCharsListArray
								.get(arrayIndex)),
								groupingCityList);
					}
				}

				finalAddressMapsList
						.add((HashMap<String, List<String>>) indexedDataResultsMap);
				finalCityMapsList
						.add((HashMap<String, List<String>>) indexedCityDataResultsMap);
				System.out.println("Data Population Completed");
				ctx.setAttribute("IndexedAddressData", finalAddressMapsList);
				ctx.setAttribute("IndexedCityData", finalCityMapsList);
			}
		} catch (SQLException e) {
			System.out.println("Exception Occured in ApplicationContextListener Class === "+e.getMessage());
		}
		long time = System.currentTimeMillis() - start;
		for (int i = 0; i < finalAddressMapsList.size(); i++) {
			for (String eachkey : finalAddressMapsList.get(i).keySet()) {
				System.out.println("SIZE OF EACH DIGIT ADDRESSES LIST++++++"
						+ finalAddressMapsList.get(i).get(eachkey).size());
			}
		}
		for (int i = 0; i < finalCityMapsList.size(); i++) {
			for (String eachkey : finalCityMapsList.get(i).keySet()) {
				System.out.println("SIZE OF EACH CHAR CITY LIST++++++"
						+ finalCityMapsList.get(i).get(eachkey).size());
			}
		}
		System.out.println("Total time took to startup : " + time);
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		Connection con = (Connection) servletContextEvent.getServletContext()
				.getAttribute("DBConnection");
		servletContextEvent.getServletContext().setAttribute(
				"IndexedAddressData", null);
		servletContextEvent.getServletContext().setAttribute("IndexedCityData",
				null);
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}