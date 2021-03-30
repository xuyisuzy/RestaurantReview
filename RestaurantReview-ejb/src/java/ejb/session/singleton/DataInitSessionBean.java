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
import entity.Reservation;
import entity.Restaurant;
import entity.TableConfiguration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.TableSize;
import util.exception.CreateNewReservationException;
import util.exception.CustomerUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.ReservationExistException;
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
        if(em.find(Reservation.class, 1l) == null)
        {
            initializeReservationData();
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
            restaurantSessionBeanLocal.createNewRestaurant(new Restaurant("restone@test.com", "password", "Restaurant One", "kent Ridge 1","111111", "66666666", true, "This is test Restaurant One", 8, 23), new TableConfiguration(2,3,4));
            restaurantSessionBeanLocal.createNewRestaurant(new Restaurant("resttwo@test.com", "password", "Restaurant Two", "kent Ridge 2","111111", "77777777", false, "This is test Restaurant Two", 9, 22), null);
        }
        catch (UnknownPersistenceException | InputDataValidationException | RestaurantUsernameExistException | TableConfigurationExistException ex)
        {
            ex.printStackTrace();
        }
    }
    
    private void initializeReservationData()
    {
        Date currDate = new Date();
        LocalDateTime newLocal = currDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        newLocal = newLocal.plusDays(2);
        Date newDate = Date.from(newLocal.atZone(ZoneId.systemDefault()).toInstant());
        
        try
        {
            reservationSessionBeanLocal.createNewReservation(new Reservation(newDate, currDate, 2, TableSize.SMALL, "noooo"), 1l, 3l, null);
            reservationSessionBeanLocal.createNewReservation(new Reservation(newDate, currDate, 8, TableSize.MEDIUM, "noooo"), 2l, 3l, null);
        }
        catch(UnknownPersistenceException | InputDataValidationException | CreateNewReservationException | ReservationExistException ex)
        {
            ex.printStackTrace();;
        }
        
    }

//    public void persist(Object object)
//    {
//        em.persist(object);
//    }
}
