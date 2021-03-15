package phabcart

import grails.gorm.transactions.Transactional
import grails.gorm.services.Service

@Service(Medicine)
interface MedicineService {

    Medicine get(Serializable id)

    List<Medicine> list(Map args)

    Long count()

    void delete(Serializable id)

    Medicine save(Medicine medicine)

}