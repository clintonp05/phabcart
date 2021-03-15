package phabcart

import grails.gorm.transactions.Transactional
import grails.gorm.services.Service

@Service(MedicineCategory)
interface MedicineCategoryService {

    MedicineCategory get(Serializable id)

    List<MedicineCategory> list(Map args)

    Long count()

    void delete(Serializable id)

    MedicineCategory save(MedicineCategory medicineCategory)

}