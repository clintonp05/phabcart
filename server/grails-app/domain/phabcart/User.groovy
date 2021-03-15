package phabcart

class User implements Serializable{
    Long id
    String name
    String mobileNumber
    String emailId
    String address
    String city
    String state
    String password
    String username
    
    Date createdDate
    Date lastUpdated
    static constraints = {
        createdDate nullable: true
        lastUpdated nullable: true
    }
}
