package tech.gebel.javaorders.services;

import tech.gebel.javaorders.models.Order;

public interface OrderService {
  Order findOrderById(long id);

  Order save(Order order);

  void save(Order order, long id);

  void deleteOrderById(long id);
}
