package phabcart

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class MedicineServiceSpec extends Specification {

    MedicineService medicineService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Medicine(...).save(flush: true, failOnError: true)
        //new Medicine(...).save(flush: true, failOnError: true)
        //Medicine medicine = new Medicine(...).save(flush: true, failOnError: true)
        //new Medicine(...).save(flush: true, failOnError: true)
        //new Medicine(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //medicine.id
    }

    void "test get"() {
        setupData()

        expect:
        medicineService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Medicine> medicineList = medicineService.list(max: 2, offset: 2)

        then:
        medicineList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        medicineService.count() == 5
    }

    void "test delete"() {
        Long medicineId = setupData()

        expect:
        medicineService.count() == 5

        when:
        medicineService.delete(medicineId)
        sessionFactory.currentSession.flush()

        then:
        medicineService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Medicine medicine = new Medicine()
        medicineService.save(medicine)

        then:
        medicine.id != null
    }
}
