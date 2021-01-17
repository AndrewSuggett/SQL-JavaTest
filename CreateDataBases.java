import java.sql.*;
public class CreateDataBases
{
    public static void main(String[] args)
    {
        final String DB_URL = "jdbc:derby:Personnel;create=true";

        try
        {
            Connection conn = DriverManager.getConnection(DB_URL);

            testTables(conn);

            buildPersonnelTables(conn);

            fillEmployeeTable(conn);


        }
        catch(Exception e)
        {
            System.out.println("Error: " + e);
        }

    }

    public static void testTables(Connection conn)
    {
        //Used incase data base is already made
        System.out.println("Testing for existing tables.");
        
        try
        {   
            Statement stmt  = conn.createStatement();

            try
            {
                stmt.execute("DROP TABLE Employee");
                System.out.println("Employee table dropped.");
            }
            catch(SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        
    }

    public static void buildPersonnelTables(Connection conn)
    {
        try
        {
            //Create Employee table
            Statement stmt = conn.createStatement();

            stmt.execute("CREATE TABLE Employee ( " +
                        "ID INT PRIMARY KEY," +
                        "name varchar(20)," +
                        "position varchar(20)," +
                        "hourly_pay numeric(10,2))");
        }
        catch(Exception e)
        {
            System.out.println("Error" + e.getMessage());
        }
    }

    public static void fillEmployeeTable(Connection conn)
    {
        //Fills up employee table with data
        try
        {
            Statement stmt = conn.createStatement();

            stmt.execute("INSERT INTO Employee VALUES ( " +
            "00001, " +
            "'John Johnston', " +
            "'Driver', " +
            "10.34 )");

            
            stmt.execute("INSERT INTO Employee VALUES ( " +
            "00010, " +
            "'Jim Jimmy', " +
            "'Owner', " +
            "100.10 )");

            
            stmt.execute("INSERT INTO Employee VALUES ( " +
            "00011, " +
            "'Candance Candy', " +
            "'Manager', " +
            "30.00 )");

            
            stmt.execute("INSERT INTO Employee VALUES ( " +
            "00100, " +
            "'Stew Stooge', " +
            "'Driver', " +
            "10.34 )");

            
            stmt.execute("INSERT INTO Employee VALUES ( " +
            "00101, " +
            "'Bruce Springsteen', " +
            "'Runner', " +
            "8.50 )");
        }
        catch(Exception e)
        {   
            System.out.println("Error: " + e.getMessage());
        }
    }
}