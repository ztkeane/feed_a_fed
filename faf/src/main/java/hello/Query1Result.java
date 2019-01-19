/*---------------------------------------------------------------------------*
 | Class <Query1Result>
 *---------------------------------------------------------------------------*
 |        Author:  Ashley Mains, Amelia Marglon, Suresh Devendran, Zach Keane
 |       Purpose:  Stores tuple information from Query1.
 | Inherits From:  None.
 |    Interfaces:  None.
 *---------------------------------------------------------------------------*
 |     Constants:  None.
 *---------------------------------------------------------------------------*
 |  Constructors:  None.
 | Class Methods:  None.
 | Inst. Methods:  getters and setters
 *---------------------------------------------------------------------------*/

package hello;

public class Query1Result {

    private String pid;
    private String pname; // First + last name
    private String gender;
    private String dob;
    private String visitdate;
    private String visitrsn;
    private String treatment;
    private String did;
    
    public String getPid() { return pid; }
    public void setPid(String pid) { this.pid = pid; }

    public String getPname() { return pname; }
    public void setPname(String pname) { this.pname = pname; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }
    
    public String getVisitdate() { return visitdate; }
    public void setVisitdate(String visitdate) { this.visitdate = visitdate; }
    
    public String getVisitrsn() { return visitrsn; }
    public void setVisitrsn(String visitrsn) { this.visitrsn = visitrsn; }
    
    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
    
    public String getDid() { return did; }
    public void setDid(String did) { this.did = did; }
    
}
