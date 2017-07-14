package gov.ca.cwds.jobs.cals.rfa;

import com.google.inject.Inject;
import gov.ca.cwds.cals.inject.CalsnsSessionFactory;
import gov.ca.cwds.cals.service.dto.changed.ChangedRFA1aFormDTO;
import gov.ca.cwds.cals.service.rfa.RFA1aFormsCollectionService;
import gov.ca.cwds.jobs.cals.IncrementalLoadDateStrategy;
import gov.ca.cwds.jobs.util.JobReader;
import java.util.Date;
import java.util.Iterator;
import org.hibernate.SessionFactory;

/**
 * @author CWDS TPT-2
 */
public class RFA1aFormReader implements JobReader<ChangedRFA1aFormDTO> {

  private IncrementalLoadDateStrategy incrementalLoadDateStrategy;
  private Iterator<ChangedRFA1aFormDTO> changedRFA1aFormDTOIterator;

  @Inject
  @CalsnsSessionFactory
  private SessionFactory calsnsSessionFactory;

  @Inject
  private RFA1aFormsCollectionService rfa1aFormsCollectionService;

  RFA1aFormReader() {
    this.incrementalLoadDateStrategy = new RFA1aFormIncrementalLoadDateStrategy();
  }

  @Override
  public void init() throws Exception {
    Date dateAfter = incrementalLoadDateStrategy.calculate();
    calsnsSessionFactory.getCurrentSession().beginTransaction();
    changedRFA1aFormDTOIterator = rfa1aFormsCollectionService.streamChangedRFA1aForms(dateAfter)
        .iterator();
  }

  @Override
  public ChangedRFA1aFormDTO read() throws Exception {
    return changedRFA1aFormDTOIterator.hasNext() ? changedRFA1aFormDTOIterator.next() : null;
  }

  @Override
  public void destroy() throws Exception {
    closeSessionFactory(calsnsSessionFactory);
  }

  private void closeSessionFactory(SessionFactory sessionFactory) {
    try {
      sessionFactory.getCurrentSession().getTransaction().rollback();
    } finally {
      sessionFactory.close();
    }
  }
}
