package tech.gebel.javaorders.controllers;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.gebel.javaorders.models.Customer;
import tech.gebel.javaorders.models.Order;
import tech.gebel.javaorders.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping(value = "/order/{id}")
  private ResponseEntity<?> listOrderById(@PathVariable long id) {
    Order order = orderService.findOrderById(id);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @PostMapping(value = "/order")
  private ResponseEntity<?> createOrder(@Valid @RequestBody Order order) {
    Order newOrder = orderService.save(order);
  }
}
