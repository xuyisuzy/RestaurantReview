package jsf.managedbean;

import ejb.session.stateless.DishSessionBeanLocal;
import entity.Dish;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.DeleteDishException;
import util.exception.DishNotFoundException;



@Named(value = "deleteDishManagedBean")
@ViewScoped

public class DeleteDishManagedBean implements Serializable
{
    @EJB
    private DishSessionBeanLocal dishSessionBeanLocal;
    
    private Long dishIdToDelete;
    private Dish dishToDelete;
    
    
    
    public DeleteDishManagedBean() 
    {
    }
    
    
    
    @PostConstruct
    public void postConstruct()
    {
        dishIdToDelete = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("dishIdToDelete");
        
        try
        {
            if(dishIdToDelete != null)
            {
                dishToDelete = dishSessionBeanLocal.retrieveDishById(dishIdToDelete);
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No dish has been selected", null));
            }
        }
        catch(DishNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the product details: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    
    
    public void deleteDish()
    {
        try
        {
            dishSessionBeanLocal.deleteDish(dishIdToDelete);
            
            dishIdToDelete = null;
            dishToDelete = null;

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dish deleted successfully", null));
        }
        catch(DishNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting dish: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    
    
    public void back(ActionEvent event) throws IOException
    {
        if(dishToDelete == null)
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllDishes.xhtml");
        }
        else
        {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("dishIdToView", dishIdToDelete);
            FacesContext.getCurrentInstance().getExternalContext().redirect("viewDishDetails.xhtml");
        }
    }
    
    
    
    public void foo()
    {        
    }
    
    
    
    public Long getDishIdToDelete() {
        return dishIdToDelete;
    }

    public void setDishIdToDelete(Long dishIdToDelete) {
        this.dishIdToDelete = dishIdToDelete;
    }

    public Dish getDishToDelete() {
        return dishToDelete;
    }

    public void setDishToDelete(Dish dishToDelete) {
        this.dishToDelete = dishToDelete;
    }
}
