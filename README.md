# dundry

Dundry is a village and civil parish, situated on Dundry Hill to the south of Bristol.

The villagers would greet visitors in English and say "Hello World!"

They also have a hobby of playing with some items of substantial values, and they love to show the data of these items to their visitor after the greeting.

## Project Structure

This project has three subsystems

1. dundry-a-lib: keeping the dictionary of greetings 
2. dundry-b-lib: keeping a DataManager for the data items
3. dundry-app: the main driver program making use the above two libraries

## How to Run

Pre-requisit: 

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

## How to Build (For Developers Only)

This project uses gradle.  If you do not have gradle on your system yet, please 
follow the procedures of this page to install it:

  https://docs.gradle.org/current/userguide/installation.html

Download the project zip file, and unzip it and put the top-level directory (e.g. `dundry-master` into a centain top-level directory _top_dir_  

(Below assumes that you are doing this in a Windows environment.  If you are using UNIX instead, then swap the executable `gradlew.bat` for `gradlew`)

```
$ cd _top_dir_
$ gradle wrapper 
$ gradlew.bat build

$ # test run the program
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

## Setting Up a Local Database

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

## Potential Future Enhancements

1. Use Dependency Injection

2. Refactor common setting in gradle scripts of sub-systems

3. Add logging 

4. Consider setting up a DB instance in the cloud so that casual users do not need to set up a local database instance to try out the program.

5. Consider encrypting the DB connection password in hibernate.cfg.xml

