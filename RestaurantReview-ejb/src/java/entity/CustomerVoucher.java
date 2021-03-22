/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author xuyis
 */
@Entity
public class CustomerVoucher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerVoucherId;
    private Boolean redeemed;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date timestamp;
    
    @ManyToOne(optional = false)
    private Customer owner;
    @ManyToOne(optional = false)
    private Voucher voucher;
    @ManyToOne(optional = false)
    private Transaction transaction;

    public CustomerVoucher() {
    }

    public CustomerVoucher(Boolean redeemed, Date timestamp) {
        this.redeemed = redeemed;
        this.timestamp = timestamp;
    }
    
    

    public Long getCustomerVoucherId() {
        return customerVoucherId;
    }

    public void setCustomerVoucherId(Long customerVoucherId) {
        this.customerVoucherId = customerVoucherId;
    }

    public Boolean getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(Boolean redeemed) {
        this.redeemed = redeemed;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerVoucherId != null ? customerVoucherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the customerVoucherId fields are not set
        if (!(object instanceof CustomerVoucher)) {
            return false;
        }
        CustomerVoucher other = (CustomerVoucher) object;
        if ((this.customerVoucherId == null && other.customerVoucherId != null) || (this.customerVoucherId != null && !this.customerVoucherId.equals(other.customerVoucherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CustomerVoucher[ id=" + customerVoucherId + " ]";
    }
    
}
