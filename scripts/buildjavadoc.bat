@echo off
::mvn clean package source:aggregate javadoc:aggregate -Dmaven.test.skip=true
cd ..
gradle aggregateJavadocs