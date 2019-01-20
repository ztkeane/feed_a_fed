//Suresh Devendran and Zachary Keane

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
public class DoctorController {

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @GetMapping("/doctor")
    public String doctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor";
    }

    /*------------------------------------------------------------------------*
     |          Method:  doctorSubmit(Doctor)
     |         Purpose:  To handle the processing of information with the database.
     |  Pre-Conditions:  That there is some database functioning.
     | Post-Conditions:  The database has been updated.
     |      Parameters:  Doctor, the Doctor object created from user input.
     |         Returns:  String, the HTML page.
     *------------------------------------------------------------------------*/
    @PostMapping("/doctor")
    public String doctorSubmit(@ModelAttribute Doctor doctor) {
        //FIRST, DID
        String did = doctor.getDid().toLowerCase();
        
        //DID does not start properly
        if (!(did.startsWith("dr"))) {
            System.out.println("Invalid DID.");
            return "doctor";
        }
        //DID has no integer value.
        if (did.length() < 3) {
            System.out.println("DID has no value.");
            return "doctor";
        }
        //Ensure DID has only integer values after dr
        for (int a = 2; a < did.length(); a++) {
            if (!Character.isDigit(did.charAt(a))) {
                System.out.println("DID has a non-integer value after dr");
                return "doctor";
            }
        }
        
        if(doctor.getAud().equals("add") || doctor.getAud().equals("update")) {
            //SECOND, LNAME
            String lname = doctor.getLname();
            //Ensure lname has no numbers.
            for (int j = 0; j < lname.length(); j++) {
                if (!Character.isLetter(lname.charAt(j))) {
                    System.out.println("Bad last name.");
                    return "doctor";
                }
            }
            
            //THIRD, FNAME
            String fname = doctor.getFname();
            //Ensure fname has no numbers.
            for (int i = 0; i < fname.length(); i++) {
                if (!Character.isLetter(fname.charAt(i))) {
                    System.out.println("Bad first name.");
                    return "doctor";
                }
            }
            
            //FOURTH, DEPTID
            String deptId = doctor.getDeptid();
            //Ensure deptId is just a number.
            for (int k = 0; k < deptId.length(); k++) {
                if (!Character.isDigit(deptId.charAt(k))) {
                    System.out.println("Non-digit found in deptId");
                    return "doctor";
                }
            }
            
            //FIFTH, OFFICENO
            String officeNo = doctor.getOfficeno();
            //Ensure officeNo is just a number.
            for (int m = 0; m < officeNo.length(); m++) {
                if (!Character.isDigit(officeNo.charAt(m))) {
                    System.out.println("Non-digit found in officeNo");
                    return "doctor";
                }
            }
            //Ensure that the office number is between 1-300 inclusive.
            int officeNoVal = Integer.parseInt(officeNo);
            if (officeNoVal < 1 || officeNoVal > 300) {
                System.out.println("Invalid officeNo");
                return "doctor";
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
    @GetMapping("/allDoctors")
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

}
