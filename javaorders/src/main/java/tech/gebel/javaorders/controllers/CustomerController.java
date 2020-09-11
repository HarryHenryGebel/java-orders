package tech.gebel.javaorders.controllers;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.gebel.javaorders.models.Customer;
import tech.gebel.javaorders.services.CustomerService;
import tech.gebel.javaorders.views.OrderCountView;

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

  @GetMapping("/customer/{id}")
  private ResponseEntity<?> listCustomerById(@PathVariable long id) {
    Customer customer = customerService.findCustomerById(id);
    return new ResponseEntity<>(customer, HttpStatus.OK);
  }

  @GetMapping("/namelike/{name}")
  private ResponseEntity<?> listCustomersLikeName(@PathVariable String name) {
    List<Customer> customers = customerService.findCustomersLikeName(name);
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @GetMapping("/orders/count")
  private ResponseEntity<?> getCustomerOrderCounts() {
    List<OrderCountView> customers = customerService.getOrderCount();
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @PostMapping("/customer")
  private ResponseEntity<?> addNewCustomer(
    @Valid @RequestBody Customer customer
  ) {
    Customer newCustomer = customerService.save(customer);
    HttpHeaders headers = new HttpHeaders();
    URI createdLocation = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(newCustomer.getCustomerCode())
      .toUri();
    headers.setLocation(createdLocation);
    return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
  }

  @PatchMapping("/customer/{id}")
  private ResponseEntity<?> updateCustomer(
    @PathVariable long id,
    @RequestBody @Valid Customer customer
  ) {
    customerService.update(customer, id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/customer/{id}")
  private ResponseEntity<?> replaceCustomer(
    @PathVariable long id,
    @RequestBody @Valid Customer customer
  ) {
    customerService.save(customer, id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
