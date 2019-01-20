/*---------------------------------------------------------------------------*
 | Class <Query3Result>
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

public class Query3Result {
    
    private String cid;
    private String name;
    private String address;
    private String city;
    private String county;
    private String state;
    private String phoneNo;
    
    public String getCid() { return cid; }
    public void setCid(String cid) { this.cid = cid; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getCity() { return city; }
    public void setCity(String city) {this.city = city; }
    
    public String getCounty() { return county; }
    public void setCounty(String county) { this.county = county; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    
}
