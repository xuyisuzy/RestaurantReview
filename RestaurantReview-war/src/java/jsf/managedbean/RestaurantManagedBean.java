/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.BankAccountSessionBeanLocal;
import ejb.session.stateless.RestaurantSessionBeanLocal;
import entity.BankAccount;
import entity.Restaurant;
import entity.TableConfiguration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.BankAccountExistException;
import util.exception.CreateNewBankAccountException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;


@Named(value = "restaurantManagedBean")
@RequestScoped
public class RestaurantManagedBean {

    @EJB
    private BankAccountSessionBeanLocal bankAccountSessionBeanLocal;

    @EJB
    private RestaurantSessionBeanLocal restaurantSessionBeanLocal;
    
    
    private Restaurant currentRestaurant;
    private BankAccount newBankAccount;
    
    public RestaurantManagedBean() {
    }
    
    public void doUpdateProduct(ActionEvent event){
        currentRestaurant = (Restaurant)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentRestaurant");
        
    }
    
    public void updateProduct(ActionEvent event){
        if (currentRestaurant.getAcceptReservation()==false) {
            currentRestaurant.setTableConfiguration(null);
        }
        
    }
    
    public void createNewBankAccount(ActionEvent event){
        try {
            Long newBankAccountId = bankAccountSessionBeanLocal.createNewBankAccount(newBankAccount, currentRestaurant.getUseId());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New bank account " + newBankAccountId + " registered successfully", null));  
        } catch (UnknownPersistenceException | InputDataValidationException | CreateNewBankAccountException | BankAccountExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid data input: " + ex.getMessage(), null));
        }
    }

    public Restaurant getCurrentRestaurant() {
        return currentRestaurant;
    }

    public void setCurrentRestaurant(Restaurant currentRestaurant) {
        this.currentRestaurant = currentRestaurant;
    }

    public BankAccount getNewBankAccount() {
        return newBankAccount;
    }

    public void setNewBankAccount(BankAccount newBankAccount) {
        this.newBankAccount = newBankAccount;
    }
    
    
    
}
