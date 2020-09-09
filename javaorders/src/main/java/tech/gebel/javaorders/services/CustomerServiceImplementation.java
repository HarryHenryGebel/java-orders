package tech.gebel.javaorders.services;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.pcollections.TreePVector;
import org.springframework.stereotype.Service;
import tech.gebel.javaorders.models.Customer;
import tech.gebel.javaorders.repositories.CustomersRepository;

@Service(value = "customerService")
public class CustomerServiceImplementation implements CustomerService {
  private final CustomersRepository customersRepository;

  public CustomerServiceImplementation(
    CustomersRepository customersRepository
  ) {
    this.customersRepository = customersRepository;
  }

  @Override
  public List<Customer> findAllCustomers() {
    ArrayList<Customer> list = new ArrayList<>();
    customersRepository.findAll().iterator().forEachRemaining(list::add);
    return TreePVector.from(list);
  }

  @Override
  public Customer findCustomerById(long id) {
    return customersRepository
      .findById(id)
      .orElseThrow(
        () ->
          new EntityNotFoundException(
            String.format("No Customer found with ID %d", id)
          )
      );
  }

  @Override
  public List<Customer> findCustomersLikeName(String name) {
    return customersRepository.findByCustomerNameContainingIgnoringCase(name);
  }
}
