/* ********************************************************************
    Appropriate copyright notice
*/
package org.bedework.util.logging;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/** This class provides basic logging support. It also allows for some
 * log messages to be output to multiple loggers.
 *
 * Each stream will be for this class possible prefixed by name + ".".
 *
 * User: mike Date: 12/11/18 Time: 15:51
 */
public class BwLogger {
  protected Class<?> loggedClass;

  protected String loggedName;

  private transient Logger log;

  private final Map<String, Logger> loggers = new HashMap<>(5);

  public final static String errorLoggerName = "errors";
  public final static String auditLoggerName = "audit"; // INFO only
  public final static String metricsLoggerName = "metrics"; // INFO only

  public BwLogger setLoggedClass(final Class<?> cl) {
    loggedClass = cl;
    return this;
  }

  public Class<?> getLoggedClass() {
    return loggedClass;
  }

  public BwLogger setLoggedName(final String name) {
    loggedName = name;
    return this;
  }

  public String getLoggedName() {
    return loggedName;
  }

  private static final Map<String, org.apache.log4j.Level> toLog4jLevel = new HashMap<>();
  static {
    toLog4jLevel.put("OFF", org.apache.log4j.Level.OFF);
    toLog4jLevel.put("SEVERE", org.apache.log4j.Level.ERROR);
    toLog4jLevel.put("WARNING", org.apache.log4j.Level.WARN);
    toLog4jLevel.put("INFO", org.apache.log4j.Level.INFO);
    toLog4jLevel.put("CONFIG", org.apache.log4j.Level.DEBUG);
    toLog4jLevel.put("FINE", org.apache.log4j.Level.DEBUG);
    toLog4jLevel.put("FINER", org.apache.log4j.Level.DEBUG);
    toLog4jLevel.put("FINEST", org.apache.log4j.Level.TRACE);
    toLog4jLevel.put("ALL", org.apache.log4j.Level.ALL);
  }

  private static final Map<org.apache.log4j.Level, Level> fromLog4jLevel = new HashMap<>();
  static {
    fromLog4jLevel.put(org.apache.log4j.Level.OFF, Level.OFF);
    fromLog4jLevel.put(org.apache.log4j.Level.ERROR, Level.SEVERE);
    fromLog4jLevel.put(org.apache.log4j.Level.FATAL, Level.SEVERE);
    fromLog4jLevel.put(org.apache.log4j.Level.WARN, Level.WARNING);
    fromLog4jLevel.put(org.apache.log4j.Level.INFO, Level.INFO);
    fromLog4jLevel.put(org.apache.log4j.Level.DEBUG, Level.FINE);
    fromLog4jLevel.put(org.apache.log4j.Level.TRACE, Level.FINEST);
    fromLog4jLevel.put(org.apache.log4j.Level.ALL, Level.ALL);
  }

  public void setLogLevel(final String className, final Level level) {
        /* At some point I'll remove the dependency on log4j.
        For the moment just translate levels
     */
    var newLevel = toLog4jLevel.get(level.getName());

    if (newLevel == null) {
      return;
    }

    var log = Logger.getLogger(className);
    log.setLevel(newLevel);

    var rootLog = Logger.getRootLogger();
    var rootLevel = rootLog.getLevel();

    if (newLevel.toInt() > rootLevel.toInt()) {
      rootLog.setLevel(newLevel);
    }
  }

  public Level getLogLevel(final String className) {
    var level = fromLog4jLevel.get(getLogger(className).getLevel());

    if (level == null) {
      return Level.INFO;
    }

    return level;
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

  public boolean debug() {
    return getLogger().isDebugEnabled();
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
    getLogger().error(t.getLocalizedMessage(), t);

    final Logger errorLogger = getErrorLoggerIfEnabled();

    if (errorLogger != null) {
      errorLogger.error(t.getLocalizedMessage(), t);
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
