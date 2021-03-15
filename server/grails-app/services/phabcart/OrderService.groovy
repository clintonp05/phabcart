package phabcart

import grails.gorm.services.Service

@Service(Order)
interface OrderService {

    Order get(Serializable id)

    List<Order> list(Map args)

    Long count()

    void delete(Serializable id)

    Order save(Order order)

}