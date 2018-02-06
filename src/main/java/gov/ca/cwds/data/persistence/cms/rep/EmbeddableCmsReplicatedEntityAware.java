package gov.ca.cwds.data.persistence.cms.rep;

/**
 * Simple interface returns a {@link EmbeddableCmsReplicatedEntity}.
 * 
 * @see EmbeddableCmsReplicatedEntity
 * @author CWDS API Team
 */
@FunctionalInterface
public interface EmbeddableCmsReplicatedEntityAware {

  EmbeddableCmsReplicatedEntity getReplicatedEntity();

}
