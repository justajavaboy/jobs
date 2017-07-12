## CWDS Jobs

The CWDS Jobs project provides java based stand alone applications which are meant to be scheduled periodically.

## Installation

## Prerequisites
Prerequisites are job dependent but typically you can expect the following to be needed :

1.  DB2 10.x
2.  Postgres 9.x
3.  Elasticsearch 5.3.2 or newer (newer would require testing)

## Development Environment

### Prerequisites

1. Source code, available at [GitHub](https://github.com/ca-cwds/jobs)
1. Java SE 8 development kit
1. DB2 Database
1. Postgres Database
1. Elasticsearch

### Building

% ./gradlew build


### Facility Indexer Job

Main Class: gov.ca.cwds.jobs.cals.facility.FacilityIndexerJob
run job using command like the following: 
```bash
$ java -DDB_FAS_JDBC_URL="jdbc:postgresql://192.168.99.100:5432/?currentSchema=fas" \
         -DDB_FAS_USER="postgres_data" -DDB_FAS_PASSWORD="CHANGEME" \
     -DDB_LIS_JDBC_URL="jdbc:postgresql://192.168.99.100:5432/?currentSchema=lis" \
        -DDB_LIS_USER="postgres_data" -DDB_LIS_PASSWORD="CHANGEME" \
     -DDB_CMS_JDBC_URL="jdbc:db2://192.168.99.100:50000/DB0TDEV" -DDB_CMS_SCHEMA="CWSCMSRS" \
        -DDB_CMS_USER="db2inst1" -DDB_CMS_PASSWORD="CHANGEME" \
     -cp build/libs/DocumentIndexerJob-0.24.jar gov.ca.cwds.jobs.cals.facility.FacilityIndexerJob \
      config/facility.yaml
```
#### Code overview
In order to create new job you have to implement 2 interfaces: _JobReader_, _JobWriter_ and optional _JobProcessor_
_JobReader_ has a single method _I read()_ which is responsible for reading items from input source. It must return null when everything is read.
_JobWriter_ has a single method _write(List\<I\> items)_ which is responsible for writing chunk of data to output target.
_JobProcessor_ has a single method _O process(I item)_, you can implement it if some mapping logic is required
Then you should provide those components to a job. Google guice example config:
```java
@Provides
@Named("job")
@Inject
public Job job(@Named("reader") JobReader reader,
               @Named("writer") JobWriter writer) {
        return new AsyncReadWriteJob(reader, writer);
}

//or with processor

@Provides
@Named("job")
@Inject
public Job job(@Named("reader") JobReader reader,
               @Named("processor") JobProcessor processor,
               @Named("writer") JobWriter writer) {
        return new AsyncReadWriteJob(reader, processor, writer);
}
```
Read and write operation runs in separate threads.
