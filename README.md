# dundry

Dundry is a village and civil parish, situated on Dundry Hill to the south of Bristol.

The villagers can greet visitors in different languages: English, French, etc.

## Project Structure

This project has three subsystems

1. dundry-a-lib: keeping the dictionary of greetings 
2. dundry-b-lib: choosing a language of greeting if the visitor prefers a random language
3. dundry-app: the main driver program making use the above two libraries

## How to run

Pre-requisit: You should have Java JRE or JDK installed on your system

If you download the jar file (e.g. dundry-app-1.0-all.jar) directly from our distribution site, just run this command:

```
$ java -jar _path_to_/dundry-app-1.0-all.jar _arg0_
```

_arg0_ is optional and is default to 0 (English greeting)

If _arg0_ is "*" or "a", then the greetings of all languages will be shown

If _arg0_ is "r", then the greetings of a random language will be shown

Examples:
```
dundry>java -jar dundry-app/build/libs/dundry-app-1.0-all.jar
Hello, World!
dundry>java -jar dundry-app/build/libs/dundry-app-1.0-all.jar 0
Hello, World!
dundry>java -jar dundry-app/build/libs/dundry-app-1.0-all.jar 1
Bonjour, Le Monde!
dundry>java -jar dundry-app/build/libs/dundry-app-1.0-all.jar r
Hello, World!
dundry>java -jar dundry-app/build/libs/dundry-app-1.0-all.jar r
Bonjour, Le Monde!
dundry>java -jar dundry-app/build/libs/dundry-app-1.0-all.jar a
Hello, World!
Bonjour, Le Monde!
```

## How to build (For Developers only)

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
Bonjour, Le Monde!
```

## Todo

1. Use Dependency Injection

2. Refactor common setting in gradle scripts of sub-systems