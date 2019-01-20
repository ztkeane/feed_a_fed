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
public class HelpCenterController {
    
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @GetMapping("/helpCenter")
    public String helpCenterForm(Model model) {
        model.addAttribute("helpCenter", new HelpCenter());
        return "helpCenter";
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  doctorSubmit(Doctor)
     |         Purpose:  To handle the processing of information with the database.
     |  Pre-Conditions:  That there is some database functioning.
     | Post-Conditions:  The database has been updated.
     |      Parameters:  Doctor, the Doctor object created from user input.
     |         Returns:  String, the HTML page.
     *------------------------------------------------------------------------*/
    @PostMapping("/helpCenter")
    public String helperSubmit(@ModelAttribute HelpCenter helpCenter) {
        
        if(helpCenter.getAud().equals("add")) {
            System.out.println("Adding");
            try {
                jdbcTemplate.update("INSERT INTO ztkeane.helpCenter VALUES ("
                                    + "'" + helpCenter.getCid().toLowerCase() + "', "
                                    + "'" + helpCenter.getName() + "', "
                                    + "'" + helpCenter.getAddress() + "', "
                                    + "'" + helpCenter.getPhoneNo() + "', "
                                    + "'" + helpCenter.getCity() + "', "
                                    + "'" + helpCenter.getCounty() + "', "
                                    + "'" + helpCenter.getState() + "' "
                                    + ")"
                                    );
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Duplicate helpCenter CID entered!");
                return "helpCenter";
            }
        }
        else if(helpCenter.getAud().equals("update")) {
            System.out.println("Updating");
            try {
                jdbcTemplate.update("UPDATE ztkeane.helpCenter SET "
                                    + "phoneNo = '" + helpCenter.getPhoneNo() + "', "
                                    + "name = '" + helpCenter.getName() + "', "
                                    + "address = '" + helpCenter.getAddress() + "', "
                                    + "city = '" + helpCenter.getCity() + "' "
                                    + "county = '" + helpCenter.getCounty() + "' "
                                    + "state = '" + helpCenter.getState() + "' "
                                    + "WHERE cid = '" + helpCenter.getCid().toLowerCase() + "'"
                                    );
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("HelpCenter cannot be updated!");
                return "helpCenter";
            }
        }
        // if (doctor.getAud().equals("delete"))
        else {
            System.out.println("Deleting");
            try {
                jdbcTemplate.update("DELETE FROM ztkeane.helpCenter "
                                    + "WHERE cid = '" + helpCenter.getCid().toLowerCase() + "'"
                                    );
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("helpCenter cannot be deleted!");
                return "helpCenter";
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
    @GetMapping("/allHelpCenters")
    public String allHelpCenters(Model model) {
        List<HelpCenter> allData = this.jdbcTemplate.query("select * from ztkeane.helpCenter", new RowMapper<HelpCenter>() {
                public HelpCenter mapRow(ResultSet rs, int rowNum) throws SQLException {
                    HelpCenter helpCenter = new HelpCenter();
                    helpCenter.setCid(rs.getString("cid"));
                    helpCenter.setName(rs.getString("name"));
                    helpCenter.setAddress(rs.getString("address"));
                    helpCenter.setCity(rs.getString("city"));
                    helpCenter.setCounty(rs.getString("county"));
                    helpCenter.setState(rs.getString("state"));
                    helpCenter.setPhoneNo(rs.getString("phoneNo"));
                    return helpCenter;
                }
            });
        model.addAttribute("data", allData);
        return "/allHelpCenters";
    }
}
