/* ********************************************************************
    Appropriate copyright notice
*/
package org.bedework.util.logging;

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
  protected Class loggedClass;

  protected String loggedName;

  private transient Logger log;

  private final Map<String, Logger> loggers = new HashMap<>(5);

  public final static String errorLoggerName = "errors";
  public final static String auditLoggerName = "audit"; // INFO only
  public final static String metricsLoggerName = "metrics"; // INFO only

  public BwLogger setLoggedClass(final Class cl) {
    loggedClass = cl;
    return this;
  }

  public Class getLoggedClass() {
    return loggedClass;
  }

  public BwLogger setLoggedName(final String name) {
    loggedName = name;
    return this;
  }

  public String getLoggedName() {
    return loggedName;
  }

  /**
   * @return Logger
   */
  protected Logger getLogger() {
    assert loggedClass != null || loggedName != null;

    if (log == null) {
      if (loggedClass != null) {
        log = Logger.getLogger(loggedClass);
      } else {
        log = Logger.getLogger(loggedName);
      }
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

  protected Logger getErrorLoggerIfEnabled() {
    return loggers.get(errorLoggerName);
  }

  protected Logger getAuditLoggerIfEnabled() {
    return loggers.get(auditLoggerName);
  }

  protected Logger getMetricsLoggerIfEnabled() {
    return loggers.get(metricsLoggerName);
  }

  public void enableErrorLogger() {
    getLogger(errorLoggerName);
  }

  public void enableAuditLogger() {
    getLogger(auditLoggerName);
  }

  public void enableMetricsLogger() {
    getLogger(metricsLoggerName);
  }

  public boolean isDebugEnabled() {
    return getLogger().isDebugEnabled();
  }

  public boolean isMetricsDebugEnabled() {
    return getLogger(metricsLoggerName).isDebugEnabled();
  }

  public boolean isTraceEnabled() {
    return getLogger().isTraceEnabled();
  }

  public boolean isErrorLoggerEnabled() {
    return getLogger(errorLoggerName) != null;
  }

  public boolean isAuditLoggerEnabled() {
    return getLogger(auditLoggerName) != null;
  }

  public boolean isMetricsLoggerEnabled() {
    return getLogger(metricsLoggerName) != null;
  }

  /**
   * @param t exception
   */
  public void error(final Throwable t) {
    getLogger().error(this, t);

    final Logger errorLogger = getErrorLoggerIfEnabled();

    if (errorLogger != null) {
      errorLogger.error(this, t);
    }
  }

  /**
   * @param msg to output
   */
  public void error(final String msg) {
    getLogger().error(msg);

    final Logger errorLogger = getErrorLoggerIfEnabled();

    if (errorLogger != null) {
      errorLogger.error(msg);
    }
  }

  /**
   * @param msg to output
   */
  public void error(final String msg,
                       final Throwable t) {
    getLogger().error(msg, t);

    final Logger errorLogger = getErrorLoggerIfEnabled();

    if (errorLogger != null) {
      errorLogger.error(msg, t);
    }
  }

  /**
   * @param msg to output
   */
  public void warn(final String msg) {
    getLogger().warn(msg);
  }

  /**
   * @param msg to output
   */
  public void info(final String msg) {
    getLogger().info(msg);
  }

  /**
   * @param msg to output
   */
  public void audit(final String msg) {
    if (isAuditLoggerEnabled()) {
      getLogger(auditLoggerName).info(msg);
    }
  }

  /**
   * @param msg to output
   */
  public void metrics(final String msg) {
    if (isMetricsLoggerEnabled()) {
      getLogger(metricsLoggerName).info(msg);
    }
  }

  /**
   * @param msg to output
   */
  public void debug(final String msg) {
    getLogger().debug(msg);
  }

  /**
   * @param msg to output
   */
  public void trace(final String msg) {
    getLogger().trace(msg);
  }
}
