package tech.gebel.javaorders.services;

import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import tech.gebel.javaorders.models.Order;
import tech.gebel.javaorders.repositories.OrdersRepository;

@Service(value = "orderService")
public class OrderServiceImplementation implements OrderService {
  private final OrdersRepository ordersRepository;

  public OrderServiceImplementation(OrdersRepository ordersRepository) {
    this.ordersRepository = ordersRepository;
  }

  @Override
  public Order findOrderById(long id) {
    return ordersRepository
      .findById(id)
      .orElseThrow(
        () ->
          new EntityNotFoundException(
            String.format("No Order found with ID %d", id)
          )
      );
  }
}
