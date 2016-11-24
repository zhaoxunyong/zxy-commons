@echo off
::SET CURRENTDIR="%cd%"
::cd zxy-commons-dependency
::call mvn clean deploy
::gradle publishToMavenLocal publish
::cd "%CURRENTDIR%"

cd ..
gradle publishToMavenLocal publish