/*---------------------------------------------------------------------------*
 | Class <Query2>
 *---------------------------------------------------------------------------*
 |        Author:  Suresh Devendran, Zach Keane
 |       Purpose:  Stores information provided in query 1.
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

public class Query2 {

    private String city;
    private String county;
    private String state;

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCounty() { return county; }
    public void setCounty(String county) { this.county = county; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
}

