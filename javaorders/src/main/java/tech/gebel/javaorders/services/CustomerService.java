package tech.gebel.javaorders.services;

import java.util.List;
import tech.gebel.javaorders.models.Customer;
import tech.gebel.javaorders.views.OrderCountView;

public interface CustomerService {
  List<Customer> findAllCustomers();

  Customer findCustomerById(long id);

  List<Customer> findCustomersLikeName(String name);

  List<OrderCountView> getOrderCount();

  Customer save(Customer customer);
}
