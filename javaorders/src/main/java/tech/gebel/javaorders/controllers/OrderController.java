package tech.gebel.javaorders.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
