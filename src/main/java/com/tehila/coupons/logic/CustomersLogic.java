package com.tehila.coupons.logic;

import com.tehila.coupons.dal.ICustomersDal;
import com.tehila.coupons.dto.SuccessfulLoginDetails;
import com.tehila.coupons.dto.Customer;
import com.tehila.coupons.dto.CustomerDetails;
import com.tehila.coupons.dto.User;
import com.tehila.coupons.entities.CustomerEntity;
import com.tehila.coupons.entities.UserEntity;
import com.tehila.coupons.enums.ErrorType;
import com.tehila.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomersLogic {

    private ICustomersDal customersDal;
    private UsersLogic usersLogic;
    private PurchasesLogic purchasesLogic;
//    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Autowired
    public CustomersLogic(ICustomersDal customersDal, UsersLogic usersLogic, PurchasesLogic purchasesLogic) {
        this.customersDal = customersDal;
        this.usersLogic = usersLogic;
        this.purchasesLogic = purchasesLogic;
    }

    public void createCustomer(Customer customer) throws ServerException {
        validateCustomer(customer);
        customer.getUser().setUserType("Customer");
        customer.getUser().setCompanyId(null);

        UserEntity userEntity = this.usersLogic.createUser(customer.getUser());
        CustomerEntity customerEntity = convertCustomerToCustomerEntity(customer);
        customerEntity.setUser(userEntity);
        this.customersDal.save(customerEntity);
    }

    private CustomerEntity convertCustomerToCustomerEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity(customer.getId(), customer.getName(), customer.getAddress(), customer.getAmountOfKids(), customer.getPhone());
        return customerEntity;
    }


    public List<Customer> getCustomers(String userType) throws ServerException {
        if(!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }
        List<CustomerEntity> customerEntities = (List<CustomerEntity>) customersDal.findAll();
        List<Customer> customers = new ArrayList<>();
        for(CustomerEntity customerEntity : customerEntities) {
            customers.add(convertCustomerEntityToCustomer(customerEntity));
        }
        return customers;
    }

    public void deleteCustomer(int id, SuccessfulLoginDetails successfulLoginDetails) throws ServerException {
        if(id != successfulLoginDetails.getId() && !successfulLoginDetails.getUserType().equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }
        this.usersLogic.deleteUser(id, successfulLoginDetails);
        this.customersDal.deleteById(id);
    }

    public void updateCustomer(Customer customer) throws ServerException {
        validateCustomer(customer);
        CustomerEntity customerEntity = convertCustomerToCustomerEntity(customer);
        this.customersDal.save(customerEntity);
    }

    public CustomerDetails getCustomer(int id, SuccessfulLoginDetails successfulLoginDetails) throws ServerException {
        if(successfulLoginDetails.getUserType().equals("Admin") && id != successfulLoginDetails.getId()) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }
        return customersDal.getCustomerDetails(id);
    }

    private Customer convertCustomerEntityToCustomer(CustomerEntity customerEntity) {
        Customer customer = new Customer(customerEntity.getId(), customerEntity.getName(), customerEntity.getAddress(), customerEntity.getAmountOfKids(), customerEntity.getPhone());
        return customer;
    }

    private void validateCustomer(Customer customer) throws ServerException {

        if(!customer.getUser().getUserType().equals("Customer")){
            throw new ServerException(ErrorType.INVALID_ACTION);
        }

        if (customer.getName() == null) {
            throw new ServerException(ErrorType.INVALID_CUSTOMER_NAME, customer.toString());
        }

        if (customer.getName().length() > 45) {
            throw new ServerException(ErrorType.CUSTOMER_NAME_TOO_LONG, customer.toString());
        }

        if (customer.getAddress() != null && customer.getAddress().length() > 45) {
            throw new ServerException(ErrorType.CUSTOMER_ADDRESS_TOO_LONG, customer.toString());
        }

        if (customer.getPhone() != null && customer.getPhone().length() < 8) {
            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER, customer.toString());
        }

        if (customer.getPhone() != null && customer.getPhone().length() > 10) {
            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER, customer.toString());
        }

    }
}
