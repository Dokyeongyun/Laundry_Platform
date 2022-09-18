package com.coders.laundry.error;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class Detail {

  private Map<String, Object> map;

  public Detail() {
    map = new HashMap<>();
  }

  public Detail with(String key, Object value) {
    map.put(key, value);
    return this;
  }
}
