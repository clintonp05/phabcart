package phabcart

class MedicineCategory implements Serializable{
    Long id
    String name
    Date createdDate
    Date lastUpdated
    
    static constraints = {
        createdDate nullable: true
        lastUpdated nullable: true
    }

}
