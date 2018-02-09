package gov.ca.cwds.data.persistence.cms;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import gov.ca.cwds.data.std.ApiMarker;

/**
 * Instead of repeatedly parsing relationship components, just parse once and lookup, as needed.
 * 
 * @author CWDS API Team
 */
public final class SonarQubeMemoryBloatComplaintCache implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private final Map<Short, CmsRelationship> mapRelationCodes = new ConcurrentHashMap<>();

  private static final SonarQubeMemoryBloatComplaintCache instance =
      new SonarQubeMemoryBloatComplaintCache();

  private SonarQubeMemoryBloatComplaintCache() {
    // whatever
  }

  public static SonarQubeMemoryBloatComplaintCache getInstance() {
    return instance;
  }

  public void clearCache() {
    mapRelationCodes.clear();
  }

  public Map<Short, CmsRelationship> getMapRelationCodes() {
    return mapRelationCodes;
  }

}
