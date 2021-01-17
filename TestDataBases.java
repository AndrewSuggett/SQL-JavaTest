import java.sql.*;
import java.util.*;
public class TestDataBases
{
    public static void main(String[] args)
    {
        final String DB_URL = "jdbc:derby:Personnel";
        
        boolean flag = true;
        
        Scanner input = new Scanner(System.in);
        
        showOptions(); //Shows the options you can pick
            
        try
        {
            Connection conn = DriverManager.getConnection(DB_URL);
                
            while(flag)
            {
                System.out.print("\nOption: ");
                int option = input.nextInt();
                
                //Does what ever the user picks to do
                switch(option)
                {
                    case 1:
                        addNewEmployee(conn);
                        break;
                    case 2:
                        lookUpEmployeeByName(conn);
                        break;

                    case 3:
                        lookUpEmployeeByID(conn);
                        break;

                    case 4:
                        updateEmployeeInfo(conn);
                        break;

                    case 5:
                        showOptions();
                        break;

                    case 6:
                        flag = false;
                        break;
                    default:
                        System.out.println("Sorry that is not an option");


                }
            }
        }
            catch(Exception e)
            {
                System.out.println("Error: " + e);
            }
        
 
    }

    public static void addNewEmployee(Connection conn)
    {
        //Adds a new employee to the Personal data base
        Scanner input = new Scanner(System.in);
        
        int id;
        float pay;
        String name, position;

        //gets all the empolyee info from the user
        System.out.println("Employee ID: ");
        id = input.nextInt();
        input.nextLine();
        
        System.out.println("Employee Name: ");
        name = input.nextLine();
        
        System.out.println("Employee Position: ");
        position = input.nextLine();
        
        System.out.println("Employee Pay: ");
        pay = input.nextFloat();

    
        try
        {
            Statement stmt = conn.createStatement();
            //adds the info to the database
            stmt.execute("INSERT INTO employee (id, name, position, hourly_pay) " +
                        "VALUES (" + id + ", " + "'" + name + "', '"  + position + "', " + pay + ")");
           
            System.out.println("Added Employee to Data Base");
        }
        catch(Exception e)
        {
            System.out.println("Unable to enter that Employee.");
        }
    }

    
    public static void lookUpEmployeeByName(Connection conn)
    {
        

        Scanner input = new Scanner(System.in);

        //gets name from user
        System.out.println("Employee Name: ");
        String name = input.nextLine();
        


        try
        {
            Statement stmt = conn.createStatement();
            //Looks up user in data base
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM employee where name = '" + name + "'");
            
            printResultSet(resultSet); //Prints out empolyee info
      
        }
        catch(Exception e)
        {
            System.out.println("Error" + e.getMessage());
        }
    }

    public static void lookUpEmployeeByID(Connection conn)
    {
        
        Scanner input = new Scanner(System.in);

        System.out.println("Employee ID: ");
        int id = input.nextInt();
        input.nextLine();
      

        try
        {
            Statement stmt = conn.createStatement();
            //Looks up user in data base
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM employee where id = " + id);
            printResultSet(resultSet); //Prints out empolyee info
      
        }
        catch(Exception e)
        {
            System.out.println("Error" + e.getMessage());
        }
    }

    public static void printResultSet(ResultSet resultSet) throws SQLException
    {
        ResultSetMetaData rsmd = resultSet.getMetaData();   // To gets the number of coloums
        int columnsNumber = rsmd.getColumnCount();
        
        //If there is no next that employee is not in the data base
        if(!resultSet.next())
        {
                System.out.println("That Employee is not in the database");
        }
        else
        {
            do
            {
                for (int i = 1; i <= columnsNumber; i++) 
                {
                    //Prints out the employee info
                    String columnValue = resultSet.getString(i);
                    System.out.print("  " + rsmd.getColumnName(i) + ": " + columnValue);
                }
            } while(resultSet.next());
        }
    }

    public static void updateEmployeeInfo(Connection conn)
    {
        
        Scanner input = new Scanner(System.in);
        
        int id;
        String toChange;
        
        //Gets the employee Id and what info the user wants to update
        System.out.println("Enter Employee ID to Update Field: ");
        id = input.nextInt();

        System.out.print("Enter the field to change (name, position, hourly_pay): ");
        input.nextLine();
        toChange = input.nextLine().toLowerCase();


        //Does what the user picks
        switch(toChange)
        {
            case "name":
                changeName(conn, id);
                break;
            case "position":
                changePosition(conn, id);
                break;
            case "hourly_pay":
                changePay(conn, id);
                break;
            case "pay":
                changePay(conn, id);
                break;
            default :
                System.out.println("That is not a valid field");
        }
        
     
    }

    public static void changeName(Connection conn, int id)
    {
        Scanner input = new Scanner(System.in);

        //Gets the new Employee name from user
        System.out.print("Enter the new name: ");
        String newName = input.nextLine();
        
        //Changes the employee name in the data base
        try
        {
            Statement stmt = conn.createStatement();

            stmt.execute("UPDATE employee SET name = '" + newName + "'" + " WHERE id = " + id);
            System.out.println("Updated name");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void changePosition(Connection conn, int id)
    {
        Scanner input = new Scanner(System.in);
        //Gets new postion from user
        System.out.print("Enter the new position: ");
        String newPosition = input.nextLine();
        
   
        try
        {
            Statement stmt = conn.createStatement();
            //Updates the postion in data base
            stmt.execute("UPDATE employee SET position = '" + newPosition + "'" + " WHERE id = " + id);
            System.out.println("Updated Position");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void changePay(Connection conn, int id)
    {
        Scanner input = new Scanner(System.in);
        //Gets new pay from user
        System.out.print("Enter the new pay: ");
        float newPay = input.nextFloat();
 
        try
        {
            Statement stmt = conn.createStatement();
            //Updates pay in data base
            stmt.execute("UPDATE employee SET hourly_pay = " + newPay + " WHERE id = " + id);
            System.out.println("Updated Pay");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void showOptions()
    {
        //Prints out the options and the number to pick them
        System.out.println("Options");
        System.out.println("-----------");
        System.out.println("Add New Employee (1)");
        System.out.println("Search Employee by Name (2)");
        System.out.println("Search Employee by ID (3)");
        System.out.println("Change Employee Info (4)");
        System.out.println("Show options (5)");
        System.out.println("Exit (6)");
    }

}