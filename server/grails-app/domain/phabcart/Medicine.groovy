package phabcart

class Medicine implements Serializable{
    Long id
    String name
    int units
    double price
    String imageURL
    double rating
    Date createdDate
    Date lastUpdated
    MedicineCategory category

    static constraints = {
        createdDate nullable: true
        lastUpdated nullable: true
    }

    static mapping = {
    }
}
