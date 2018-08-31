import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import junit.framework.AssertionFailedError;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;


public class APICompare {

    public static void compareAPIResponse() throws IOException {
        try {
            Scanner sc1 = new Scanner(new File(".\\src\\test\\resources\\File1"));
            Scanner sc2 = new Scanner(new File(".\\src\\test\\resources\\File2"));

            while (sc1.hasNext() && sc2.hasNext()) {
                String line1 = sc1.next();
                String line2 = sc2.next();

                //RestAssured.proxy("10.65.128.43", 8080);

                String response1 = RestAssured.get(line1).asString();
                String response2 = RestAssured.get(line2).asString();

                Response resp1 = RestAssured.get(line1);
                Response resp2 = RestAssured.get(line2);

                String contentType1 = resp1.header("Content-Type");
                String contentType2 = resp2.header("Content-Type");

                //checking if API response is in JSON format
                if (contentType1.contains("json") && contentType2.contains("json")) {
                    ObjectMapper om = new ObjectMapper();
                    try {
                        Map<String, Object> m1 = (Map<String, Object>) (om.readValue(response1, Map.class));
                        Map<String, Object> m2 = (Map<String, Object>) (om.readValue(response2, Map.class));
                        if(m1.equals(m2)){
                            System.out.println(line1 +" equals to "+line2);;
                        }else
                            System.out.println(line1 +" doesn't equal to "+line2);
                    } catch (JsonParseException | JsonMappingException e) {
                        e.printStackTrace();
                    }
                    //checking if API response is in JSON format
                } else if (contentType1.contains("xml") && contentType2.contains("xml")) {
                    try {
                        if(compareXml(response1, response2)){
                            System.out.println(line1 +" equals to "+line2);;
                        }else
                            System.out.println(line1 +" doesn't equal to "+line2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //checking if API response is in String or AnyOther format. Comparing as String.
                } else {
                    if(response1.equals(response2)){
                        System.out.println(line1 +" equals to "+line2);;
                    }else
                        System.out.println(line1 +" doesn't equal to "+line2);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This function compares XML document as String
    public static boolean compareXml(String xml1, String xml2) {
        XMLUnit.setIgnoreWhitespace(true);
        boolean isMatch = false;
        try {
            new XmlComparator().assertXMLEqual(xml1, xml2);
            isMatch=true;
        } catch (SAXException e) {
            e.printStackTrace();
            isMatch=false;
        } catch (IOException e) {
            e.printStackTrace();
            isMatch=false;
        }
        catch (AssertionFailedError e) {
            return false;
        }
        return isMatch;
    }

    public static void main(String... args)
    {
        try {
            compareAPIResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
