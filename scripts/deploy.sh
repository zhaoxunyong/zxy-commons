#!/bin/sh
baseName=$(basename `pwd`)
if [[ "$baseName" != "scripts" ]]; then
	echo "Please into scripts folder first."
	exit -1
fi
cd ..

#cd zxy-commons-dependency
#mvn clean deploy
#gradle publishToMavenLocal publish

#cd $oldPwd
gradle publishToMavenLocal publish