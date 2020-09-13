package tech.gebel.javaorders;

import static java.lang.String.format;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import tech.gebel.javaorders.models.Customer;
import tech.gebel.javaorders.models.Order;
import tech.gebel.javaorders.models.Payment;
import tech.gebel.javaorders.repositories.PaymentsRepository;

public final class Utility {

  private Utility() {}

  public static Object optionallyReplace(Object receiver, Object provider) {
    if (provider == null) return receiver; else return provider;
  }

  public static long optionallyReplace(long receiver, long provider) {
    if (provider == 0) return receiver; else return provider;
  }

  public static double optionallyReplace(double receiver, double provider) {
    if (provider == 0.0d) return receiver; else return provider;
  }

  public static void makeOrder(
    Order order,
    Order newOrder,
    Customer customer,
    PaymentsRepository paymentsRepository
  ) {
    newOrder.setCustomer(customer);

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
  }
}
