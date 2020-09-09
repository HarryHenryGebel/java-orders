package tech.gebel.javaorders.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.gebel.javaorders.models.Agent;
import tech.gebel.javaorders.services.AgentService;

@RestController
@RequestMapping(value = "/agents")
public class AgentController {
  private final AgentService agentService;

  public AgentController(AgentService agentService) {
    this.agentService = agentService;
  }

  @GetMapping(value = "agent/{id}")
  ResponseEntity<?> listAgentById(@PathVariable long id) {
    Agent agent = agentService.findAgentById(id);
    return new ResponseEntity<>(agent, HttpStatus.OK);
  }
}
