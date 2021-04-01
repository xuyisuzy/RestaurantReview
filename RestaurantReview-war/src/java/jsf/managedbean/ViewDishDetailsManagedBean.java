package jsf.managedbean;

import ejb.session.stateless.DishSessionBeanLocal;
import entity.Dish;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.DishNotFoundException;



@Named(value = "viewDishDetailsManagedBean")
@ViewScoped

public class ViewDishDetailsManagedBean implements Serializable
{
    @EJB
    private DishSessionBeanLocal dishSessionBeanLocal;
    
    private Long dishIdToView;
    private String backMode;
    private Dish dishToView;
    
    
    
    public ViewDishDetailsManagedBean() 
    {
    }
    
    
    
    @PostConstruct
    public void postConstruct()
    {
        dishIdToView = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("dishIdToView");
        backMode = (String)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backMode");
        
        try
        {
            if(dishIdToView != null)
            {
                dishToView = dishSessionBeanLocal.retrieveDishById(dishIdToView);
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No dish has been selected", null));
            }
        }
        catch(DishNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the dish details: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    
    
    public void back(ActionEvent event) throws IOException
    {
        if(backMode == null || backMode.trim().length() == 0)
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllDishes.xhtml");
        }
        else
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect(backMode + ".xhtml");
        }
    }
    
    
    
    public void foo()
    {        
    }
    
    
    
    public void updateDish(ActionEvent event) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("dishIdToUpdate", dishIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("updateDish.xhtml");
    }
    
    
    
    public void deleteDish(ActionEvent event) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("dishIdToDelete", dishIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("deleteDish.xhtml");
    }
    
    public Dish getDishToView() {
        return dishToView;
    }

    public void setDishToView(Dish dishToView) {
        this.dishToView = dishToView;
    }

}