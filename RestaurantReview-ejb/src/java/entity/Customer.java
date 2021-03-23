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
public class Customer extends User implements Serializable {

    private Long firstName;
    private Long lastName;
    private String phoneNumber;
    private Integer level;
    
    @OneToMany(mappedBy = "owner")
    private List<CreditCard> creditCards;
    @OneToOne(mappedBy = "customer")
    private Reservation reservation;
    @OneToMany(mappedBy = "creater")
    private List<Review> reviews;
    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;
    @OneToMany(mappedBy = "owner")
    private List<CustomerVoucher> customerVouchers;

    public Customer() {
    }


    public Customer(String email, String password, Long firstName, Long lastName, String phoneNumber, Long useId) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.level = 1;
    }
    

    public Long getId() {
        return super.getUseId();
    }

    public void setId(Long id) {
        super.setUseId(id);
    }

    public Long getFirstName() {
        return firstName;
    }

    public void setFirstName(Long firstName) {
        this.firstName = firstName;
    }

    public Long getLastName() {
        return lastName;
    }

    public void setLastName(Long lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
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

    public List<CustomerVoucher> getCustomerVouchers() {
        return customerVouchers;
    }

    public void setCustomerVouchers(List<CustomerVoucher> customerVouchers) {
        this.customerVouchers = customerVouchers;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getUseId() != null ? super.getUseId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.getId()== null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + getId()+ " ]";
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    
}
