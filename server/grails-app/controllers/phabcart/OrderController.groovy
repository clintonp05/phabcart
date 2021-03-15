package phabcart

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import grails.converters.JSON

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class OrderController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        def orderList = Order.findAllByUser(User.get(params.userId))
        def responseList = []
        def responseMap = [:]
        orderList?.each{ orderIns->
            def orderMap = [:]
            orderMap.put('orderId',orderIns?.id)
            orderMap.put('createdDate',orderIns?.createdDate)
            orderMap.put('price',orderIns?.price)
            orderMap.put('status',orderIns?.status)
            def orderDetails = []
            orderIns?.cart?.itemList?.each{cartItem->
                def medIns = cartItem?.item
                def map = [:]
                map.put("id", medIns?.id)
                map.put("name", medIns?.name)
                map.put("category" , medIns?.category?.name)
                map.put("price" , medIns?.price)
                map.put("rating" , medIns?.rating)
                map.put("imageURL" , medIns?.imageURL)
                map.put("quantity" , cartItem?.quantity)
                orderDetails << map
            }
            orderMap.put('orderDetails',orderDetails)
            responseList << orderMap
        }
        if(responseList?.size () > 0 ) {
            responseMap.put("status",OK)    
            responseMap.put("statusCode",200)
            responseMap.put("data",responseList)
        } else {
            responseMap.put("status",NO_CONTENT)    
            responseMap.put("statusCode",204)
            responseMap.put("data",responseList)
        }
        

        withFormat {
            json { render responseMap as JSON }
        }

    }

}
