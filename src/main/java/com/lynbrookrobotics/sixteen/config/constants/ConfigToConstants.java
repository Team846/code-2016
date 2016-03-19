package com.lynbrookrobotics.sixteen.config.constants;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.regex.Pattern;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface ConfigLoaded {}

public class ConfigToConstants {
  private static LinkedList<Field> toSet = null;
  private static Config currentConfig = null;

  public static void loadInto(Class<?> clazz, Config conf) {
    if (!(toSet == null || toSet.isEmpty())) {
      throw new RuntimeException("Config loading of " + toSet.peekFirst().getDeclaringClass() + " did not load in all variables that were annotated");
    }

    currentConfig = conf;
    toSet = new LinkedList<>();

    for (Field field: clazz.getDeclaredFields()) {
      boolean hasAnnotation = false;
      for (Annotation annotation: field.getDeclaredAnnotations()) {
        if (annotation instanceof ConfigLoaded) {
          hasAnnotation = true;
          break;
        }
      }

      if (hasAnnotation && Modifier.isStatic(field.getModifiers())) {
        toSet.add(field);
      }
    }
  }

  public static <T> T config() {
    Field field = toSet.remove();
    String[] parts = field.getName().split(Pattern.quote("_"));
    String configParam = "";
    for (String part: parts) {
      configParam += part.toLowerCase() + "-";
    }

    configParam = configParam.substring(0, configParam.length() - 1);

    Object value;
    if (field.getType() == boolean.class) {
      value = currentConfig.getBoolean(configParam);
    } else if (field.getType() == Number.class) {
      value = currentConfig.getNumber(configParam);
    } else if (field.getType() == int.class) {
      value = currentConfig.getInt(configParam);
    } else if (field.getType() == long.class) {
      value = currentConfig.getLong(configParam);
    } else if (field.getType() == double.class) {
      value = currentConfig.getDouble(configParam);
    } else if (field.getType() == String.class) {
      value = currentConfig.getString(configParam);
    } else if (field.getType() == ConfigObject.class) {
      value = currentConfig.getObject(configParam);
    } else if (field.getType() == Config.class) {
      value = currentConfig.getConfig(configParam);
    } else {
      value = currentConfig.getValue(configParam).unwrapped();
    }

    return (T) value;
  }
}
