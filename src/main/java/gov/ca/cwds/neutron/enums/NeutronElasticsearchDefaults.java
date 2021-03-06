package gov.ca.cwds.neutron.enums;

public enum NeutronElasticsearchDefaults {

  /**
   * People index settings.
   */
  ES_PEOPLE_INDEX_SETTINGS("/elasticsearch/setting/people-index-settings.json"),

  /**
   * People Summary index settings.
   */
  ES_PEOPLE_SUMMARY_INDEX_SETTINGS("/elasticsearch/setting/people-summary-index-settings.json"),

  /**
   * People index, Person document mapping.
   */
  ES_PEOPLE_PERSON_MAPPING("/elasticsearch/mapping/map_person_5x_snake.json"),

  /**
   * People Summary index, Person Summary document mapping.
   */
  ES_PEOPLE_SUMMARY_PERSON_MAPPING("/elasticsearch/mapping/map_person_summary.json");

  private final String value;

  private NeutronElasticsearchDefaults(String value) {
    this.value = value;
  }

  @SuppressWarnings("javadoc")
  public String getValue() {
    return value;
  }

}
