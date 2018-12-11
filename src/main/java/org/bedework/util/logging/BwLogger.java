/* ********************************************************************
    Appropriate copyright notice
*/
package org.bedework.util.misc;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


/** This class provides basic logging support. It also allows for some
 * log messages to be output to multiple loggers.
 *
 * Each stream will be for this class possible prefixed by name + ".".
 *
 * User: mike Date: 12/11/18 Time: 15:51
 */
public class BwLogger {
  protected boolean debug;

  protected Class loggedClass;

  private transient Logger log;

  private final Map<String, Logger> loggers = new HashMap<>(5);

  public final static String errorLoggerName = "errors";
  public final static String auditLoggerName = "audit"; // INFO only
  public final static String metricsLoggerName = "metrics"; // INFO only

  public void setLoggedClass(final Class cl) {
    loggedClass = cl;
  }

  public Class getLoggedClass() {
    return loggedClass;
  }

  /**
   * @return Logger
   */
  protected Logger getLogger() {
    assert loggedClass != null;

    if (log == null) {
      log = Logger.getLogger(loggedClass);
    }

    return log;
  }

  /**
   * @return Logger
   */
  protected Logger getLogger(final String name) {
    Logger theLogger = loggers.get(name);
    if (theLogger != null) {
      return theLogger;
    }

    theLogger = Logger.getLogger(name + "." + loggedClass.getName());

    loggers.put(name, theLogger);

    return theLogger;
  }

  protected void enableErrorLogger() {
    getLogger(errorLoggerName);
  }

  protected void enableAuditLogger() {
    getLogger(auditLoggerName);
  }

  protected void enableMetricsLogger() {
    getLogger(metricsLoggerName);
  }

  protected boolean isdebugEnabled() {
    return getLogger().isDebugEnabled();
  }

  protected boolean isErrorLoggerEnabled() {
    return getLogger(errorLoggerName) != null;
  }

  protected boolean isAuditLoggerEnabled() {
    return getLogger(auditLoggerName) != null;
  }

  protected boolean isMetricsLoggerEnabled() {
    return getLogger(metricsLoggerName) != null;
  }

  protected Logger getErrorLoggerIfEnabled() {
    return loggers.get(errorLoggerName);
  }

  protected Logger getAuditLoggerIfEnabled() {
    return loggers.get(auditLoggerName);
  }

  protected Logger getMetricsLoggerIfEnabled() {
    return loggers.get(metricsLoggerName);
  }

  /**
   * @param t exception
   */
  protected void error(final Throwable t) {
    getLogger().error(this, t);

    final Logger errorLogger = getErrorLoggerIfEnabled();

    if (errorLogger != null) {
      errorLogger.error(this, t);
    }
  }

  /**
   * @param msg to output
   */
  protected void error(final String msg) {
    getLogger().error(msg);

    final Logger errorLogger = getErrorLoggerIfEnabled();

    if (errorLogger != null) {
      errorLogger.error(msg);
    }
  }

  /**
   * @param msg to output
   */
  protected void warn(final String msg) {
    getLogger().warn(msg);
  }

  /**
   * @param msg to output
   */
  protected void info(final String msg) {
    getLogger().info(msg);
  }

  /**
   * @param msg to output
   */
  protected void audit(final String msg) {
    if (isAuditLoggerEnabled()) {
      getLogger(auditLoggerName).info(msg);
    }
  }

  /**
   * @param msg to output
   */
  protected void metrics(final String msg) {
    if (isMetricsLoggerEnabled()) {
      getLogger(metricsLoggerName).info(msg);
    }
  }

  /**
   * @param msg to output
   */
  protected void debug(final String msg) {
    getLogger().debug(msg);
  }
}
