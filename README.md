# Liquibase Singlestore Extension

This is a Liquibase extension for connecting to a Singlestore database.

## Configuring the extension

As [Singlestore](https://www.singlestore.com/) is a MySQL compatible database, standard Liquibase will be able to treat it as a MySQL database when connecting with a `jdbc:mysql://...` URL.

This extension:
- Recognizes a `jdbc:singlestore://...` URL.
- Allows you to use "dbms="singlestore"` to differentiate between mysql and singlestore databases

These instructions will help you get the extension up and running on your local machine for development and testing purposes. This extension has a prerequisite of Liquibase core in order to use it. Liquibase core can be found at https://www.liquibase.org/download

### Liquibase CLI

Download [the latest released Liquibase extension](https://github.com/liquibase/liquibase-singlestore/releases) `.jar` file and place it in the `liquibase/lib` install directory.

### Maven

Specify the Liquibase extension in the `<dependency>` section of your POM file by adding the `org.liquibase.ext` dependency for the Liquibase plugin.

```  
<plugin>
     <!--start with basic information to get Liquibase plugin:
     include <groupId>, <artifactID>, and <version> elements-->
     <groupId>org.liquibase</groupId>
     <artifactId>liquibase-maven-plugin</artifactId>
     <version>4.3.2</version>
     <configuration>
        <!--set values for Liquibase properties and settings
        for example, the location of a properties file to use-->
        <propertyFile>liquibase.properties</propertyFile>
     </configuration>
     <dependencies>
     <!--set up any dependencies for Liquibase to function in your
     environment for example, a database-specific plugin-->
            <dependency>
                 <groupId>org.liquibase.ext</groupId>
                 <artifactId>liquibase-singlestore</artifactId>
                 <version>${liquibase-singlestore.version}</version>
            </dependency>
         </dependencies>
      </plugin>
  ``` 

## Contribution

To file a bug, improve documentation, or contribute code, follow our [guidelines for contributing](https://contribute.liquibase.com).

[This step-by-step instructions](https://contribute.liquibase.com/extensions-integrations/extensions-overview/) will help you contribute code for the extension.

## Issue Tracking

Any issues can be logged in the [Github issue tracker](https://github.com/liquibase/liquibase-singlestore/issues).

## License

This project is licensed under the [Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.html).
