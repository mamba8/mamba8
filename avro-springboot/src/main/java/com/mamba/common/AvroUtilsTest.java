package com.mamba.common;


import java.io.File;

import org.apache.avro.Schema;
import org.junit.Test;

public class AvroUtilsTest {

       private static final String schemaDescription =
         "{ \n" +
            " \"namespace\": \"com.navteq.avro\", \n" +
            " \"name\": \"FacebookUser\", \n" +
            " \"type\": \"record\",\n" +
            " \"fields\": [\n" +
                     " {\"name\": \"name\", \"type\": [\"string\", \"null\"] },\n" +
                     " {\"name\": \"num_likes\", \"type\": \"int\"},\n" +
                     " {\"name\": \"num_photos\", \"type\": \"int\"},\n" +
                     " {\"name\": \"num_groups\", \"type\": \"int\"} ]\n" +
         "}";

       private static final String schemaDescriptionExt =
         " { \n" +
             " \"namespace\": \"com.navteq.avro\", \n" +
             " \"name\": \"FacebookSpecialUser\", \n" +
             " \"type\": \"record\",\n" +
             " \"fields\": [\n" +
                      " {\"name\": \"user\", \"type\": com.navteq.avro.FacebookUser },\n" +
                      " {\"name\": \"specialData\", \"type\": \"int\"} ]\n" +
          "}";

       @Test
       public void testParseSchema() throws Exception{

    	   	   Schema extended2 =  AvroUtils.parseSchema(schemaDescription);
               System.out.println(extended2.toString(true));
               Schema extended = AvroUtils.parseSchema(schemaDescriptionExt);
               System.out.println(extended.toString(true));
       }
}