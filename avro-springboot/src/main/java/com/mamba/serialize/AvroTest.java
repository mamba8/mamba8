package com.mamba.serialize;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;

public class AvroTest {
	/**
     * copy by http://blog.kazaff.me/2015/04/30/Avro%E7%9A%84%E4%B8%89%E7%A7%8D%E5%BA%8F%E5%88%97%E5%8C%96%E4%B8%8E%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%E6%96%B9%E6%B3%95/
     * Avro的序列化方式
     * 
     * 此种方式，编码解码都需要指定schema[new GenericDatumReader<GenericRecord>(s);]
     * 需要通信的俩段必须提前通过某种交互来完成schema定义的同步工作
     * 比如：通信双方各保存此份schema定义
     * （貌似Avro自带的RPC就实现了这个细节）
     * 
     */
	public static void main(String[] args) throws IOException {
        // Schema
        String schemaDescription = " {    \n"
                + " \"name\": \"FacebookUser\", \n"
                + " \"type\": \"record\",\n" + " \"fields\": [\n"
                + "   {\"name\": \"name\", \"type\": \"string\"},\n"
                + "   {\"name\": \"num_likes\", \"type\": \"int\"},\n"
                + "   {\"name\": \"num_photos\", \"type\": \"int\"},\n"
                + "   {\"name\": \"num_groups\", \"type\": \"int\"} ]\n" + "}";

        Schema s = Schema.parse(schemaDescription);    //parse方法在当前的Avro版本下已不推荐使用
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Encoder e = EncoderFactory.get().binaryEncoder(outputStream, null);
        GenericDatumWriter w = new GenericDatumWriter(s);

        // Populate data
        GenericRecord r = new GenericData.Record(s);
        r.put("name", new org.apache.avro.util.Utf8("kazaff"));
        r.put("num_likes", 1);
        r.put("num_groups", 423);
        r.put("num_photos", 0);

        // Encode
        w.write(r, e);
        e.flush();

        byte[] encodedByteArray = outputStream.toByteArray();
        String encodedString = outputStream.toString();

        System.out.println("encodedString: "+encodedString);

        // Decode using same schema
        DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(s);
        Decoder decoder = DecoderFactory.get().binaryDecoder(encodedByteArray, null);
        GenericRecord result = reader.read(null, decoder);
        System.out.println(result.get("name").toString());
        System.out.println(result.get("num_likes").toString());
        System.out.println(result.get("num_groups").toString());
        System.out.println(result.get("num_photos").toString());

    }
}