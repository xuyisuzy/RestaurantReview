/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ReservationSessionBeanLocal;
import ejb.session.stateless.RestaurantSessionBeanLocal;
import ejb.session.stateless.TableConfigurationSessionBeanLocal;
import entity.Customer;
import entity.Restaurant;
import entity.TableConfiguration;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CustomerUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.RestaurantUsernameExistException;
import util.exception.TableConfigurationExistException;
import util.exception.UnknownPersistenceException;


@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private ReservationSessionBeanLocal reservationSessionBeanLocal;
    
    @PersistenceContext(unitName = "RestaurantReview-ejbPU")
    private EntityManager em;
    
    
    @EJB
    private RestaurantSessionBeanLocal restaurantSessionBeanLocal;
    


    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;
    
    
    @EJB
    private TableConfigurationSessionBeanLocal tableConfigurationSessionBeanLocal;
    
    
    
    @PostConstruct
    public void postConstruct()
    {
        if(em.find(Customer.class, 1l) == null)
        {
            initializeCustomerData();
        }
        if(em.find(Restaurant.class, 3l) == null)
        {
            initializeRestaurantData();
        }
            
    }
    
    private void initializeCustomerData()
    {
        try
        {
            customerSessionBeanLocal.createNewCustomer(new Customer("custone@test.com", "password", "Customer", "One", "12345678"));
            customerSessionBeanLocal.createNewCustomer(new Customer("custotwo@test.com", "password", "Customer", "Two", "87654321"));
        }
        catch(UnknownPersistenceException | InputDataValidationException | CustomerUsernameExistException ex)
        {
            ex.printStackTrace();
        }
    }
    
    private void initializeRestaurantData()
    {
        try
        {
            restaurantSessionBeanLocal.createNewRestaurant(new Restaurant("restone@test.com", "password", "Restaurant One", "kent Ridge 1","111111", "66666666", false, "This is test Restaurant One", 8, 23), null);
            restaurantSessionBeanLocal.createNewRestaurant(new Restaurant("resttwo@test.com", "password", "Restaurant Two", "kent Ridge 2","111111", "77777777", true, "This is test Restaurant Two", 9, 22), new TableConfiguration(2,3,4));
        }
        catch (UnknownPersistenceException | InputDataValidationException | RestaurantUsernameExistException | TableConfigurationExistException ex)
        {
            ex.printStackTrace();
        }
    }
    
    private void initializeReservationData()
    {

        
    }

//    public void persist(Object object)
//    {
//        em.persist(object);
//    }
}
