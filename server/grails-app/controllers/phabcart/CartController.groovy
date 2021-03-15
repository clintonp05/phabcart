package phabcart

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import grails.converters.JSON

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class CartController {

    CartService cartService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def show() {
        def cartIns = Cart.findByUser(Long.parseLong(params?.id))
        def responseMap = [:]
        responseMap.put("status",NO_CONTENT)
        responseMap.put("statusCode",204)
        responseMap.put("data",[])

        if( cartIns ) {
            def resMap = [:]
            resMap.put("cartId",cartIns?.id)
            def itemList = []
            cartIns?.itemList?.each{ cartItem->
                def cartItemMap = [:]
                cartItemMap.put("cartItemId",cartItem?.id)
                cartItemMap.put("itemId",cartItem?.item?.id)
                cartItemMap.put("itemName", cartItem?.item?.name)
                cartItemMap.put("itemCategory" , cartItem?.item?.category?.name)
                cartItemMap.put("itemUnits" , cartItem?.item?.units)
                cartItemMap.put("itemPrice" , cartItem?.item?.price)
                cartItemMap.put("itemRating" , cartItem?.item?.rating)
                cartItemMap.put("itemImageURL" , cartItem?.item?.imageURL)
                cartItemMap.put("itemUserStockQuantity" , cartItem?.quantity)
                itemList << cartItemMap
            }
            resMap.put("cartItemList",itemList)
            responseMap.put("status",OK)    
            responseMap.put("statusCode",200)
            responseMap.put("data",resMap)
        }
        withFormat {
            json { render responseMap as JSON }
        }
    }

    @Transactional
    def save() {
        def cart = new Cart()
        if (cart == null) {
            render status: NOT_FOUND
            return
        }
        if (cart.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cart.errors
            return
        }

        try {
            cartService.save(cart)
        } catch (ValidationException e) {
            respond cart.errors
            return
        }

        respond cart, [status: CREATED, view:"show"]
    }

    @Transactional
    def update() {
        def cartIns = Cart.findByUser(Long.parseLong(params?.id))
        def cartItemList =  request.JSON.itemList
        
        def responseMap = [:]
        def result = cartService.updateCart(cartIns,cartItemList)
        if(result) {
            responseMap.put('status',OK)
            responseMap.put('statusCode',200)
            responseMap.put('message','cart updated')

        } else {
            responseMap.put('status',INTERNAL_SERVER_ERROR)
            responseMap.put('statusCode',500)
            responseMap.put('message','cart update failed')
        }
         withFormat {
            json { render responseMap as JSON }
        }
    }

    @Transactional
    def checkoutCart() {
        
        def cartIns = Cart.get(request.JSON.cartId)
        def newCartIns = new Cart()
        newCartIns.itemList = []
        newCartIns.user = cartIns?.user
        newCartIns.createdDate = new Date()

        def orderIns = new Order()
        orderIns.cart = cartIns
        orderIns.price = request.JSON.cartValue
        orderIns.user = cartIns.user
        orderIns.status = "Dispatched"
        orderIns.createdDate = new Date()


        def responseMap = [:]
        responseMap.put('status',OK)
        responseMap.put('statusCode',200)
        responseMap.put('message','Order placed successfully')

        if(orderIns.save(flush:true)) {
            println "order created"
        } else{
            responseMap.put('status',INTERNAL_SERVER_ERROR)
            responseMap.put('statusCode',500)
            responseMap.put('message','order creation failed')
        }

        cartIns.user = null

        if(cartIns.save(flush:true)) {
            println "moving items from cart"
        } else{
            responseMap.put('status',INTERNAL_SERVER_ERROR)
            responseMap.put('statusCode',500)
            responseMap.put('message','error placing order')
        }

        if(newCartIns.save(flush:true)) {
            println "updating cart"
        } else{
            responseMap.put('status',INTERNAL_SERVER_ERROR)
            responseMap.put('statusCode',500)
            responseMap.put('message','error updating cart')
        }
        cartService.updateMedicine(cartIns)

        withFormat {
            json { render responseMap as JSON }
        }
    }
}
