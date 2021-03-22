/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Restaurant extends User implements Serializable {

    private String name;
    private String address;
    private String contactNumber;
    private String[] photos;
    private Boolean acceptReservation;
    private double creditAmount;
    
    @OneToOne
    private BankAccount bankAccount;
    @OneToOne
    private TableConfiguration tableConfiguration;
    @OneToMany(mappedBy = "restaurant")
    private List<Dish> dishs;
    @OneToOne(mappedBy = "restaurant")
    private Reservation reservation;
    @OneToMany(mappedBy = "restaurant")
    private List<Promotion> promotions;
    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews;
    @OneToMany(mappedBy = "restaurant")
    private List<Transaction> transactions;
    
    public Restaurant() {
        
    }

    public Restaurant(String email, String password, String name, String address, String contactNumber, String[] photos, Boolean acceptReservation, double creditAmount) {
        super(email, password);
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.photos = photos;
        this.acceptReservation = acceptReservation;
        this.creditAmount = creditAmount;
    }

    
    public Long getId() {
        return super.getUseId();
    }

    public void setId(Long id) {
        super.setUseId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public Boolean getAcceptReservation() {
        return acceptReservation;
    }

    public void setAcceptReservation(Boolean acceptReservation) {
        this.acceptReservation = acceptReservation;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public TableConfiguration getTableConfiguration() {
        return tableConfiguration;
    }

    public void setTableConfiguration(TableConfiguration tableConfiguration) {
        this.tableConfiguration = tableConfiguration;
    }

    public List<Dish> getDishs() {
        return dishs;
    }

    public void setDishs(List<Dish> dishs) {
        this.dishs = dishs;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Restaurant)) {
            return false;
        }
        Restaurant other = (Restaurant) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Restaurant[ id=" + this.getId() + " ]";
    }
    
}
