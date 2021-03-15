package phabcart

class CartItem implements Serializable{
    Long id
    Medicine item
    int quantity
    Cart cart

    Date createdDate
    Date lastUpdated
    static constraints = {
        createdDate nullable: true
        lastUpdated nullable: true
    }
}
