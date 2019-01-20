/*---------------------------------------------------------------------------*
 | Class <DoctorController>
 *---------------------------------------------------------------------------*
 |        Author:  Ashley Mains, Amelia Marglon, Suresh Devendran, Zach Keane
 |       Purpose:  To control the flow of information to the Doctor class.
 | Inherits From:  None.
 |    Interfaces:  None.
 *---------------------------------------------------------------------------*
 |     Constants:  None.
 *---------------------------------------------------------------------------*
 |  Constructors:  None.
 | Class Methods:  None.
 | Inst. Methods:  postConstruct(), doctorForm(Model), doctorSubmit(Doctor),
 allDoctors(Model)
 *---------------------------------------------------------------------------*/

package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class EmployeeController {
    
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @GetMapping("/employee")
    public String doctorForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee";
    }
    
    /*private String aud;
    private String eid;
    private String name;
    private String email;
    private String city;
    private String county;
    private String state;
    private String phoneNo;
    private int dependents;*/
    
    @PostMapping("/employee")
    public String employeeSubmit(@ModelAttribute Employee employee) {
        /*//FIRST, EID
        String eid = employee.getEid().toLowerCase();
        
        //EID does not start properly
        if (!(eid.startsWith("e"))) {
            System.out.println("Invalid EID.");
            return "employee";
        }
        //EID has no integer value.
        if (eid.length() < 3) {
            System.out.println("EID has no value.");
            return "employee";
        }
        //Ensure EID has only integer values after e
        for (int a = 2; a < eid.length(); a++) {
            if (!Character.isDigit(eid.charAt(a))) {
                System.out.println("EID has a non-integer value after e");
                return "employee";
            }
        }
        
        if(employee.getAud().equals("add") || employee.getAud().equals("update")) {
            //SECOND, NAME
            String name = employee.getName();
            //Ensure name has no numbers.
            for (int j = 0; j < name.length(); j++) {
                if (!Character.isLetter(name.charAt(j)) && name.charAt(j) != " ") {
                    System.out.println("Bad name.");
                    return "employee";
                }
            }
            
            String city = employee.getCity();
            for (int k = 0; k < city.length(); k++) {
                if (!Character.isLetter(city.charAt(k)) && city.charAt(k) != " ") {
                    System.out.println("Non-letter found in city.");
                    return "employee";
                }
            }
            
            String county = employee.getCounty();
            for (int l = 0; l < county.length(); l++) {
                if (!Character.isLetter(county.charAt(l)) && county.charAt(l) != " ") {
                    System.out.println("Non-letter found in county.");
                    return "employee";
                }
            }
            
            String state = employee.getState();
            for (int m = 0; m < state.length(); m++) {
                if (!Character.isLetter(state.charAt(m)) && state.charAt(m) != " ") {
                    System.out.println("Non-letter found in city.");
                    return "employee";
                }
            }
            
            String phoneNo = employee.getPhoneNo();
            for (int n = 0; n < phoneNo.length(); n++) {
                if (!Character.isDigit(phoneNo.charAt(n)) && phone.charAt(n) != " ") {
                    System.out.println("phoneNo has non-digit.");
                    return "employee";
                }
            }
            
        }
        System.out.println("COMPLETE Doctor valid!");*/
        
        if(employee.getAud().equals("add")) {
            System.out.println("Adding");
            try {
                jdbcTemplate.update("INSERT INTO ztkeane.employee VALUES ("
                                    + "'" + employee.getEid().toLowerCase() + "', "
                                    + "'" + employee.getName() + "', "
                                    + "'" + employee.getEmail() + "', "
                                    + "'" + employee.getCity() + "', "
                                    + "'" + employee.getCounty() + "', "
                                    + "'" + employee.getState() + "', "
                                    + "'" + employee.getPhoneNo() + "', "
                                    + employee.getDependents()
                                    + ")"
                                    );
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Duplicate employee EID entered!");
                return "employee";
            }
        }
        else if(employee.getAud().equals("update")) {
            System.out.println("Updating");
            try {
                jdbcTemplate.update("UPDATE ztkeane.employee SET "
                                    + "name = '" + employee.getName() + "', "
                                    + "email = '" + employee.getEmail() + "', "
                                    + "city = '" + employee.getCity() + "', "
                                    + "county = '" + employee.getCounty() + "', "
                                    + "state = '" + employee.getState() + "' "
                                    + "phoneNo = '" + employee.getPhoneNo() + "', "
                                    + "WHERE eid = '" + employee.getEid().toLowerCase() + "'"
                                    );
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Employee cannot be updated!");
                return "employee";
            }
        }
        // if (doctor.getAud().equals("delete"))
        else {
            System.out.println("Deleting");
            try {
                jdbcTemplate.update("DELETE FROM ztkeane.employee "
                                    + "WHERE eid = '" + employee.getEid().toLowerCase() + "'"
                                    );
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Employee cannot be deleted!");
                return "employee";
            }
        }
        return "success";
    }
    
    /*@GetMapping("/deletePerson")
     public String personFormDelete(Model model) {
     model.addAttribute("person", new Person());
     return "deletePerson";
     }
     
     @PostMapping("/deletePerson")
     public String personDelete(@ModelAttribute Person person) {
     jdbcTemplate.update("delete from baileynottingham.people where first_name = ? and last_name = ?", person.getFirstName(), person.getLastName());
     return "deletePersonResult";
     }*/
    
    /*------------------------------------------------------------------------*
     |          Method:  allDoctors(Model)
     |         Purpose:  Forms the Doctor table.
     |  Pre-Conditions:  The database is updated properly.
     | Post-Conditions:  The user can view all doctors.
     |      Parameters:  Model
     |         Returns:  String, the HTML page.
     *------------------------------------------------------------------------*/
    @GetMapping("/allEmployees")
    public String allEmployees(Model model) {
        List<Employee> allData = this.jdbcTemplate.query("select * from ztkeane.employee", new RowMapper<Employee>() {
                public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Employee employee = new Employee();
                    employee.setEid(rs.getString("eid"));
                    employee.setName(rs.getString("name"));
                    employee.setEmail(rs.getString("email"));
                    employee.setCity(rs.getString("city"));
                    employee.setCounty(rs.getString("county"));
                    employee.setState(rs.getString("state"));
                    employee.setState(rs.getInt("dependents").toString());
                    employee.setPhoneNo(rs.getString("phoneNo"));
                    return employee;
                }
            });
        model.addAttribute("data", allData);
        return "/allEmployees";
    }
    
}
