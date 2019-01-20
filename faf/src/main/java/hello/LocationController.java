/*---------------------------------------------------------------------------*
 | Class <HelperController>
 *---------------------------------------------------------------------------*
 |        Author:  Suresh Devendran, Zach Keane
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
public class LocationController {
    
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @GetMapping("/location")
    public String locationForm(Model model) {
        model.addAttribute("location", new Location());
        return "location";
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  doctorSubmit(Doctor)
     |         Purpose:  To handle the processing of information with the database.
     |  Pre-Conditions:  That there is some database functioning.
     | Post-Conditions:  The database has been updated.
     |      Parameters:  Doctor, the Doctor object created from user input.
     |         Returns:  String, the HTML page.
     *------------------------------------------------------------------------*/
    @PostMapping("/location")
    public String locationSubmit(@ModelAttribute Location location) {
        
        if(location.getAud().equals("add")) {
            System.out.println("Adding");
            try {
                jdbcTemplate.update("INSERT INTO ztkeane.location VALUES ("
                                    + "'" + location.getCity() + "', "
                                    + "'" + location.getCounty() + "', "
                                    + "'" + location.getState() + "' "
                                    + ")"
                                    );
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Duplicate location entered!");
                return "location";
            }
        }
        else if(location.getAud().equals("update")) {
            ;
        }
        // if (doctor.getAud().equals("delete"))
        else {
            ;
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
    /*@GetMapping("/allDoctors")
     public String allDoctors(Model model) {
     List<Doctor> allData = this.jdbcTemplate.query(
     "select * from amains.doctor",
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
     } else {
     doctor.setDeptid("");
     }
     int officeNo = rs.getInt("officeNo");
     if(officeNo != 0) {
     doctor.setOfficeno(""+officeNo);
     } else {
     doctor.setOfficeno("");
     }
     return doctor;
     }
     });
     model.addAttribute("data", allData);
     return "/allDoctors";
     }
     */
}
