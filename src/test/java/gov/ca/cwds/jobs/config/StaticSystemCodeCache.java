package gov.ca.cwds.jobs.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StaticSystemCodeCache {

  private static final Logger LOGGER = LogManager.getLogger(StaticSystemCodeCache.class);

  // private static ApiSystemCodeCache sysCodeCache;

  // private static synchronized void loadCache() {
  // sysCodeCache = new CmsSystemCodeCacheService(new SystemCodeDaoFileImpl());
  //
  // synchronized (BasePersonIndexerJob.class) {
  // LOGGER.info("Lock BasePersonIndexerJob monitor");
  // BasePersonIndexerJob.setSystemCodes(sysCodeCache);
  // }
  //
  // synchronized (ElasticSearchPerson.class) {
  // LOGGER.info("Lock ElasticSearchPerson monitor");
  // ElasticSearchPerson.setSystemCodes(sysCodeCache);
  // }
  // }

  // static {
  // loadCache();
  // }
  //
  // public static void deploy() {
  // LOGGER.info("Load System Code Cache");
  // }
  //
  // public static synchronized void reload() {
  // LOGGER.info("Reload System Code Cache");
  // }

}
