package phabcart

class Order implements Serializable{
    Cart cart
    User user
    Date createdDate
    Double price
    String status
    
    static constraints = {
    }
}
