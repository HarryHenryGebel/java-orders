package tech.gebel.javaorders.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.gebel.javaorders.repositories.AgentsRepository;

@RestController
@RequestMapping("/agents")
public class AgentController {
  private final AgentsRepository agentsRepository;

  public AgentController(AgentsRepository agentsRepository) {
    this.agentsRepository = agentsRepository;
  }
}
