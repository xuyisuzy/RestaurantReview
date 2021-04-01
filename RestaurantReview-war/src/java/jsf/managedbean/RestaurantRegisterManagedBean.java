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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
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
//            newRestaurant.setAcceptReservation(getReservationStatus());
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
    
    public void handleFileUpload(FileUploadEvent event)
    {
        try
        {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + event.getFile().getFileName();
            newRestaurant.getPhotos().add(newFilePath);
//            System.err.println("********** Demo03ManagedBean.handleFileUpload(): File name: " + event.getFile().getFileName());
//            System.err.println("********** Demo03ManagedBean.handleFileUpload(): newFilePath: " + newFilePath);
            

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputStream();

            while (true)
            {
                a = inputStream.read(buffer);

                if (a < 0)
                {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  "File uploaded successfully", ""));
        }
        catch(IOException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  "File upload error: " + ex.getMessage(), ""));
        }
    }
    
}
