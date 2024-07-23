package org.apache.avro;

import org.junit.Assert;
import org.junit.Test;

public class TestSchema {
  @Test
  public void addAliasTest() {
    Schema schema = Schema.createRecord("name", "doc", "namespace", false);
    schema.addAlias("test");
    Assert.assertTrue(true);
  }
}
