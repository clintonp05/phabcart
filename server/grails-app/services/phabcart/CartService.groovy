package phabcart

import grails.gorm.transactions.Transactional
import groovy.transform.Synchronized

@Transactional
class CartService {

    @Transactional
    def updateCart(cartIns,cartItemList) {
        def existingCartItemIdList = cartIns?.itemList?.collect{it?.item?.id}
        def actualCartItemIdList = cartItemList?.collect{it?.id}
        def actualDiffList = actualCartItemIdList - existingCartItemIdList
        def existingDiffList = existingCartItemIdList - actualCartItemIdList
        def intersectList = actualCartItemIdList.intersect(existingCartItemIdList)

        cartIns?.itemList?.each{existingCartItemIns ->
            def actualCartItem = cartItemList?.find{it?.id == existingCartItemIns?.item?.id}
            if( actualCartItem != null && actualCartItem?.userStockOrder != existingCartItemIns?.quantity) {
                existingCartItemIns?.quantity = actualCartItem?.userStockOrder
                existingCartItemIns?.save(flush : true)
            }
        }
        
        actualDiffList?.each{medId->
            def cartItemIns = new CartItem()
            cartItemIns.item = Medicine.get(medId)
            cartItemIns.quantity = cartItemList?.find{it?.id == medId}?.userStockOrder
            cartItemIns.cart = cartIns
            cartIns.itemList << cartItemIns
            cartIns?.save()
        }

        existingDiffList?.each{medId->
            def cartItemIns = cartIns?.itemList?.find{it?.item?.id == medId}
            cartIns?.cartItemList?.remove(cartItemIns)
            cartItemIns?.delete()
        }
        return true
    }

    @Transactional
    @Synchronized
    updateMedicine(cartIns) {
        cartIns?.itemList?.each{existingCartItemIns ->
            def medIns = Medicine.get(existingCartItemIns?.item?.id)
            def sub = (medIns?.units - existingCartItemIns?.quantity) > 0 ? medIns?.units - existingCartItemIns?.quantity : 0;
            medIns?.units = sub
            if(medIns.save(flush: true)) {
                println "updating stock"
            } else{
                println "error updating stock"
            }
        }
    }
}