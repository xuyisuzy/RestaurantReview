/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Restaurant;
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
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.RestaurantNotFoundException;
import util.exception.RestaurantUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author fengyuan
 */
@Stateless
public class RestaurantSessionBean implements RestaurantSessionBeanLocal {

    @PersistenceContext(unitName = "RestaurantReview-ejbPU")
    private EntityManager em;
            
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public RestaurantSessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewRestaurant(Restaurant newRestaurant) throws UnknownPersistenceException, InputDataValidationException, RestaurantUsernameExistException
    {
        Set<ConstraintViolation<Restaurant>>constraintViolations = validator.validate(newRestaurant);
        
        if(constraintViolations.isEmpty())
        {
            try
            {
                em.persist(newRestaurant);
                em.flush();

                return newRestaurant.getUseId();
            }
            catch(PersistenceException ex)
            {
                if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
                {
                    if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                    {
                        throw new RestaurantUsernameExistException();
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
    public List<Restaurant> retrieveAllRestaurants()
    {
        Query query = em.createQuery("SELECT r FROM Restaurant r ORDER BY r.useId ASC");        
        List<Restaurant> restaurants = query.getResultList();
        
//        for(Customer customer: customers)
//        {
//            customer.getCreditCards();
//            customer.getCustomerVouchers();
//            customer.getReviews();
//        }
        
        return restaurants;
    }
    
    @Override
    public Restaurant retrieveRestaurantById(Long restaurantId) throws RestaurantNotFoundException 
    {
        Restaurant restaurant = em.find(Restaurant.class, restaurantId);
        
        if(restaurant != null)
        {
            return restaurant;
        }
        else
        {
            throw new RestaurantNotFoundException("Restaurant ID " + restaurantId + " does not exist!");
        }
    }
    
    @Override
    public Restaurant retrieveCustomerByEmail(String email) throws RestaurantNotFoundException
    {
        Query query = em.createQuery("SELECT r FROM Restaurant r WHERE r.email = :inEmail");
        query.setParameter("inEmail", email);
        
        try
        {
            return (Restaurant)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new RestaurantNotFoundException("Restaurant with Email " + email + " does not exist!");
        }
    }
    
    @Override
    public Restaurant restaurantLogin(String username, String password) throws InvalidLoginCredentialException
    {
        try
        {
            Restaurant restaurant = retrieveCustomerByEmail(username);
            
            if(restaurant.getPassword().equals(password))
            {
//                customerEntity.getSaleTransactionEntities().size();                
                return restaurant;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(RestaurantNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Restaurant>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
