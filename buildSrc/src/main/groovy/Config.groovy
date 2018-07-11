/*
 * Copyright 2017 wetransform GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.nio.charset.StandardCharsets

import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.SafeConstructor

class Config {

  /**
   * Merge configuration maps together.
   * Configurations in subsequent maps may override configuration from the previous maps.
   *
   * @param configs the configurations
   * @return the merged configuration
   */
  static Map mergeConfigs(Iterable<Map> configs) {
    configs.asCollection().inject([:], Config.&combineMap)
  }

  private static Map combineMap(Map a, Map b) {
    if (a.is(b)) {
      return a
    }

    Map result = [:]
    result.putAll(a)
    b.each { key, value ->
      if (value != null) {
        result.merge(key, value, Config.&combineValue)
      }
    }
    result
  }

  private static Object combineValue(Object a, Object b) {
    if (a instanceof Map) {
      if (b instanceof Map) {
        combineMap(a, b)
      }
      else {
        //XXX error?
        a
      }
    }
    else if (b instanceof Map) {
      //XXX error?
      b
    }
    else if (a instanceof List && b instanceof List) {
      def combined = []
      combined.addAll(a)
      combined.addAll(b)
      combined
    }
    else if (a instanceof List && !(b instanceof List)) {
      def combined = []
      combined.addAll(a)
      combined.add(b)
      combined
    }
    else if (!(a instanceof List) && b instanceof List) {
      def combined = []
      combined.add(a)
      combined.addAll(b)
      combined
    }
    else {
      // b overrides a
      //XXX message?
      b
    }
  }

  /**
   * Load a configuration from a YAML file.
   *
   * @param yamlFile the YAML file
   * @return the loaded configuration map
   */
  static Map loadYaml(File yamlFile) {
    Yaml yaml = new Yaml(new SafeConstructor());
    Map result
    yamlFile.withInputStream {
      result = yaml.load(it)
    }
    result ?: [:]
  }

  /**
   * Save a configuration to a YAML file.
   *
   * @param yamlFile the YAML file
   * @return the loaded configuration map
   */
  static void saveYaml(Map config, File yamlFile) {
    DumperOptions options = new DumperOptions()
//    options.explicitStart = true
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)
    Yaml yaml = new Yaml(options);
    Map result
    yamlFile.withWriter(StandardCharsets.UTF_8.name()) {
      result = yaml.dump(config, it)
    }
  }

  /**
   * Save a configuration to a YAML file.
   *
   * @param yamlFile the YAML file
   * @return the loaded configuration map
   */
  static void appendYaml(Map config, File yamlFile) {
    DumperOptions options = new DumperOptions()
    options.explicitStart = true
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)
    Yaml yaml = new Yaml(options);
    Map result
    yamlFile.withWriterAppend(StandardCharsets.UTF_8.name()) {
      result = yaml.dump(config, it)
    }
  }

  static Map load(Object... files) {
    mergeConfigs(files.collect { loadYaml(it as File) })
  }

}
