import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class RegistrantAlertClassic
{
    public static void main(String[]args) throws IOException
    {
        String API_URL =
            "https://www.whoisxmlapi.com/registrant-alert-api/search.php";

        String username = "Your registrant alert api username";
        String password = "your registrant alert api password";

        String term1 = "test";
        String rows = "100";

        CloseableHttpClient httpclient = null;
        try {

            String url = API_URL
                       + "?username=" + URLEncoder.encode(username, "UTF-8")
                       + "&password=" + URLEncoder.encode(password, "UTF-8")
                       + "&term1=" + URLEncoder.encode(term1, "UTF-8")
                       + "&rows=" + URLEncoder.encode(rows, "UTF-8")
                       + "&output_format=xml";

            httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request " + httpget.getURI());

            // Create a response handler
            ResponseHandler<String> responseHandler =
                new BasicResponseHandler();

            String responseBody = httpclient.execute(httpget,responseHandler);
            System.out.println(responseBody);
            System.out.println("----------------------------------------");

            // Parse
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(responseBody));

            Document doc = db.parse(is);
            String content = doc.getDocumentElement().getNodeName();

            System.out.println("Root element " + content);

        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally{
            if (httpclient != null)
                httpclient.close();
        }
    }
}