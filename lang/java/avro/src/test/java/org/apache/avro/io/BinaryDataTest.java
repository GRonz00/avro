package org.apache.avro.io;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RunWith(Enclosed.class)
public class BinaryDataTest {
  @RunWith(Parameterized.class)
  public static class CompareTest {
    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
      List<Schema.Field> fields1 = Arrays.asList(
          new Schema.Field("a", Schema.create(Schema.Type.INT), null, null),
          new Schema.Field("b", Schema.create(Schema.Type.FLOAT), null, null)
      );
      Schema recordSchema1 = Schema.createRecord("RecordTest1", null, null, false, fields1);
      Schema enumSchema1 = Schema.createEnum("EnumTest1", null, null, Arrays.asList("a", "b"));
      Schema arraySchema1 = Schema.createArray(Schema.create(Schema.Type.INT));
      Schema mapSchema1 = Schema.createMap(Schema.create(Schema.Type.INT));
      Schema unionSchema1 = Schema.createUnion(Schema.create(Schema.Type.INT), Schema.create(Schema.Type.STRING));
      Schema fixedSchema1 = Schema.createFixed("FixedTest1", null, null, 16);
      byte[] fixedData = new byte[16];
      for (int i = 0; i < 16; i++) {
        fixedData[i] = (byte) i;
      }
      byte[] fixedData2 = new byte[16];
      for (int i = 0; i < 16; i++) {
        fixedData[i] = (byte) (i + 1);
      }
      GenericData.Fixed fixedValue = new GenericData.Fixed(fixedSchema1, fixedData);
      GenericData.Fixed fixedValue2 = new GenericData.Fixed(fixedSchema1, fixedData2);
      byte[] b_string = Utils.encodeString(Schema.create(Schema.Type.STRING), "test");
      byte[] b_string2 = Utils.encodeString(Schema.create(Schema.Type.STRING), "prova");
      byte[] b_float = Utils.encode_float(new ArrayList<>(Arrays.asList(3.14f, 2.15f, 6.13f)));
      byte[] b_float2 = Utils.encode_float(new ArrayList<>(Arrays.asList(4.14f, 2.15f, 6.13f, 33.0f)));
      byte[] b_int = Utils.encode_int(new ArrayList<>(Arrays.asList(1, 2, 3)));
      byte[] b_long = Utils.encode_long(new ArrayList<>(Arrays.asList(1L, 2L, 3L)));
      byte[] b_long2 = Utils.encode_long(new ArrayList<>(Arrays.asList(2L, 2L, 3L, 4L)));
      byte[] b_double = Utils.encode_double(new ArrayList<>(Arrays.asList(1d, 2d, 3d)));
      byte[] b_double2 = Utils.encode_double(new ArrayList<>(Collections.singletonList(7d)));
      byte[] b_boolean = Utils.encode_boolean(new ArrayList<>(Arrays.asList(false, false, true)));
      byte[] b_boolean2 = Utils.encode_boolean(new ArrayList<>(Arrays.asList(true, false, true, false)));
      byte[] b_record, b_union, b_union2, b_enum, b_array, b1, b2, b3, b4, b5, b_array1, b_array2, b_array3, b_array5, b_fixed, b_fixed2;
      try {
        b_record = Utils.encodeRecord("pippo", 10);
        b_enum = Utils.encodeEnum("a");
        b_fixed = Utils.encodeFixed(fixedValue, fixedSchema1);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      //jacoco improvement
      Schema.Field nameField = new Schema.Field("name", Schema.create(Schema.Type.STRING), "User name", null, Schema.Field.Order.ASCENDING);
      Schema.Field ageField = new Schema.Field("age", Schema.create(Schema.Type.INT), "User age", null, Schema.Field.Order.IGNORE);
      Schema.Field matField = new Schema.Field("mat", Schema.create(Schema.Type.STRING), "User mat", null, Schema.Field.Order.DESCENDING);
      Schema.Field field1 = new Schema.Field("field1", Schema.createFixed("FixedTest2", null, null, 4), "field1", null);
      Schema.Field field2 = new Schema.Field("field2", Schema.create(Schema.Type.STRING), "field2", null, Schema.Field.Order.IGNORE);
      Schema.Field field3 = new Schema.Field("field3", Schema.create(Schema.Type.STRING), "field3", null);
      Schema.Field field4 = new Schema.Field("field4", Schema.create(Schema.Type.STRING), "field4", null, Schema.Field.Order.IGNORE);

      Schema schemaIgnOrd = Schema.createRecord("User", null, null, false, Arrays.asList(nameField, ageField));
      Schema schemaIgnOrd2 = Schema.createRecord("matr", null, null, false, Collections.singletonList(matField));
      Schema schemaIgnOrd3 = Schema.createRecord("field", null, null, false, Arrays.asList(field1, field2, field3, field4));


      GenericRecord record1 = new GenericData.Record(schemaIgnOrd);
      record1.put("name", "test");
      record1.put("age", 1);
      GenericRecord record2 = new GenericData.Record(schemaIgnOrd);
      record2.put("name", "prova");
      record2.put("age", 23);
      GenericRecord record3 = new GenericData.Record(schemaIgnOrd2);
      record3.put("mat", "123");
      GenericRecord record4 = new GenericData.Record(schemaIgnOrd2);
      record4.put("mat", "4567");
      GenericRecord record5 = new GenericData.Record(schemaIgnOrd3);
      record5.put("field1", new GenericData.Fixed(schemaIgnOrd3.getField("field1").schema(), new byte[]{0x01, 0x02, 0x03, 0x04}));
      record5.put("field2", "5678");
      record5.put("field3", "4567");
      record5.put("field4", "47");


      try {
        b1 = Utils.encodeRecordOrd(record1, schemaIgnOrd);
        b2 = Utils.encodeRecordOrd(record2, schemaIgnOrd);
        b3 = Utils.encodeRecordOrd(record3, schemaIgnOrd2);
        b4 = Utils.encodeRecordOrd(record4, schemaIgnOrd2);
        b5 = Utils.encodeRecordOrd(record5, schemaIgnOrd3);
        b_array = Utils.encodeArray(Arrays.asList(1, 2, 3), arraySchema1);
        b_array1 = Utils.encodeArray(Arrays.asList(1, 2, 3, 4), arraySchema1);
        b_array3 = Utils.encodeArray(Arrays.asList(7, 8, 9), arraySchema1);
        b_array2 = new byte[]{3, 7, 5, 0};
        b_array5 = new byte[]{};
        b_union = Utils.encodeUnion("test", unionSchema1);
        b_union2 = Utils.encodeUnion(2, unionSchema1);
        b_fixed2 = Utils.encodeFixed(fixedValue2, fixedSchema1);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }


      return Arrays.asList(
          new Object[][]{

              //{null,null,-1,-1,null,-1,-1,-1,true},
              {Schema.create(Schema.Type.NULL), new byte[1], 0, 0, new byte[1], 0, 0, 0, false},

              {Schema.create(Schema.Type.STRING), b_string, b_string.length, 1, b_string, b_string.length, 1, -1, true},
              {Schema.create(Schema.Type.BYTES), b_string, -1, b_string.length, b_string2, -1, b_string2.length, -1, true},
              {Schema.create(Schema.Type.INT), null, 0, -1, b_string, 0, -1, -1, true},
              {Schema.create(Schema.Type.LONG), new byte[1], 1, 0, null, 1, 0, -1, true},
              {Schema.create(Schema.Type.FLOAT), b_float, -1, 1, new byte[1], -1, 1, -1, true},
              {Schema.create(Schema.Type.DOUBLE), b_string, 0, b_string.length, b_string, 0, b_string.length, -1, true},
              {Schema.create(Schema.Type.BOOLEAN), null, 0, -1, null, 0, -1, -1, true},
              {recordSchema1, new byte[1], -1, 0, b_record, -1, 0, -1, true},
              {enumSchema1, b_enum, 0, 1, null, 0, 1, -1, true},
              {arraySchema1, b_string, b_string.length, b_string.length, new byte[1], 0, 0, -1, true},
              {mapSchema1, null, -1, -1, null, -1, -1, -1, true},
              {unionSchema1, new byte[1], 0, 0, b_union, 0, 0, -1, true},

              //jacoco improvement
              //uguali
              {Schema.create(Schema.Type.STRING), b_string, 0, b_string.length, b_string, 0, b_string.length, 0, false},
              {Schema.create(Schema.Type.BYTES), b_string, 0, b_string.length, b_string, 0, b_string.length, 0, false},
              {Schema.create(Schema.Type.INT), b_int, 0, b_int.length, b_int, 0, b_int.length, 0, false},
              {Schema.create(Schema.Type.LONG), b_long, 0, b_long.length, b_long, 0, b_long.length, 0, false},
              {Schema.create(Schema.Type.FLOAT), b_float, 0, b_float.length, b_float, 0, b_float.length, 0, false},
              {Schema.create(Schema.Type.DOUBLE), b_double, 0, b_double.length, b_double, 0, b_double.length, 0, false},
              {Schema.create(Schema.Type.BOOLEAN), b_boolean, 0, b_boolean.length, b_boolean, 0, b_boolean.length, 0, false},
              {enumSchema1, b_enum, 0, b_enum.length, b_enum, 0, b_enum.length, 0, false},
              {arraySchema1, b_array, 0, b_array.length, b_array, 0, b_array.length, 0, false},
              {unionSchema1, b_union, 0, b_union.length, b_union, 0, b_union.length, 0, false},

              {schemaIgnOrd, b1, 0, b1.length, b1, 0, b1.length, 0, false},
              {fixedSchema1, b_fixed, 0, b_fixed.length, b_fixed, 0, b_fixed.length, 0, false},
              //diversi
              {arraySchema1, b_array, 0, b_array.length, b_array1, 0, b_array1.length, -1, false},
              {arraySchema1, b_array1, 0, b_array1.length, b_array, 0, b_array.length, 1, false},
              {arraySchema1, b_array1, 0, b_array1.length, b_array2, 0, b_array2.length, 1, false},
              {arraySchema1, b_array2, 0, b_array2.length, b_array1, 0, b_array1.length, -1, false},
              {schemaIgnOrd, b1, 0, b1.length, b2, 0, b2.length, 4, false},
              {schemaIgnOrd, b2, 0, b2.length, b1, 0, b1.length, -4, false},
              {schemaIgnOrd2, b3, 0, b3.length, b4, 0, b4.length, 3, false},
              {unionSchema1, b_union, 0, b_union.length, b_union2, 0, b_union2.length, 1, false},
              //pit
              {arraySchema1, b_array1, 0, b_array1.length, b_array3, 0, b_array3.length, -1, false},
              {arraySchema1, b_array3, 0, b_array3.length, b_array1, 0, b_array1.length, 1, false},
              {Schema.create(Schema.Type.LONG), b_long, 0, b_long.length, b_long2, 0, b_long2.length, -1, false},
              {Schema.create(Schema.Type.FLOAT), b_float, 0, b_float.length, b_float2, 0, b_float2.length, 1, false},
              {Schema.create(Schema.Type.DOUBLE), b_double, 0, b_double.length, b_double2, 0, b_double2.length, -1, false},
              {Schema.create(Schema.Type.BOOLEAN), b_boolean, 0, b_boolean.length, b_boolean2, 0, b_boolean2.length, -1, false},
              {schemaIgnOrd3, b5, 0, b5.length, b5, 0, b5.length, 0, false},
              {fixedSchema1, b_fixed, 0, b_fixed.length, b_fixed2, 0, b_fixed2.length, 1, false},
              {fixedSchema1, b_fixed, 0, b_fixed.length, b_fixed2, 0, b_fixed2.length, 1, false},

              //<du var="schema" def="86" use="88" target="175" covered="0"/> e <du var="$SwitchMap$org$apache$avro$Schema$Type" def="86" use="88" target="175" covered="0"/> è una enum non posso provare il caso non trovato
              //<du var="i" def="115" use="138" target="146" covered="0"/> impossibile coprire si dovrebbe avere l = 0 ma se l1 o l2 è uguale a zero il metodo ritorna prima
          });
    }

    private final Schema schema;
    private final int s1;
    private final int l1;
    private final int s2;
    private final int l2;
    private final byte[] b1;
    private final byte[] b2;
    private final int expected;
    private final boolean expectedException;

    public CompareTest(Schema schema, byte[] b1, int s1, int l1, byte[] b2, int s2, int l2, int expected, boolean expectedException) {
      this.schema = schema;
      this.b1 = b1;
      this.l2 = l2;
      this.b2 = b2;
      this.l1 = l1;
      this.s2 = s2;
      this.s1 = s1;
      this.expected = expected;
      this.expectedException = expectedException;
    }

    @Test
    public void compareTest() {
      try {
        int result = BinaryData.compare(b1, s1, l1, b2, s2, l2, schema);
        if (expectedException) Assert.fail();
        Assert.assertEquals(expected, result);


      } catch (Exception ignored) {
        if (!expectedException) Assert.fail();
      }
    }
  }
  @RunWith(Parameterized.class)
  public static class EncodeTest {
    @Parameterized.Parameters
    public static Collection<Object[]> getParametersEncode() {
      return Arrays.asList(
          new Object[][]{
              {-1L, null, -1, -1, new byte[]{0},true},
              {0L, new byte[1], 0, 1, new byte[]{0},false},
              {1L, new byte[0], 0, -1,new byte[]{80,80}, true},
              {Long.MAX_VALUE, new byte[10], 0, 10,new byte[]{-2,-1,-1,-1,-1,-1,-1,-1,-1,1}, false},
              //jacoco
              {(long)Math.pow(2,6),new byte[2],0,2,new byte[]{-128,1},false},
              {(long)Math.pow(2,13),new byte[3],0,3,new byte[]{-128,-128,1},false},
              {(long)Math.pow(2,20),new byte[4],0,4,new byte[]{-128,-128,-128,1},false},
              {(long)Math.pow(2,27),new byte[5],0,5,new byte[]{-128,-128,-128,-128,1},false},
              {(long)Math.pow(2,34),new byte[6],0,6,new byte[]{-128,-128,-128,-128,-128,1},false},
              {(long)Math.pow(2,41),new byte[7],0,7,new byte[]{-128,-128,-128,-128,-128,-128,1},false},
              {(long)Math.pow(2,48),new byte[8],0,8,new byte[]{-128,-128,-128,-128,-128,-128,-128,1},false},
              {(long)Math.pow(2,55),new byte[9],0,9,new byte[]{-128,-128,-128,-128,-128,-128,-128,-128,1},false},
              {(long)Math.pow(2,62),new byte[10],0,10,new byte[]{-128,-128,-128,-128,-128,-128,-128,-128,-128,1},false},
              //pit
              {(long)Math.pow(2,13)-1,new byte[2],0,2,new byte[]{-2,127},false},
              {(long)Math.pow(2,20)-1,new byte[3],0,3,new byte[]{-2,-1,127},false},
              {(long)Math.pow(2,27)-1,new byte[4],0,4,new byte[]{-2,-1,-1,127},false},
              {(long)Math.pow(2,34)-1,new byte[5],0,5,new byte[]{-2,-1,-1,-1,127},false},
              {(long)Math.pow(2,41)-1,new byte[6],0,6,new byte[]{-2,-1,-1,-1,-1,127},false},
              {(long)Math.pow(2,48)-1,new byte[7],0,7,new byte[]{-2,-1,-1,-1,-1,-1,127},false},
              {(long)Math.pow(2,55)-1,new byte[8],0,8,new byte[]{-2,-1,-1,-1,-1,-1,-1,127},false},
              {(long)Math.pow(2,62)-1,new byte[9],0,9,new byte[]{-2,-1,-1,-1,-1,-1,-1,-1,127},false},
              //{(long)Math.pow(2,69)-1,new byte[10],0,10,new byte[]{-4,-1,-1,-1,-1,-1,-1,-1,-1,1},false},



          }
      );
    }

    private final long n;
    private final byte[] buf;
    private final int pos;
    private final boolean ecc;
    private final int expPos;
    private final byte[] expEnc;
    public EncodeTest(long n, byte[] buf, int pos,int expPos,byte[] expEnc, boolean ecc){
      this.n = n;
      this.buf = buf;
      this.pos = pos;
      this.expPos = expPos;
      this.expEnc = expEnc;
      this.ecc = ecc;
    }
    @Test
    public void encodeTest(){
      try{
        int result = BinaryData.encodeLong(n,buf,pos);
        if(ecc){
          Assert.fail();
        }
        Assert.assertEquals(expPos,result);
          Assert.assertArrayEquals(buf, expEnc);

      }catch (Exception e){
        if(!ecc){
          Assert.fail();
        }
      }
    }
  }
  @RunWith(Parameterized.class)
  public static class HashCodeTest{

  }






}


