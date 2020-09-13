package tech.gebel.javaorders.services;

import static java.lang.String.format;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.gebel.javaorders.Utility;
import tech.gebel.javaorders.models.Customer;
import tech.gebel.javaorders.models.Order;
import tech.gebel.javaorders.repositories.CustomersRepository;
import tech.gebel.javaorders.repositories.OrdersRepository;
import tech.gebel.javaorders.repositories.PaymentsRepository;

@Service(value = "orderService")
public class OrderServiceImplementation implements OrderService {
  private final OrdersRepository ordersRepository;
  private final CustomersRepository customersRepository;
  private final PaymentsRepository paymentsRepository;

  public OrderServiceImplementation(
    OrdersRepository ordersRepository,
    CustomersRepository customersRepository,
    PaymentsRepository paymentsRepository
  ) {
    this.ordersRepository = ordersRepository;
    this.customersRepository = customersRepository;
    this.paymentsRepository = paymentsRepository;
  }

  @Override
  public Order findOrderById(long id) {
    return ordersRepository
      .findById(id)
      .orElseThrow(
        () ->
          new EntityNotFoundException(format("No Order found with ID %d", id))
      );
  }

  @Transactional
  @Override
  public Order save(Order order) {
    Order newOrder = new Order(order);

    Customer customer = customersRepository
      .findById(order.getCustomer().getCustomerCode())
      .orElseThrow(
        () ->
          new EntityNotFoundException(
            format(
              "Customer with id %d not found",
              order.getCustomer().getCustomerCode()
            )
          )
      );
    Utility.makeOrder(order, newOrder, customer, paymentsRepository);

    return ordersRepository.save(newOrder);
  }

  @Transactional
  @Override
  public void save(Order order, long id) {
    if (!ordersRepository.existsById(id)) throw new EntityNotFoundException(
      format("Order with id %d not found", id)
    );
    order.setOrderNumber(id);
    save(order);
  }

  @Transactional
  @Override
  public void deleteOrderById(long id) {
    Order order = findOrderById(id);
    ordersRepository.delete(order);
  }
}
