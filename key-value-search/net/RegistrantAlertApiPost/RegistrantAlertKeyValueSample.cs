using System;
using System.IO;
using System.Net;

using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

// Note that you need to make sure your Project is set to ".NET Framework 4"
// and NOT ".NET Framework 4 Client Profile". Once that is set, make sure the
// following references are present under the References tree under the
// project: Microsoft.CSharp, System, System.Web.Extensions, and System.XML.

namespace RegistrantAlertApiPost
{
    public static class RegistrantAlertKeyValueSample
    {
        private static void Main()
        {
            //////////////////////////
            // Fill in your details //
            //////////////////////////
            var regAlert = new RegAlertSample
            {
                Username = "Your registrant alert api username",
                Password = "Your registrant alert api password"
            };

            //////////////////////////
            // Send POST request    //
            //////////////////////////
            var responsePost = regAlert.SendPostRegistrantAlert();
            PrintResponse(responsePost);

            //////////////////////////
            // Send GET request     //
            //////////////////////////
            var responseGet = regAlert.SendGetRegistrantAlert();
            PrintResponse(responseGet);

            // Prevent command window from automatically closing
            Console.WriteLine("\nPress any key to continue...");
            Console.ReadKey();
        }
        
        private static void PrintResponse(string response)
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

    public class RegAlertSample
    {
        private const string Url =
            "https://www.whoisxmlapi.com/registrant-alert-api/search.php";
        
        public string SendGetRegistrantAlert()
        {
            /////////////////////////////
            // Use query string params //
            /////////////////////////////
            var requestParams =
                "?term1=test"
                + "&mode=purchase"
                + "&username=" + Uri.EscapeDataString(Username)
                + "&password=" + Uri.EscapeDataString(Password);

            var fullUrl = Url + requestParams;

            Console.Write("Sending request to: " + fullUrl + "\n");

            // Download JSON into a string
            var result = new WebClient().DownloadString(fullUrl);
                
            // Print a nice informative string
            try
            {
                return result;
            }
            catch (Exception)
            {
                return "{\"error\":\"An unkown error has occurred!\"}";
            }
        }
        
        public string SendPostRegistrantAlert()
        {
            /////////////////////////
            // Use a JSON resource //
            /////////////////////////

            Console.Write("Sending request to: " + Url + "\n");

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(Url);
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "POST";

            using (var requestStream = httpWebRequest.GetRequestStream())
            using (var streamWriter = new StreamWriter(requestStream))
            {
                var json = @"{
                    terms: [
                        {
                            section: 'Registrant',
                            attribute: 'name',
                            value: 'n',
                            matchType: 'EndsWith',
                            exclude: false
                        }
                    ],
                    recordsCounter: false,
                    outputFormat: 'json',
                    username: 'USER_NAME',
                    password: 'USER_PASS',
                    rows: 10
                }";

                json = json.Replace("USER_NAME", Username)
                           .Replace("USER_PASS", Password);
                
                var jsonData = JObject.Parse(json).ToString();

                streamWriter.Write(jsonData);
                streamWriter.Flush();
                streamWriter.Close();
            }

            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
            
            var res = "";
            
            if (httpResponse.GetResponseStream() == null)
                return null;

            using (var responseStream = httpResponse.GetResponseStream())
            {
                if (responseStream == null || responseStream == Stream.Null)
                {
                    return res;
                }

                using (var streamReader = new StreamReader(responseStream))
                {
                    res = streamReader.ReadToEnd();
                }
            }

            return res;
        }

        public string Password { get; set; }
        
        public string Username { get; set; }
    }
}