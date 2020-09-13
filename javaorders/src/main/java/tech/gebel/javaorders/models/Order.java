package tech.gebel.javaorders.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "order_number", nullable = false)
  private long orderNumber;

  @Column(name = "advance_amount")
  private double advanceAmount;

  @Column(name = "order_amount")
  private double orderAmount;

  @Column(name = "order_description", nullable = false)
  private String orderDescription;

  @ManyToOne
  @JoinColumn(name = "customer_code", nullable = false)
  private Customer customer;

  @ManyToMany
  @JoinTable(
    name = "orders_payments",
    joinColumns = @JoinColumn(name = "order_number"),
    inverseJoinColumns = @JoinColumn(name = "payment_id")
  )
  Set<Payment> payments = new HashSet<>();

  public Order() {}

  public Order(
    long orderNumber,
    double advanceAmount,
    double orderAmount,
    String orderDescription,
    Customer customer,
    Set<Payment> payments
  ) {
    this.orderNumber = orderNumber;
    this.advanceAmount = advanceAmount;
    this.orderAmount = orderAmount;
    this.orderDescription = orderDescription;
    this.customer = customer;
    this.payments = payments;
  }

  public Order(Order order) {
    advanceAmount = order.advanceAmount;
    orderAmount = order.orderAmount;
    orderDescription = order.orderDescription;
    customer = order.customer;
    orderNumber = order.orderNumber;
    payments = order.payments;
  }

  public long getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(long orderNumber) {
    this.orderNumber = orderNumber;
  }

  public double getAdvanceAmount() {
    return advanceAmount;
  }

  public void setAdvanceAmount(double advanceAmount) {
    this.advanceAmount = advanceAmount;
  }

  public double getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(double orderAmount) {
    this.orderAmount = orderAmount;
  }

  public String getOrderDescription() {
    return orderDescription;
  }

  public void setOrderDescription(String orderDescription) {
    this.orderDescription = orderDescription;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Set<Payment> getPayments() {
    return payments;
  }

  public void setPayments(Set<Payment> payments) {
    this.payments = payments;
  }

  public void addPayments(Payment payment) {
    payments.add(payment);
  }
}
