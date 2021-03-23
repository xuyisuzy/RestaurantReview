/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Restaurant;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.RestaurantNotFoundException;
import util.exception.RestaurantUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author fengyuan
 */
@Local
public interface RestaurantSessionBeanLocal {

    public Long createNewRestaurant(Restaurant newRestaurant) throws UnknownPersistenceException, InputDataValidationException, RestaurantUsernameExistException;

    public List<Restaurant> retrieveAllRestaurants();

    public Restaurant retrieveCustomerByEmail(String email) throws RestaurantNotFoundException;

    public Restaurant restaurantLogin(String username, String password) throws InvalidLoginCredentialException;

    public Restaurant retrieveRestaurantById(Long restaurantId) throws RestaurantNotFoundException;
    
}
