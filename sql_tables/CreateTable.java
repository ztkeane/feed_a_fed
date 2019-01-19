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
    
    private final static String[][] allTables =
        {{"patient", "Patients.txt"},
         {"building", "Buildings.txt"},
         {"roomType", "RoomTypes.txt"},
         {"room", "Rooms.txt"},
         {"department", "Departments.txt"},
         {"doctor", "Doctors.txt"},
         {"pharmacist", "Pharmacists.txt"},
         {"nurse", "Nurses.txt"},
         {"staff", "Staff.txt"},
         //{"receptionist", "TODO.txt"},
         {"medicine", "SubscribedMedicine.txt"},
         {"appointment", "Appointments.txt"},
         {"treatment", "PatientTreatments.txt"},
         {"payment", "Payments.txt"}};
    
    private final static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        //----------
        // Magic lectura -> aloe access spell
        
        final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
        String username = "";
        String password = "";
        
        try {
            System.out.println("Oracle Authentication:");
            System.out.print(" Username: ");
            username = input.nextLine().trim();
            System.out.print(" Password: ");
            password = input.nextLine().trim();
        } catch (IOError e) {
            System.err.println("IOException: unable to scan for input.");
            System.exit(-1);
        }
        System.out.println();
        
        //----------
        // Load the (Oracle) JDBC driver by initializing its base class, 'oracle.jdbc.OracleDriver'.
        
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("*** ClassNotFoundException:  "
                + "Error loading Oracle JDBC driver.  \n"
                + "\tPerhaps the driver is not on the Classpath?");
            System.exit(-1);
        }
        
        //----------
        // Make and return a database connection to the user's Oracle database
        
        Connection dbconn = null;
        
        try {
            dbconn = DriverManager.getConnection(oracleURL,username,password);
        } catch (SQLException e) {
            System.err.println("*** SQLException:  Could not open JDBC connection.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
        
        //----------
        // Send the query to the DBMS, and get and display the results
        // Scan input file and create table by executing SQL statements
        
        Statement stmt = null;
        ResultSet answer = null;
        ResultSetMetaData answermetadata = null;
        
        String sqlCode = "";
        
        try {
            stmt = dbconn.createStatement();
            dbconn.setAutoCommit(false);
            
            printOptions();
            String command = input.nextLine().trim();
            
            while(!command.equals("quit")) {
                
                String[] inputData = command.split(" ");
                
                //---------------------------------------
                // CREATE TABLE
                //---------------------------------------
                if(inputData[0].equals("create")) {
                    String tableName = inputData[1];
                    
                    if(tableName.equals("*")) {
                        for(int i=0; i<allTables.length; i++) {
                            tableName = allTables[i][0];
                            sqlCode = createTable(tableName);
                            try {
                                stmt.execute(sqlCode);
                                //stmt.execute("GRANT SELECT ON " + username + "." + tableName + " TO PUBLIC");
                                stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO amains");
                                stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO sureshdevendran");
                                stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO ameliamarglon");
                                stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO ztkeane");
                                stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO mccann");
                                stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO hlim1");
                                stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO baileynottingham");
                            } catch (SQLException e) {
                                System.err.println("Table " + tableName + ": " + e.getMessage());
                            }
                        }
                    } else {
                        sqlCode = createTable(tableName);
                        try {
                            stmt.execute(sqlCode);
                            //stmt.execute("GRANT SELECT ON " + username + "." + tableName + " TO PUBLIC");
                            stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO amains");
                            stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO sureshdevendran");
                            stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO ameliamarglon");
                            stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO ztkeane");
                            stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO mccann");
                            stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO hlim1");
                            stmt.execute("GRANT SELECT, INSERT, UPDATE, DELETE ON " + username + "." + tableName + " TO baileynottingham");
                        } catch (SQLException e) {
                            System.err.println("Table " + tableName + ": " + e.getMessage());
                        }
                    }                    
                    
                    System.out.println("Create done.");
                } else
                
                //---------------------------------------
                // POPULATE TABLE
                //---------------------------------------
                if(inputData[0].equals("populate")) {
                    String tableName = inputData[1];
                    
                    if(tableName.equals("*")) {
                        for(int i=0; i<allTables.length; i++) {
                            tableName = allTables[i][0];
                            populateTable(username, tableName, stmt);
                        }
                    } else {
                        populateTable(username, tableName, stmt);
                    }
                    
                    System.out.println("Populate done.");
                }
                
                //---------------------------------------
                // DROP TABLE
                //---------------------------------------
                if(inputData[0].equals("drop")) {
                    String tableName = inputData[1];
                    
                    if(tableName.equals("*")) {
                        for(int i=allTables.length-1; i>=0; i--) {
                            tableName = allTables[i][0];
                            sqlCode = dropTable(username, tableName);
                            try {
                                stmt.execute(sqlCode);
                            } catch (SQLException e) {
                                System.err.println("Table " + tableName + ": " + e.getMessage());
                            }
                        }
                    } else {
                        sqlCode = dropTable(username, tableName);
                        try {
                            stmt.execute(sqlCode);
                        } catch (SQLException e) {
                            System.err.println("Table " + tableName + ": " + e.getMessage());
                        }
                    }
                    
                    System.out.println("Drop done.");
                } else
                
                //---------------------------------------
                // QUERY
                //---------------------------------------
                /*if(inputData[0].equals("query")) {
                    sqlCode = queryTable();
                    answer = stmt.executeQuery(sqlCode);
                    //TODO: error
                    //displayQueryResult(answer, answermetadata);
                    System.out.println("Query done.");
                }*/
                
                dbconn.commit();
                
                System.out.println();
                printOptions();
                command = input.nextLine().trim();
            }
            
            input.close();
            
            // Shut down the connection to the DBMS.
            stmt.close();  
            dbconn.close();
            
        /*} catch (IOException e) {
            
            System.err.println("IOException: could not read file.");
            System.exit(-1);*/
            
        } catch (SQLException e) {
            
            System.err.println("*** SQLException:  Could not execute statement.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
            
        }
        
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  printOptions()
     |         Purpose:  Prints the program options for user convenience.
     |  Pre-Conditions:  None.
     | Post-Conditions:  None.
     |      Parameters:  None.
     |         Returns:  None.
     *------------------------------------------------------------------------*/
    private static void printOptions() {
        System.out.println("+---------------------------------------------------------------------");
        System.out.println("| Progam Command Options:");
        System.out.println("|  1) create <table name>");
        System.out.println("|  2) populate <table name>");
        System.out.println("|  3) drop <table name>");
        //System.out.println("|  4) query");
        System.out.println("|  4) quit");
        System.out.println("+---------------------------------------------------------------------");
        System.out.print(" > ");
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  createTable()
     |         Purpose:  Creates the SQL command needed to create the specified
     |                   table.
     |  Pre-Conditions:  ?
     | Post-Conditions:  ?
     |      Parameters:  String tableName - the name of the table
     |         Returns:  The String SQl command to create tableName.
     *------------------------------------------------------------------------*
     | TODO: order of create matters for foreign keys?
     *------------------------------------------------------------------------*/
    private static String createTable(String tableName) {
        
        switch(tableName) {
            
            case "patient":
                return "CREATE TABLE patient (\n"
                     + "pid "        + "VARCHAR2(7) NOT NULL,"   + "\n"  //NOT NULL	NO UPDATE
                     + "lname "      + "VARCHAR2(20) NOT NULL,"  + "\n"  //NOT NULL
                     + "fname "      + "VARCHAR2(20) NOT NULL,"  + "\n"  //NOT NULL
                     + "gender "     + "VARCHAR2(1),"            + "\n"
                     + "dob "        + "DATE NOT NULL,"          + "\n"  //NOT NULL	NO UPDATE
                     + "address "    + "VARCHAR(50),"   + "\n"
                     + "contactNo "  + "VARCHAR(10),"   + "\n"
                     + "primary key (pid)"              + "\n"  
                     + ")";
            
            case "doctor":
                return "CREATE TABLE doctor (\n"
                     + "did "       + "VARCHAR2(7) NOT NULL,"   + "\n"   //NOT NULL	NO UPDATE
                     + "lname "     + "VARCHAR2(20) NOT NULL,"  + "\n"	//NOT NULL
                     + "fname "     + "VARCHAR2(20) NOT NULL,"  + "\n"	//NOT NULL
                     + "dob "       + "DATE NOT NULL,"          + "\n"	//NOT NULL	NO UPDATE
                     + "status "    + "VARCHAR(12),"   + "\n"
                     + "deptId "    + "INTEGER,"       + "\n"
                     + "officeNo "  + "INTEGER,"       + "\n"
                     + "primary key (did),"             + "\n" 
                     + "foreign key (deptId) references department (deptId),\n" 
                     + "foreign key (officeNo) references room (roomNo)\n"
                     + ")";
            
            case "pharmacist":
                return "CREATE TABLE pharmacist (\n"
                     + "phid "      + "VARCHAR2(7) NOT NULL,"   + "\n"	//NOT NULL NO UPDATE
                     + "lname "     + "VARCHAR2(20) NOT NULL,"  + "\n"	//NOT NULL
                     + "fname "     + "VARCHAR2(20) NOT NULL,"  + "\n"	//NOT NULL
                     + "dob "       + "DATE NOT NULL,"          + "\n"	//NOT NULL NO UPDATE
                     + "deptId "    + "INTEGER,"       + "\n"
                     + "officeNo "  + "INTEGER,"       + "\n"
                     + "primary key (phid),"            + "\n" 
                     + "foreign key (deptId) references department (deptId),\n"
                     + "foreign key (officeNo) references room (roomNo)\n"
                     + ")";
            
            case "nurse":
                return "CREATE TABLE nurse (\n"
                     + "nid "     + "VARCHAR2(7) NOT NULL,"   + "\n"	//NOT NULL NO UPDATE
                     + "lname "   + "VARCHAR2(20) NOT NULL,"  + "\n"	//NOT NULL
                     + "fname "   + "VARCHAR2(20) NOT NULL,"  + "\n"	//NOT NULL
                     + "dob "     + "DATE NOT NULL,"          + "\n"	//NOT NULL NO UPDATE
                     + "deptId "  + "INTEGER,"       + "\n"
                     + "roomNo "  + "INTEGER,"       + "\n"
                     + "primary key (nid),"           + "\n"
                     + "foreign key (deptId) references department (deptId),\n"
                     + "foreign key (roomNo) references room (roomNo)\n"
                     + ")";
            
            case "staff":
                return "CREATE TABLE staff (\n"
                     + "eid "        + "VARCHAR2(7) NOT NULL,"   + "\n"	//NOT NULL NO UPDATE
                     + "lname "      + "VARCHAR2(20) NOT NULL,"  + "\n"	//NOT NULL
                     + "fname "      + "VARCHAR2(20) NOT NULL,"  + "\n"	//NOT NULL
                     + "dob "        + "DATE NOT NULL,"          + "\n"	//NOT NULL NO UPDATE
                     + "salary "     + "NUMERIC(9,2),"  + "\n"
                     + "deptId "     + "INTEGER,"       + "\n"
                     + "officeNo "   + "INTEGER,"       + "\n"
                     + "jobTitle "   + "VARCHAR2(20),"  + "\n"
                     + "gender "     + "VARCHAR2(1),"   + "\n"
                     + "contactNo "  + "VARCHAR(10),"   + "\n"
                     + "primary key (eid),"              + "\n"
                     + "foreign key (deptId) references department (deptId),\n"
                     + "foreign key (officeNo) references room (roomNo)\n"
                     + ")";
          
            case "building":
                return "CREATE TABLE building (\n"
                     + "buildingName "  + "VARCHAR2(20) NOT NULL,"    + "\n"
                     + "address "       + "VARCHAR2(50),"    + "\n"
                     + "primary key (buildingName)"          + "\n"
                     + ")";
            
            case "department":
                return "CREATE TABLE department (\n"
                     + "deptId "    + "INTEGER NOT NULL,"       + "\n"	//NOT NULL NO UPDATE
                     + "name "      + "VARCHAR2(20),"  + "\n"
                     + "officeNo "  + "INTEGER,"       + "\n"
                     + "primary key (deptId),"          + "\n"
                     + "foreign key (officeNo) references room (roomNo)\n"
                     + ")";
            
            case "room":
                return "CREATE TABLE room (\n"
                     + "roomNo INTEGER, \n"
                     + "buildingName VARCHAR2(20), \n"
                     + "roomType VARCHAR2(20), \n"
                     + "patientOccupancy INTEGER, \n"
                     + "staffOccupancy INTEGER, \n"
                     + "primary key (roomNo),\n"
                     + "foreign key (buildingName) references building (buildingName),\n"
                     + "foreign key (roomType) references roomType (roomType)\n"
                     + ")";
            
            case "roomType":
                return "CREATE TABLE roomType (\n"
                     + "roomType "         + "VARCHAR2(20),"  + "\n"
                     + "patientCapacity "  + "INTEGER,"       + "\n"
                     + "staffCapacity "    + "INTEGER,"       + "\n"
                     + "primary key (roomType)"               + "\n"
                     + ")";
            
            case "medicine":
                return "CREATE TABLE medicine (\n"
                     + "pid "         + "VARCHAR2(7),"   + "\n"
                     + "phid "        + "VARCHAR2(7),"   + "\n"
                     + "medName "     + "VARCHAR2(20),"  + "\n"
                     + "daysToTake "  + "INTEGER,"       + "\n"
                     + "primary key (pid, phid),"         + "\n"
                     + "foreign key (pid) references patient (pid),\n"
                     + "foreign key (phid) references pharmacist (phid)\n"
                     + ")";
            
            case "appointment":
                return "CREATE TABLE appointment (\n"
                     + "pid "       + "VARCHAR2(7),"  + "\n"
                     + "apmtDate "  + "DATE,"         + "\n"
                     + "apmtNo "    + "INTEGER,"      + "\n"
                     + "eid "       + "VARCHAR2(7),"  + "\n"
                     + "primary key (pid, apmtNo),"    + "\n"
                     + "foreign key (pid) references patient (pid),\n"
                     + "foreign key (eid) references staff (eid)\n"
                     + ")";
            
            case "payment":
                return "CREATE TABLE payment (\n"
                     + "pid "        + "VARCHAR2(7) NOT NULL,"   + "\n"	//NOT NULL NO UPDATE
                     + "apmtNo "     + "INTEGER NOT NULL,"       + "\n"	//NOT NULL
                     + "amountDue "  + "NUMERIC(9,2) NOT NULL,"  + "\n"	//NOT NULL
                     + "dueDate "    + "DATE NOT NULL,"          + "\n"	//NOT NULL
                     + "payStatus "  + "VARCHAR2(10) NOT NULL,"  + "\n"	//NOT NULL
                     + "payDate "    + "DATE,"                   + "\n"
                     + "eid "        + "VARCHAR2(7) NOT NULL,"   + "\n"	//NOT NULL
                     + "primary key (pid, apmtNo),"      + "\n"
                     + "foreign key (pid) references patient (pid),\n" 
                     + "foreign key (pid, apmtNo) references treatment (pid, apmtNo),\n"
                     + "foreign key (eid) references staff (eid)\n"
                     + ")";
            
            case "treatment":
                return "CREATE TABLE treatment (\n"
                     + "pid "        + "VARCHAR2(7) NOT NULL,"    + "\n"		//NOT NULL NO UPDATE
                     + "apmtNo "     + "INTEGER NOT NULL,"        + "\n"		//NOT NULL NO UPDATE
                     + "visitRsn "   + "VARCHAR2(255) NOT NULL,"  + "\n"		//NOT NULL NO UPDATE
                     + "visitDate "  + "DATE NOT NULL,"           + "\n"		//NOT NULL NO UPDATE
                     + "ihd "        + "DATE,"           + "\n"
                     + "edd "        + "DATE,"           + "\n"
                     + "discharge "  + "DATE,"           + "\n"
                     + "roomNo "     + "INTEGER,"        + "\n"
                     + "treatment "  + "VARCHAR2(8),"    + "\n"
                     + "did "        + "VARCHAR2(7),"    + "\n"
                     + "primary key (pid, apmtNo),"       + "\n"
                     + "foreign key (pid) references patient (pid),\n" 
                     + "foreign key (pid, apmtNo) references appointment (pid, apmtNo),\n"
                     + "foreign key (did) references doctor (did)\n"
                     + ")";
        }
        
        return ""; // TODO: default case
        
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  getTableFile()
     |         Purpose:  Fetches the file name of the table's data.
     |  Pre-Conditions:  File path is correct.
     | Post-Conditions:  None.
     |      Parameters:  String tableName - the table to populate.
     |         Returns:  The String file location of the table's data.
     *------------------------------------------------------------------------*/
    private static String getTableFile(String tableName) {
        for(int i=0; i<allTables.length; i++) {
            if(tableName.equals(allTables[i][0])) {
                return allTables[i][1];
            }
        }
        return ""; // TODO: default case
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  populateTable()
     |         Purpose:  Fills the selected table with it's specified data.
     |  Pre-Conditions:  ?
     | Post-Conditions:  ?
     |      Parameters:  String username - the current user
     |                   String tableName - the table to populate.
     |                   Statement stmt - object to execute SQL commands
     |         Returns:  None.
     *------------------------------------------------------------------------*
     | TODO: check for NULL values
     *------------------------------------------------------------------------*/
    private static void populateTable(String username, String tableName, Statement stmt) {
        String fileName = getTableFile(tableName);
        File file = new File(fileName);
        
        try {
            
            if(file.exists()) {
                
                Scanner scanner = new Scanner(file);
                
                String[] columns = null;
                String line = "";
                String[] data = null;
                String insert = "";
                
                switch(tableName) {
                    
                    case "patient":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + "'" + data[0] + "', "
                                    + "'" + data[1] + "', "
                                    + "'" + data[2] + "', "
                                    + "'" + data[3] + "', "
                                    + "TO_DATE ('" + data[4] + "', 'MM/DD/YYYY'), "
                                    + "'" + data[5] + "', "
                                    + "'" + data[6] + "'"
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;
                        
                    case "doctor":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + "'" + data[0] + "', "
                                    + "'" + data[1] + "', "
                                    + "'" + data[2] + "', "
                                    + "TO_DATE ('" + data[3] + "', 'MM/DD/YYYY'), "
                                    + "'" + data[4] + "', "
                                    + data[5] + ", "
                                    + data[6]
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;
                        
                    case "pharmacist":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + "'" + data[0] + "', "
                                    + "'" + data[1] + "', "
                                    + "'" + data[2] + "', "
                                    + "TO_DATE ('" + data[3] + "', 'MM/DD/YYYY'), "
                                    + data[4] + ", "
                                    + data[5]
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;
                        
                    case "nurse":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + "'" + data[0] + "', "
                                    + "'" + data[1] + "', "
                                    + "'" + data[2] + "', "
                                    + "TO_DATE ('" + data[3] + "', 'MM/DD/YYYY'), "
                                    + data[4] + ", "
                                    + data[5]
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;
                        
                    case "staff":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + "'" + data[0] + "', "
                                    + "'" + data[1] + "', "
                                    + "'" + data[2] + "', "
                                    + "TO_DATE ('" + data[3] + "', 'MM/DD/YYYY'), "
                                    + data[4] + ", "
                                    + data[5] + ", "
                                    + data[6] + ", "
                                    + "'" + data[7] + "', "
                                    + "'" + data[8] + "', "
                                    + "'" + data[9] + "'"
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;
                        
                    /*case "receptionist":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + "'" + data[0] + "', "
                                    + "'" + data[1] + "', "
                                    + "'" + data[2] + "', "
                                    + "TO_DATE ('" + data[3] + "', 'MM/DD/YYYY'), "
                                    + data[4] + ", "
                                    + data[5] + ", "
                                    + data[6] + ", "
                                    + "'" + data[7] + "', "
                                    + "'" + data[8] + "', "
                                    + "'" + data[9] +  "'"
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;*/
                        
                    case "building":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + "'" + data[0] + "', "
                                    + "'" + data[1] + "'"
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;
                        
                    case "department":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + data[0] + ", "
                                    + "'" + data[1] + "', "
                                    + data[2]
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;
                        
                    case "room":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + data[0] + ", "
                                    + "'" + data[1] + "', "
                                    + "'" + data[2] + "', "
                                    + data[3] + ", "
                                    + data[4]
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;
                        
                    case "roomType":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + "'" + data[0] + "', "
                                    + data[1] + ", "
                                    + data[2]
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;
                        
                    case "medicine":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            String medName = data[2];
                            if(!data[2].equals("NULL")) { medName = "'" + data[2] + "'"; }
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + "'" + data[0] + "', "
                                    + "'" + data[1] + "', "
                                    //+ "'" + data[2] + "', "
                                    + medName + ", "
                                    + data[3]
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;
                        
                    case "appointment":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + "'" + data[0] + "', "
                                    + "TO_DATE ('" + data[1] + "', 'MM/DD/YYYY'), "
                                    + data[2] + ", "
                                    + "'" + data[3] + "'"
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;
                        
                    case "payment":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            String payDate = data[5];
                            if(!payDate.equals("NULL")) { payDate = "TO_DATE ('" + data[5] + "', 'MM/DD/YYYY')"; }
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + "'" + data[0] + "', "
                                    + data[1] + ", "
                                    + data[2] + ", "
                                    + "TO_DATE ('" + data[3] + "', 'MM/DD/YYYY'), "
                                    + "'" + data[4] + "', "
                                    + payDate +  ", "
                                    + "'" + data[6] + "'"
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;
                        
                    case "treatment":
                        columns = scanner.nextLine().split("\t");
                        while(scanner.hasNextLine()) {
                            line = scanner.nextLine().replace("'", "''");
                            data = line.split("\t");
                            String discharge = data[6];
                            if(!discharge.equals("NULL")) { discharge = "TO_DATE ('" + data[6] + "', 'MM/DD/YYYY')"; }
                            insert = "INSERT INTO " + username + "." + tableName + " VALUES ("
                                    + "'" + data[0] + "', "
                                    + data[1] + ", "
                                    + "'" + data[2] + "', "
                                    + "TO_DATE ('" + data[3] + "', 'MM/DD/YYYY'), "
                                    + "TO_DATE ('" + data[4] + "', 'MM/DD/YYYY'), "
                                    + "TO_DATE ('" + data[5] + "', 'MM/DD/YYYY'), "
                                    + discharge + ", "
                                    + data[7] + ", "
                                    + "'" + data[8] + "', "
                                    + "'" + data[9] + "'"
                                    + ")";
                            stmt.execute(insert);
                        }
                        break;
                    
                }
                
                scanner.close();
                
            }
            
        } catch (FileNotFoundException e) {
            
            System.err.println("File " + fileName + " does not exist.");
            
        } catch (SQLException e) {
            
            System.err.println("*** SQLException:  Could not execute statement.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
            
        }
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  dropTable()
     |         Purpose:  Drop the selected table from the current user'salary
     |                   environment.
     |  Pre-Conditions:  ?
     | Post-Conditions:  ?
     |      Parameters:  String username - the current user
     |                   String tableName - the name of the table to drop
     |         Returns:  The SQL command to drop the given table.
     *------------------------------------------------------------------------*/
    private static String dropTable(String username, String tableName) {
        return "DROP TABLE " + username + "." + tableName;
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  queryTable()
     |         Purpose:  Builds a query from the user on the database.
     |  Pre-Conditions:  Tables to query must exist.
     | Post-Conditions:  None.
     |      Parameters:  None.
     |         Returns:  The String SQL query.
     *------------------------------------------------------------------------*/
    private static String queryTable() {        
        String query = "";
        int count = 1;
        
        System.out.printf("%3d ", count);
        String queryLine = input.nextLine().trim();
        
        while(!queryLine.endsWith(";")) {
            query = query + "\n" + queryLine;
            
            count++;
            System.out.printf("%3d ", count);
            queryLine = input.nextLine().trim();
        }
        
        query = query + "\n" + queryLine;
        query = query.substring(0, query.length());
        return query.trim();
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  displayQueryResult()
     |         Purpose:  Displays the result of the query to the database.
     |  Pre-Conditions:  ?
     | Post-Conditions:  ?
     |      Parameters:  ResultSet answer - the query result object
     |                   ResultSetMetaData answermetadata - result metadata
     |         Returns:  None.
     *------------------------------------------------------------------------*/
    private static void displayQueryResult(ResultSet answer, ResultSetMetaData answermetadata) {
        try {
            
            if(answer != null) {
                // Get the data about the query result to learn the attribute names and use them as column headers
                answermetadata = answer.getMetaData();
                
                // Store columns headers
                String[] columns = new String[answermetadata.getColumnCount()];
                for (int i = 0; i < answermetadata.getColumnCount(); i++) {
                    columns[i] = answermetadata.getColumnName(i+1);
                }
                
                for (int i = 0; i < columns.length; i++) {
                    System.out.print(columns[i] + "\t");
                }
                System.out.println();

                //TODO: format output
                
                // Use next() to advance cursor through the result tuples and print their attribute values
                /*while (answer.next()) {
                    System.out.println(answer.getInt("distNo") + "\t"
                        + answer.getString("distName") + "\t"
                        + answer.getInt("schoolNo")
                        + answer.getString("schoolName") + "\t"
                        + answer.getInt("stuCount") + "\t"
                        + answer.getInt("meanScore") + "\t"
                        + answer.getInt("passing") + "\t"
                        + answer.getInt("lvl1") + "\t"
                        + answer.getInt("lvl2") + "\t"
                        + answer.getInt("lvl3") + "\t"
                        + answer.getInt("lvl4") + "\t"
                        + answer.getInt("lvl5") + "\t");
                }*/
                System.out.println();
            }
            
        } catch (SQLException e) {
            
            System.err.println("*** SQLException:  Could not execute statement.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
            
        }
    }
    
}