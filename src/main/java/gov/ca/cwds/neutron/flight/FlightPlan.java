package gov.ca.cwds.neutron.flight;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.neutron.exception.NeutronCheckedException;
import gov.ca.cwds.neutron.jetpack.CheeseRay;
import gov.ca.cwds.neutron.util.shrinkray.NeutronDateUtils;

/**
 * Represents batch rocket options from the command line.
 * 
 * @author CWDS API Team
 */
public class FlightPlan implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(FlightPlan.class);

  /**
   * Location of Elasticsearch configuration file for people index.
   */
  String esConfigPeopleLoc;

  /**
   * Location of Elasticsearch configuration file for people summary index.
   */
  String esConfigPeopleSummaryLoc;

  /**
   * Name of index to create or use. If this is not provided then alias is used from ES Config file.
   */
  private String indexName;

  private boolean simulateLaunch;

  /**
   * Last time rocket was executed in format 'yyyy-MM-dd HH.mm.ss' If this is provided then time
   * stamp given in last run time file is ignored.
   */
  private Date overrideLastRunTime;

  /**
   * Optional end date for standalone last change runs. Pseudo code:
   * {@code BETWEEN :overrideLastRunTime AND overrideLastEndTime}.
   * 
   * <p>
   * If not provided, defaults to current timestamp.
   * </p>
   */
  private Date overrideLastEndTime;

  /**
   * Location of last run file.
   */
  private String lastRunLoc;

  /**
   * Whether to run in periodic "last run" mode or "initial" mode.
   */
  boolean lastRunMode = true;

  /**
   * When running in "initial load" mode, specifies the starting bucket of records to be processed
   * by this rocket.
   * <p>
   * Required for "initial load" mode.
   * </p>
   */
  private long startBucket = 1L;

  /**
   * When running in "initial load" mode, specifies the ending bucket of records to be processed by
   * this rocket.
   * <p>
   * Required for "initial load" mode.
   * </p>
   */
  private long endBucket = 1L;

  /**
   * Total threads to allocate to this batch run. Defaults to all available cores.
   */
  private long threadCount;

  /**
   * If true then load sealed and sensitive data.
   */
  private boolean loadSealedAndSensitive;

  private boolean rangeGiven;

  private String baseDirectory;

  private boolean refreshMqt;

  private boolean dropIndex;

  /**
   * Default constructor.
   */
  public FlightPlan() {
    // Default constructor
  }

  /**
   * Construct from all settings.
   * 
   * @param esConfigPeopleLoc location of Elasticsearch configuration file for people index
   * @param esConfigPeopleSummaryLoc location of Elasticsearch configuration file for people summary
   *        index
   * @param indexName Name of index to use. If not provided, then use alias from ES config.
   * @param lastRunTime Last run time to use
   * @param lastRunLoc location of last run file
   * @param lastRunMode is last run mode or not
   * @param startBucket starting bucket number
   * @param endBucket ending bucket number
   * @param threadCount number of simultaneous threads
   * @param loadSealedAndSensitive If true then load sealed and sensitive data
   * @param rangeGiven initial load -- provided range (full load only)
   * @param baseDirectory base folder for rocket execution (full load only)
   * @param refreshMqt refresh materialized query tables (full load only)
   * @param dropIndex drop the index before start (full load only)
   * @param simulateLaunch simulate launch (test mode!)
   */
  public FlightPlan(String esConfigPeopleLoc, String esConfigPeopleSummaryLoc, String indexName,
      Date lastRunTime, String lastRunLoc, boolean lastRunMode, long startBucket, long endBucket,
      long threadCount, boolean loadSealedAndSensitive, boolean rangeGiven, String baseDirectory,
      boolean refreshMqt, boolean dropIndex, boolean simulateLaunch) {
    this.esConfigPeopleLoc = esConfigPeopleLoc;
    this.esConfigPeopleSummaryLoc = esConfigPeopleSummaryLoc;
    this.indexName = StringUtils.isBlank(indexName) ? null : indexName;
    this.overrideLastRunTime = NeutronDateUtils.freshDate(lastRunTime);
    this.lastRunLoc = lastRunLoc;
    this.lastRunMode = lastRunMode;
    this.startBucket = startBucket;
    this.endBucket = endBucket;
    this.threadCount = threadCount;
    this.loadSealedAndSensitive = loadSealedAndSensitive;
    this.rangeGiven = rangeGiven;
    this.baseDirectory = baseDirectory;
    this.refreshMqt = refreshMqt;
    this.dropIndex = dropIndex;
    this.simulateLaunch = simulateLaunch;
  }

  /**
   * Copy constructor.
   * 
   * @param flightPlan other rocket options
   */
  public FlightPlan(final FlightPlan flightPlan) {
    this.esConfigPeopleLoc = flightPlan.esConfigPeopleLoc;
    this.esConfigPeopleSummaryLoc = flightPlan.esConfigPeopleSummaryLoc;
    this.indexName = StringUtils.isBlank(flightPlan.indexName) ? null : flightPlan.indexName;
    this.overrideLastRunTime = flightPlan.overrideLastRunTime;
    this.lastRunLoc = flightPlan.lastRunLoc;
    this.lastRunMode = flightPlan.lastRunMode;
    this.startBucket = flightPlan.startBucket;
    this.endBucket = flightPlan.endBucket;
    this.threadCount = flightPlan.threadCount;
    this.loadSealedAndSensitive = flightPlan.loadSealedAndSensitive;
    this.rangeGiven = flightPlan.rangeGiven;
    this.baseDirectory = flightPlan.baseDirectory;
    this.refreshMqt = flightPlan.refreshMqt;
    this.dropIndex = flightPlan.dropIndex;
    this.simulateLaunch = flightPlan.simulateLaunch;
  }

  /**
   * Smart/auto mode. If last run date is older than 25 years, assume initial load. Written when
   * DevOps started using Rundeck and was unable to pass parameters to jobs.
   * 
   * <p>
   * HACK: This approach was concocted because Rundeck was not configured to accept job parameters.
   * </p>
   * 
   * @param lastRun last successful run date
   * @return true if running initial load
   */
  public boolean determineInitialLoad(final Date lastRun) {
    LOGGER.debug("Last successsful run time: {}", lastRun); // NOSONAR

    final Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -25);
    return !isLastRunMode() || lastRun.before(cal.getTime());
  }

  /**
   * Getter for location of Elasticsearch configuration file.
   * 
   * @return location of Elasticsearch configuration file
   */
  public String getEsConfigLoc() {
    return esConfigPeopleLoc;
  }

  /**
   * Get name of the index to create or use.
   * 
   * @return Name of the index to use.
   */
  public String getIndexName() {
    return indexName;
  }

  /**
   * Get last run time override in format 'yyyy-MM-dd HH.mm.ss'. If this is non-null then time
   * provided in last run time file is ignored.
   * 
   * @return Last run time
   */
  public Date getOverrideLastRunTime() {
    return overrideLastRunTime != null ? new Date(overrideLastRunTime.getTime()) : null;
  }

  /**
   * Getter for location of last run date/time file.
   * 
   * @return location of last run file
   */
  public String getLastRunLoc() {
    return lastRunLoc;
  }

  public void setLastRunLoc(String lastRunLoc) {
    this.lastRunLoc = lastRunLoc;
  }

  /**
   * Getter for last run mode.
   * 
   * @return last run mode
   */
  public boolean isLastRunMode() {
    return lastRunMode;
  }

  /**
   * Getter for starting bucket.
   * 
   * @return starting bucket
   */
  public long getStartBucket() {
    return startBucket;
  }

  /**
   * Getter for last bucket.
   * 
   * @return last bucket
   */
  public long getEndBucket() {
    return endBucket;
  }

  /**
   * Getter for thread count.
   * 
   * @return thread count
   */
  public long getThreadCount() {
    return threadCount;
  }

  /**
   * Get if sealed and sensitive data should be loaded.
   * 
   * @return true if sealed and sensitive data should be loaded, false otherwise.
   */
  public boolean isLoadSealedAndSensitive() {
    return loadSealedAndSensitive;
  }

  /**
   * Define a command line option.
   * 
   * @param shortOpt single letter option name
   * @param longOpt long option name
   * @param description option description
   * @param required true if required
   * @param argc number of arguments to this option
   * @param type arguments Java class
   * @param sep argument separator
   * @return command line option
   */
  public static Option makeOpt(String shortOpt, String longOpt, String description,
      boolean required, int argc, Class<?> type, char sep) {
    return Option.builder(shortOpt).argName(longOpt).required(required).longOpt(longOpt)
        .desc(description).numberOfArgs(argc).type(type).valueSeparator(sep).build();
  }

  /**
   * Define command line options.
   * 
   * @return command line option definitions
   */
  protected static Options buildCmdLineOptions() {
    final Options ret = new Options();

    ret.addOption(NeutronCmdLineOption.SIMULATE_LAUNCH.getOpt());
    ret.addOption(NeutronCmdLineOption.ES_CONFIG_PEOPLE.getOpt());
    ret.addOption(NeutronCmdLineOption.ES_CONFIG_PEOPLE_SUMMARY.getOpt());
    ret.addOption(NeutronCmdLineOption.INDEX_NAME.getOpt());
    ret.addOption(NeutronCmdLineOption.LAST_RUN_TIME.getOpt());
    ret.addOption(NeutronCmdLineOption.THREADS.getOpt());
    ret.addOption(NeutronCmdLineOption.LOAD_SEALED_SENSITIVE.getOpt());

    ret.addOption(NeutronCmdLineOption.FULL_LOAD.getOpt());
    ret.addOption(NeutronCmdLineOption.REFRESH_MQT.getOpt());
    ret.addOption(NeutronCmdLineOption.DROP_INDEX.getOpt());

    ret.addOption(NeutronCmdLineOption.BUCKET_RANGE.getOpt());
    ret.addOption(NeutronCmdLineOption.BUCKET_TOTAL.getOpt()); // Obsolete
    ret.addOption(NeutronCmdLineOption.MIN_ID.getOpt());
    ret.addOption(NeutronCmdLineOption.MAX_ID.getOpt());

    // RUN MODE: mutually exclusive choice.
    final OptionGroup group = new OptionGroup();
    group.setRequired(true);
    group.addOption(NeutronCmdLineOption.LAST_RUN_FILE.getOpt());
    group.addOption(NeutronCmdLineOption.BASE_DIRECTORY.getOpt());
    ret.addOptionGroup(group);

    return ret;
  }

  /**
   * Pretty print usage.
   * 
   * @throws NeutronCheckedException on IO exception
   */
  protected static void printUsage() throws NeutronCheckedException {
    try (final StringWriter sw = new StringWriter()) {
      final String pad = StringUtils.leftPad("", 90, '=');
      new HelpFormatter().printHelp(new PrintWriter(sw), 100, "Batch loader",
          pad + "\nUSAGE: java <rocket class> ...\n" + pad, buildCmdLineOptions(), 4, 8, pad, true);
      LOGGER.error(sw.toString()); // NOSONAR
    } catch (IOException e) {
      throw CheeseRay.checked(LOGGER, e, "INCORRECT USAGE! {}", e.getMessage());
    }
  }

  private static Pair<Long, Long> parseBuckets(final String[] vals) {
    Long startBucket = Long.MIN_VALUE;
    Long endBucket = startBucket;

    // Appease SonarQube.
    int cntr = 0;
    for (String val : vals) {
      if (cntr++ == 0) {
        startBucket = Long.valueOf(val);
      } else {
        endBucket = Long.valueOf(val);
      }
    }

    return Pair.of(startBucket, endBucket);
  }

  /**
   * Parse the command line return the rocket settings.
   * 
   * @param args command line to parse
   * @return JobOptions defining this rocket
   * @throws NeutronCheckedException if unable to parse command line
   */
  public static FlightPlan parseCommandLine(final String[] args) throws NeutronCheckedException {
    String esConfigPeopleLoc = null;
    String esConfigPeopleSummaryLoc = null;
    String indexName = null;
    String lastRunLoc = null;
    String baseDirectory = null;

    Date lastRunTime = null;
    long threadCount = 0L;

    boolean lastRunMode = true;
    boolean loadSealedAndSensitive = false;
    boolean rangeGiven = false;
    boolean refreshMqt = false;
    boolean dropIndex = false;
    boolean simulateLaunch = false;

    // CHECKSTYLE:OFF
    Pair<Long, Long> bucketRange = Pair.of(-1L, 0L);
    // CHECKSTYLE:ON

    try {
      final Options options = buildCmdLineOptions();
      final CommandLineParser parser = new DefaultParser();
      final CommandLine cmd = parser.parse(options, args);

      // Java clincher: case statements only take constants. Even compile-time constants, like
      // enum members (evaluated at compile time), are not considered "constants."
      for (final Option opt : cmd.getOptions()) {
        switch (opt.getArgName()) {
          case NeutronLongCmdLineName.CMD_LINE_ES_CONFIG_PEOPLE:
            esConfigPeopleLoc = opt.getValue().trim();
            break;

          case NeutronLongCmdLineName.CMD_LINE_ES_CONFIG_PEOPLE_SUMMARY:
            esConfigPeopleSummaryLoc = opt.getValue().trim();
            break;

          case NeutronLongCmdLineName.CMD_LINE_INDEX_NAME:
            indexName = opt.getValue().trim();
            break;

          case NeutronLongCmdLineName.CMD_LINE_LAST_RUN_TIME:
            String lastRunTimeStr = opt.getValue().trim();
            lastRunTime = createDate(lastRunTimeStr);
            break;

          case NeutronLongCmdLineName.CMD_LINE_LAST_RUN_FILE:
            lastRunLoc = opt.getValue().trim();
            break;

          case NeutronLongCmdLineName.CMD_LINE_BASE_DIRECTORY:
            lastRunMode = true;
            baseDirectory = opt.getValue().trim();
            break;

          case NeutronLongCmdLineName.CMD_LINE_INITIAL_LOAD:
            lastRunMode = false;
            break;

          case NeutronLongCmdLineName.CMD_LINE_BUCKET_RANGE:
            lastRunMode = false;
            rangeGiven = true;
            bucketRange = parseBuckets(opt.getValues());
            break;

          case NeutronLongCmdLineName.CMD_LINE_THREADS:
            threadCount = Long.parseLong(opt.getValue());
            break;

          case NeutronLongCmdLineName.CMD_LINE_LOAD_SEALED_AND_SENSITIVE:
            loadSealedAndSensitive = Boolean.parseBoolean(opt.getValue().trim());
            break;

          case NeutronLongCmdLineName.CMD_LINE_REFRESH_MQT:
            lastRunMode = false;
            refreshMqt = true;
            break;

          case NeutronLongCmdLineName.CMD_LINE_DROP_INDEX:
            lastRunMode = false;
            dropIndex = true;
            break;

          case NeutronLongCmdLineName.CMD_LINE_SIMULATE_LAUNCH:
            simulateLaunch = true;
            break;

          default:
            throw new IllegalArgumentException(opt.getArgName());
        }
      }
    } catch (IllegalArgumentException | java.text.ParseException | ParseException e) {
      printUsage();
      throw CheeseRay.checked(LOGGER, e, "INVALID ARGS", e.getMessage(), e);
    }

    return new FlightPlan(esConfigPeopleLoc, esConfigPeopleSummaryLoc, indexName, lastRunTime,
        lastRunLoc, lastRunMode, bucketRange.getLeft(), bucketRange.getRight(), threadCount,
        loadSealedAndSensitive, rangeGiven, baseDirectory, refreshMqt, dropIndex, simulateLaunch);
  }

  public void setStartBucket(long startBucket) {
    this.startBucket = startBucket;
  }

  public void setEndBucket(long endBucket) {
    this.endBucket = endBucket;
  }

  public void setThreadCount(long threadCount) {
    this.threadCount = threadCount;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  private static Date createDate(String timestamp) throws java.text.ParseException {
    Date date = null;
    String trimTimestamp = StringUtils.trim(timestamp);
    if (StringUtils.isNotEmpty(trimTimestamp)) {
      date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(trimTimestamp);
    }
    return date;
  }

  public boolean isRangeGiven() {
    return rangeGiven;
  }

  public void setLoadSealedAndSensitive(boolean loadSealedAndSensitive) {
    this.loadSealedAndSensitive = loadSealedAndSensitive;
  }

  public void setRangeGiven(boolean rangeGiven) {
    this.rangeGiven = rangeGiven;
  }

  public void setLastRunMode(boolean flag) {
    this.lastRunMode = flag;
  }

  public String getBaseDirectory() {
    return baseDirectory;
  }

  public void setBaseDirectory(String baseDirectory) {
    this.baseDirectory = baseDirectory;
  }

  public void setOverrideLastRunTime(Date lastRunTime) {
    this.overrideLastRunTime = NeutronDateUtils.freshDate(lastRunTime);
  }

  public boolean isRefreshMqt() {
    return refreshMqt;
  }

  public void setRefreshMqt(boolean refreshMqt) {
    this.refreshMqt = refreshMqt;
  }

  public boolean isDropIndex() {
    return dropIndex;
  }

  public void setDropIndex(boolean dropIndex) {
    this.dropIndex = dropIndex;
  }

  public void setEsConfigLoc(String esConfigLoc) {
    this.esConfigPeopleLoc = esConfigLoc;
  }

  public boolean isSimulateLaunch() {
    return simulateLaunch;
  }

  public void setSimulateLaunch(boolean testMode) {
    this.simulateLaunch = testMode;
  }

  public String getEsConfigPeopleLoc() {
    return esConfigPeopleLoc;
  }

  public String getEsConfigPeopleSummaryLoc() {
    return esConfigPeopleSummaryLoc;
  }

  public Date getOverrideLastEndTime() {
    return overrideLastEndTime;
  }

  public void setOverrideLastEndTime(Date overrideLastEndTime) {
    this.overrideLastEndTime = overrideLastEndTime;
  }

}
