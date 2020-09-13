package tech.gebel.javaorders.controllers;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.gebel.javaorders.models.Order;
import tech.gebel.javaorders.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @DeleteMapping(value = "/order/{id}")
  private ResponseEntity<?> deleteCustomerById(@PathVariable long id) {
    orderService.deleteOrderById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping(value = "/order/{id}")
  private ResponseEntity<?> listOrderById(@PathVariable long id) {
    Order order = orderService.findOrderById(id);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @PostMapping(value = "/order")
  private ResponseEntity<?> addNewOrder(@Valid @RequestBody Order order) {
    Order newOrder = orderService.save(order);
    HttpHeaders httpHeaders = new HttpHeaders();
    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(newOrder.getOrderNumber())
      .toUri();
    httpHeaders.setLocation(uri);

    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
  }

  @PutMapping(value = "/order/{id}")
  private ResponseEntity<?> replaceOrder(
    @PathVariable long id,
    @RequestBody @Valid Order order
  ) {
    orderService.save(order, id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
