/*---------------------------------------------------------------------------*
 | Class <Query1Result>
 *---------------------------------------------------------------------------*
 |        Author:  Suresh Devendran, Zach Keane
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

    private String hid;
    private String name;
    private String email;
    private String city;
    private String county;
    private String state;
    private String phoneNo;
    private String description;
    
    public String getHid() { return hid; }
    public void setHid(String hid) { this.hid = hid; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getCity() { return city; }
    public void setCity(String city) {this.city = city; }
    
    public String getCounty() { return county; }
    public void setCounty(String county) { this.county = county; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    
}
