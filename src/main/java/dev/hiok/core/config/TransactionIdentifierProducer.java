package dev.hiok.core.config;

import dev.hiok.core.utils.TransactionIdentifier;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;

public class TransactionIdentifierProducer {

  @Produces
  @RequestScoped
  @DefaultBean
  public TransactionIdentifier testProduce() {
    return new TransactionIdentifier("Test:");
  }

  @Produces
  @RequestScoped
  @IfBuildProfile("prod")
  public TransactionIdentifier prodProduce() {
    return new TransactionIdentifier("Prod:");
  }

}
