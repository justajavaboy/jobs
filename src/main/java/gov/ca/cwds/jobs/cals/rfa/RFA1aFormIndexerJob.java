package gov.ca.cwds.jobs.cals.rfa;

import com.google.inject.Guice;
import com.google.inject.Injector;
import gov.ca.cwds.cals.inject.CalsnsDataAccessModule;
import gov.ca.cwds.cals.service.rfa.RFA1aFormsCollectionService;
import gov.ca.cwds.jobs.Job;
import gov.ca.cwds.jobs.cals.BaseCALSIndexerJob;
import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p> Command line arguments: </p>
 *
 * <pre>
 * {@code run script: $ java -DDB_CALSNS_JDBC_URL="jdbc:postgresql://192.168.99.100:5432/?currentSchema=calsns" \
-DDB_CALSNS_USER="postgres_data" -DDB_CALSNS_PASSWORD="postgres_data" \
-cp build/libs/DocumentIndexerJob-0.24.jar gov.ca.cwds.jobs.cals.rfa.RFA1aFormIndexerJob \
config/CALS_RFA1aForm-my.yaml}
 * </pre>
 *
 * @author CWDS TPT-2
 */
public final class RFA1aFormIndexerJob extends BaseCALSIndexerJob<RFA1aFormReader> {

  private static final Logger LOGGER = LogManager.getLogger(RFA1aFormIndexerJob.class);

  public static void main(String[] args) {
    if (args.length == 0) {
      LOGGER.warn(
          "usage: java -cp jobs.jar gov.ca.cwds.jobs.cals.rfa.RFA1aFormIndexerJob path/to/config/file.yaml");
    }

    try {
      final File configFile = new File(args[0]);
      final Injector injector = Guice.createInjector(new RFA1aFormIndexerJob(configFile));
      Job job = injector.getInstance(Job.class);
      job.run();
    } catch (Exception e) {
      LOGGER.fatal("ERROR: ", e.getMessage(), e);
    }
  }

  private RFA1aFormIndexerJob(File config) {
    super(config, RFA1aFormReader.class);
  }

  @Override
  protected void configure() {
    super.configure();
    install(new CalsnsDataAccessModule("calsns-hibernate.cfg.xml"));
    bind(RFA1aFormsCollectionService.class);
  }
}
