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
public class HelperController {
    
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @GetMapping("/helper")
    public String helperForm(Model model) {
        model.addAttribute("helper", new Helper());
        return "helper";
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  doctorSubmit(Doctor)
     |         Purpose:  To handle the processing of information with the database.
     |  Pre-Conditions:  That there is some database functioning.
     | Post-Conditions:  The database has been updated.
     |      Parameters:  Doctor, the Doctor object created from user input.
     |         Returns:  String, the HTML page.
     *------------------------------------------------------------------------*/
    @PostMapping("/helper")
    public String helperSubmit(@ModelAttribute Helper helper) {
        
        if(doctor.getAud().equals("add")) {
            System.out.println("Adding");
            try {
                jdbcTemplate.update("INSERT INTO ztkeane.helper VALUES ("
                                    + "'" + helper.getHid().toLowerCase() + "', "
                                    + "'" + helper.getName() + "', "
                                    + "'" + helper.getEmail() + "', "
                                    + "'" + helper.getPhoneNo() + "', "
                                    + "'" + helper.getDescription() + "', "
                                    + "'" + helper.getCity() + "', "
                                    + "'" + helper.getCounty() + "', "
                                    + "'" + helper.getState() + "' "
                                    + ")"
                                    );
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Duplicate helper HID entered!");
                return "helper";
            }
        }
        else if(doctor.getAud().equals("update")) {
            System.out.println("Updating");
            try {
                jdbcTemplate.update("UPDATE ztkeane.helper SET "
                                    + "name = '" + helper.getName() + "', "
                                    + "email = '" + helper.getEmail() + "', "
                                    + "phoneNo = '" + helper.getPhoneNo() + "', "
                                    + "description = '" + helper.getDescription() + ", "
                                    + "city = '" + helper.getCity() + "', "
                                    + "county = '" + helper.getCounty() + "', "
                                    + "state = '" + helper.getState() + "' "
                                    + "WHERE hid = '" + helper.getHid().toLowerCase() + "'"
                                    );
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Helper cannot be updated!");
                return "doctor";
            }
        }
        // if (doctor.getAud().equals("delete"))
        else {
            System.out.println("Deleting");
            try {
                jdbcTemplate.update("DELETE FROM ztkeane.helper "
                                    + "WHERE hid = '" + helper.getHid().toLowerCase() + "'"
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
