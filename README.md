# IOLMasterImport

JAVA code for importing IOL Master device binary files

## Compilation

To compile you'll need JDK version 1.7 or higher.

Compile (under Linux):
```
ant -f /root/IOLMasterImport/ -Dnb.internal.action.name=rebuild clean jar
```

## Execution

Run this command to see the latest help text:
```
java -cp ./lib/fontbox-2.0.0-RC3.jar:./lib/pdfbox-2.0.0-RC3.jar:./lib/antlr-2.7.7.jar:./lib/dcm4che-core-3.3.7.jar:./lib/dom4j-1.6.1.jar:./lib/geronimo-jta_1.1_spec-1.1.1.jar:./lib/hibernate-commons-annotations-5.0.0.Final.jar:./lib/hibernate-core-5.0.0.Final.jar:./lib/hibernate-jpa-2.1-api-1.0.0.Final.jar:./lib/jandex-1.2.2.Final.jar:./lib/javassist-3.18.1-GA.jar:./lib/jboss-logging-3.3.0.Final.jar:./lib/slf4j-api-1.7.5.jar:./lib/slf4j-log4j12-1.7.5.jar:./lib/mysql-connector-java-5.1.23-bin.jar:./lib/json-simple-1.1.1.jar:./lib/commons-cli-1.3.1.jar:./lib/jaxen-1.1.6.jar:./lib/log4j-1.2.17.jar:./lib/ini4j-0.5.4.jar:./lib/httpclient/commons-codec-1.9.jar:./lib/httpclient/commons-logging-1.2.jar:./lib/httpclient/fluent-hc-4.5.1.jar:./lib/httpclient/httpclient-4.5.1.jar:./lib/httpclient/httpclient-cache-4.5.1.jar:./lib/httpclient/httpclient-win-4.5.1.jar:./lib/httpclient/httpcore-4.4.3.jar:./lib/httpclient/httpmime-4.5.1.jar:./lib/httpclient/jna-4.1.0.jar:./lib/httpclient/jna-platform-4.1.0.jar:./OE_IOLMasterImport.jar uk.org.openeyes.OE_IOLMasterImport -h
```

## Patient hospital number matching

By default the patient hospital number on the incoming DICOM file is padded to seven digits with leading zeros, and then compared to the patient.hos_num column in the database (along with gender and date of birth).

Two parameters may be used to override the default behaviour.

First, specify `-r` or `--hosnumregex` with a Java regex to capture the digits from the hospital number. If a second capturing group is found this is the result, otherwise the first capturing group is used. For example, this would get digits after stripping off any "ABC" prefix:
```
-r "(?i)(ABC)?(\\d+)"
```

Secondly, specify `-p` or `--hosnumpad` with a format specification (Java String.format) that accepts an integer. For example, this would start with "ABC" and then pad the integer to eight digits with leading zeros:
```
-p "ABC%08d"
```

So using the above together, a file containing hospital number "abc123" would attempt to match the database for "ABC00000123".
