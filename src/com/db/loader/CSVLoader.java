package com.db.loader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import au.com.bytecode.opencsv.CSVReader;

/**
 * 
 * @author viralpatel.net
 * 
 */
public class CSVLoader {

	private static final 
		String SQL_INSERT = "INSERT INTO ${table}(${keys}) VALUES(${values})";
	private static final String TABLE_REGEX = "\\$\\{table\\}";
	private static final String KEYS_REGEX = "\\$\\{keys\\}";
	private static final String VALUES_REGEX = "\\$\\{values\\}";

	private Connection connection;
	private char seprator;

	/**
	 * Public constructor to build CSVLoader object with
	 * Connection details. The connection is closed on success
	 * or failure.
	 * @param connection
	 */
	public CSVLoader(Connection connection) {
		this.connection = connection;
		//Set default separator
		this.seprator = ';';
	}
	
	/**
	 * Parse CSV file using OpenCSV library and load in 
	 * given database table. 
	 * @param csvFile Input CSV file
	 * @param tableName Database table name to import data
	 * @param truncateBeforeLoad Truncate the table before inserting 
	 * 			new records.
	 * @throws Exception
	 */
	public void loadCSV(String csvFile, String tableName,
			boolean truncateBeforeLoad, boolean createBeforeLoad) throws Exception {

		CSVReader csvReader = null;
		if(null == this.connection) {
			throw new Exception("Not a valid connection.");
		}
		try {
			
			csvReader = new CSVReader(new FileReader(csvFile), this.seprator);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error occured while executing file. "
					+ e.getMessage());
		}

		String[] headerRow = csvReader.readNext();

		if (null == headerRow) {
			throw new FileNotFoundException(
					"No columns defined in given CSV file." +
					"Please check the CSV file format.");
		}

		String questionmarks = StringUtils.repeat("?,", headerRow.length);
		questionmarks = (String) questionmarks.subSequence(0, questionmarks
				.length() - 1);

		String query = SQL_INSERT.replaceFirst(TABLE_REGEX, tableName);
		query = query
				.replaceFirst(KEYS_REGEX, StringUtils.join(headerRow, ","));
		query = query.replaceFirst(VALUES_REGEX, questionmarks);

		System.out.println("Query: " + query);

		String[] nextLine;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = this.connection;
			con.setAutoCommit(false);
			ps = con.prepareStatement(query);
			if(createBeforeLoad) {/*
				//delete data from table before loading csv
				System.out.println("creating table");
				con.createStatement().execute("CREATE TABLE "+tableName+"(address_id text,"+
						"address text,"+
						"address2 text,"+
						"city text,"+
						"state text,"+
						"zipcode text,"+
						"last_update text)");
				con.commit();
				System.out.println("commit done");
			*/}
			if(truncateBeforeLoad) {
				//delete data from table before loading csv
				//String deletequery = "DELETE FROM " + tableName +" where address_id > '20000'";
				//con.createStatement().execute(deletequery);
				//con.commit();
			}
			//System.out.println("**Deleted From Table executing select statement");
			
			
			final int batchSize = 2000;
			int count = 0;
			Date date = null;
			while ((nextLine = csvReader.readNext()) != null) {

				if (null != nextLine) {
					int index = 1;
					for (String string : nextLine) {
						date = DateUtil.convertToDate(string);
						if (null != date) {
							ps.setDate(index++, new java.sql.Date(date
									.getTime()));
						} else {
							ps.setString(index++, string);
						}
					}
					//ps.addBatch();
				}
				if (++count % batchSize == 0) {
					//ps.executeBatch();
				}
			}
			//ps.executeBatch(); // insert remaining records
			//con.commit();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			throw new Exception(
					"Error occured while loading data from file to database."
							+ e.getMessage());
		} finally {
			if (null != ps)
				//ps.close();
			if (null != con){
				
				PreparedStatement ps1= con.prepareStatement("select * from "+ tableName );
				System.out.println("Record Count *************************** ");
				ResultSet rs1 = ps1.executeQuery();
				int sizetest = 0;
				 while(rs1.next()){
					 sizetest++;
				 }
						
				System.out.println("COUNT ++++++++++++++++++++++++++++++++ "+ sizetest);
			}
				//con.close();

			//csvReader.close();
		}
	}

	public char getSeprator() {
		return seprator;
	}

	public void setSeprator(char seprator) {
		this.seprator = seprator;
	}

}
