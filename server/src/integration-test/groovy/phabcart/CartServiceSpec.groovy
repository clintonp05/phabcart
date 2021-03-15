package phabcart

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class CartServiceSpec extends Specification {

    CartService cartService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Cart(...).save(flush: true, failOnError: true)
        //new Cart(...).save(flush: true, failOnError: true)
        //Cart cart = new Cart(...).save(flush: true, failOnError: true)
        //new Cart(...).save(flush: true, failOnError: true)
        //new Cart(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //cart.id
    }

    void "test get"() {
        setupData()

        expect:
        cartService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Cart> cartList = cartService.list(max: 2, offset: 2)

        then:
        cartList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        cartService.count() == 5
    }

    void "test delete"() {
        Long cartId = setupData()

        expect:
        cartService.count() == 5

        when:
        cartService.delete(cartId)
        sessionFactory.currentSession.flush()

        then:
        cartService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Cart cart = new Cart()
        cartService.save(cart)

        then:
        cart.id != null
    }
}
