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
    
    private String aud;
    private String eid;
    private String name;
    private String email;
    private String city;
    private String county;
    private String state;
    private String phoneNo;
    private int dependents;
    
    @PostMapping("/employee")
    public String employeeSubmit(@ModelAttribute Employee employee) {
        //FIRST, EID
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
                if (!Character.isLetter(name.charAt(j))) && name.charAt(j) != " ") {
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
        System.out.println("COMPLETE Doctor valid!");
        
        // setting the correct did back to Doctor object
        doctor.setDid(did);
        
        System.out.println(doctor.getDid().toLowerCase() +" "+  doctor.getLname() +" "+  doctor.getFname() +" "+ doctor.getDob()+" "+ doctor.getStatus()+" "+ doctor.getDeptid()+" "+ doctor.getOfficeno());
        
        // sql query to insert the doctor object
        
        if(doctor.getAud().equals("add")) {
            System.out.println("Adding");
            try {
                jdbcTemplate.update("INSERT INTO amains.doctor VALUES ("
                                    + "'" + doctor.getDid().toLowerCase() + "', "
                                    + "'" + doctor.getLname() + "', "
                                    + "'" + doctor.getFname() + "', "
                                    + "TO_DATE ('" + doctor.getDob() + "', 'YYYY-MM-DD'), "
                                    + "'" + doctor.getStatus() + "', "
                                    + doctor.getDeptid() + ", "
                                    + doctor.getOfficeno()
                                    + ")"
                                    );
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Duplicate Doctor DID entered!");
                return "doctor";
            }
        }
        else if(doctor.getAud().equals("update")) {
            System.out.println("Updating");
            try {
                jdbcTemplate.update("UPDATE amains.doctor SET "
                                    + "lname = '" + doctor.getLname() + "', "
                                    + "fname = '" + doctor.getFname() + "', "
                                    + "status = '" + doctor.getStatus() + "', "
                                    + "deptId = " + doctor.getDeptid() + ", "
                                    + "officeNo = " + doctor.getOfficeno() + " "
                                    + "WHERE did = '" + doctor.getDid().toLowerCase() + "'"
                                    );
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Doctor cannot be updated!");
                return "doctor";
            }
        }
        // if (doctor.getAud().equals("delete"))
        else {
            System.out.println("Deleting");
            try {
                jdbcTemplate.update("DELETE FROM amains.doctor "
                                    + "WHERE did = '" + doctor.getDid().toLowerCase() + "'"
                                    );
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Doctor cannot be deleted!");
                return "doctor";
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
    @GetMapping("/allDoctors")
    public String allDoctors(Model model) {
        List<Doctor> allData = this.jdbcTemplate.query("select * from amains.doctor",
        new RowMapper<Doctor>() {
            public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
                Doctor doctor = new Doctor();
                doctor.setDid(rs.getString("did"));
                doctor.setLname(rs.getString("lname"));
                doctor.setFname(rs.getString("fname"));
                doctor.setDob(rs.getDate("dob").toString());
                doctor.setStatus(rs.getString("status"));
                int deptId = rs.getInt("deptId");
                if(deptId != 0) {
                    doctor.setDeptid(""+deptId);
                }
                else {
                    doctor.setDeptid("");
                }
                int officeNo = rs.getInt("officeNo");
                if(officeNo != 0) {
                    doctor.setOfficeno(""+officeNo);
                }
                else {
                    doctor.setOfficeno("");
                }
                return doctor;
            }
        });
        model.addAttribute("data", allData);
        return "/allDoctors";
    }
    
}
