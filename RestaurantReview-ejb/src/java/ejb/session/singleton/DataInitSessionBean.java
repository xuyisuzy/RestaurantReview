/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.RestaurantSessionBeanLocal;
import ejb.session.stateless.VoucherSessionBeanLocal;
import entity.Customer;
import entity.CustomerVoucher;
import entity.Restaurant;
import entity.Voucher;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CreateNewCustomerVoucherException;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.CustomerVoucherExistException;
import util.exception.InputDataValidationException;
import util.exception.RestaurantUsernameExistException;
import util.exception.TableConfigurationExistException;
import util.exception.UnknownPersistenceException;
import util.exception.VoucherExistException;
import util.exception.VoucherNotFoundException;


@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @PersistenceContext(unitName = "RestaurantReview-ejbPU")
    private EntityManager em;
    
    
    @EJB
    private RestaurantSessionBeanLocal restaurantSessionBeanLocal;
    
    @EJB
    private VoucherSessionBeanLocal voucherSessionBeanLocal;

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    
    
    
    @PostConstruct
    public void postConstruct()
    {
        if(em.find(Customer.class, 1l) == null)
        {
            initializeCustomerData();
        }
//        if(em.find(Restaurant.class, 3l) == null)
//        {
//            initializeRestaurantData();
//        }
        if(em.find(Voucher.class, 1l) == null)
        {
            initializeVoucherData();
        }
        if(em.find(CustomerVoucher.class, 1l) == null)
        {
            initializeCustomerVoucherData();
        }
            
    }
    
    private void initializeCustomerData()
    {
        try
        {
            customerSessionBeanLocal.createNewCustomer(new Customer("custone@test.com", "password", "Custoemr", "One", "12345678"));
            customerSessionBeanLocal.createNewCustomer(new Customer("custotwo@test.com", "password", "Custoemr", "Two", "87654321"));
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
            restaurantSessionBeanLocal.createNewRestaurant(new Restaurant("restone@test.com", "password", "Restaurant One", "kent Ridge 1", "66666666", false, "This is test Restaurant One"), null);
            restaurantSessionBeanLocal.createNewRestaurant(new Restaurant("resttwo@test.com", "password", "Restaurant Two", "kent Ridge 2", "77777777", false, "This is test Restaurant Two"), null);
        }
        catch (UnknownPersistenceException | InputDataValidationException | RestaurantUsernameExistException | TableConfigurationExistException ex)
        {
            ex.printStackTrace();
        }
    }
    
    private void initializeVoucherData() 
    {
        try
        {
            voucherSessionBeanLocal.createNewVoucher(new Voucher("Voucher1", new Date(new Date().getTime() + (24 * 60 * 60 * 1000)), new BigDecimal(10.00), new BigDecimal(10.00), true, "for testing"));
        }
        catch (UnknownPersistenceException | InputDataValidationException | VoucherExistException ex)
        {
            ex.printStackTrace();
        }
    }
    
    private void initializeCustomerVoucherData()
    {
        try
        {
            Voucher voucher = voucherSessionBeanLocal.retrieveVoucherById(1L);
            System.out.println(voucher);
            Customer customer = customerSessionBeanLocal.retrieveCustomerById(1L);
            System.out.println(customer);
//            CustomerVoucher test = new CustomerVoucher(false, new Date());
//            System.out.println("Six digit code: " + test.getSixDigitCode());
            voucherSessionBeanLocal.createNewCustomerVoucher(new CustomerVoucher(false, new Date(new Date().getTime() + (60 * 60 * 1000))), voucher.getVoucherId(), customer.getUseId());
            voucherSessionBeanLocal.createNewCustomerVoucher(new CustomerVoucher(false, new Date(new Date().getTime() + (60 * 60 * 1000))), voucher.getVoucherId(), customer.getUseId());
        }
        catch (UnknownPersistenceException | InputDataValidationException | CreateNewCustomerVoucherException | CustomerNotFoundException | CustomerVoucherExistException | VoucherNotFoundException ex)
        {
            ex.printStackTrace();
        }
        
    }

//    public void persist(Object object)
//    {
//        em.persist(object);
//    }
}
