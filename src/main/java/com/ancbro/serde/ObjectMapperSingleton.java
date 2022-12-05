package com.ancbro.serde;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum ObjectMapperSingleton {
  INSTANCE;

  private static final ObjectMapper OBJECT_MAPPER;

  static {
    OBJECT_MAPPER = new ObjectMapper();
    OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public ObjectMapper getObjectMapper() {
    return OBJECT_MAPPER;
  }
}
