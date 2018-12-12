/* ********************************************************************
    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.
*/
package org.bedework.util.logging;

/** This interface provides access to logging for non-static methods.
 *
 * @author douglm
 */
public interface Logged {
  BwLogger logger = new BwLogger();

  public final static String errorLoggerName = BwLogger.errorLoggerName;
  public final static String auditLoggerName = BwLogger.auditLoggerName;
  public final static String metricsLoggerName = BwLogger.metricsLoggerName;

  default void setLoggerClass() {
    logger.setLoggedClass(getClass());
  }

  default void setLoggerClass(final Class cl) {
    logger.setLoggedClass(cl);
  }

  default BwLogger getLogger() {
    if (logger.getLoggedClass() == null) {
      setLoggerClass();
    }
    return logger;
  }

  default boolean debug() {
    return getLogger().isDebugEnabled();
  }

  default boolean trace() {
    return getLogger().isTraceEnabled();
  }

  default boolean isMetricsDebugEnabled() {
    return getLogger().isMetricsDebugEnabled();
  }

  @SuppressWarnings("unused")
  default void enableErrorLogger() {
    getLogger().enableErrorLogger();
  }

  @SuppressWarnings("unused")
  default void enableAuditLogger() {
    getLogger().enableAuditLogger();
  }

  @SuppressWarnings("unused")
  default void enableMetricsLogger() {
    getLogger().enableMetricsLogger();
  }

  @SuppressWarnings("unused")
  default boolean isErrorLoggerEnabled() {
    return getLogger().isErrorLoggerEnabled();
  }

  @SuppressWarnings("unused")
  default boolean isAuditLoggerEnabled() {
    return getLogger().isAuditLoggerEnabled();
  }

  @SuppressWarnings("unused")
  default boolean isMetricsLoggerEnabled() {
    return getLogger().isMetricsLoggerEnabled();

  }

  /**
   * @param t exception
   */
  default void error(final Throwable t) {
    getLogger().error(t);
  }

  /**
   * @param msg to output
   */
  default void error(final String msg) {
    getLogger().error(msg);
  }

  /**
   * @param msg to output
   */
  default void error(final String msg,
                     final Throwable t) {
    getLogger().error(msg, t);
  }

  /**
   * @param msg to output
   */
  default void warn(final String msg) {
    getLogger().warn(msg);
  }

  /**
   * @param msg to output
   */
  default void info(final String msg) {
    getLogger().info(msg);
  }

  /**
   * @param msg to output
   */
  @SuppressWarnings("unused")
  default void audit(final String msg) {
    getLogger().audit(msg);
  }

  /**
   * @param msg to output
   */
  @SuppressWarnings("unused")
  default void metrics(final String msg) {
    getLogger().metrics(msg);
  }

  /**
   * @param msg to output
   */
  default void debug(final String msg) {
    getLogger().debug(msg);
  }

  /**
   * @param msg to output
   */
  default void trace(final String msg) {
    getLogger().trace(msg);
  }
}
