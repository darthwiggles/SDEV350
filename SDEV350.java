import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;

public class SDEV350 {
 
   //declare variables for DB connection
   private static final String driver = "oracle.jdbc.driver.OracleDriver";
   private static final String dbconnect = "jdbc:oracle:thin:@localhost:1521:orcl";
   private static String user;
   private static String password;
   private static String menu;

   public static void main(String args[]) throws SQLException {
      
      Scanner input = new Scanner(System.in); 
      System.out.println("Enter the username: ");
      user = input.nextLine();
      System.out.println("Enter the password: ");
      password = input.nextLine();
      System.out.println("Select from one of the following options.");
      System.out.println("1: Insert a new record");
      System.out.println("2: Update existing record");
      System.out.println("3: Delete a record");
      System.out.println("4: Retrieve records");
      menu = input.nextLine();
      int m = Integer.parseInt(menu);
      
      try{          
        if (m == 1) {
        tableInsert();
        }
        else if (m == 2) {
        tableUpdate();
        }
        else if (m == 3) {
        tableDelete();
        }
        else if (m == 4) {
        tableSelect();
        }
        else {
        System.out.println("Invalid selection.");
        }
      }
      catch (SQLException e) {
         System.out.println(e.getMessage());
      }
   }

   private static void tableInsert() throws SQLException {

      Connection conn;
      PreparedStatement statement1;
      
      Scanner input = new Scanner(System.in);
      System.out.println("Enter the Carrier ID: ");
      String inputS = input.nextLine();
      int input1 = Integer.parseInt(inputS);
      System.out.println("Enter the Carrier Name: ");
      String input2 = input.nextLine();
      System.out.println("Enter the Shipping Time: ");
      String input3 = input.nextLine();
      System.out.println("Enter the Price: ");
      String input4 = input.nextLine();

      String shipTableMod = "insert into Shipping"
                + "(carrierid, carriername, shiptime, price) VALUES"
                + "(?,?,?,?)";

        try {
            conn = getDBConnection();
            statement1 = conn.prepareStatement(shipTableMod);

            statement1.setInt(1, input1);
            statement1.setString(2, input2);
            statement1.setString(3, input3);
            statement1.setString(4, input4);
            statement1.executeUpdate();

            System.out.println("Table insert was successful.");

        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } 

    }

    private static void tableUpdate() throws SQLException {

      Connection conn;
      PreparedStatement statement;
      PreparedStatement statement2;
      
      Scanner input = new Scanner(System.in);
      System.out.println("Enter the Carrier ID to update: ");
      String inputS = input.nextLine();
      int input1 = Integer.parseInt(inputS);
      System.out.println("Enter the new Carrier Name: ");
      String input2 = input.nextLine();
      System.out.println("Enter the new Shipping Time: ");
      String input3 = input.nextLine();
      System.out.println("Enter the new Price: ");
      String input4 = input.nextLine();

      String clearString = "delete from Shipping where carrierid = "
              + input1;
      String updateString = "insert into Shipping"
              + "(carrierid, carriername, shiptime, price) VALUES"
              + "(?,?,?,?)";
         
        try {
            conn = getDBConnection();
            statement = conn.prepareStatement(clearString);
            statement2 = conn.prepareStatement(updateString);
            statement.executeUpdate();
            statement.execute(clearString);
            statement2.setInt(1, input1);
            statement2.setString(2, input2);
            statement2.setString(3, input3);
            statement2.setString(4, input4);
            statement2.executeUpdate();
            statement2.execute(updateString);

            System.out.println("Table update was successful.");

        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } 


    }
   
      private static void tableDelete() throws SQLException {

      Connection conn;
      PreparedStatement statement1;
      
      Scanner input = new Scanner(System.in);
      System.out.println("Enter the Carrier ID to delete: ");
      String input1 = input.nextLine();

      String deleteString = "delete from Shipping where carrierid = "
                + input1;

        try {
            conn = getDBConnection();
            statement1 = conn.prepareStatement(deleteString);
            statement1.executeUpdate();
            System.out.println("Record deletion was successful.");

        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } 

    }
      
         private static void tableSelect() throws SQLException {

      Connection conn;
      Statement statement1;

        try {
            conn = getDBConnection();
            statement1 = conn.createStatement();
            String selectString = "SELECT carrierid, carriername, shiptime, price FROM Shipping";
            ResultSet result = statement1.executeQuery(selectString);

            while(result.next()){
                int carrierid  = result.getInt("carrierid");
                String carriername = result.getString("carriername");
                String shiptime = result.getString("shiptime");
                String price = result.getString("price");

                System.out.println("Carrier ID: " + carrierid);
                System.out.println("-Carrier name: " + carriername);
                System.out.println("-Ship time: " + shiptime);
                System.out.println("-Price: " + price);
            }
            result.close();
            
            System.out.println("Table select was successful.");

        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } 

    }
         
   private static Connection getDBConnection() {

      Connection conn = null;

      try {
         Class.forName(driver);
      } 

      catch (ClassNotFoundException e) {
         System.out.println(e.getMessage());
      }

      try {
            conn = DriverManager.getConnection(dbconnect, user, password);
            return conn;
        } 

        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;

    }

}
