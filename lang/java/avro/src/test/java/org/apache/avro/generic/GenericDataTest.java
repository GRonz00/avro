package org.apache.avro.generic;

import org.apache.avro.Conversions;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
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
          new Schema.Field("a", Schema.create(Schema.Type.INT), null, 1),
          new Schema.Field("b", Schema.create(Schema.Type.FLOAT), null, 1.1f)
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
      GenericData.Array<Integer> arrayData2 = new GenericData.Array<>(arraySchema1, Arrays.asList(1, 2));
      Map<Object, Object> mapData = new HashMap<>();
      mapData.put("Test", 1.0f);
      Map<Object, Object> mapData2 = new HashMap<>();
      mapData2.put("Test", 1);

      Schema arraySchema = Schema.createArray(Schema.create(Schema.Type.STRING));
      Schema mapSchema = Schema.createMap(Schema.create(Schema.Type.INT));

      Schema schema = Schema.createUnion(Arrays.asList(arraySchema, mapSchema));

      Object datum = Arrays.asList("valid", 42); // Un array contenente un elemento non valido




      return Arrays.asList(
          new Object[][]{
              {null, null, true, true},
              {Schema.create(Schema.Type.NULL), null, true, false},
              {Schema.create(Schema.Type.STRING), 1, false, false},
              {Schema.create(Schema.Type.BYTES), ByteBuffer.allocate(1), true, false},
              {Schema.create(Schema.Type.INT), 1.1f, false, false},
              {Schema.create(Schema.Type.LONG), 1L, true, false},
              {Schema.create(Schema.Type.FLOAT), 1, false, false},
              {Schema.create(Schema.Type.DOUBLE), 1.1, true, false},
              {recordSchema1, 1, false, false},
              {enumSchema1, new GenericData.EnumSymbol(enumSchema1, "a"), true, false},
              {arraySchema1, 1, false, false},
              {mapSchema1, new HashMap<String, Integer>(), true, false},
              {unionSchema1, 1.1f, false, false},
              {fixedSchema1, new GenericData.Fixed(fixedSchema1, new byte[16]), true, false},
              {Schema.create(Schema.Type.BOOLEAN), 1, false, false},

              // Miglioramenti
              {Schema.create(Schema.Type.NULL), 1, false, false},
              {recordSchema1, new GenericRecordBuilder(recordSchema1).build(), true, false},
              {arraySchema1, arrayData1, true, false},
              {unionSchema1, 1, true, false},
              {recordSchema1, new GenericRecordBuilder(recordSchema2).build(), false, false},
              {enumSchema1, 1, false, false},
              {arraySchema1, arrayData2, true, false},
              {arraySchema1, Collections.singletonList(1.1f), false, false},
              {mapSchema1, 1, false, false},
              {mapSchema1, mapData, false, false},
              {mapSchema1, mapData2, true, false},
              {fixedSchema1, 1, false, false},
              {fixedSchema1, new GenericData.Fixed(fixedSchema1, new byte[8]), false, false},
              //due missed badua per enum default
              // PIT Improvements
              {enumSchema1, new GenericData.EnumSymbol(enumSchema1, "c"), false, false},
              {Schema.create(Schema.Type.STRING), "Test", true, false},
              {Schema.create(Schema.Type.BYTES), 1, false, false},
              {Schema.create(Schema.Type.LONG), 1.1f, false, false},
              {Schema.create(Schema.Type.DOUBLE), 1, false, false},
              {Schema.create(Schema.Type.BOOLEAN), false, true, false},
              {schema, datum, false, false},


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

      Schema enumSchema = Schema.createEnum("EnumTest1", null, null, Arrays.asList("a", "b"));      GenericData.EnumSymbol enumSymbol = new GenericData.EnumSymbol(enumSchema, "1");

      Schema arraySchema = Schema.createArray(Schema.create(Schema.Type.INT));
      GenericData.Array<Integer> array = new GenericData.Array<>(8, arraySchema);
      array.add(0, 1);

      Schema mapSchema1 = Schema.createMap(Schema.create(Schema.Type.STRING));
      Map<String, String> map1 = new HashMap<>();
      map1.put("key", "v");

      Schema fixedSchema1 = Schema.createFixed(null, null, null, 16);


      Map<String, String> map2 = new HashMap<>();
      map2.put("key1", "v1");
      map2.put("key2", null);

      Map<String, String> map3 = new HashMap<>();
      map3.put("key1", "v1");
      map3.put("key2", "v2");

      return Arrays.asList(new Object[][]{

          {null, Schema.create(Schema.Type.NULL),false},
          {"test", Schema.create(Schema.Type.STRING),false},
          {ByteBuffer.allocate(1), Schema.create(Schema.Type.BYTES), false},
          {1, Schema.create(Schema.Type.INT), false},
          {1L, Schema.create(Schema.Type.LONG), false},
          {1.1f, Schema.create(Schema.Type.FLOAT), false},
          {1.1, Schema.create(Schema.Type.DOUBLE), false},
          {record, recordSchema, false},
          //{new GenericData.EnumSymbol(enumSchema, "a"), enumSchema, false}, // non supportato
          {Arrays.asList(0, 1), arraySchema, false},
          {map1, mapSchema1, false},
          {new GenericData.Fixed(fixedSchema1), fixedSchema1, false},
          {true, Schema.create(Schema.Type.BOOLEAN), false},


          // Improvements
          {new GenericData(), Schema.create(Schema.Type.NULL), true},
          {Arrays.asList(1, null), null,true},
          {array, arraySchema, false},
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



  @RunWith(Parameterized.class)
  public static class TestResolveUnion {
    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
      Schema unionSchema = Schema.createUnion(Schema.create(Schema.Type.INT), Schema.create(Schema.Type.STRING));
      Schema intDate = SchemaBuilder.builder().intType();
      intDate.addProp("logicalType", "date");
      Schema stringUuid = new Schema.Parser().parse("{\"type\":\"string\",\"logicalType\":\"uuid\"}");
      Schema unionSchema2 = Schema.createUnion(Schema.create(Schema.Type.INT), stringUuid);
      Schema unionSchema3 = Schema.createUnion(intDate, stringUuid);
      Schema unionSchema4 = Schema.createUnion();
      Schema unionSchema5 = Schema.createUnion(stringUuid);

      return Arrays.asList(new Object[][]{
          {null, null, -1, true},
          {Schema.create(Schema.Type.LONG), 1L, -1,true},
          {unionSchema, 1, 0, false},
          {null, "value", -1,true},
          // Jacoco
          {unionSchema, new UUID(1, 1), -1,true},
          {unionSchema2, new UUID(1, 1), 1,false},
          {unionSchema3, new UUID(1, 1), 1,false},
          // badua
          {unionSchema4, new UUID(1, 1), -1,true},
          {unionSchema5, new UUID(1, 1), 0,false},
          // Pit
          {unionSchema, "value", 1,false},



      });
    }

    private final Schema union;
    private final Object datum;
    private final int expected;
    private final boolean exception;

    public TestResolveUnion(Schema union, Object datum, int expected, boolean exception) {
      this.union = union;
      this.datum = datum;
      this.expected = expected;
      this.exception = exception;
    }

    @Before
    public void setup() {
      GenericData.get().addLogicalTypeConversion(new Conversions.UUIDConversion());
    }

    @Test
    public void resolveUnionTest() {
      try {
        int result = GenericData.get().resolveUnion(union, datum);
        if(exception){
          Assert.fail();
        }else {
          Assert.assertEquals(expected, result);
        }

      } catch (Exception ignored) {
        if(!exception){
          Assert.fail();
        }
      }
    }
  }

  @RunWith(Parameterized.class)
  public static class TestCompare {
    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
      Schema recordSchema = Schema.createRecord("Record1", null, null, false, Arrays.asList(
          new Schema.Field("a", Schema.create(Schema.Type.INT), null, 0, Schema.Field.Order.IGNORE),
          new Schema.Field("b", Schema.create(Schema.Type.FLOAT), null, 1.1f, Schema.Field.Order.DESCENDING)
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
      GenericData.Record record2 = new GenericRecordBuilder(recordSchema).set("a", 1).set("b", 2.2f).build();
      GenericData.Record record3 = new GenericRecordBuilder(recordSchema).set("a", 0).set("b", 1.1f).build();
      GenericData.Record record4 = new GenericRecordBuilder(recordSchema2).build();
      GenericData.Record record5 = new GenericRecordBuilder(recordSchema2).set("a", 1).build();
      return Arrays.asList(new Object[][]{
          //{null,null,null,true,-2,true},//non controlla se lo schema è valido
          //{1,0,Schema.create(Schema.Type.NULL),false,-2,true}// non controlla se gli oggetti sono compatibili con lo schema
          {"a","a",Schema.create(Schema.Type.STRING),true,0,false},
          //{1,2,Schema.create(Schema.Type.BYTES),false,-2,true},
          {1,0,Schema.create(Schema.Type.INT),true,1,false},
          //{1,1,Schema.create(Schema.Type.LONG),false,-2,true},
          {1.1f,2.2f,Schema.create(Schema.Type.FLOAT),true,-1,false},
          //{"ba","a",Schema.create(Schema.Type.DOUBLE),false,-2,true},
          {record1,record1,recordSchema,true,0,false},
          {1,2,enumSchema,false,-2,true},
          {Arrays.asList(1),Arrays.asList(0),arraySchema,true,1,false},
          //{"a","a",mapSchema,false,-2,true},
          {1,2,unionSchema,true,-1,false},
          //{1,0,fixedSchema,false,-2,true},
          {true,true,Schema.create(Schema.Type.BOOLEAN),true,0,false},


          {"a", null, Schema.create(Schema.Type.NULL), true, 0,false},
          {"a", "ab", Schema.create(Schema.Type.STRING), true, -1,false},
          {new Utf8("a"), new Utf8("ab"), Schema.create(Schema.Type.STRING), true, -1,false},
          {"a", 1, unionSchema, true, 1,false},
          {"a", "ab", unionSchema, true, -1,false},
          {map1, map2, mapSchema, true, 1,false},
          {map1, map2, mapSchema, false, -2,true},
          {Arrays.asList(1, 2), Arrays.asList(3, 4), arraySchema, true, -1,false},
          {Arrays.asList(1, 1), Collections.singletonList(1), arraySchema, true, 1,false},
          {Collections.singletonList(1), Arrays.asList(1, 1), arraySchema, true, -1,false},
          {Collections.singletonList(1), Collections.singletonList(1), arraySchema, true, 0,false},
          {record1, record2, recordSchema, true, 1,false},
          {record2, record1, recordSchema, true, -1,false},
          {record1, record3, recordSchema, true, 0,false},
          {record4, record5, recordSchema2, true, -1,false},
          {new GenericData.EnumSymbol(enumSchema, "a"), new GenericData.EnumSymbol(enumSchema, "b"), enumSchema, true, -1,false},
          {new GenericData.EnumSymbol(enumSchema, "b"), new GenericData.EnumSymbol(enumSchema, "a"), enumSchema, true, 1,false},






      });
    }

    private final Object o1;
    private final Object o2;
    private final Schema s;
    private final boolean equals;
    private final int expected;
    private final boolean exception;

    public TestCompare(Object o1, Object o2, Schema s, boolean equals, int expected, boolean exception) {
      this.o1 = o1;
      this.o2 = o2;
      this.s = s;
      this.equals = equals;
      this.expected = expected;
      this.exception = exception;
    }

    @Test
    public void compareTest() {
      try {
        int result = GenericData.get().compare(o1, o2, s, equals);
        if(exception){
          Assert.fail();
        }else {
          Assert.assertEquals(expected, result);
        }

      } catch (Exception ignored) {
        if (!exception){
          Assert.fail();
        }
      }
    }
  }

}
