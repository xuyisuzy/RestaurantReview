/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author fengyuan
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "RestaurantReview-ejbPU")
    private EntityManager em;
            
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public CustomerSessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewCustomer(Customer newCustomer) throws UnknownPersistenceException, InputDataValidationException, CustomerUsernameExistException
    {
        Set<ConstraintViolation<Customer>>constraintViolations = validator.validate(newCustomer);
        
        if(constraintViolations.isEmpty())
        {
            try
            {
                em.persist(newCustomer);
                em.flush();

                return newCustomer.getUseId();
            }
            catch(PersistenceException ex)
            {
                if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
                {
                    if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                    {
                        throw new CustomerUsernameExistException();
                    }
                    else
                    {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public List<Customer> retrieveAllCustomers()
    {
        Query query = em.createQuery("SELECT c FROM Customer c ORDER BY c.useId ASC");        
        List<Customer> customers = query.getResultList();
        
//        for(Customer customer: customers)
//        {
//            customer.getCreditCards();
//            customer.getCustomerVouchers();
//            customer.getReviews();
//        }
        
        return customers;
    }
    
    @Override
    public Customer retrieveCustomerById(Long customerId) throws CustomerNotFoundException 
    {
        Customer customer = em.find(Customer.class, customerId);
        
        if(customer != null)
        {
            return customer;
        }
        else
        {
            throw new CustomerNotFoundException("Customer ID " + customerId + " does not exist!");
        }
    }
    
    @Override
    public Customer retrieveCustomerByEmail(String email) throws CustomerNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.email = :inEmail");
        query.setParameter("inEmail", email);
        
        try
        {
            return (Customer)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new CustomerNotFoundException("Customer with Email " + email + " does not exist!");
        }
    }
    
    @Override
    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException
    {
        try
        {
            Customer customer = retrieveCustomerByEmail(username);
            
            if(customer.getPassword().equals(password))
            {
//                customerEntity.getSaleTransactionEntities().size();                
                return customer;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(CustomerNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Customer>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
