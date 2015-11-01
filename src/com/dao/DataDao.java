package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataDao {
        private static Connection connection;
        private static List<HashMap<String, List<String>>> resultAddressList;
        private static List<HashMap<String, List<String>>> resultCityList;
		ArrayList<String> list = new ArrayList<String>();
		Character charArrayForIndexing[] = {'0', '1', '2', '3', '4', '5', '6',
				'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
				'v', 'w', 'x', 'y', 'z'  };

        public DataDao() throws Exception {
        }

        public List <String> getFrameWork(HttpServletRequest request, HttpServletResponse response, String searchWord) {
        	try{
        
        PreparedStatement ps = null;
		 connection = (Connection) request.getServletContext().getAttribute("DBConnection");
		 @SuppressWarnings("unchecked")
		 List<HashMap<String, List<String>>> addressList = ((List<HashMap<String, List<String>>>) request.getServletContext().getAttribute("IndexedAddressData"));
		 resultAddressList =  addressList;
		
		 @SuppressWarnings("unchecked")
		 List<HashMap<String, List<String>>>  cityList = ((List<HashMap<String, List<String>>>) request.getServletContext().getAttribute("IndexedCityData"));
		 resultCityList =  cityList;
        try {
        	//int test =2;
        	if(Character.isDigit(searchWord.charAt(0))){
        		list.addAll(resultAddressList.get(Integer.parseInt(searchWord)).get(searchWord));
        		
        	}else{        	
        		
        		list.addAll(resultCityList.get(Arrays.asList(charArrayForIndexing).indexOf(searchWord.charAt(0))).get(searchWord));
        	/*if(searchWord.equals("1") || searchWord.equals("0")){
        		/*list.addAll(resultAddressList.get(0).get("0"));
        		list.addAll(resultAddressList.get(1).get("1"));
        		System.out.println("IF BLOCK");
        	}else{
        		
        	System.out.println("ELSE BLOCK");
        	
        	 ps = connection.prepareStatement("select * from store_locator_main WHERE address1  LIKE ?");
                ps.setString(1, searchWord + "%");
                //System.out.println(frameWork);
                //System.out.println(ps.toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
    				list.add(rs.getString("address1"));
    				
                }	
    				
    				
                }*/
        }
        	} catch (Exception e) {
                System.out.println("Exception Occured in DataDao Class::: "+e.getMessage());
                e.printStackTrace();
        }
        	}catch(Exception e){
        		e.printStackTrace();
        	}
		 System.out.println("Result List SIZE:: "+list.size());
        return list;
}
}