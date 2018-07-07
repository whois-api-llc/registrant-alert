using System;
using System.Net;
using System.IO;

using Newtonsoft.Json;

// Note that you need to make sure your Project is set to ".NET Framework 4"
// and NOT ".NET Framework 4 Client Profile". Once that is set, make sure the
// following references are present under the References tree under the
// project: Microsoft.CSharp, System, System.Web.Extensions, and System.XML

namespace RegistrantAlertApiPost
{
    public class RegistrantAlertKeyValueSample
    {
        private string _username;
        private string _password;
        public const string Url =
            "https://www.whoisxmlapi.com/registrant-alert-api/search.php";

        static void Main(string[] args)
        {
            RegistrantAlertKeyValueSample regAlert =
                new RegistrantAlertKeyValueSample();

            //////////////////////////
            // Fill in your details //
            //////////////////////////
            regAlert.SetUsername("Your registrant alert api username");
            regAlert.SetPassword("Your registrant alert api password");

            //////////////////////////
            // Send POST request    //
            //////////////////////////
            string responsePost = regAlert.SendPostRegistrantAlert();
            regAlert.PrintResponse(responsePost);

            //////////////////////////
            // Send GET request     //
            //////////////////////////
            string responseGet = regAlert.SendGetRegistrantAlert();
            regAlert.PrintResponse(responseGet);

            // Prevent command window from automatically closing
            Console.WriteLine("\nPress any key to continue...");
            Console.ReadKey();
        }

        protected string SendPostRegistrantAlert()
        {
            /////////////////////////
            // Use a JSON resource //
            /////////////////////////

            Console.Write("Sending request to: " + Url + "\n");

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(Url);
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "POST";

            using (var streamWriter =
                        new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                string json = "{" +
                    "\"terms\": [" +
                        "{"
                            + "\"section\": \"Registrant\","
                            + "\"attribute\": \"name\","
                            + "\"value\": \"n\","
                            + "\"matchType\": \"EndsWith\","
                            + "\"exclude\": \"false\""
                      + "}],"
                    + "\"recordsCounter\": \"false\","
                    + "\"outputFormat\": \"xml\","
                    + "\"username\": \"" + this.GetUsername() + "\","
                    + "\"password\": \"" + this.GetPassword() + "\","
                    + "\"rows\": \"10\", \"searchType\": \"current\""
                + "}";
                streamWriter.Write(json);
                streamWriter.Flush();
                streamWriter.Close();
            }

            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
            
            string res;
            using (var streamReader = 
                        new StreamReader(httpResponse.GetResponseStream()))
            {
                res = streamReader.ReadToEnd();
            }
            return res;
        }

        protected string SendGetRegistrantAlert()
        {
            /////////////////////////////
            // Use query string params //
            /////////////////////////////
            string requestParams =
                "?term1=" + "test" +  "&mode=purchase" + "&username="
                + this.GetUsername() + "&password=" + this.GetPassword();

            string fullUrl = Url + requestParams;
            fullUrl = Uri.EscapeUriString(fullUrl);

            Console.Write("Sending request to: " + fullUrl + "\n");

            // Download JSON into a string
            string result = new WebClient().DownloadString(fullUrl);
                
            // Print a nice informative string
            try
            {
                return result;
            }
            catch (Exception e)
            {
                return "{\"error\":\"An unkown error has occurred!\"}";
            }
        }

        public void SetUsername(string login)
        {
            this._username = login;
        }
        public void SetPassword(string pass)
        {
            this._password = pass;
        }

        public String GetUsername()
        {
            return this._username;
        }

        public String GetPassword()
        {
            return this._password;
        }

        protected void PrintResponse(string response)
        {
            dynamic responseObject = JsonConvert.DeserializeObject(response);
            if (responseObject != null)
            {
                Console.Write(responseObject);
                Console.WriteLine("--------------------------------");
                return;
            }

            Console.WriteLine(response);
            Console.WriteLine();
        }
    }
}