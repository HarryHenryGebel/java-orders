package tech.gebel.javaorders.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import tech.gebel.javaorders.models.Customer;
import tech.gebel.javaorders.views.OrderCountView;

@Component
public interface CustomersRepository extends CrudRepository<Customer, Long> {
  List<Customer> findByCustomerNameContainingIgnoringCase(String name);

  @Query(
    value = "SELECT customers.custcode customerId, customers.custname customerName, customers.phone customerPhone , count(orders.ordnum) orderCount " +
    "FROM customers " +
    "JOIN orders " +
    "ON customers.custcode = orders.custcode " +
    "GROUP BY customers.custcode " +
    "ORDER BY ordercount DESC",
    nativeQuery = true
  )
  List<OrderCountView> getCustomersOrderCount();
}
