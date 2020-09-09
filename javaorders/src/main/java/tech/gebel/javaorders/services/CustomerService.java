package tech.gebel.javaorders.services;

import java.util.List;
import tech.gebel.javaorders.models.Customer;

public interface CustomerService {
  List<Customer> findAllCustomers();

  Customer findCustomerById(long id);
}
