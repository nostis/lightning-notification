package com.nostis.lightning_core.service;

import com.nostis.lightning_core.dao.CustomerCrud;
import com.nostis.lightning_core.exception.CustomerAlreadyExist;
import com.nostis.lightning_core.exception.CustomerNotExist;
import com.nostis.lightning_core.model.Customer;
import com.nostis.lightning_core.model.Lightning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {

    @Autowired
    private CustomerCrud customerCrud;

    public Customer addCustomer(String email, List<Float> location) {
        Optional<Customer> customer = customerCrud.findByEmail(email);

        if(customer.isPresent()) {
            throw new CustomerAlreadyExist("Customer with email '" + email + "' already exists");
        }
        else {
            return customerCrud.save(new Customer(email, location));
        }
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

    public List<Customer> getAllCustomers() {
        return StreamSupport
                .stream(customerCrud.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
