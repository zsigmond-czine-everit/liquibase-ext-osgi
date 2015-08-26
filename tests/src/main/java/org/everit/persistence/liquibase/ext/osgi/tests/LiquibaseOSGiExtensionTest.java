package org.everit.persistence.liquibase.ext.osgi.tests;

import java.sql.Connection;
import java.sql.SQLException;

import org.everit.osgi.dev.testrunner.TestDuringDevelopment;
import org.everit.osgi.ecm.annotation.Activate;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Service;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.extender.ECMExtenderConstants;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import aQute.bnd.annotation.headers.ProvideCapability;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.osgi.OSGiResourceAccessor;

@Component(configurationPolicy = ConfigurationPolicy.IGNORE)
@ProvideCapability(ns = ECMExtenderConstants.CAPABILITY_NS_COMPONENT,
    value = ECMExtenderConstants.CAPABILITY_ATTR_CLASS + "=${@class}")
@StringAttributes({
    @StringAttribute(attributeId = "eosgi.testId", defaultValue = "LiquibaseOSGiExtension"),
    @StringAttribute(attributeId = "eosgi.testEngine", defaultValue = "junit4") })
@Service
public class LiquibaseOSGiExtensionTest {

  private Bundle bundle;

  @Activate
  public void activate(final BundleContext bundleContext) {
    this.bundle = bundleContext.getBundle();
  }

  @Test
  @TestDuringDevelopment
  public void testParser() {
    JdbcDataSource h2DataSource = new JdbcDataSource();
    h2DataSource.setURL("jdbc:h2:mem:");

    try (Connection connection = h2DataSource.getConnection()) {
      JdbcConnection jdbcConnection = new JdbcConnection(connection);
      Liquibase liquibase = new Liquibase(
          "META-INF/liquibase/org.everit.persistence.liquibase.ext.osgi.changelog.xml",
          new OSGiResourceAccessor(bundle), jdbcConnection);
      liquibase.update((Contexts) null);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (LiquibaseException e) {
      throw new RuntimeException(e);
    }
  }
}