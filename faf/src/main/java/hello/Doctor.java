/*---------------------------------------------------------------------------*
 | Class <Doctor>
 *---------------------------------------------------------------------------*
 |        Author:  Ashley Mains, Amelia Marglon, Suresh Devendran, Zach Keane
 |       Purpose:  To provide and store information on doctors.
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

public class Doctor {

    private String aud;
    
    private String did;
    private String lname;
    private String fname;
    private String dob;
    private String status;
    private String deptid;
    private String officeno;

    public String getAud() { return aud; }
    public void setAud(String aud) { this.aud = aud; }
    
    public String getDid() { return did; }
    public void setDid(String did) { this.did = did; }

    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }

    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }
    
    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getDeptid() { return deptid; }
    public void setDeptid(String deptid) { this.deptid = deptid; }
    
    public String getOfficeno() { return officeno; }
    public void setOfficeno(String officeno) { this.officeno = officeno; }

}
