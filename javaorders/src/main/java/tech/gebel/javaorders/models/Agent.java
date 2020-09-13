package tech.gebel.javaorders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "agents")
public class Agent {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "agent_code", nullable = false)
  private long agentCode;

  @Column(name = "agent_name")
  private String agentName;

  @Column(name = "working_area")
  private String workingArea;

  @OneToMany(
    mappedBy = "agent",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @JsonIgnoreProperties(value = "agent", allowSetters = true)
  private List<Customer> customers = new ArrayList<>();

  private double commission;
  private String country, phone;

  public Agent() {}

  public Agent(
    String agentName,
    String workingArea,
    double commission,
    String phone,
    String country,
    List<Customer> customers
  ) {
    this.agentName = agentName;
    this.commission = commission;
    this.country = country;
    this.phone = phone;
    this.workingArea = workingArea;
    this.customers = customers;
  }

  public List<Customer> getCustomers() {
    return customers;
  }

  public void setCustomers(List<Customer> customers) {
    this.customers = customers;
  }

  public void setAgentCode(long agentCode) {
    this.agentCode = agentCode;
  }

  public long getAgentCode() {
    return agentCode;
  }

  public String getAgentName() {
    return agentName;
  }

  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }

  public double getCommission() {
    return commission;
  }

  public void setCommission(double commission) {
    this.commission = commission;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getWorkingArea() {
    return workingArea;
  }

  public void setWorkingArea(String workingArea) {
    this.workingArea = workingArea;
  }
}
