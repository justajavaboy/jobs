package gov.ca.cwds.jobs.cals.facility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Provides;
import gov.ca.cwds.cals.inject.CwsCmsDataAccessModule;
import gov.ca.cwds.cals.inject.FasDataAccessModule;
import gov.ca.cwds.cals.inject.LisDataAccessModule;
import gov.ca.cwds.cals.service.ChangedFacilityService;
import gov.ca.cwds.cals.service.dto.changed.ChangedFacilityDTO;
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
 * {@code run script: $ java -DDB_FAS_JDBC_URL="jdbc:postgresql://192.168.99.100:5432/?currentSchema=fas" \
-DDB_FAS_USER="postgres_data" -DDB_FAS_PASSWORD="CHANGEME" \
-DDB_LIS_JDBC_URL="jdbc:postgresql://192.168.99.100:5432/?currentSchema=lis" \
-DDB_LIS_USER="postgres_data" -DDB_LIS_PASSWORD="CHANGEME" \
-DDB_CMS_JDBC_URL="jdbc:db2://192.168.99.100:50000/DB0TDEV" -DDB_CMS_SCHEMA="CWSCMSRS" \
-DDB_CMS_USER="db2inst1" -DDB_CMS_PASSWORD="CHANGEME" \
-cp build/libs/DocumentIndexerJob-0.24.jar gov.ca.cwds.jobs.cals.facility.FacilityIndexerJob \
config/facility.yaml}
 * </pre>
 *
 * @author CWDS TPT-2
 */
public final class FacilityIndexerJob extends BaseCALSIndexerJob {

  private static final Logger LOGGER = LogManager.getLogger(FacilityIndexerJob.class);

  // todo tests, run javadoc, sonar

  public static void main(String[] args) {
    if (args.length == 0) {
      LOGGER.warn(
          "usage: java -cp jobs.jar gov.ca.cwds.jobs.cals.facility.FacilityIndexerJob path/to/config/file.yaml");
    }
    new FacilityIndexerJob(args[0]).run();
  }

  private FacilityIndexerJob(String configFileName) {
    super(configFileName);
  }

  @Override
  protected void configure() {
    super.configure();
    install(new CwsCmsDataAccessModule("jobs-cms-hibernate.cfg.xml"));
    install(new LisDataAccessModule("lis-hibernate.cfg.xml"));
    install(new FasDataAccessModule("fas-hibernate.cfg.xml"));
    bind(FacilityReader.class);
    bind(FacilityElasticJobWriter.class);
    bind(ChangedFacilityService.class);
  }

  @Provides
  @Inject
  public Job provideJob(FacilityReader jobReader, FacilityElasticJobWriter jobWriter) {
    return new AsyncReadWriteJob(jobReader, jobWriter);
  }

  static class FacilityElasticJobWriter extends CalsElasticJobWriter<ChangedFacilityDTO> {

    /**
     * Constructor.
     *
     * @param elasticsearchDao ES DAO
     * @param objectMapper Jackson object mapper
     */
    @Inject
    FacilityElasticJobWriter(Elasticsearch5xDao elasticsearchDao, ObjectMapper objectMapper) {
      super(elasticsearchDao, objectMapper);
    }
  }
}
