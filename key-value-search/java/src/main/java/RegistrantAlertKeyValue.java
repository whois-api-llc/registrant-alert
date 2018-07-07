import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.*;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class RegistrantAlertKeyValue
{
    private final String USER_AGENT = "Mozilla/5.0";

    // Use getter and setter functions for accessing username and password
    protected String username;
    protected String password;
    public final String url =
        "https://www.whoisxmlapi.com/registrant-alert-api/search.php";

    public static void main(String[] args) throws Exception
    {
        RegistrantAlertKeyValue query = new RegistrantAlertKeyValue();

        // Fill in your details
        query.setUsername("Your registrant alert username");
        query.setPassword("Your registrant alert password");

        // Send POST request
        String responseStringPost= query.sendPost();
        query.printPrettyJson(responseStringPost);

        // Send GET request
        String responseStringGet = query.sendGet();
        query.printPrettyJson(responseStringGet);
    }

    // HTTP POST request
    protected String sendPost() throws Exception
    {
        URL obj = new URL(this.url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);

        String searchTerm1 =
            "\"section\": \"Registrant\",  \"attribute\": \"name\","
            + "\"value\": \"J\", \"matchType\": \"anywhere\","
            + "\"exclude\": \"false\"";

        String requestOptions =
            "\"recordsCounter\": \"false\", \"rows\": 10, \"username\":"
            + this.getUsername(true) + ", \"password\":"
            + this.getPassword(true) + ", \"output_format\": \"json\"";

        String requestObject =
                "{\"terms\":[{" + searchTerm1 + "}], " + requestOptions +"}";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(requestObject);
        wr.flush();
        wr.close();

        System.out.println("\nSending 'POST' request to URL : " + url);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    protected String sendGet()
    {
        HttpClient httpclient = null;
        String responseBody = "";
        try {
            String url_api = this.url
               + "?term1=whois&search_type=current&mode=purchase&rows=5"
               + "&username=" + URLEncoder.encode(this.getUsername(),"UTF-8")
               + "&password=" + URLEncoder.encode(this.getPassword(),"UTF-8");
            
            httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url_api);
            System.out.println("executing request " + httpget.getURI());

            // Create a response handler
            ResponseHandler<String> responseHandler =
                    new BasicResponseHandler();
            responseBody = httpclient.execute(httpget, responseHandler);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally{
            if (httpclient != null)
                httpclient.getConnectionManager().shutdown();
        }
        return responseBody;
    }

    public void setUsername(String login)
    {
        this.username = login;
    }

    public void setPassword(String pass)
    {
        this.password = pass;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getUsername(boolean quotes)
    {
        if(quotes) return "\"" + this.getUsername() + "\"";
        else return this.getUsername();
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getPassword(boolean quotes)
    {
        if(quotes) return "\"" + this.getPassword() + "\"";
        else return this.getPassword();
    }

    public void printPrettyJson(String jsonString)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonString);
        String prettyJsonString = gson.toJson(je);

        System.out.println("\n\n" + prettyJsonString);
        System.out.println("----------------------------------------");
    }
}