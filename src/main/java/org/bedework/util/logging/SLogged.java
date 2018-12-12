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

/** This interface provides access to logging for static methods.
 *
 * @author douglm
 */
public interface SLogged {
  BwLogger logger = new BwLogger();

  public final static String errorLoggerName = BwLogger.errorLoggerName;
  public final static String auditLoggerName = BwLogger.auditLoggerName;
  public final static String metricsLoggerName = BwLogger.metricsLoggerName;

  static void setLoggerClass(final Class cl) {
    logger.setLoggedClass(cl);
  }

  static BwLogger getLogger() {
    return logger;
  }

  static boolean debug() {
    return logger.isDebugEnabled();
  }

  static boolean trace() {
    return getLogger().isTraceEnabled();
  }

  @SuppressWarnings("unused")
  static void enableErrorLogger() {
    logger.enableErrorLogger();
  }

  @SuppressWarnings("unused")
  static void enableAuditLogger() {
    logger.enableAuditLogger();
  }

  @SuppressWarnings("unused")
  static void enableMetricsLogger() {
    logger.enableMetricsLogger();
  }

  @SuppressWarnings("unused")
  static boolean isErrorLoggerEnabled() {
    return logger.isErrorLoggerEnabled();
  }

  @SuppressWarnings("unused")
  static boolean isAuditLoggerEnabled() {
    return logger.isAuditLoggerEnabled();
  }

  @SuppressWarnings("unused")
  static boolean isMetricsLoggerEnabled() {
    return logger.isMetricsLoggerEnabled();

  }

  /**
   * @param t exception
   */
  static void error(final Throwable t) {
    logger.error(t);
  }

  /**
   * @param msg to output
   */
  static void error(final String msg) {
    logger.error(msg);
  }

  /**
   * @param msg to output
   */
  static void error(final String msg,
                     final Throwable t) {
    getLogger().error(msg, t);
  }

  /**
   * @param msg to output
   */
  static void warn(final String msg) {
    logger.warn(msg);
  }

  /**
   * @param msg to output
   */
  static void info(final String msg) {
    logger.info(msg);
  }

  /**
   * @param msg to output
   */
  @SuppressWarnings("unused")
  static void audit(final String msg) {
    logger.audit(msg);
  }

  /**
   * @param msg to output
   */
  @SuppressWarnings("unused")
  static void metrics(final String msg) {
    logger.metrics(msg);
  }

  /**
   * @param msg to output
   */
  static void debug(final String msg) {
    logger.debug(msg);
  }

  /**
   * @param msg to output
   */
  static void trace(final String msg) {
    getLogger().trace(msg);
  }
}
