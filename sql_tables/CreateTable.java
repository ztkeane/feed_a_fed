/*----------------------------------------------------------------------------*
 |      Author:  Ashley Mains, Amelia Marglon, Suresh Devendran, Zach Keane
 |  Assignment:  Program #4: Database-driven Web Application
 |    Due Date:  December 4, 2018 3:30pm
 |
 |      Course:  CSC 460 - Database Design
 |  Instructor:  Lester I. McCann, Ph.D.
 |         TAs:  Heuichan "Terrence" Lim, Bailey Nottingham
 |
 | Description:  "Command line" program to create, populate, and drop tables.
 |
 |    Language:  Java 1.8
 |       Input:  None
 |
 | Deficiencies: - Manual some data scrubbing.
 *----------------------------------------------------------------------------*/

import java.io.*;
import java.sql.*;         // For access to the SQL interaction methods
import java.util.Scanner;

public class CreateTable {
    
    /*
     * main
     * Purpose: To take in a single argument and process a tab-delimited text file into an Oracle table based on the file name.
     * Pre-Conditions: The tab-delimited .txt files are ready to go.
     * Post-Conditions: The Oracle database has been updated.
     * Return value: None
     * Parameters: args, a String[] that will contain the .txt file name as its sole value at index zero.
     */
    public static void main(String[] args) {
        //Ensure that we have three arguments.
        if (args.length != 1) {
            System.err.println("There should be a filename as an argument.");
            System.exit(1);
        }
        //fileName will contain the full text file name.
        String fileName = args[0];
        //inFile will be the file object.
        File inFile = new File(fileName);
        //Find last index of "." in file.
        int extensionIndex = inFile.getName().lastIndexOf(".");
        //tableName will contain the name of the table, which is based on the file name.
        String tableName = inFile.getName().substring(0, extensionIndex);
        //Initialize in, the Scanner object.
        Scanner in = null;
        try {
            in = new Scanner(inFile);
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found.");
            System.exit(1);
        }
        
        //Get oracle set up with oracleURL
        final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
        //Initialize username and password to connect to Oracle.
        String username = "ztkeane";
        String password = "a3655";
        //Oracle JDBC driver setup.
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        }
        catch (ClassNotFoundException e) {
            System.err.println("*** ClassNotFoundException:  "
                               + "Error loading Oracle JDBC driver.  \n"
                               + "\tPerhaps the driver is not on the Classpath?");
            System.exit(-1);
            
        }
        //Establish a connection variable.
        Connection dbconn = null;
        //Create a counter variable, so we ensure we don't insert too many or too little tuples.
        int counter = 0;
        while (in.hasNextLine()) {
            try {
                //Initialize a new connection every loop, preventing errors.
                dbconn = DriverManager.getConnection
                (oracleURL,username,password);
                
            } catch (SQLException e) {
                
                System.err.println("*** SQLException:  "
                                   + "Could not open JDBC connection.");
                System.err.println("\tMessage:   " + e.getMessage());
                System.err.println("\tSQLState:  " + e.getSQLState());
                System.err.println("\tErrorCode: " + e.getErrorCode());
                System.exit(-1);
                
            }
            String myStatement = "insert into ztkeane." + tableName + " values ('";
            //Split on tabs and store in info[]
            String [] info = in.nextLine().split("\t");
            if (tableName.equals("helper")) {
                if (info.length != 7) {
                    System.err.println("helper should have 9 fields.");
                    System.exit(-1);
                }
                String hid = info[0];
                String name = info[1];
                String email = info[2];
                String description = info[3];
                String city = info[4];
                String county = info[5];
                String state = info[6];
                myStatement += hid + "', '" + name + "', '" + email + "', '" + description + "', '" + city + "', '" + county + "', '" + state + "')";
            }
            else if (tableName.equals("employee")) {
                if (info.length != 6) {
                    System.err.println("employee should have 6 fields.");
                    System.exit(-1);
                }
                String eid = info[0];
                String name = info[1];
                String email = info[2];
                String city = info[3];
                String county = info[4];
                String state = info[5];
                String phoneNo = info[6];
                int integer = Integer.parseInt(info[7]);
                myStatement += eid + "', '" + name + "', '" + email + "', '" + city + "', '" + county + "', '" + state + "', '" + phoneNo + "', " + integer + ")";
            }
            else if (tableName.equals("helpCenter")) {
                if (info.length != 7) {
                    System.err.println("helpCenter should have 7 fields.");
                    System.exit(-1);
                }
                String cid = info[0];
                String name = info[1];
                String address = info[2];
                String phoneNo = info[3];
                String city = info[4];
                String county = info[5];
                String state = info[6];
            }
            else if (tableName.equals("location")) {
                if (into.length != 3) {
                    Sytem.err.println("location should have 3 fields.");
                }
                String city = info[0];
                String county = info[1];
                String state = info[2];
            }
            else {
                System.err.println("Table name is not valid.");
                System.exit(-1);
            }
            //Create stmt, so we may update the tables.
            Statement stmt = null;
            //Create myStatement, a String containing the entirety of the SQL query.
            myStatement += ("insert into ztkeane." + tableName + " values ('" + districtNum + "', '" + districtName + "', '" + schoolNum + "', '" + schoolName + "', '" + numStudents + "', ");
            //Keep building myStatement through nulls.
            if (meanScore.equals("NULL")) {
                myStatement += (meanScore + ", ");
            }
            else {
                myStatement += ("'" + meanScore + "', ");
            }
            //Finish building myStatement
            myStatement += (percPassing + ", " + one + ", " + two + ", " + three + ", " + four + ", " + five + ")");
            
            try {
                //Create a statement.
                stmt = dbconn.createStatement();
                //Send in the insert command, it will return nothing.
                stmt.executeUpdate(myStatement);
                //Commit table.
                dbconn.commit();
                //Close and reopen with each loop.
                stmt.close();
                dbconn.close();
                counter++;
            }
            catch (SQLException e) {
                //Throw exception, give information about tuple that throws error.
                //Standard SQL error handling.
                System.err.println("*** SQLException:  "
                                   + "Could not fetch query results for entry number " + (counter + 1));
                System.exit(-1);
            }
        }
        //Print out tuple insertion count.
        System.out.println("You have created " + counter + " tuples.");
        //Exit normally with 0.
        System.exit(0);
    }
    
}
