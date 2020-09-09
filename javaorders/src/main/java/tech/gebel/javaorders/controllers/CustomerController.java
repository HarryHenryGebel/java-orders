package tech.gebel.javaorders.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.gebel.javaorders.models.Customer;
import tech.gebel.javaorders.repositories.AgentsRepository;
import tech.gebel.javaorders.services.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/orders")
  private ResponseEntity<?> listCustomersWithOrders() {
    final List<Customer> list = customerService.findAllCustomers();
    return new ResponseEntity<>(list, HttpStatus.OK);
  }
}
