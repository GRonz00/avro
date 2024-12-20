package org.apache.avro.generic;

import org.apache.avro.Conversions;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.util.Utf8;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.ByteBuffer;
import java.util.*;

@RunWith(Enclosed.class)
public class GenericDataTest {
  @RunWith(Parameterized.class)
  public static class TestValidate {
    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
      List<Schema.Field> fields1 = Arrays.asList(
          new Schema.Field("a", Schema.create(Schema.Type.INT), null, 3),
          new Schema.Field("b", Schema.create(Schema.Type.FLOAT), null, 3.14f)
      );
      Schema recordSchema1 = Schema.createRecord("RecordTest1", null, null, false, fields1);
      Schema enumSchema1 = Schema.createEnum("EnumTest1", null, null, Arrays.asList("a", "b"));
      Schema arraySchema1 = Schema.createArray(Schema.create(Schema.Type.INT));
      Schema mapSchema1 = Schema.createMap(Schema.create(Schema.Type.INT));
      Schema unionSchema1 = Schema.createUnion(Schema.create(Schema.Type.INT), Schema.create(Schema.Type.STRING));
      Schema fixedSchema1 = Schema.createFixed("FixedTest1", null, null, 16);
      GenericData.Array<Integer> arrayData1 = new GenericData.Array<>(arraySchema1, Collections.emptyList());
      List<Schema.Field> fields2 = Collections.singletonList(
          new Schema.Field("c", Schema.create(Schema.Type.STRING), null, "test")
      );
      Schema recordSchema2 = Schema.createRecord("RecordTest2", null, null, false, fields2);
      GenericData.Array<Integer> arrayData2 = new GenericData.Array<>(arraySchema1, Arrays.asList(3, 4));
      Map<Object, Object> mapData = new HashMap<>();
      mapData.put("Test", 0.14f);
      Map<Object, Object> mapData2 = new HashMap<>();
      mapData2.put("Test", 3);



      return Arrays.asList(
          new Object[][]{
              {null, null, true, true},
              {Schema.create(Schema.Type.NULL), null, true, false},
              {Schema.create(Schema.Type.STRING), 3, false, false},
              {Schema.create(Schema.Type.BYTES), ByteBuffer.allocate(3), true, false},
              {Schema.create(Schema.Type.INT), 3.14f, false, false},
              {Schema.create(Schema.Type.LONG), 3L, true, false},
              {Schema.create(Schema.Type.FLOAT), 3, false, false},
              {Schema.create(Schema.Type.DOUBLE), 3.14, true, false},
              {recordSchema1, 3, false, false},
              {enumSchema1, new GenericData.EnumSymbol(enumSchema1, "a"), true, false},
              {arraySchema1, 3, false, false},
              {mapSchema1, new HashMap<String, Integer>(), true, false},
              {unionSchema1, 3.14f, false, false},
              {fixedSchema1, new GenericData.Fixed(fixedSchema1, new byte[16]), true, false},
              {Schema.create(Schema.Type.BOOLEAN), false, true, false},
              // Improvements
              {Schema.create(Schema.Type.NULL), 3, false, false},
              {recordSchema1, new GenericRecordBuilder(recordSchema1).build(), true, false},
              {arraySchema1, arrayData1, true, false},
              {unionSchema1, 3, true, false},
              {recordSchema1, new GenericRecordBuilder(recordSchema2).build(), false, false},
              {enumSchema1, 3, false, false},
              {arraySchema1, arrayData2, true, false},
              {arraySchema1, Collections.singletonList(3.14f), false, false},
              {mapSchema1, 3, false, false},
              {mapSchema1, mapData, false, false},
              {mapSchema1, mapData2, true, false},
              {fixedSchema1, 3, false, false},
              {fixedSchema1, new GenericData.Fixed(fixedSchema1, new byte[8]), false, false},
              //due missed badua per enum default
              // PIT Improvements
              {enumSchema1, new GenericData.EnumSymbol(enumSchema1, "c"), false, false},
              {Schema.create(Schema.Type.STRING), "Test", true, false},
              {Schema.create(Schema.Type.BYTES), 3, false, false},
              {Schema.create(Schema.Type.LONG), 3.14f, false, false},
              {Schema.create(Schema.Type.DOUBLE), 3, false, false},
              {Schema.create(Schema.Type.BOOLEAN), 3, false, false},
          }
      );
    }

    private final Schema schema;
    private final Object datum;
    private final boolean expected;
    private final boolean expectedException;

    public TestValidate(Schema schema, Object datum, boolean expected, boolean expectedException) {
      this.schema = schema;
      this.datum = datum;
      this.expected = expected;
      this.expectedException = expectedException;
    }

    @Test
    public void validateTest() {
      try {
        boolean result = GenericData.get().validate(schema, datum);
        Assert.assertEquals(expected, result);

        // PIT improvements
        if (schema.getType() == Schema.Type.ENUM && datum instanceof GenericData.EnumSymbol) {
          GenericData.EnumSymbol enumSymbol = (GenericData.EnumSymbol) datum;
          Assert.assertEquals(result, schema.hasEnumSymbol(enumSymbol.toString()));
        }
        if (schema.getType() == Schema.Type.MAP && datum instanceof Map) {
          Map<String, Object> mapValue = (Map<String, Object>) datum;
          for (Map.Entry<String, Object> entry : mapValue.entrySet()) {
            Assert.assertEquals(result, entry.getValue() instanceof Integer);
          }
        }




      } catch (Exception ignored) {
        if (!expectedException) Assert.fail();
      }
    }
  }

  @RunWith(Parameterized.class)
  public static class TestInduce {
    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
      Schema recordSchema = Schema.createRecord(null, null, null, false, Collections.emptyList());
      GenericData.Record record = new GenericRecordBuilder(recordSchema).build();

      //Schema enumSchema = Schema.createEnum(null, null, null, Arrays.asList("1", "2"));
      //GenericData.EnumSymbol enumSymbol = new GenericData.EnumSymbol(enumSchema, "1");

      Schema arraySchema = Schema.createArray(Schema.create(Schema.Type.INT));
      GenericData.Array<Integer> array = new GenericData.Array<>(16, arraySchema);
      array.add(0, 1);

      Schema mapSchema1 = Schema.createMap(Schema.create(Schema.Type.STRING));
      Map<String, String> map1 = new HashMap<>();
      map1.put("key", "v");

      Schema fixedSchema1 = Schema.createFixed(null, null, null, 16);
      GenericData.Fixed fixed1 = new GenericData.Fixed(fixedSchema1);

      Map<String, String> map2 = new HashMap<>();
      map2.put("key1", "v1");
      map2.put("key2", null);

      Map<String, String> map3 = new HashMap<>();
      map3.put("key1", "v1");
      map3.put("key2", "v2");

      return Arrays.asList(new Object[][]{
          {new GenericData(), Schema.create(Schema.Type.NULL), true},
          {null, Schema.create(Schema.Type.NULL),false},
          {"string", Schema.create(Schema.Type.STRING),false},
          {ByteBuffer.allocate(3), Schema.create(Schema.Type.BYTES), false},
          {1, Schema.create(Schema.Type.INT), false},
          {1L, Schema.create(Schema.Type.LONG), false},
          {1.1f, Schema.create(Schema.Type.FLOAT), false},
          {1.1, Schema.create(Schema.Type.DOUBLE), false},
          {true, Schema.create(Schema.Type.BOOLEAN), false},
          {record, recordSchema, false},
//        {enum1, enumSchema1, false}, // Fail: induce does not support EnumSchema
          {array, arraySchema, false},
          {map1, mapSchema1, false},
          {fixed1, fixedSchema1, false},
          // Improvements
          {Arrays.asList(1, null), null,true},
          {Arrays.asList(1, 2), arraySchema, false},
          {Collections.emptyList(), null,true},
          {map2, null,true},
          {map3, mapSchema1, false},
          {new HashMap<>(), null,true},

      });
    }

    private final Object datum;
    private final Schema expected;
    private final boolean exception;

    public TestInduce(Object datum, Schema expected, boolean exception) {
      this.datum = datum;
      this.expected = expected;
      this.exception = exception;
    }

    @Test
    public void induceTest() {
      try {
        Schema result = GenericData.get().induce(datum);
        if(!exception){
          Assert.assertEquals(expected, result);
        }else {
          Assert.fail();
        }

      } catch (Exception e) {
        if(!exception){
          Assert.fail();
        }
      }
    }
  }
/*
  @RunWith(Parameterized.class)
  public static class TestResolveUnion {
    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
      Schema unionSchema = Schema.createUnion(Schema.create(Schema.Type.INT), Schema.create(Schema.Type.STRING));
      Schema stringUuid = new Schema.Parser().parse("{\"type\":\"string\",\"logicalType\":\"uuid\"}");
      Schema unionSchema2 = Schema.createUnion(Schema.create(Schema.Type.INT), stringUuid);
      Schema intDate = new Schema.Parser().parse("{\"type\":\"int\",\"logicalType\":\"date\"}");
      Schema unionSchema3 = Schema.createUnion(intDate, stringUuid);
      Schema unionSchema4 = Schema.createUnion();
      Schema unionSchema5 = Schema.createUnion(stringUuid);
      ExpectedResult<Integer> zero = 0, null);
      ExpectedResult<Integer> one = 1, null);
      ExpectedResult<Integer> exception = null, Exception.class);
      return Arrays.asList(new Object[][]{
          {null, null, exception},
          {Schema.create(Schema.Type.STRING), 3.14f, exception},
          {unionSchema, 3, zero},
          {null, "generic", exception},
          // JaCoCo improvements
          {unionSchema, new UUID(1, 1), exception},
          {unionSchema2, new UUID(1, 1), one},
          {unionSchema3, new UUID(1, 1), one},
          // ba-dua improvements
          {unionSchema4, new UUID(1, 1), exception},
          {unionSchema5, new UUID(1, 1), zero},
          // PIT improvements
          {unionSchema, "generic", one},
      });
    }

    private final Schema union;
    private final Object datum;
    private final ExpectedResult<Integer> expected;

    public TestResolveUnion(Schema union, Object datum, ExpectedResult<Integer> expected) {
      this.union = union;
      this.datum = datum;
      this.expected = expected;
    }

    @Before
    public void setup() {
      GenericData.get().addLogicalTypeConversion(new Conversions.UUIDConversion());
    }

    @Test
    public void resolveUnionTest() {
      try {
        Integer result = GenericData.get().resolveUnion(union, datum);
        Assert.assertEquals(expected.getResult(), result);
      } catch (Exception ignored) {
        Assert.assertNotNull(expected.getException());
      }
    }
  }

  @RunWith(Parameterized.class)
  public static class TestCompare {
    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
      Schema recordSchema = Schema.createRecord("Record1", null, null, false, Arrays.asList(
          new Schema.Field("a", Schema.create(Schema.Type.INT), null, 0, Schema.Field.Order.IGNORE),
          new Schema.Field("b", Schema.create(Schema.Type.FLOAT), null, 3.14f, Schema.Field.Order.DESCENDING)
      ));
      Schema recordSchema2 = Schema.createRecord("Record2", null, null, false, Collections.singletonList(
          new Schema.Field("a", Schema.create(Schema.Type.INT), null, 0, Schema.Field.Order.ASCENDING)
      ));
      Schema enumSchema = Schema.createEnum("Enum1", null, null, Arrays.asList("a", "b"));
      Schema arraySchema = Schema.createArray(Schema.create(Schema.Type.INT));
      Schema mapSchema = Schema.createMap(Schema.create(Schema.Type.INT));
      Schema unionSchema = Schema.createUnion(Schema.create(Schema.Type.INT), Schema.create(Schema.Type.STRING));
      Schema fixedSchema = Schema.createFixed("Fixed1", null, null, 16);
      Map<String, Integer> map1 = new HashMap<>();
      map1.put("key", 0);
      Map<String, Integer> map2 = new HashMap<>();
      map2.put("key", 1);
      GenericData.Record record1 = new GenericRecordBuilder(recordSchema).build();
      GenericData.Record record2 = new GenericRecordBuilder(recordSchema).set("a", 3).set("b", 6.28f).build();
      GenericData.Record record3 = new GenericRecordBuilder(recordSchema).set("a", 0).set("b", 3.14f).build();
      GenericData.Record record4 = new GenericRecordBuilder(recordSchema2).build();
      GenericData.Record record5 = new GenericRecordBuilder(recordSchema2).set("a", 3).build();
      ExpectedResult<Integer> zero = 0, null);
      ExpectedResult<Integer> minus1 = -1, null);
      ExpectedResult<Integer> one = 1, null);
      ExpectedResult<Integer> exception = null, Exception.class);
      return Arrays.asList(new Object[][]{
//        {null, null, null, true, exception}, // Fail: shouldn't work with invalid schema
//        {"generic", 3, Schema.create(Schema.Type.NULL), false, exception}, // Fail: objects are incompatible with schema
          {"generic", "generic", Schema.create(Schema.Type.STRING), true, zero},
          {"generic", 3, Schema.create(Schema.Type.BYTES), false, exception},
          {3, 3, Schema.create(Schema.Type.INT), true, zero},
          {3.14f, 3, Schema.create(Schema.Type.LONG), false, exception},
          {3.14f, 3.14f, Schema.create(Schema.Type.FLOAT), true, zero},
          {3, 3.14f, Schema.create(Schema.Type.DOUBLE), false, exception},
          {true, true, Schema.create(Schema.Type.BOOLEAN), true, zero},
          {3, 3.14f, recordSchema, false, exception},
          {new GenericData.EnumSymbol(enumSchema, "a"), new GenericData.EnumSymbol(enumSchema, "a"), enumSchema, true, zero},
          {3, 3.14f, arraySchema, false, exception},
          {map1, map1, mapSchema, true, zero},
          {map1, 3.14f, unionSchema, false, exception},
          {new GenericData.Fixed(fixedSchema), new GenericData.Fixed(fixedSchema), fixedSchema, true, zero},
          // Improvements
          {3, null, Schema.create(Schema.Type.NULL), true, zero},
          {"generic", "generic1", Schema.create(Schema.Type.STRING), true, minus1},
          {Arrays.asList(1, 2), Arrays.asList(3, 4), arraySchema, true, minus1},
          {map1, map2, mapSchema, true, one},
          {"generic", 3, unionSchema, true, one},
          {new Utf8("generic"), new Utf8("generic1"), Schema.create(Schema.Type.STRING), true, minus1},
          {"generic", "generic1", unionSchema, true, minus1},
          {map1, map2, mapSchema, false, exception},
          {Arrays.asList(1, 1), Collections.singletonList(1), arraySchema, true, one},
          {Collections.singletonList(1), Arrays.asList(1, 1), arraySchema, true, minus1},
          {Collections.singletonList(1), Collections.singletonList(1), arraySchema, true, zero},
          {record1, record2, recordSchema, true, one},
          {record2, record1, recordSchema, true, minus1},
          {record1, record3, recordSchema, true, zero},
          {record4, record5, recordSchema2, true, minus1},
          // PIT improvements
          {new GenericData.EnumSymbol(enumSchema, "b"), new GenericData.EnumSymbol(enumSchema, "a"), enumSchema, true, one},
          {new GenericData.EnumSymbol(enumSchema, "a"), new GenericData.EnumSymbol(enumSchema, "b"), enumSchema, true, minus1},
      });
    }

    private final Object o1;
    private final Object o2;
    private final Schema s;
    private final boolean equals;
    private final ExpectedResult<Integer> expected;

    public TestCompare(Object o1, Object o2, Schema s, boolean equals, ExpectedResult<Integer> expected) {
      this.o1 = o1;
      this.o2 = o2;
      this.s = s;
      this.equals = equals;
      this.expected = expected;
    }

    @Test
    public void compareTest() {
      try {
        Integer result = GenericData.get().compare(o1, o2, s, equals);
        Assert.assertEquals(expected.getResult(), result);
      } catch (Exception ignored) {
        Assert.assertNotNull(expected.getException());
      }
    }
  }

 */
}
