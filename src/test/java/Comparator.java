import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//import com.fasterxml.jackson.databind.ObjectMapper;

public class Comparator {

    private static String response;

    public static boolean compare() throws IOException {

        try {
            Scanner sc = new Scanner(new File(".\\src\\test\\resources\\File1"));
            Scanner sc2 = new Scanner(new File(".\\src\\test\\resources\\File2"));

            while(sc.hasNext() && sc2.hasNext()){
                String line = sc.next();
                String line2 = sc2.next();
                //System.out.println(line);
                RestAssured.proxy("10.65.128.43",8080);
                String response = RestAssured.get(line).asString();
                String response2 = RestAssured.get(line2).asString();

                System.out.println(response);
                //System.out.println(response2);

                ObjectMapper mapper = new ObjectMapper();
                //String jsonInput = "{\"key\": \"value\"}";
                TypeReference<HashMap<Object, Object>> typeRef
                        = new TypeReference<HashMap<Object, Object>>() {};
                Map<Object, Object> map = mapper.readValue(response, typeRef);

                System.out.println(map.toString());


                if(response.equals(response2))
                    System.out.println(line +" is equal to " + line2);
                else
                    System.out.println(line + " is not equal to " + line2);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args){
        try {
            boolean test = compare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
