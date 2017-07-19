package gov.ca.cwds.jobs.cals.rfa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Provides;
import gov.ca.cwds.cals.inject.CalsnsDataAccessModule;
import gov.ca.cwds.cals.service.dto.changed.ChangedRFA1aFormDTO;
import gov.ca.cwds.cals.service.rfa.RFA1aFormsCollectionService;
import gov.ca.cwds.data.es.Elasticsearch5xDao;
import gov.ca.cwds.jobs.Job;
import gov.ca.cwds.jobs.cals.BaseCALSIndexerJob;
import gov.ca.cwds.jobs.util.AsyncReadWriteJob;
import gov.ca.cwds.jobs.util.elastic.CalsElasticJobWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p> Command line arguments: </p>
 *
 * <pre>
 * {@code run script: $ java -DDB_CALSNS_JDBC_URL="jdbc:postgresql://192.168.99.100:5432/?currentSchema=calsns" \
-DDB_CALSNS_USER="postgres_data" -DDB_CALSNS_PASSWORD="CHANGEME" \
-cp build/libs/DocumentIndexerJob-0.24.jar gov.ca.cwds.jobs.cals.rfa.RFA1aFormIndexerJob \
config/CALS_RFA1aForm.yaml}
 * </pre>
 *
 * @author CWDS TPT-2
 */
public final class RFA1aFormIndexerJob extends BaseCALSIndexerJob {

  private static final Logger LOGGER = LogManager.getLogger(RFA1aFormIndexerJob.class);

  public static void main(String[] args) {
    if (args.length == 0) {
      LOGGER.warn(
          "usage: java -cp jobs.jar gov.ca.cwds.jobs.cals.rfa.RFA1aFormIndexerJob path/to/config/file.yaml");
    }
    new RFA1aFormIndexerJob(args[0]).run();
  }

  private RFA1aFormIndexerJob(String configFileName) {
    super(configFileName);
  }

  @Override
  protected void configure() {
    super.configure();
    install(new CalsnsDataAccessModule("calsns-hibernate.cfg.xml"));
    bind(RFA1aFormReader.class);
    bind(RFA1aFormElasticJobWriter.class);
    bind(RFA1aFormsCollectionService.class);
  }

  @Provides
  @Inject
  public Job provideJob(RFA1aFormReader jobReader, RFA1aFormElasticJobWriter jobWriter) {
    return new AsyncReadWriteJob(jobReader, jobWriter);
  }

  static class RFA1aFormElasticJobWriter extends CalsElasticJobWriter<ChangedRFA1aFormDTO> {

    /**
     * Constructor.
     *
     * @param elasticsearchDao ES DAO
     * @param objectMapper Jackson object mapper
     */
    @Inject
    public RFA1aFormElasticJobWriter(Elasticsearch5xDao elasticsearchDao,
        ObjectMapper objectMapper) {
      super(elasticsearchDao, objectMapper);
    }
  }
}
