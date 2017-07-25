package gov.ca.cwds.jobs.inject;

import java.io.File;
import java.net.InetAddress;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import gov.ca.cwds.dao.DocumentMetadataDao;
import gov.ca.cwds.dao.cms.DocumentMetadataDaoImpl;
import gov.ca.cwds.dao.cms.ReplicatedAttorneyDao;
import gov.ca.cwds.dao.cms.ReplicatedClientR1Dao;
import gov.ca.cwds.dao.cms.ReplicatedCollateralIndividualR1Dao;
import gov.ca.cwds.dao.cms.ReplicatedEducationProviderContactR1Dao;
import gov.ca.cwds.dao.cms.ReplicatedOtherAdultInPlacemtHomeR1Dao;
import gov.ca.cwds.dao.cms.ReplicatedOtherChildInPlacemtHomeR1Dao;
import gov.ca.cwds.dao.cms.ReplicatedOtherClientNameR1Dao;
import gov.ca.cwds.dao.cms.ReplicatedReporterR1Dao;
import gov.ca.cwds.dao.cms.ReplicatedServiceProviderR1Dao;
import gov.ca.cwds.dao.cms.ReplicatedSubstituteCareProviderR1Dao;
import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.data.es.ElasticsearchDao;
import gov.ca.cwds.data.persistence.cms.Allegation;
import gov.ca.cwds.data.persistence.cms.CmsDocReferralClient;
import gov.ca.cwds.data.persistence.cms.CmsDocument;
import gov.ca.cwds.data.persistence.cms.CmsDocumentBlobSegment;
import gov.ca.cwds.data.persistence.cms.CmsSystemCodeCacheService;
import gov.ca.cwds.data.persistence.cms.CollateralIndividual;
import gov.ca.cwds.data.persistence.cms.CrossReport;
import gov.ca.cwds.data.persistence.cms.ISystemCodeCache;
import gov.ca.cwds.data.persistence.cms.ISystemCodeDao;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.data.persistence.cms.StaffPerson;
import gov.ca.cwds.data.persistence.cms.SystemCodeDaoFileImpl;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedAttorney;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedClientR1;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedCollateralIndividual;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedCollateralIndividualR1;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedEducationProviderContactR1;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherAdultInPlacemtHomeR1;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherChildInPlacemtHomeR1;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedOtherClientNameR1;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedReporter;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedReporterR1;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedServiceProviderR1;
import gov.ca.cwds.data.persistence.cms.rep.ReplicatedSubstituteCareProviderR1;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.inject.NsSessionFactory;
import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.api.ApiException;

/**
 * Guice dependency injection (DI), module which constructs and manages common class instances for
 * batch jobs.
 * 
 * @author CWDS API Team
 */
public class JobsGuiceInjector extends AbstractModule {
  private static final Logger LOGGER = LogManager.getLogger(JobsGuiceInjector.class);

  private File esConfig;
  private String lastJobRunTimeFilename;

  /**
   * Default constructor.
   */
  public JobsGuiceInjector() {
    // Default, no-op.
  }

  /**
   * Usual constructor.
   * 
   * @param esConfigFileLoc location of Elasticsearch configuration file
   * @param lastJobRunTimeFilename location of last run file
   */
  public JobsGuiceInjector(File esConfigFileLoc, String lastJobRunTimeFilename) {
    this.esConfig = esConfigFileLoc;
    this.lastJobRunTimeFilename =
        !StringUtils.isBlank(lastJobRunTimeFilename) ? lastJobRunTimeFilename : "";
  }

  /**
   * Register all known CMS entity classes with Hibernate. Note that method addPackage() is not
   * working as hoped.
   * 
   * <p>
   * Parent class:
   * </p>
   * {@inheritDoc}
   * 
   * @see com.google.inject.AbstractModule#configure()
   */
  @Override
  protected void configure() {
    bind(SessionFactory.class).annotatedWith(CmsSessionFactory.class).toInstance(new Configuration()
        .configure("jobs-cms-hibernate.cfg.xml").addPackage("gov.ca.cwds.data.persistence.cms")
        .addAnnotatedClass(Allegation.class).addAnnotatedClass(ReplicatedAttorney.class)
        .addAnnotatedClass(CmsDocReferralClient.class).addAnnotatedClass(CmsDocument.class)
        .addAnnotatedClass(CmsDocumentBlobSegment.class)
        .addAnnotatedClass(ReplicatedCollateralIndividual.class)
        .addAnnotatedClass(CollateralIndividual.class).addAnnotatedClass(CrossReport.class)
        .addAnnotatedClass(ReplicatedEducationProviderContactR1.class)
        .addAnnotatedClass(ReplicatedOtherAdultInPlacemtHomeR1.class)
        .addAnnotatedClass(ReplicatedOtherChildInPlacemtHomeR1.class)
        .addAnnotatedClass(ReplicatedOtherClientNameR1.class).addAnnotatedClass(Referral.class)
        // .addAnnotatedClass(ReferralClient.class).addAnnotatedClass(ReplicatedReporter.class)
        .addAnnotatedClass(ReplicatedServiceProviderR1.class).addAnnotatedClass(StaffPerson.class)
        .addAnnotatedClass(ReplicatedSubstituteCareProviderR1.class)
        .addAnnotatedClass(ReplicatedClientR1.class)
        .addAnnotatedClass(ReplicatedCollateralIndividualR1.class)
        .addAnnotatedClass(ReplicatedReporterR1.class).addAnnotatedClass(ReplicatedReporter.class)
        .buildSessionFactory());

    // Register required DAO classes.
    bind(DocumentMetadataDao.class).to(DocumentMetadataDaoImpl.class);

    bind(ReplicatedClientR1Dao.class);
    bind(ReplicatedReporterR1Dao.class);
    bind(ReplicatedCollateralIndividualR1Dao.class);
    bind(ReplicatedOtherClientNameR1Dao.class);
    bind(ReplicatedServiceProviderR1Dao.class);
    bind(ReplicatedSubstituteCareProviderR1Dao.class);
    bind(ReplicatedOtherAdultInPlacemtHomeR1Dao.class);
    bind(ReplicatedOtherChildInPlacemtHomeR1Dao.class);
    bind(ReplicatedEducationProviderContactR1Dao.class);
    bind(ReplicatedAttorneyDao.class);

    // Instantiate as a singleton, else Guice creates a new instance each time.
    bind(ObjectMapper.class).asEagerSingleton();

    // Required for annotation injection.
    bindConstant().annotatedWith(LastRunFile.class).to(this.lastJobRunTimeFilename);

    // Register CMS system code translator.
    bind(ISystemCodeDao.class).to(SystemCodeDaoFileImpl.class);
    bind(ISystemCodeCache.class).to(CmsSystemCodeCacheService.class).asEagerSingleton();
    bind(CmsSystemCodeSerializer.class).asEagerSingleton();

    // Only one instance of ES DAO.
    bind(ElasticsearchDao.class).asEagerSingleton();
  }

  /**
   * Instantiate the singleton ElasticSearch client on demand.
   * 
   * @return initialized singleton ElasticSearch client
   */
  @Provides
  public Client elasticsearchClient() {
    Client client = null;
    if (esConfig != null) {
      LOGGER.warn("Create NEW ES client");
      try {
        final ElasticsearchConfiguration config = new ObjectMapper(new YAMLFactory())
            .readValue(esConfig, ElasticsearchConfiguration.class);
        Settings settings = Settings.settingsBuilder()
            .put("cluster.name", config.getElasticsearchCluster()).build();
        client = TransportClient.builder().settings(settings).build().addTransportAddress(
            new InetSocketTransportAddress(InetAddress.getByName(config.getElasticsearchHost()),
                Integer.parseInt(config.getElasticsearchPort())));
      } catch (Exception e) {
        LOGGER.error("Error initializing Elasticsearch client: {}", e.getMessage(), e);
        throw new ApiException("Error initializing Elasticsearch client: " + e.getMessage(), e);
      }
    }
    return client;
  }

  /**
   * Provides Hibernate session factory for PostgreSQL.
   * 
   * @return PostgreSQL Hibernate session factory
   */
  @Provides
  @NsSessionFactory
  SessionFactory nsSessionFactory() {
    // NOTE: Configuration.configure() only looks for the named file, not resources in jar files.
    return new Configuration().configure("ns-hibernate.cfg.xml").buildSessionFactory();
  }

}
