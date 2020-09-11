package tech.gebel.javaorders.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.pcollections.HashTreePSet;
import org.pcollections.TreePVector;
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

  @Override
  public List<OrderCountView> getOrderCount() {
    List<OrderCountView> customers = customersRepository.getCustomersOrderCount();
    return customers;
  }

  @Transactional
  @Override
  public Customer save(Customer customer) {
    Customer newCustomer;
    if (!customersRepository.existsById(customer.getCustomerCode())) {
      // if customer exists, load existing customer instance and replace data
      newCustomer =
        customersRepository
          .findById(customer.getCustomerCode())
          .orElseThrow(
            () ->
              new AssertionError(
                String.format(
                  "Existing ID %d did not return a record.\n" +
                  "This should not be possible, please report.",
                  customer.getCustomerCode()
                )
              )
          );
      newCustomer.replaceCustomerData(customer);
    } else newCustomer = new Customer(customer);

    Agent agent = agentsRepository
      .findById(customer.getAgent().getAgentCode())
      .orElseThrow(
        () ->
          new EntityNotFoundException(
            String.format(
              "Agent with id %d not found",
              customer.getAgent().getAgentCode()
            )
          )
      );
    newCustomer.setAgent(agent);

    List<Order> orders = new ArrayList<>();
    // don't have to check for orders on a new customer
    for (Order order : customer.getOrders()) {
      Order newOrder = new Order(order);
      newOrder.setCustomer(newCustomer);

      Set<Payment> payments = new HashSet();

      for (Payment payment : order.getPayments()) {
        Payment newPayment = paymentsRepository
          .findById(payment.getPaymentId())
          .orElseThrow(
            () ->
              new EntityNotFoundException(
                String.format(
                  "Payment with id $d not found",
                  payment.getPaymentId()
                )
              )
          );
        payments.add(newPayment);
      }
      newOrder.setPayments(HashTreePSet.from(payments));
      orders.add(newOrder);
    }
    newCustomer.setOrders(TreePVector.from(orders));

    return customersRepository.save(newCustomer);
  }

  @Transactional
  @Override
  public Customer save(Customer customer, long id) {
    if (!customersRepository.existsById(id)) {
      throw new EntityNotFoundException(
        String.format("Customer with id %d not found", id)
      );
    }
    customer.setCustomerCode(id);
    return save(customer);
  }
}
