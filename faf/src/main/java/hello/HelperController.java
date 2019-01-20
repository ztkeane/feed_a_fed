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
        
        if(helper.getAud().equals("add")) {
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
        else if(helper.getAud().equals("update")) {
            System.out.println("Updating");
            try {
                jdbcTemplate.update("UPDATE ztkeane.helper SET "
                                    + "name = '" + helper.getName() + "', "
                                    + "email = '" + helper.getEmail() + "', "
                                    + "phoneNo = '" + helper.getPhoneNo() + "', "
                                    + "description = '" + helper.getDescription() + "', "
                                    + "city = '" + helper.getCity() + "', "
                                    + "county = '" + helper.getCounty() + "', "
                                    + "state = '" + helper.getState() + "' "
                                    + "WHERE hid = '" + helper.getHid().toLowerCase() + "'"
                                    );
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Helper cannot be updated!");
                return "helper";
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
                return "helper";
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
    @GetMapping("/allHelpers")
     public String allHelpers(Model model) {
         List<Helper> allData = this.jdbcTemplate.query("select * from ztkeane.helper",
         new RowMapper<Helper>() {
                public Helper mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Helper helper = new Helper();
                    helper.setHid(rs.getString("hid"));
                    helper.setName(rs.getString("name"));
                    helper.setEmail(rs.getString("email"));
                    helper.setCity(rs.getString("city"));
                    helper.setCounty(rs.getString("county"));
                    helper.setState(rs.getString("state"));
                    helper.setDescription(rs.getString("description"));
                    helper.setPhoneNo(rs.getString("phoneNo"));
                    return helper;
                }
         });
         model.addAttribute("data", allData);
         return "/allHelpers";
     }
}
