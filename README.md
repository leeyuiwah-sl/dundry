# dundry

Dundry is a village and civil parish, situated on Dundry Hill to the south of Bristol.

Phase 1 Story: The villagers would greet visitors in English and say "Hello World!"

Phase 2 Story: They also have a hobby of playing with some items of substantial values, and they love to show the data of these items to their visitor after the greeting.

Phase 3 Story: The villagers also like to express their friendliness by clapping to visitors.  However, they prefer to send these claps by using either a message queue or a publish/subscribe model.  As such, they will ask visitor to adopt the Kafka technology to receive these claps.

## Project Structure

This project has three subsystems

*Phases 1 and 2 stories*

1. dundry-a-lib: keeping the dictionary of greetings 
2. dundry-b-lib: keeping a DataManager for the data items
3. dundry-app: the main driver program making use the above two libraries

*Phase 3 story*

4. dundry-kafka-producer: a villager sending claps to a visitor
5. dundry-kafka-consumer: a visitor receiving claps from a visitor
6. dundry-kafka-common: parameters that have been agreed upon by both the producer and the receiver

## Phases 1 & 2: How to Run

Prerequisite: 

1. You should have Java JRE or JDK installed on your system
2. For now the DataManager is backed by a local MySQL instance.  You should have set up this instance with a database and a table.  Please see the section for "Setting Up a Local Database"

If you download the jar file (e.g. dundry-app-1.0-all.jar) directly from our distribution site, just run this command:

```
$ java -jar _path_to_/dundry-app-1.0-all.jar 
```

Examples:
```
dundry>java -jar dundry-app/build/libs/dundry-app-1.0-all.jar
Hello, World!
Got 4 items from the DB
         1        100     191.24 AAPL
         2        200     168.70 FB
         3        150    1194.43 GOOG
         4        200    1814.19 AMZN
Total number of items: 4
```
## Phase 3: How to run 

### Prerequisite: 
1. zookeeper
2. kafka 

If you are using Windows, you can following the procedures in these two articles to set up zookeeper and kafka 

1. [Installing Apache ZooKeeper on Windows](https://medium.com/@shaaslam/installing-apache-zookeeper-on-windows-45eda303e835)
2. [Installing Apache Kafka on Windows](https://medium.com/@shaaslam/installing-apache-kafka-on-windows-495f6f2fd3c8)

#### Configuration parameters for `kafka` 

1. Kafka Broker Port: 9092.  Defined in
   * KAFKA_HOME/config/server.properties: `listeners=PLAINTEXT://:9092`
   * dundry-kafka-common/Constant.java: `KAFKA_BROKERS               = "localhost:9092"`   


### Steps

1. Start zookeeper

```
$ zkserver
```

2. Start kafka 

(Assuming you are running in a windows environment)
```
c:\opt\kafka_2.12-2.2.0\bin\windows>kafka-server-start.bat ..\..\config\server.properties
```

3. Start Consumer 

(Note: for now there are some System.err messages that can be safely ignored.)

In a `cmd` window:
```
$ cd _top_dir_
gradlew  :dundry-kafka-consumer:run

> Task :dundry-kafka-consumer:run
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Prepared to receive message from Topic: southwest2 ...
```

4. Start Producer
```
$ cd _top_dir_
$ gradlew :dundry-kafka-producer:run
Starting a Gradle Daemon, 1 busy and 3 stopped Daemons could not be reused, use --status for details

> Task :dundry-kafka-producer:run
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Prepared to send message from Topic: southwest2 ...
Producer about to sent 3 records
Sending record with key 0 to partition 0 with offset 1000:    1 claps
Sending record with key 1 to partition 0 with offset 1001:    2 claps
Sending record with key 2 to partition 0 with offset 1002:    3 claps

Done!

BUILD SUCCESSFUL in 6s
4 actionable tasks: 2 executed, 2 up-to-date
```

5. Examine the result of the Consumer
```
Receiving record with key 0 from partition 0 with offset 1000:    1 claps
Receiving record with key 1 from partition 0 with offset 1001:    2 claps
Receiving record with key 2 from partition 0 with offset 1002:    3 claps
<===========--> 87% EXECUTING [32s]
> :dundry-kafka-consumer:run```
```
(Press Ctrl-C to terminate the consumer)

## How to Build (For Developers Only)

Note: This project uses gradle.  However, there gradle wrapper files (two script files and a small gradle-wrapper.jar and gradle-wrapper.properties) that come with the source distribution and let you use gradle without needing to download and install the full gradle distribution.  This is the recommended way of using gradle (c.f. https://docs.gradle.org/current/dsl/org.gradle.api.tasks.wrapper.Wrapper.html) although some may find it a bit odd that we would check in a jar file into Git.

Download the project zip file (`dundry-master.zip`) from github, and unzip it and put the top-level directory (e.g. `dundry-master` into a centain top-level directory _top_dir_  

(Below assumes that you are doing this in a Windows environment.  If you are using UNIX instead, then swap the executable `gradlew.bat` for `gradlew`)

```
$ cd _top_dir_
$ gradlew.bat build
```

### Test run after build (Phases 1 & 2)

```
$ java -jar dundry-app/build/libs/dundry-app-1.0-all.jar a
Hello, World!
Got 4 items from the DB
         1        100     191.24 AAPL
         2        200     168.70 FB
         3        150    1194.43 GOOG
         4        200    1814.19 AMZN
Total number of items: 4
```

Or if you prefer to avoid the above long command line, you can also use the `run` task of gradle

```
$ gradlew run

> Task :dundry-app:run
Hello, World!
Got 4 items from the DB
         1        100     191.24 AAPL
         2        200     168.70 FB
         3        150    1194.43 GOOG
         4        200    1814.19 AMZN
Total number of items: 4

BUILD SUCCESSFUL in 3s
7 actionable tasks: 1 executed, 6 up-to-date
```

### Gradle build in Eclipse and IntelliJ

It is also possible to run gradle build in Eclipse and IntelliJ

## Setting Up a Local Database (for Phases 1 and 2)

1. Download and configure a local MySQL database

2. Create a database and a table
```
create database dundry;
use dundry;
drop table if exists test_table;
create table test_table  (
  test_table_id     int not null auto_increment,
  test_int          int,
  test_float        float,
  test_string       varchar(256),
  primary key       (test_table_id)
);
```

3. Insert some data into the database manually.  

```
insert into test_table values 
(null, 100, 191.24,     "AAPL"),
(null, 200, 168.70,     "FB"),
(null, 150, 1194.43,    "GOOG"),
(null, 200, 1814.19,    "AMZN");
```

4. Testing the table contents using an SQL query

```
select * from test_table;
```

5. Create a DB user for the JDBC connection into the DB

```
create user 'dbUser'@'localhost';
set password for 'dbUser'@'localhost' = "password"; // substitute in a real password as defined in hibernate.cfg.xml
grant all on dundry.* to 'dbUser'@'localhost';
```

## Troubleshooting Guide

### Error: Failed to set up DataManager. Please double check that your local database instance is up and running

### Warning message during build.

(Developers only) 

If you see the following warning message during build, then please check your local database instance and make sure that it is up and running

You can ignore this message (the test had failed, but the build may still be okay)

```
C:\Users\clee\proj\dundry>gradlew build

> Task :dundry-b-lib:test

dundry.hibernate.first.TestTableTest > testReadingFromDb FAILED
    org.hibernate.service.spi.ServiceException at TestTableTest.java:27
        Caused by: org.hibernate.exception.JDBCConnectionException at TestTableTest.java:27
            Caused by: com.mysql.cj.jdbc.exceptions.CommunicationsException at TestTableTest.java:27
                Caused by: com.mysql.cj.exceptions.CJCommunicationsException at TestTableTest.java:27
                    Caused by: java.net.ConnectException at TestTableTest.java:27

2 tests completed, 1 failed
There were failing tests. See the report at: file:///C:/Users/clee/proj/dundry/dundry-b-lib/build/reports/tests/test/index.html

BUILD SUCCESSFUL in 9s
18 actionable tasks: 18 executed
```

Check if your local DB instance is up and running.  Please refer to the section of "Setting Up a Local Database" for more details.

### You expect some log messages from Hibernate but they are not shown

(Developers only) 

Since we have not set up logging for this project, currently the log messages from Hibernate are sent to `System.err` (standard error).  They would be distracting to end users in production mode.  As such we switched off the standard error in production mode.

If you want to re-enable hibernate messages, or see standard error messages in general, you can edit an internal boolean variable `debug` in the file `DundryApp.java` and set it to true:

```
    final static boolean debug = false;
    private static void redirectStdErr() {
        if (!debug) {
            System.err.close();
        }        
    }
```

### Failed gradle build within Eclipse

If you gradle build works in a Windows `cmd` window, but fails within `Eclipse`, with this error message:

```
> Task :dundry-a-lib:compileJava FAILED

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':dundry-a-lib:compileJava'.
> Could not find tools.jar. Please check that C:\Program Files\Java\jre1.8.0_191 contains a valid JDK installation.
```

Then please try the various tips on [this Q&A from StackOverflow.com](https://stackoverflow.com/questions/37669664/eclipse-gradle-build-could-not-find-tools-jar).  Specifically I tried the suggestion of upgrading JDK (which is a good thing to do anyway) to the latest update number (same major and minor versions of 1.8) and the problem went away.

## Potential Future Enhancements

1. Use Dependency Injection

2. Refactor common setting in gradle scripts of sub-systems

3. Add logging 

4. Consider setting up a DB instance in the cloud so that casual users do not need to set up a local database instance to try out the program.

5. Consider encrypting the DB connection password in hibernate.cfg.xml

