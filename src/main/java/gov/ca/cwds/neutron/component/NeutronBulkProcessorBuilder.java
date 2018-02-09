package gov.ca.cwds.neutron.component;

import org.elasticsearch.action.bulk.BulkProcessor;


public interface NeutronBulkProcessorBuilder {

  /**
   * Instantiate one Elasticsearch BulkProcessor per working thread.
   * 
   * <p>
   * ES BulkProcessor is technically thread safe, but you can safely construct an instance per
   * thread, if desired.
   * </p>
   * 
   * <p>
   * NEXT: make configurable.
   * </p>
   * 
   * @return an ES bulk processor
   */
  BulkProcessor buildBulkProcessor();

}
