# bw-util-logging
Move all logging dependencies to a single module

## Requirements

1. JDK 11
2. Maven 3

## Building Locally

> mvn clean install

## Releasing

Releases of this fork are published to Maven Central via Sonatype.

To create a release, you must have:

1. Permissions to publish to the `org.bedework` groupId.
2. `gpg` installed with a published key (release artifacts are signed).

To perform a new release:

> mvn -P bedework-dev release:clean release:prepare

When prompted, select the desired version; accept the defaults for scm tag and next development version.
When the build completes, and the changes are committed and pushed successfully, execute:

> mvn -P bedework-dev release:perform

For full details, see [Sonatype's documentation for using Maven to publish releases](http://central.sonatype.org/pages/apache-maven.html).


## Changes
### 4.0.5
  * Allow setting of log level
  * Remove some references to log4j
### 4.0.6
   * Update commons-codec
   * Remove wildfly deploy plugin. Deploy now a separate module
### 5.0.0
   * Update to log4j2
### 5.1.0
   * Use bedework parent for builds
### 5.2.0
   * Needed to fix release issues. No other change
### 5.2.1
   * Updated parent dependency. Later jackson version.
### 5.2.2
   * Add methods to take parameters. Update to latest released parent
### 5.2.3
* Update parent
* Last pre-jakarta release
