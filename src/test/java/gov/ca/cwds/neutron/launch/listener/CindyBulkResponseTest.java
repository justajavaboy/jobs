package gov.ca.cwds.neutron.launch.listener;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.junit.Test;

public class CindyBulkResponseTest {

  @Test
  public void type() throws Exception {
    assertThat(CindyBulkResponse.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    final BulkItemResponse[] responses = null;
    final long tookInMillis = 0L;
    final CindyBulkResponse target = new CindyBulkResponse(responses, tookInMillis);
    assertThat(target, notNullValue());
  }

}
