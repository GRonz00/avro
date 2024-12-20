package org.apache.avro.io;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDataTest;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
  public static byte[] encode_int(ArrayList<Integer> int_list){
    byte[] b_int = new byte[20];
    int n;
    for(int i=0;i<int_list.size();i++ ){
      BinaryData.encodeInt(int_list.get(i),b_int,i);
    }
    return b_int;
  }
  public static byte[] encode_float(ArrayList<Float> floats){
    byte[] b = new byte[20];
    int n;
    for(int i=0;i<floats.size();i++ ){
      BinaryData.encodeFloat(floats.get(i),b,i);
    }
    return b;
  }
  public static byte[] encode_long(ArrayList<Long> longs){
    byte[] b = new byte[20];
    int n;
    for(int i=0;i<longs.size();i++ ){
      BinaryData.encodeLong(longs.get(i),b,i);
    }
    return b;
  }
  public static byte[] encode_boolean(ArrayList<Boolean> booleans){
    byte[] b = new byte[20];
    int n;
    for(int i=0;i<booleans.size();i++ ){
      BinaryData.encodeBoolean(booleans.get(i),b,i);
    }
    return b;
  }
  public static byte[] encode_double(ArrayList<Double> doubles){
    byte[] b = new byte[20];
    int n;
    for(int i=0;i<doubles.size();i++ ){
      BinaryData.encodeDouble(doubles.get(i),b,i);
    }
    return b;
  }
  public static byte[] encodeRecord(String name, int age) throws IOException {
    String schemaString = "{"
        + "\"type\":\"record\","
        + "\"name\":\"User\","
        + "\"fields\":["
        + "{\"name\":\"name\", \"type\":\"string\"},"
        + "{\"name\":\"age\", \"type\":\"int\"}"
        + "]}";
    Schema schema = new Schema.Parser().parse(schemaString);
    GenericRecord record = new GenericData.Record(schema);
    record.put("name", name);
    record.put("age", age);
    DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Encoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
    writer.write(record, encoder);
    encoder.flush();

    return outputStream.toByteArray();
  }
  public static byte[] encodeString(Schema schema, String s){
    GenericDatumWriter<String> writer = new GenericDatumWriter<>(schema);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Encoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
    try {
      writer.write(s, encoder);
      encoder.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return outputStream.toByteArray();
  }
  public static byte[] encodeEnum(String value) throws IOException {
    String schemaString = "{"
        + "\"type\":\"enum\","
        + "\"name\":\"Suit\","
        + "\"symbols\":[\"a\", \"b\", \"c\", \"d\"]"
        + "}";
    Schema schema = new Schema.Parser().parse(schemaString);
    GenericData.EnumSymbol enumValue = new GenericData.EnumSymbol(schema, value);
    DatumWriter<GenericData.EnumSymbol> writer = new GenericDatumWriter<>(schema);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Encoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
    writer.write(enumValue, encoder);
    encoder.flush();

    return outputStream.toByteArray();
  }
  public static byte[] encodeUnion(Object object, Schema schema) throws  IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
    DatumWriter<Object> writer = new GenericDatumWriter<>(schema);

    try {
      writer.write(object, encoder);
      encoder.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
      return out.toByteArray();
    }
  public static byte[] encodeFixed(GenericData.Fixed fixed, Schema schema) throws  IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
    DatumWriter<Object> writer = new GenericDatumWriter<>(schema);

    try {
      writer.write(fixed, encoder);
      encoder.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return out.toByteArray();
  }


  public static byte[] encodeRecordOrd(GenericRecord record, Schema schema) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
    DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);

    try {
      writer.write(record, encoder);
      encoder.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
   return out.toByteArray();
  }
  public static byte[] encodeArray(List<Integer> list, Schema schema) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
    DatumWriter<List<Integer>> writer = new GenericDatumWriter<>(schema);

    try {
      writer.write(list, encoder);
      encoder.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return out.toByteArray();
  }

}
