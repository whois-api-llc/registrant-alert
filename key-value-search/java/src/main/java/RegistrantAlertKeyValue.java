import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RegistrantAlertKeyValue
{
    private String username;
    private String password;

    private final String url =
        "https://www.whoisxmlapi.com/registrant-alert-api/search.php";

    public static void main(String[] args) throws Exception
    {
        RegistrantAlertKeyValue query = new RegistrantAlertKeyValue();

        // Fill in your details
        query.setUsername("Your registrant alert username");
        query.setPassword("Your registrant alert password");

        try {
            // Send POST request
            String responseStringPost = query.sendPost();
            query.PrettyPrintJson(responseStringPost);

            // Send GET request
            String responseStringGet = query.sendGet();
            query.PrettyPrintJson(responseStringGet);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPassword(boolean quotes)
    {
        if (quotes)
            return "\"" + this.getPassword() + "\"";
        else
            return this.getPassword();
    }

    public String getUsername(boolean quotes)
    {
        if (quotes)
            return "\"" + this.getUsername() + "\"";
        else
            return this.getUsername();
    }

    public void PrettyPrintJson(String jsonString)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonString);
        String prettyJsonString = gson.toJson(je);

        System.out.println("\n\n" + prettyJsonString);
        System.out.println("----------------------------------------");
    }

    public String sendGet() throws IOException
    {
        CloseableHttpClient httpclient = null;
        String responseBody = "";

        try {
            String url_api =
                this.url
                + "?term1=whois&search_type=current&mode=purchase&rows=5"
                + "&username=" + URLEncoder.encode(this.getUsername(),"UTF-8")
                + "&password=" +URLEncoder.encode(this.getPassword(),"UTF-8");

            httpclient = HttpClients.createDefault();
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
                httpclient.close();
        }

        return responseBody;
    }

    // HTTP POST request
    public String sendPost() throws Exception
    {
        URL obj = new URL(this.url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        String searchTerm1 = "\"section\": \"Registrant\","
                           + "\"attribute\": \"name\","
                           + "\"value\": \"J\","
                           + "\"matchType\": \"anywhere\","
                           + "\"exclude\": false";

        String requestOptions = "\"recordsCounter\": false,"
                              + "\"rows\": 10,"
                              + "\"username\":" + this.getUsername(true) + ","
                              + "\"password\":" + this.getPassword(true) + ","
                              + "\"outputFormat\": \"json\"";

        String requestObject =
                "{\"terms\":[{" + searchTerm1 + "}], " + requestOptions +"}";

        // Send POST request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(requestObject);
        wr.flush();
        wr.close();

        System.out.println("\nSending 'POST' request to URL : " + url);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    private String getUsername()
    {
        return this.username;
    }

    private String getPassword()
    {
        return this.password;
    }
}