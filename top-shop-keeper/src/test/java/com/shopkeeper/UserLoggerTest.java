package com.shopkeeper;

import junit.framework.TestCase;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-25
 * Time: 下午3:05
 * To change this template use File | Settings | File Templates.
 */
public class UserLoggerTest extends TestCase {
    public void testError() throws Exception {
        JsonFactory jsonFactory = new JsonFactory(); // or, for data binding, org.codehaus.jackson.mapper.MappingJsonFactory
        File file = new File("C:\\Users\\zhanghaojie\\Downloads\\15542872-fbf9-4ebd-9ad0-3261a6053edd\\json_1.txt");
        JsonParser jp = jsonFactory.createJsonParser(file);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> obj = null;
        int i = 0;
        do {
            obj = (Map)objectMapper.readValue(jp, Map.class);
            System.out.print(obj);
            System.out.print("\n");
            i ++;
        }while (jp.nextToken() != null);

        System.out.print("total num:" + i);
        //JsonToken token = jp.nextToken();
        //jp.nextToken(); // will return JsonToken.START_OBJECT (verify?)
        //while (jp.nextToken() != null) {
            // fieldname = jp.getCurrentName();
            //System.out.print(fieldname + ":" + jp.getText() + "\n" );
            //jp.nextToken(); // move to value, or START_OBJECT/START_ARRAY
        //}
    }
}
