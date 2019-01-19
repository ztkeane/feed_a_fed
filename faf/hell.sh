#!/usr/local/bin/bash
# declare STRING variable
#STRING="Hello World"
#print variable on a screen
#echo $STRING

rm target/classes/hello/*
rm target/classes/static/*
rm target/classes/templates/*

mvn install:install-file -Dfile=ojdbc14.jar -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=11.2.0 -Dpackaging=jar
mvn spring-boot:run
