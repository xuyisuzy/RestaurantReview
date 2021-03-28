/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.RestaurantSessionBeanLocal;
import ejb.session.stateless.TableConfigurationSessionBeanLocal;
import entity.Restaurant;
import entity.TableConfiguration;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.InputDataValidationException;
import util.exception.RestaurantUsernameExistException;
import util.exception.TableConfigurationExistException;
import util.exception.UnknownPersistenceException;


@Named(value = "restaurantRegisterManagedBean")
@RequestScoped
public class RestaurantRegisterManagedBean {

    @EJB
    private TableConfigurationSessionBeanLocal tableConfigurationSessionBeanLocal;
    @EJB
    private RestaurantSessionBeanLocal restaurantSessionBeanLocal;
    

    private Restaurant newRestaurant;
    private TableConfiguration newTableConfiguration; 
    
    public RestaurantRegisterManagedBean() {
        newRestaurant = new Restaurant();
        newTableConfiguration = new TableConfiguration();
    }
    
    public void createNewRestaurant(ActionEvent event) throws IOException {
        try {
            Long newRestaurantId = restaurantSessionBeanLocal.createNewRestaurant(getNewRestaurant(), getNewTableConfiguration());
            getNewRestaurant().setUseId(newRestaurantId);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Restaurant " + newRestaurantId + " registered successfully", null));  
        } catch (UnknownPersistenceException|InputDataValidationException|RestaurantUsernameExistException|TableConfigurationExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }

    public Restaurant getNewRestaurant() {
        return newRestaurant;
    }

    public void setNewRestaurant(Restaurant newRestaurant) {
        this.newRestaurant = newRestaurant;
    }

    public TableConfiguration getNewTableConfiguration() {
        return newTableConfiguration;
    }

    public void setNewTableConfiguration(TableConfiguration newTableConfiguration) {
        this.newTableConfiguration = newTableConfiguration;
    }
    
    
    
}
