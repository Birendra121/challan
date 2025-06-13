package com.avis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avis.entity.CoalTransaction;
import com.avis.entity.Customer;
import com.avis.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ResponseBody
    @GetMapping("/details")
    public ResponseEntity<Customer> getCustomerDetails(@RequestParam String name) {
        return customerService.getByName(name)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    @ResponseBody
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }
    
    
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("transaction", new CoalTransaction());
        model.addAttribute("customers", customerService.getAll());
        return "add_transaction";
    }

}

