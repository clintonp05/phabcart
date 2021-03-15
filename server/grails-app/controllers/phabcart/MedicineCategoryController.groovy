package phabcart

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class MedicineCategoryController {

    MedicineCategoryService medicineCategoryService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond medicineCategoryService.list(params), model:[medicineCategoryCount: medicineCategoryService.count()]
    }

    def show(Long id) {
        respond medicineCategoryService.get(id)
    }

    @Transactional
    def save(MedicineCategory medicineCategory) {
        if (medicineCategory == null) {
            render status: NOT_FOUND
            return
        }
        if (medicineCategory.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond medicineCategory.errors
            return
        }

        try {
            medicineCategoryService.save(medicineCategory)
        } catch (ValidationException e) {
            respond medicineCategory.errors
            return
        }

        respond medicineCategory, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(MedicineCategory medicineCategory) {
        if (medicineCategory == null) {
            render status: NOT_FOUND
            return
        }
        if (medicineCategory.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond medicineCategory.errors
            return
        }

        try {
            medicineCategoryService.save(medicineCategory)
        } catch (ValidationException e) {
            respond medicineCategory.errors
            return
        }

        respond medicineCategory, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        medicineCategoryService.delete(id)

        render status: NO_CONTENT
    }
}
