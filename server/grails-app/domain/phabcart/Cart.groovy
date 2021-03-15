package phabcart

class Cart implements Serializable{
    Long id
    List<CartItem> itemList
    User user

    Date createdDate
    Date lastUpdated

    static constraints = {
        user nullable : true
        createdDate nullable: true
        lastUpdated nullable: true
    }
}
