/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ReservationSessionBeanLocal;
import entity.Reservation;
import entity.Restaurant;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author zhiliangwang
 */
@Named(value = "reservationManagedBean")
@ViewScoped
public class ReservationManagedBean implements Serializable
{

    

    @EJB
    private ReservationSessionBeanLocal reservationSessionBeanLocal;
    
    private List<Reservation> reservations;
    
    private Reservation selectedReservation;
    
    private Restaurant currRestaurant;
    
    public ReservationManagedBean()
    {
    }
    
    @PostConstruct
    public void postConstruct()
    {

            Restaurant currRestaurant = (Restaurant)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentRestaurant");
            System.out.println("current rest: " + currRestaurant.getName());
            reservations = currRestaurant.getReservations();
            //reservationSessionBeanLocal.retrieveReservationById(currRest.getId());

    }
    
    public void viewReservationDetails(ActionEvent event) throws IOException
    {
        Long reservationIdToView = (Long)event.getComponent().getAttributes().get("reservationId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("reservationIdToView", reservationIdToView);
        //FacesContext.getCurrentInstance().getExternalContext().redirect();
    }
    
    public List<Reservation> getReservations()
    {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations)
    {
        this.reservations = reservations;
    }

    public Reservation getSelectedReservation()
    {
        return selectedReservation;
    }

    public void setSelectedReservation(Reservation selectedReservation)
    {
        this.selectedReservation = selectedReservation;
    }

    public Restaurant getCurrRestaurant()
    {
        return currRestaurant;
    }

    public void setCurrRestaurant(Restaurant currRestaurant)
    {
        this.currRestaurant = currRestaurant;
    }
}
