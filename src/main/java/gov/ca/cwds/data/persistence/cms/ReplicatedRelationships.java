package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import gov.ca.cwds.data.es.ElasticSearchLegacyDescriptor;
import gov.ca.cwds.data.es.ElasticSearchPersonRelationship;
import gov.ca.cwds.data.persistence.cms.rep.CmsReplicatedEntity;
import gov.ca.cwds.data.persistence.cms.rep.CmsReplicationOperation;
import gov.ca.cwds.data.persistence.cms.rep.EmbeddableCmsReplicatedEntity;
import gov.ca.cwds.neutron.util.shrinkray.RetrovillePerson;
import gov.ca.cwds.neutron.util.transform.ElasticTransformer;
import gov.ca.cwds.rest.api.domain.cms.LegacyTable;

/**
 * Pseudo-normalized container for CMS legacy relationships by legacy person id.
 * 
 * @author CWDS API Team
 */
public class ReplicatedRelationships implements RetrovillePerson, CmsReplicatedEntity {

  private static final long serialVersionUID = 1L;

  /**
   * Legacy person id.
   */
  private String id;

  private List<ElasticSearchPersonRelationship> relations = new ArrayList<>();

  private EmbeddableCmsReplicatedEntity embeddableCmsReplicatedEntity =
      new EmbeddableCmsReplicatedEntity();

  /**
   * Default constructor.
   */
  public ReplicatedRelationships() {
    this.embeddableCmsReplicatedEntity.setReplicationDate(new Date());
    this.embeddableCmsReplicatedEntity.setReplicationOperation(CmsReplicationOperation.I);
  }

  /**
   * Preferred constructor.
   * 
   * @param id legacy id
   */
  public ReplicatedRelationships(String id) {
    this.id = id;
  }

  @Override
  public Serializable getPrimaryKey() {
    return id;
  }

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<ElasticSearchPersonRelationship> getRelations() {
    return relations;
  }

  public void setRelations(List<ElasticSearchPersonRelationship> relations) {
    this.relations = relations;
  }

  public void addRelation(ElasticSearchPersonRelationship relation) {
    this.relations.add(relation);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  @Override
  public String getLegacyId() {
    return id;
  }

  @Override
  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return ElasticTransformer.createLegacyDescriptor(getId(), new Date(),
        LegacyTable.CLIENT_RELATIONSHIP);
  }

  @Override
  public EmbeddableCmsReplicatedEntity getReplicatedEntity() {
    return embeddableCmsReplicatedEntity;
  }

}
