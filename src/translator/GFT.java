package translator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GFT {
	private static HashtableExt<Integer, String> charConstants = new HashtableExt<>();
	public static HashtableExt<Integer, String> getCharTable () { return charConstants; }
	
	private static HashtableExt<Integer, Float> numberConstants = new HashtableExt<>();
	public static HashtableExt<Integer, Float> getNumberTable () { return numberConstants; }
	public static void setNumberTable (HashtableExt<Integer, Float> value) { numberConstants = value; }
	
	private static HashtableExt<Integer, String> operations = new HashtableExt<>();
	public static HashtableExt<Integer, String> getOperationsTable () { return operations; }
	
	private static HashtableExt<Integer, Character> separators = new HashtableExt<>();
	public static HashtableExt<Integer, Character> getSeparatorsTable () { return separators; }
	
	private static HashtableExt<Integer, String> identifiers = new HashtableExt<>();
	public static HashtableExt<Integer, String> getIdentifiersTable () { return identifiers; }
	public static void setIdentifiersTable (HashtableExt<Integer, String> value) { identifiers = value; }
	
	private static HashtableExt<Integer, String> functions = new HashtableExt<>();
	public static HashtableExt<Integer, String> getFunctionsTable () { return functions; }
	
	/**
	 * Clears all necessary tables.
	 */
	public static void clearTables() {
		numberConstants.clear();
	}
	
	/**
	 * Initiates tables with start values using stored SQL database.
	 */
	public static void initTables() throws Exception {
		initStringTable("select * from GMOGA.operations", operations);
		initCharTable("select * from GMOGA.separators", separators);
		initStringTable("select * from GMOGA.constants", charConstants);
		initStringTable("select * from GMOGA.functions", functions);
	}

	/**
	 * Initiates String table with values from stored SQL database
	 * 
	 * @param sqlQuery SQL query select string
	 * @param table table to be filled
	 * @throws Exception
	 */
	private static void initStringTable(String sqlQuery,
			HashtableExt<Integer, String> table) throws Exception {
		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;

		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/GMOGA?"
				+ "user=greshnikk&password=46w5w54s6");
		statement = connect.createStatement();
		resultSet = statement.executeQuery(sqlQuery);
		while (resultSet.next()) {
			table.put(resultSet.getInt(1), resultSet.getString(2));
		}
	}

	/**
	 * Initiates Character table with values from stored SQL database.
	 * 
	 * @param sqlQuery SQL query select string
	 * @param table table to be filled
	 * @throws Exception
	 */
	private static void initCharTable(String sqlQuery,
			HashtableExt<Integer, Character> table) throws Exception {
		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;

		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://localhost/GMOGA?"
				+ "user=greshnikk&password=46w5w54s6");
		statement = connect.createStatement();
		resultSet = statement.executeQuery(sqlQuery);
		while (resultSet.next()) {
			table.put(resultSet.getInt(1), resultSet.getString(2).charAt(0));
		}
	}
}
