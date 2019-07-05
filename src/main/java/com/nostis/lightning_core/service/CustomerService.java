package com.nostis.lightning_core.service;

import com.nostis.lightning_core.dao.CustomerCrud;
import com.nostis.lightning_core.exception.CustomerNotExist;
import com.nostis.lightning_core.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerCrud customerCrud;

    public Customer addCustomer(String email, List<Float> location) {
        return customerCrud.save(new Customer(email, location));
    }

    public String removeCustomer(String email) {
        Optional<Customer> customer = customerCrud.findByEmail(email);

        if(customer.isPresent()) {
            customerCrud.delete(customer.get());
        }
        else {
            throw new CustomerNotExist("Customer with email '" + email + "' not exists");
        }

        return email;
    }
}
