#!/bin/sh
baseName=$(basename `pwd`)
if [[ "$baseName" != "scripts" ]]; then
	echo "Please into scripts folder first."
	exit -1
fi
cd ..

#mvn clean package source:aggregate javadoc:aggregate -Dmaven.test.skip=true
gradle aggregateJavadocs

if [[ $? == 0 ]]; then
	open "build/docs/javadoc/index.html"
fi
