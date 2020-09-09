package tech.gebel.javaorders.services;

import tech.gebel.javaorders.models.Agent;

public interface AgentService {
  Agent findAgentById(long id);
}
