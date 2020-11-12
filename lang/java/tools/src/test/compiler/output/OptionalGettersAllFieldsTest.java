/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package avro.examples.baseball;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;
import java.util.Optional;
/** Test that optional getters are created for all fields */
@org.apache.avro.specific.AvroGenerated
public class OptionalGettersAllFieldsTest extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 874861432798554536L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"OptionalGettersAllFieldsTest\",\"namespace\":\"avro.examples.baseball\",\"doc\":\"Test that optional getters are created for all fields\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"nullable_name\",\"type\":[\"string\",\"null\"]},{\"name\":\"favorite_number\",\"type\":[\"int\"]},{\"name\":\"nullable_favorite_number\",\"type\":[\"int\",\"null\"]}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<OptionalGettersAllFieldsTest> ENCODER =
      new BinaryMessageEncoder<OptionalGettersAllFieldsTest>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<OptionalGettersAllFieldsTest> DECODER =
      new BinaryMessageDecoder<OptionalGettersAllFieldsTest>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<OptionalGettersAllFieldsTest> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<OptionalGettersAllFieldsTest> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<OptionalGettersAllFieldsTest> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<OptionalGettersAllFieldsTest>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this OptionalGettersAllFieldsTest to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a OptionalGettersAllFieldsTest from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a OptionalGettersAllFieldsTest instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static OptionalGettersAllFieldsTest fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.CharSequence name;
   private java.lang.CharSequence nullable_name;
   private java.lang.Object favorite_number;
   private java.lang.Integer nullable_favorite_number;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public OptionalGettersAllFieldsTest() {}

  /**
   * All-args constructor.
   * @param name The new value for name
   * @param nullable_name The new value for nullable_name
   * @param favorite_number The new value for favorite_number
   * @param nullable_favorite_number The new value for nullable_favorite_number
   */
  public OptionalGettersAllFieldsTest(java.lang.CharSequence name, java.lang.CharSequence nullable_name, java.lang.Object favorite_number, java.lang.Integer nullable_favorite_number) {
    this.name = name;
    this.nullable_name = nullable_name;
    this.favorite_number = favorite_number;
    this.nullable_favorite_number = nullable_favorite_number;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return name;
    case 1: return nullable_name;
    case 2: return favorite_number;
    case 3: return nullable_favorite_number;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: name = (java.lang.CharSequence)value$; break;
    case 1: nullable_name = (java.lang.CharSequence)value$; break;
    case 2: favorite_number = value$; break;
    case 3: nullable_favorite_number = (java.lang.Integer)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'name' field as an Optional&lt;java.lang.CharSequence&gt;.
   * @return The value wrapped in an Optional&lt;java.lang.CharSequence&gt;.
   */
  public Optional<java.lang.CharSequence> getName() {
    return Optional.<java.lang.CharSequence>ofNullable(name);
  }


  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.CharSequence value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'nullable_name' field as an Optional&lt;java.lang.CharSequence&gt;.
   * @return The value wrapped in an Optional&lt;java.lang.CharSequence&gt;.
   */
  public Optional<java.lang.CharSequence> getNullableName() {
    return Optional.<java.lang.CharSequence>ofNullable(nullable_name);
  }


  /**
   * Sets the value of the 'nullable_name' field.
   * @param value the value to set.
   */
  public void setNullableName(java.lang.CharSequence value) {
    this.nullable_name = value;
  }

  /**
   * Gets the value of the 'favorite_number' field as an Optional&lt;java.lang.Object&gt;.
   * @return The value wrapped in an Optional&lt;java.lang.Object&gt;.
   */
  public Optional<java.lang.Object> getFavoriteNumber() {
    return Optional.<java.lang.Object>ofNullable(favorite_number);
  }


  /**
   * Sets the value of the 'favorite_number' field.
   * @param value the value to set.
   */
  public void setFavoriteNumber(java.lang.Object value) {
    this.favorite_number = value;
  }

  /**
   * Gets the value of the 'nullable_favorite_number' field as an Optional&lt;java.lang.Integer&gt;.
   * @return The value wrapped in an Optional&lt;java.lang.Integer&gt;.
   */
  public Optional<java.lang.Integer> getNullableFavoriteNumber() {
    return Optional.<java.lang.Integer>ofNullable(nullable_favorite_number);
  }


  /**
   * Sets the value of the 'nullable_favorite_number' field.
   * @param value the value to set.
   */
  public void setNullableFavoriteNumber(java.lang.Integer value) {
    this.nullable_favorite_number = value;
  }

  /**
   * Creates a new OptionalGettersAllFieldsTest RecordBuilder.
   * @return A new OptionalGettersAllFieldsTest RecordBuilder
   */
  public static avro.examples.baseball.OptionalGettersAllFieldsTest.Builder newBuilder() {
    return new avro.examples.baseball.OptionalGettersAllFieldsTest.Builder();
  }

  /**
   * Creates a new OptionalGettersAllFieldsTest RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new OptionalGettersAllFieldsTest RecordBuilder
   */
  public static avro.examples.baseball.OptionalGettersAllFieldsTest.Builder newBuilder(avro.examples.baseball.OptionalGettersAllFieldsTest.Builder other) {
    if (other == null) {
      return new avro.examples.baseball.OptionalGettersAllFieldsTest.Builder();
    } else {
      return new avro.examples.baseball.OptionalGettersAllFieldsTest.Builder(other);
    }
  }

  /**
   * Creates a new OptionalGettersAllFieldsTest RecordBuilder by copying an existing OptionalGettersAllFieldsTest instance.
   * @param other The existing instance to copy.
   * @return A new OptionalGettersAllFieldsTest RecordBuilder
   */
  public static avro.examples.baseball.OptionalGettersAllFieldsTest.Builder newBuilder(avro.examples.baseball.OptionalGettersAllFieldsTest other) {
    if (other == null) {
      return new avro.examples.baseball.OptionalGettersAllFieldsTest.Builder();
    } else {
      return new avro.examples.baseball.OptionalGettersAllFieldsTest.Builder(other);
    }
  }

  /**
   * RecordBuilder for OptionalGettersAllFieldsTest instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<OptionalGettersAllFieldsTest>
    implements org.apache.avro.data.RecordBuilder<OptionalGettersAllFieldsTest> {

    private java.lang.CharSequence name;
    private java.lang.CharSequence nullable_name;
    private java.lang.Object favorite_number;
    private java.lang.Integer nullable_favorite_number;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(avro.examples.baseball.OptionalGettersAllFieldsTest.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.nullable_name)) {
        this.nullable_name = data().deepCopy(fields()[1].schema(), other.nullable_name);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.favorite_number)) {
        this.favorite_number = data().deepCopy(fields()[2].schema(), other.favorite_number);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.nullable_favorite_number)) {
        this.nullable_favorite_number = data().deepCopy(fields()[3].schema(), other.nullable_favorite_number);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
    }

    /**
     * Creates a Builder by copying an existing OptionalGettersAllFieldsTest instance
     * @param other The existing instance to copy.
     */
    private Builder(avro.examples.baseball.OptionalGettersAllFieldsTest other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.nullable_name)) {
        this.nullable_name = data().deepCopy(fields()[1].schema(), other.nullable_name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.favorite_number)) {
        this.favorite_number = data().deepCopy(fields()[2].schema(), other.favorite_number);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.nullable_favorite_number)) {
        this.nullable_favorite_number = data().deepCopy(fields()[3].schema(), other.nullable_favorite_number);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'name' field.
      * @return The value.
      */
    public java.lang.CharSequence getName() {
      return name;
    }


    /**
      * Sets the value of the 'name' field.
      * @param value The value of 'name'.
      * @return This builder.
      */
    public avro.examples.baseball.OptionalGettersAllFieldsTest.Builder setName(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.name = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'name' field has been set.
      * @return True if the 'name' field has been set, false otherwise.
      */
    public boolean hasName() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'name' field.
      * @return This builder.
      */
    public avro.examples.baseball.OptionalGettersAllFieldsTest.Builder clearName() {
      name = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'nullable_name' field.
      * @return The value.
      */
    public java.lang.CharSequence getNullableName() {
      return nullable_name;
    }


    /**
      * Sets the value of the 'nullable_name' field.
      * @param value The value of 'nullable_name'.
      * @return This builder.
      */
    public avro.examples.baseball.OptionalGettersAllFieldsTest.Builder setNullableName(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.nullable_name = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'nullable_name' field has been set.
      * @return True if the 'nullable_name' field has been set, false otherwise.
      */
    public boolean hasNullableName() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'nullable_name' field.
      * @return This builder.
      */
    public avro.examples.baseball.OptionalGettersAllFieldsTest.Builder clearNullableName() {
      nullable_name = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'favorite_number' field.
      * @return The value.
      */
    public java.lang.Object getFavoriteNumber() {
      return favorite_number;
    }


    /**
      * Sets the value of the 'favorite_number' field.
      * @param value The value of 'favorite_number'.
      * @return This builder.
      */
    public avro.examples.baseball.OptionalGettersAllFieldsTest.Builder setFavoriteNumber(java.lang.Object value) {
      validate(fields()[2], value);
      this.favorite_number = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'favorite_number' field has been set.
      * @return True if the 'favorite_number' field has been set, false otherwise.
      */
    public boolean hasFavoriteNumber() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'favorite_number' field.
      * @return This builder.
      */
    public avro.examples.baseball.OptionalGettersAllFieldsTest.Builder clearFavoriteNumber() {
      favorite_number = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'nullable_favorite_number' field.
      * @return The value.
      */
    public java.lang.Integer getNullableFavoriteNumber() {
      return nullable_favorite_number;
    }


    /**
      * Sets the value of the 'nullable_favorite_number' field.
      * @param value The value of 'nullable_favorite_number'.
      * @return This builder.
      */
    public avro.examples.baseball.OptionalGettersAllFieldsTest.Builder setNullableFavoriteNumber(java.lang.Integer value) {
      validate(fields()[3], value);
      this.nullable_favorite_number = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'nullable_favorite_number' field has been set.
      * @return True if the 'nullable_favorite_number' field has been set, false otherwise.
      */
    public boolean hasNullableFavoriteNumber() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'nullable_favorite_number' field.
      * @return This builder.
      */
    public avro.examples.baseball.OptionalGettersAllFieldsTest.Builder clearNullableFavoriteNumber() {
      nullable_favorite_number = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OptionalGettersAllFieldsTest build() {
      try {
        OptionalGettersAllFieldsTest record = new OptionalGettersAllFieldsTest();
        record.name = fieldSetFlags()[0] ? this.name : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.nullable_name = fieldSetFlags()[1] ? this.nullable_name : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.favorite_number = fieldSetFlags()[2] ? this.favorite_number :  defaultValue(fields()[2]);
        record.nullable_favorite_number = fieldSetFlags()[3] ? this.nullable_favorite_number : (java.lang.Integer) defaultValue(fields()[3]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<OptionalGettersAllFieldsTest>
    WRITER$ = (org.apache.avro.io.DatumWriter<OptionalGettersAllFieldsTest>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<OptionalGettersAllFieldsTest>
    READER$ = (org.apache.avro.io.DatumReader<OptionalGettersAllFieldsTest>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}









