package gov.ca.cwds.jobs.component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.tuple.Pair;

import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Track job progress and record counts.
 * 
 * @author CWDS API Team
 */
public class JobProgressTrack extends ApiObjectIdentity {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

  private final AtomicInteger recsSentToIndexQueue = new AtomicInteger(0);

  private final AtomicInteger recsSentToBulkProcessor = new AtomicInteger(0);

  private final AtomicInteger rowsNormalized = new AtomicInteger(0);

  /**
   * Running count of records prepared for bulk indexing.
   */
  private final AtomicInteger recsBulkPrepared = new AtomicInteger(0);

  /**
   * Running count of records prepared for bulk deletion.
   */
  private final AtomicInteger recsBulkDeleted = new AtomicInteger(0);

  /**
   * Running count of records before bulk indexing.
   */
  private final AtomicInteger recsBulkBefore = new AtomicInteger(0);

  /**
   * Running count of records after bulk indexing.
   */
  private final AtomicInteger recsBulkAfter = new AtomicInteger(0);

  /**
   * Running count of errors during bulk indexing.
   */
  private final AtomicInteger recsBulkError = new AtomicInteger(0);

  private final List<Pair<String, String>> initialLoadRangesStarted = new ArrayList<>();

  private final List<Pair<String, String>> initialLoadRangesCompleted = new ArrayList<>();

  public AtomicInteger getRecsSentToIndexQueue() {
    return recsSentToIndexQueue;
  }

  public AtomicInteger getRecsSentToBulkProcessor() {
    return recsSentToBulkProcessor;
  }

  public AtomicInteger getRecsBulkPrepared() {
    return recsBulkPrepared;
  }

  public AtomicInteger getRecsBulkDeleted() {
    return recsBulkDeleted;
  }

  public AtomicInteger getRecsBulkBefore() {
    return recsBulkBefore;
  }

  public AtomicInteger getRecsBulkAfter() {
    return recsBulkAfter;
  }

  public AtomicInteger getRecsBulkError() {
    return recsBulkError;
  }

  public int trackQueuedToIndex() {
    return this.getRecsSentToIndexQueue().incrementAndGet();
  }

  public int trackNormalized() {
    return this.rowsNormalized.incrementAndGet();
  }

  public int trackBulkDeleted() {
    return this.getRecsBulkDeleted().getAndIncrement();
  }

  public int trackBulkPrepared() {
    return this.getRecsBulkPrepared().getAndIncrement();
  }

  public int trackBulkError() {
    return this.getRecsBulkError().getAndIncrement();
  }

  @Override
  public String toString() {
    return MessageFormat.format(
        "STATS: \nRecs To Index:  {0}\nRecs To Delete: {1}\nrecsBulkBefore: {2}\nrecsBulkAfter:  {3}\nrecsBulkError:  {4}",
        getRecsBulkPrepared(), getRecsBulkDeleted(), getRecsBulkBefore(), getRecsBulkAfter(),
        getRecsBulkError());
  }

}
