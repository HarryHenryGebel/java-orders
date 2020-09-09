package tech.gebel.javaorders.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.gebel.javaorders.repositories.AgentsRepository;

@RestController
public class AgentController {
  private final AgentsRepository agentsRepository;

  public AgentController(AgentsRepository agentsRepository) {
    this.agentsRepository = agentsRepository;
  }

  @GetMapping("/customers/orders")
  public ResponseEntity<?> listCustomersWithOrders() {
    final list
  }
}
