package gov.ca.cwds.jobs.util;


/**
 * Created by dmitry.rudenko on 4/28/2017.
 */
public interface ItemProcessor<I , O> {

    O process(I item) throws Exception;
}
