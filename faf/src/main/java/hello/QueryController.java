/*---------------------------------------------------------------------------*
 | Class <QueryController>
 *---------------------------------------------------------------------------*
 |        Author:  Ashley Mains, Amelia Marglon, Suresh Devendran, Zach Keane
 |       Purpose:  Controls flow of information from all of the query classes.
 | Inherits From:  None.
 |    Interfaces:  None.
 *---------------------------------------------------------------------------*
 |     Constants:  None.
 *---------------------------------------------------------------------------*
 |  Constructors:  None.
 | Class Methods:  None.
 | Inst. Methods:  postConstruct(), query1Form(Model), query1Submit(Model, Query1),
                query2Form(Model), query2Submit(Model, Query2), query3Form(Model),
                query3Submit(Model, Query3), query4Form(Model), query4Submit(Model, Query4)
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
public class QueryController {

  @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    /******************************************************
     * QUERY #1
     ******************************************************/
        
    @GetMapping("/query1")
    public String query1Form(Model model) {
        model.addAttribute("query1", new Query1());
        return "query1";
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  query1Submit
     |         Purpose:  To perform actions according to this query.
     |  Pre-Conditions:  The database exists and can be queried.
     | Post-Conditions:  The query is given to the user.
     |      Parameters:  Model, Query1
     |         Returns:  String, the HTML page.
     *------------------------------------------------------------------------*/
    @PostMapping("/query1")
    public String query1Submit(Model model, @ModelAttribute Query1 query) {
        
        /*String sqlQuery1 = "SELECT p1.pid, p1.lname, p1.fname, p1.gender, p1.dob, t1.visitDate, t1.visitRsn, t1.treatment, t1.did "
            + "FROM amains.patient p1, amains.treatment t1 "
            + "WHERE p1.pid=t1.pid AND p1.fname='"+query.getFname()+"' AND p1.lname='"+query.getLname()+"' AND p1.dob=TO_DATE('"+query.getDob()+"', 'YYYY-MM-DD') "
            + "AND t1.visitDate >= ALL (SELECT t2.visitDate "
                + "FROM amains.patient p2, amains.treatment t2 "
                + "WHERE p2.pid=t2.pid AND p2.fname='"+query.getFname()+"' AND p2.lname='"+query.getLname()+"' AND p2.dob=TO_DATE('"+query.getDob()+"', 'YYYY-MM-DD'))";
        
        List<Query1Result> allData = this.jdbcTemplate.query(sqlQuery1,
            new RowMapper<Query1Result>() {
                public Query1Result mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Query1Result result = new Query1Result();
                    result.setPid(rs.getString("pid"));
                    result.setPname(rs.getString("fname") + " " + rs.getString("lname"));
                    result.setGender(rs.getString("gender"));
                    result.setDob(rs.getDate("dob").toString());
                    result.setVisitdate(rs.getDate("visitDate").toString());
                    result.setVisitrsn(rs.getString("visitRsn"));
                    result.setTreatment(rs.getString("treatment"));
                    result.setDid(rs.getString("did"));
                    return result;
                }
            });
        
        model.addAttribute("query1", query);
        model.addAttribute("data", allData);
        return "query1Result"; */
    }
    
}
