package tech.gebel.javaorders.services;

import java.util.List;
import tech.gebel.javaorders.models.Customer;

public interface CustomerService {
  List<Customer> findAllCustomers();

  Customer findCustomerById(long id);

  List<Customer> findCustomersLikeName(String name);

  Customer save(Customer customer);

  Customer save(Customer customer, long id);

  Customer update(Customer customer, long id);

  void deleteCustomerById(long id);
}
