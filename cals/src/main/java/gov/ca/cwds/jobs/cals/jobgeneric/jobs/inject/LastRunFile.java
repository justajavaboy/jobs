package gov.ca.cwds.jobs.cals.jobgeneric.jobs.inject;

import com.google.inject.BindingAnnotation;
import gov.ca.cwds.jobs.cals.jobgeneric.jobs.LastSuccessfulRunJob;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation identifies batch jobs that implement the "last run" policy.
 * 
 * @author CWDS API Team
 * @see LastSuccessfulRunJob
 */
@BindingAnnotation
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LastRunFile {
}
