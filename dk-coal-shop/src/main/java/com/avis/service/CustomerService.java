package com.avis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avis.entity.Customer;
import com.avis.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repo;

    public List<Customer> getAll() {
        return repo.findAll();
    }

    public Customer save(Customer customer) {
        return repo.save(customer);
    }

    public Optional<Customer> getByName(String name) {
        return repo.findByName(name);
    }
}

