package dev.hiok.core.utils;

import java.time.Instant;
import java.util.UUID;

public class TransactionIdentifier {

  private final String prefix;
  private final UUID transactionId;
  private final Instant startAt;

  public TransactionIdentifier(String prefix) {
    this.prefix = prefix;
    this.transactionId = UUID.randomUUID();
    this.startAt = Instant.now();
  }

  public String getTransactionIdentifier() {
    return prefix + transactionId + ":" + startAt;
  }

}
