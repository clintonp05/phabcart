package phabcart

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class MedicineCategoryServiceSpec extends Specification {

    MedicineCategoryService medicineCategoryService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new MedicineCategory(...).save(flush: true, failOnError: true)
        //new MedicineCategory(...).save(flush: true, failOnError: true)
        //MedicineCategory medicineCategory = new MedicineCategory(...).save(flush: true, failOnError: true)
        //new MedicineCategory(...).save(flush: true, failOnError: true)
        //new MedicineCategory(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //medicineCategory.id
    }

    void "test get"() {
        setupData()

        expect:
        medicineCategoryService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<MedicineCategory> medicineCategoryList = medicineCategoryService.list(max: 2, offset: 2)

        then:
        medicineCategoryList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        medicineCategoryService.count() == 5
    }

    void "test delete"() {
        Long medicineCategoryId = setupData()

        expect:
        medicineCategoryService.count() == 5

        when:
        medicineCategoryService.delete(medicineCategoryId)
        sessionFactory.currentSession.flush()

        then:
        medicineCategoryService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        MedicineCategory medicineCategory = new MedicineCategory()
        medicineCategoryService.save(medicineCategory)

        then:
        medicineCategory.id != null
    }
}
