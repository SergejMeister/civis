# update version
mvn --batch-mode release:update-versions -DdevelopmentVersion=1.3.0-SNAPSHOT

# prepare release
mvn release:prepare
mvn release:clean release:prepare