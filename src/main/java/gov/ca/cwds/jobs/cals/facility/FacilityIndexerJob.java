package gov.ca.cwds.jobs.cals.facility;

import com.google.inject.Guice;
import com.google.inject.Injector;
import gov.ca.cwds.cals.inject.CwsCmsDataAccessModule;
import gov.ca.cwds.cals.inject.FasDataAccessModule;
import gov.ca.cwds.cals.inject.LisDataAccessModule;
import gov.ca.cwds.cals.service.ChangedFacilityService;
import gov.ca.cwds.jobs.Job;
import gov.ca.cwds.jobs.cals.BaseCALSIndexerJob;
import java.io.File;
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
public final class FacilityIndexerJob extends BaseCALSIndexerJob<FacilityReader> {

  private static final Logger LOGGER = LogManager.getLogger(FacilityIndexerJob.class);

  // todo tests, run javadoc, sonar

  public static void main(String[] args) {
    if (args.length == 0) {
      LOGGER.warn(
          "usage: java -cp jobs.jar gov.ca.cwds.jobs.cals.facility.FacilityIndexerJob path/to/config/file.yaml");
    }
    try {
      final File configFile = new File(args[0]);
      final Injector injector = Guice.createInjector(new FacilityIndexerJob(configFile));
      Job job = injector.getInstance(Job.class);
      job.run();
    } catch (Exception e) {
      LOGGER.fatal("ERROR: ", e.getMessage(), e);
    }
  }

  private FacilityIndexerJob(File config) {
    super(config, FacilityReader.class);
  }

  @Override
  protected void configure() {
    super.configure();
    install(new CwsCmsDataAccessModule("jobs-cms-hibernate.cfg.xml"));
    install(new LisDataAccessModule("lis-hibernate.cfg.xml"));
    install(new FasDataAccessModule("fas-hibernate.cfg.xml"));
    bind(ChangedFacilityService.class);
  }
}
