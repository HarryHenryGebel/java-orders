package tech.gebel.javaorders.services;

import tech.gebel.javaorders.models.Order;

public interface OrderService {
  Order findOrderById(long id);
}
