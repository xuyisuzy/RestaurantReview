package jsf.managedbean;

import ejb.session.stateless.DishSessionBeanLocal;
import entity.Dish;
import entity.Restaurant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import util.exception.CreateNewDishException;
import util.exception.DishExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import javax.faces.event.ActionEvent;



@Named(value = "CreateNewDishManagedBean")
@RequestScoped
public class CreateNewDishManagedBean 
{
    @EJB
    private DishSessionBeanLocal dishEntitySessionBeanLocal;
    
    private Restaurant currentRestaurant;
    
    private Dish newDish;
    
    private String recommended;
    
    
    
    public CreateNewDishManagedBean() 
    {
        newDish = new Dish();
    }
    
    
    
    @PostConstruct
    public void postConstruct()
    {
    }
    
    
    
    public void createNewDish(ActionEvent event) throws IOException
    {
        currentRestaurant = (Restaurant)event.getComponent().getAttributes().get("currentRestaurant");
        
        boolean recommending = false;
        if (recommended.equals("Yes"))
        {
            recommending = true;
        }
        try
        {
            Dish dish = dishEntitySessionBeanLocal.createNewDish(newDish, currentRestaurant.getId());
            newDish = new Dish();
            dish.setRecommended(recommending);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Dish created successfully (Dish ID: " + dish.getDishId() + ")", null));
        
        } catch (DishExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dish already exists " + ex.getMessage(), null));
        }
        catch(InputDataValidationException | CreateNewDishException | UnknownPersistenceException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new dish: " + ex.getMessage(), null));
        }
    }

    
    
    public Dish getNewDish() {
        return newDish;
    }

    public void setNewDishEntity(Dish newDish) {
        this.newDish = newDish;
    }    

}
