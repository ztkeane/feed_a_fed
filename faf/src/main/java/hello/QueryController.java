/*---------------------------------------------------------------------------*
 | Class <QueryController>
 *---------------------------------------------------------------------------*
 |        Author:  Suresh Devendran, Zach Keane
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

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;

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

    String sqlQuery1 = "SELECT p1.hid, p1.name, p1.email, p1.phoneNo, p1.city, p1.county, p1.state, p1.description "
        + "FROM ztkeane.helper p1 "
        + "WHERE (p1.city='"+query.getCity()+"' OR p1.county='"+query.getCounty()+"' ) AND p1.state='"+query.getState()+"' ";



    List<Query1Result> allData = this.jdbcTemplate.query(sqlQuery1,
        new RowMapper<Query1Result>() {
      public Query1Result mapRow(ResultSet rs, int rowNum) throws SQLException {
        Query1Result result = new Query1Result();
        result.setHid(rs.getString("hid"));
        result.setName(rs.getString("name"));
        result.setEmail(rs.getString("email"));

        //****************************************

        // Recipient's email ID needs to be mentioned.
        String to = rs.getString("email");

        // Sender's email ID needs to be mentioned
        String from = "feedafederalworker@gmail.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
          // Create a default MimeMessage object.
          MimeMessage message = new MimeMessage(session);

          // Set From: header field of the header.
          message.setFrom(new InternetAddress(from));

          // Set To: header field of the header.
          message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

          // Set Subject: header field
          message.setSubject("Federal Workers in your area!");

          // Now set the actual message
          message.setText("Thank you for participating in #FeedAFed! Here are local federal workers that need your help:");

          //For above, create SQL statement querying for federal workers in the area that you specified. Build row by row the content of the message.

          // Send message
          Transport.send(message);
          System.out.println("Sent mail message successfully....");
        } catch (MessagingException mex) {
          mex.printStackTrace();
        }



        //****************************************

        result.setPhoneNo(rs.getString("phoneNo"));
        result.setCity(rs.getString("city"));
        result.setCounty(rs.getString("county"));
        result.setState(rs.getString("state"));
        result.setDescription(rs.getString("description"));
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
    return "query2";
  }

  /*------------------------------------------------------------------------*
     |          Method:  query1Submit
     |         Purpose:  To perform actions according to this query.
     |  Pre-Conditions:  The database exists and can be queried.
     | Post-Conditions:  The query is given to the user.
     |      Parameters:  Model, Query1
     |         Returns:  String, the HTML page.
   *------------------------------------------------------------------------*/
  @PostMapping("/query2")
  public String query2Submit(Model model, @ModelAttribute Query2 query) {

    String sqlQuery2 = "SELECT p1.cid, p1.name, p1.address, p1.phoneNo, p1.city, p1.county, p1.state "
        + "FROM ztkeane.helpCenter p1 "
        + "WHERE (p1.city='"+query.getCity()+"' OR p1.county='"+query.getCounty()+"' ) AND p1.state='"+query.getState()+"' ";

    List<Query2Result> allData = this.jdbcTemplate.query(sqlQuery2,
        new RowMapper<Query2Result>() {
      public Query2Result mapRow(ResultSet rs, int rowNum) throws SQLException {
        Query2Result result = new Query2Result();
        result.setCid(rs.getString("cid"));
        result.setName(rs.getString("name"));
        result.setAddress(rs.getString("address"));
        result.setCity(rs.getString("city"));
        result.setCounty(rs.getString("county"));
        result.setState(rs.getString("state"));
        result.setPhoneNo(rs.getString("phoneNo"));
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
     |          Method:  query1Submit
     |         Purpose:  To perform actions according to this query.
     |  Pre-Conditions:  The database exists and can be queried.
     | Post-Conditions:  The query is given to the user.
     |      Parameters:  Model, Query1
     |         Returns:  String, the HTML page.
   *------------------------------------------------------------------------*/
  @PostMapping("/query3")
  public String query3Submit(Model model, @ModelAttribute Query3 query) {

    String sqlQuery3 = "SELECT p1.cid, p1.name, p1.address, p1.phoneNo, p1.city, p1.county, p1.state "
        + "FROM ztkeane.helpCenter p1 "
        + "WHERE (p1.city='"+query.getCity()+"' OR p1.county='"+query.getCounty()+"' ) AND p1.state='"+query.getState()+"' ";

    List<Query3Result> allData = this.jdbcTemplate.query(sqlQuery3,
        new RowMapper<Query3Result>() {
      public Query3Result mapRow(ResultSet rs, int rowNum) throws SQLException {
        Query3Result result = new Query3Result();
        result.setCid(rs.getString("cid"));
        result.setName(rs.getString("name"));
        result.setAddress(rs.getString("address"));
        result.setCity(rs.getString("city"));
        result.setCounty(rs.getString("county"));
        result.setState(rs.getString("state"));
        result.setPhoneNo(rs.getString("phoneNo"));
        return result;
      }
    });
    model.addAttribute("query3", query);
    model.addAttribute("data", allData);
    return "query3Result";
  }
}
