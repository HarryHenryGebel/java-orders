package tech.gebel.javaorders.services;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.gebel.javaorders.models.Agent;
import tech.gebel.javaorders.models.Customer;
import tech.gebel.javaorders.models.Order;
import tech.gebel.javaorders.models.Payment;
import tech.gebel.javaorders.repositories.AgentsRepository;
import tech.gebel.javaorders.repositories.CustomersRepository;
import tech.gebel.javaorders.repositories.PaymentsRepository;
import tech.gebel.javaorders.views.OrderCountView;

@Service(value = "customerService")
public class CustomerServiceImplementation implements CustomerService {
  private final CustomersRepository customersRepository;
  private final AgentsRepository agentsRepository;
  private final PaymentsRepository paymentsRepository;

  public CustomerServiceImplementation(
    CustomersRepository customersRepository,
    AgentsRepository agentsRepository,
    PaymentsRepository paymentsRepository
  ) {
    this.customersRepository = customersRepository;
    this.agentsRepository = agentsRepository;
    this.paymentsRepository = paymentsRepository;
  }

  @Override
  public List<Customer> findAllCustomers() {
    ArrayList<Customer> list = new ArrayList<>();
    customersRepository.findAll().iterator().forEachRemaining(list::add);
    return list;
  }

  @Override
  public Customer findCustomerById(long id) {
    return customersRepository
      .findById(id)
      .orElseThrow(
        () ->
          new EntityNotFoundException(
            format("No Customer found with ID %d", id)
          )
      );
  }

  @Override
  public List<Customer> findCustomersLikeName(String name) {
    return customersRepository.findByCustomerNameContainingIgnoringCase(name);
  }

  @Override
  public List<OrderCountView> getOrderCount() {
    return customersRepository.getCustomersOrderCount();
  }

  @Transactional
  @Override
  public Customer save(Customer customer) {
    Customer newCustomer = new Customer(customer);

    Agent agent = agentsRepository
      .findById(customer.getAgent().getAgentCode())
      .orElseThrow(
        () ->
          new EntityNotFoundException(
            format(
              "Agent with id %d not found",
              customer.getAgent().getAgentCode()
            )
          )
      );
    newCustomer.setAgent(agent);

    Set<Order> orders = new HashSet<>();
    // don't have to check for orders on a new customer
    for (Order order : customer.getOrders()) {
      Order newOrder = new Order(order);
      newOrder.setCustomer(newCustomer);

      Set<Payment> payments = new HashSet<>();

      for (Payment payment : order.getPayments()) {
        Payment newPayment = paymentsRepository
          .findById(payment.getPaymentId())
          .orElseThrow(
            () ->
              new EntityNotFoundException(
                format("Payment with id %d not found", payment.getPaymentId())
              )
          );
        payments.add(newPayment);
      }
      newOrder.setPayments(payments);
      orders.add(newOrder);
    }
    newCustomer.setOrders(orders);

    return customersRepository.save(newCustomer);
  }

  @Transactional
  @Override
  public Customer save(Customer customer, long id) {
    if (!customersRepository.existsById(id)) {
      throw new EntityNotFoundException(
        format("Customer with id %d not found", id)
      );
    }
    customer.setCustomerCode(id);
    return save(customer);
  }

  @Override
  public Customer update(Customer customer, long id) {
    if (!customersRepository.existsById(id)) throw new EntityNotFoundException(
      format("Customer with id %d not found", id)
    );

    Customer originalCustomer = customersRepository
      .findById(id)
      .orElseThrow(
        () ->
          new AssertionError(
            format(
              "Customer record with id %d returns NULL despite existing.\n" +
              "This should not be possible, please report this as a bug.",
              id
            )
          )
      );
    originalCustomer.update(customer);
    if (customer.getAgent() != null) originalCustomer.setAgent(
      customer.getAgent()
    );
    if (customer.getOrders() != null) {
      originalCustomer.getOrders().clear();
      originalCustomer.getOrders().addAll(customer.getOrders());
    }
    return save(originalCustomer);
  }
}
