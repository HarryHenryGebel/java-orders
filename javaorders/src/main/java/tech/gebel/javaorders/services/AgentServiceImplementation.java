package tech.gebel.javaorders.services;

import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import tech.gebel.javaorders.models.Agent;
import tech.gebel.javaorders.repositories.AgentsRepository;

@Service(value = "agentService")
public class AgentServiceImplementation implements AgentService {
  private final AgentsRepository agentsRepository;

  public AgentServiceImplementation(AgentsRepository agentsRepository) {
    this.agentsRepository = agentsRepository;
  }

  @Override
  public Agent findAgentById(long id) {
    return agentsRepository
      .findById(id)
      .orElseThrow(
        () ->
          new EntityNotFoundException(
            String.format("No Agent found with ID %d", id)
          )
      );
  }
}
