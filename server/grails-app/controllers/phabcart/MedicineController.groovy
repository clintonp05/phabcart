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
class MedicineController {

    MedicineService medicineService
    MedicineCategoryService medicineCategoryService
    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        def medList = Medicine.findAll()
        def responseList = []
        def cartIns = Cart.findByUser(User.get(params.userId))
        
        def copyCartItemList = cartIns?.itemList?.collect{it} 
        medList?.each{medIns->
            def cartItemListSize = copyCartItemList?.size()
            def map = [:]
            map.put("id", medIns?.id)
            map.put("name", medIns?.name)
            map.put("category" , medIns?.category?.name)
            map.put("units" , medIns?.units)
            map.put("price" , medIns?.price)
            map.put("rating" , medIns?.rating)
            map.put("imageURL" , medIns?.imageURL)
            map.put("userStockOrder" , 0)
            if( cartItemListSize > 0 ) {
                def index = 0
                while( index < cartItemListSize ) { 
                    if( copyCartItemList[index]?.item?.id == medIns?.id ){
                        map.put("userStockOrder" , cartIns?.itemList[index]?.quantity )
                        copyCartItemList?.remove(index)
                        break;
                    }
                    index++
                }   
            }            
            responseList << map
        }
        
        def responseMap = [:]
        responseMap.put("status",NO_CONTENT)
        responseMap.put("statusCode",204)
        responseMap.put("data",[])
        if( responseList?.size() > 0 ) {
            responseMap.put("status",OK)    
            responseMap.put("statusCode",200)
            responseMap.put("data",responseList)
        }
        withFormat {
            json { render responseMap as JSON }
        }
    }

    def show(Long id) {
        respond Medicine.get(id)
    }

    @Transactional
    def save(Medicine medicine) {
        respond medicine, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Medicine medicine) {
        if (medicine == null) {
            render status: NOT_FOUND
            return
        }
        if (medicine.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond medicine.errors
            return
        }

        try {
            medicine.save()
        } catch (ValidationException e) {
            respond medicine.errors
            return
        }

        respond medicine, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        Medicine.delete(id)

        render status: NO_CONTENT
    }
}
