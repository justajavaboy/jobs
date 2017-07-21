package gov.ca.cwds.jobs.util;

/**
 * 
 * @param <I> input type
 * @param <O> output type
 * @author CWDS TPT-2
 */
public interface JobProcessor<I, O> {

  /**
   * Transform item I into O.
   * 
   * @param item input
   * @return an O
   */
  O process(I item);

}
