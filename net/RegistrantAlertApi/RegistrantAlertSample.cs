using System;
using System.Xml;

// Note that you need to make sure your Project is set to ".NET Framework 4"
// and NOT ".NET Framework 4 Client Profile". Once that is set, make sure the
// following references are present under the References tree under the
// project: Microsoft.CSharp, System, System.Web.Extensions, and System.XML

namespace RegistrantAlertApi
{
    public class RegistrantAlertApiQuery
    {
        static void Main(string[] args)
        {
            //////////////////////////
            // Fill in your details //
            //////////////////////////
            string username = "Your registrant alert api username";
            string password = "Your registrant alert api password";

            string term1 = "test";
            string exclude_term1 = "online";

            /////////////////////////
            // Use an XML resource //
            /////////////////////////
            String format = "XML";
            String url =
                "https://www.whoisxmlapi.com/registrant-alert-api/search.php?"
                + "username=" + username + "&password=" + password
                + "&output_format=" + format + "&term1=" + term1
                + "&exclude_term1=" + exclude_term1;
            url = Uri.EscapeUriString(url);

            // Download XML into a dynamic object
            dynamic result = new System.Net.WebClient().DownloadString(url);
            try
            {
                var xmlDoc = new XmlDocument();
                xmlDoc.LoadXml(result);
                WriteXml(xmlDoc);
            }
            catch (Exception e)
            {
                try
                {
                    Console.WriteLine("JSON:\nErrorMessage:\n\t{0}",
                                      result.ErrorMessage.msg);
                }
                catch (Exception e2)
                {
                    Console.WriteLine("An unkown error has occurred!");
                }
            }
            // Prevent command window from automatically closing
            Console.WriteLine("Press any key to continue...");
            Console.ReadKey();
        }

        public static void WriteXml(XmlDocument doc)
        {
            XmlTextWriter writer = new XmlTextWriter(Console.Out);
            writer.Formatting = System.Xml.Formatting.Indented;
            doc.WriteTo(writer);
            writer.Flush();
            Console.WriteLine();
        }
    }
}