/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.persistence.liquibase.ext.osgi.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;

/**
 * A resource that is accessible by the classloader of a bundle.
 */
public class BundleResource {

  public final Map<String, Object> attributes;

  public final Bundle bundle;

  public final String resourceName;

  /**
   * Constructor.
   *
   * @param bundle
   *          the {@link Bundle} instance.
   * @param resourceName
   *          the name of the resource.
   * @param attributes
   *          The attributes of the bundle capability.
   */
  public BundleResource(final Bundle bundle, final String resourceName,
      final Map<String, Object> attributes) {
    this.bundle = bundle;
    this.resourceName = resourceName;
    if (attributes == null) {
      this.attributes = Collections.emptyMap();
    } else {
      this.attributes = Collections.unmodifiableMap(new HashMap<String, Object>(attributes));
    }
  }

}
