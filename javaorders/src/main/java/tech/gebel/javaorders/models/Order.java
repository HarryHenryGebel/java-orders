package tech.gebel.javaorders.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ordnum", nullable = false)
  private long orderNumber;

  @Column(name = "advanceamount")
  private double advanceAmount;

  @Column(name = "ordamount")
  private double orderAmount;

  @Column(name = "orderdescription", nullable = false)
  private String orderDescription;

  @ManyToOne
  @JoinColumn(name = "custcode", nullable = false)
  private Customer customer;

  @ManyToMany
  @JoinTable(
    name = "orderspayments",
    joinColumns = @JoinColumn(name = "ordnum"),
    inverseJoinColumns = @JoinColumn(name = "paymentid")
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
