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
        
        String sqlQuery1 = "SELECT p1.pid, p1.lname, p1.fname, p1.gender, p1.dob, t1.visitDate, t1.visitRsn, t1.treatment, t1.did "
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
        return "query1Result";
    }
    
    /******************************************************
     * QUERY #2
     ******************************************************/
    
    @GetMapping("/query2")
    public String query2Form(Model model) {
        model.addAttribute("query2", new Query2());
        
        String sqlQueryDepts = "SELECT name FROM amains.department";
        
        List<DepartmentResult> allData = this.jdbcTemplate.query(sqlQueryDepts,
            new RowMapper<DepartmentResult>() {
                public DepartmentResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                    DepartmentResult result = new DepartmentResult();
                    result.setDeptname(rs.getString("name"));
                    return result;
                }
            });
        
        model.addAttribute("data", allData);
        return "query2";
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  query2Submit
     |         Purpose:  To perform actions according to this query.
     |  Pre-Conditions:  The database exists and can be queried.
     | Post-Conditions:  The query is given to the user.
     |      Parameters:  Model, Query2
     |         Returns:  String, the HTML page.
     *------------------------------------------------------------------------*/
    @PostMapping("/query2")
    public String query2Submit(Model model, @ModelAttribute Query2 query) {
        
        String sqlQuery2 = "SELECT dr.fname, dr.lname, dr.officeNo, rm.buildingName "
            + "FROM amains.doctor dr, amains.department dp, amains.room rm "
            + "WHERE dp.name='"+query.getDeptname()+"' AND dr.deptId=dp.deptId AND dr.officeNo=rm.roomNo";
        
        List<Query2Result> allData = this.jdbcTemplate.query(sqlQuery2,
            new RowMapper<Query2Result>() {
                public Query2Result mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Query2Result result = new Query2Result();
                    result.setDname(rs.getString("fname")+" "+rs.getString("lname"));
                    result.setOfficeno(""+rs.getInt("officeNo"));
                    result.setBuildingname(rs.getString("buildingName"));
                    return result;
                }
            });
        
        model.addAttribute("query2", query);
        model.addAttribute("data", allData);
        return "query2Result";
    }
    
    /******************************************************
     * QUERY #3
     ******************************************************/
    
    @GetMapping("/query3")
    public String query3Form(Model model) {
        model.addAttribute("query3", new Query3());
        return "query3";
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  query3Submit
     |         Purpose:  To perform actions according to this query.
     |  Pre-Conditions:  The database exists and can be queried.
     | Post-Conditions:  The query is given to the user.
     |      Parameters:  Model, Query3
     |         Returns:  String, the HTML page.
     *------------------------------------------------------------------------*/
    @PostMapping("/query3")
    public String query3Submit(Model model, @ModelAttribute Query3 query) {
        
        String sqlQuery3 = "SELECT p.pid, p.lname, p.fname, t.edd-t.ihd AS \"Days\", t.roomNo, f.amountDue "
            + "FROM amains.patient p, amains.treatment t, amains.payment f "
            + "WHERE p.pid=f.pid AND f.pid=t.pid AND f.apmtNo=t.apmtNo "
                + "AND t.discharge IS NULL AND t.edd-t.ihd>5 "
                + "AND f.payStatus='unpaid'";
        
        List<Query3Result> allData = this.jdbcTemplate.query(sqlQuery3,
            new RowMapper<Query3Result>() {
                public Query3Result mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Query3Result result = new Query3Result();
                    result.setPid(rs.getString("pid"));
                    result.setPname(rs.getString("fname") + " " + rs.getString("lname"));
                    result.setDays(""+rs.getInt("days"));
                    result.setRoomno(""+rs.getInt("roomNo"));
                    String amountDue = "" + rs.getFloat("amountDue");
                    if((""+amountDue.charAt(amountDue.length()-2)).equals(".")) { amountDue = amountDue + "0"; }
                    result.setAmountdue(amountDue);
                    return result;
                }
            });
        
        model.addAttribute("data", allData);
        return "query3Result";
    }
    
    /******************************************************
     * QUERY #4
     ******************************************************/
    
    @GetMapping("/query4")
    public String query4Form(Model model) {
        model.addAttribute("query4", new Query4());
        return "query4";
    }
    
    /*------------------------------------------------------------------------*
     |          Method:  query4Submit
     |         Purpose:  To perform actions according to this query.
     |  Pre-Conditions:  The database exists and can be queried.
     | Post-Conditions:  The query is given to the user.
     |      Parameters:  Model, Query4
     |         Returns:  String, the HTML page.
     *------------------------------------------------------------------------*/
    @PostMapping("/query4")
    public String query4Submit(Model model, @ModelAttribute Query4 query) {
                
        /*String sqlQuery4 = "SELECT p.fname, p.lname, p.contactNo, m.medName, m.daysToTake, ph.fname, ph.lname "
            + "FROM amains.patient p, amains.pharmacist ph, amains.medicine m, amains.staff s, amains.treatment t, amains.appointment a "
            + "WHERE p.pid=t.pid AND t.discharge IS NOT NULL AND CURRENT_DATE-t.discharge="+query.getDays()+" "
            + "AND p.pid=m.pid AND m.phid=ph.phid AND p.pid=a.pid AND a.eid='"+query.getEid()+"'";*/
            
        String sqlQuery4 = "SELECT t.pid, p.fname AS \"pfname\", p.lname AS \"plname\", t.apmtNo, m.medName, m.daysToTake, m.phid, ph.fname AS \"phfname\", ph.lname AS \"phlname\", d.amountDue, d.payStatus, s.eid, s.fname AS \"sfname\", s.lname AS \"slname\""
            + "FROM amains.patient p, amains.treatment t, amains.medicine m, amains.pharmacist ph, amains.payment d, amains.staff s "
            + "WHERE t.discharge IS NULL "
                + "AND t.pid=m.pid AND m.medName IS NOT NULL "
                + "AND m.phid=ph.phid "
                + "AND t.pid=d.pid AND t.apmtNo=d.apmtNo "
                + "AND d.eid=s.eid "
                + "AND p.pid=t.pid";
        
        List<Query4Result> allData = this.jdbcTemplate.query(sqlQuery4,
            new RowMapper<Query4Result>() {
                public Query4Result mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Query4Result result = new Query4Result();
                    result.setPid(rs.getString("pid"));
                    result.setPname(rs.getString("pfname") + " " + rs.getString("plname"));
                    result.setApmtno(""+rs.getInt("apmtNo"));
                    result.setMedname(rs.getString("medName"));
                    result.setDays(""+rs.getInt("daysToTake"));
                    result.setPhid(rs.getString("phid"));
                    result.setPhname(rs.getString("phfname") + " " + rs.getString("phlname"));
                    String amountDue = "" + rs.getFloat("amountDue");
                    if((""+amountDue.charAt(amountDue.length()-2)).equals(".")) { amountDue = amountDue + "0"; }
                    result.setAmountdue(amountDue);
                    result.setPaystatus(rs.getString("payStatus"));
                    result.setEid(rs.getString("eid"));
                    result.setSname(rs.getString("sfname") + " " + rs.getString("slname"));
                    return result;
                }
            });
        
        model.addAttribute("query4", query);
        model.addAttribute("data", allData);
        return "query4Result";
    }
    
}
