# IOLMasterImport
JAVA code for importing IOL Master device binary files

To compile you'll need JDK version 1.7 or higher.

Compile (under Linux):
ant -f /root/IOLMasterImport/ -Dnb.internal.action.name=rebuild clean jar

Run:
java -cp ./lib/antlr-2.7.7.jar:./lib/dcm4che-core-3.3.7.jar:./lib/dom4j-1.6.1.jar:./lib/geronimo-jta_1.1_spec-1.1.1.jar:./lib/hibernate-commons-annotations-5.0.0.Final.jar:./lib/hibernate-core-5.0.0.Final.jar:./lib/hibernate-jpa-2.1-api-1.0.0.Final.jar:./lib/jandex-1.2.2.Final.jar:./lib/javassist-3.18.1-GA.jar:./lib/jboss-logging-3.3.0.Final.jar:./lib/log4j-1.2.17.jar:./lib/slf4j-api-1.7.5.jar:./lib/slf4j-log4j12-1.7.5.jar:./lib/mysql-connector-java-5.1.23-bin.jar:./lib/json-simple-1.1.1.jar:./lib/commons-cli-1.3.1.jar:./lib/jaxen-1.1.6.jar:./dist/OE_IOLMasterImport.jar uk.org.openeyes.OE_IOLMasterImport -h

