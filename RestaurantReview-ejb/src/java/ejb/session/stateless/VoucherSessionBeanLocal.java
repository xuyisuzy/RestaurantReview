/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerVoucher;
import entity.Voucher;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewCustomerVoucherException;
import util.exception.CustomerVoucherExistException;
import util.exception.CustomerVoucherNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.VoucherExistException;
import util.exception.VoucherNotFoundException;

/**
 *
 * @author fengyuan
 */
@Local
public interface VoucherSessionBeanLocal {

    public CustomerVoucher createNewCustomerVoucher(CustomerVoucher newCustomerVoucher, Long voucherId, Long customerId) throws UnknownPersistenceException, InputDataValidationException, CreateNewCustomerVoucherException, CustomerVoucherExistException;

    public Voucher createNewDish(Voucher newVoucher) throws UnknownPersistenceException, InputDataValidationException, VoucherExistException;

    public List<Voucher> retrieveAllVouchers();

    public Voucher retrieveVoucherById(Long voucherId) throws VoucherNotFoundException;

    public List<CustomerVoucher> retrieveAllCustomerVouchersByCustomerId(Long customerId) throws CustomerVoucherNotFoundException;
    
}
