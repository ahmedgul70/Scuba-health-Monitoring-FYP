#include <SoftwareSerial.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>

SoftwareSerial arduino(D7, D8);
ESP8266WebServer server(80);   // port 80 (http port)
const char* ssid = "Jazz-LTE-41E0"; //ssid of your wifi Jazz-LTE-41E0  ZONG MBB-E5573-CEA3
const char* password = "82264594"; //password of your wifi 82264594   82690146
String page;
String data = " ";
String temperature;
String hb;
String spo2;
int t=0,h=0,s=0;

void setup() 
{ 
  Serial.begin(9600);   
  arduino.begin(9600);
  Serial.println("Initializing . . . . . ");
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password); //begin WiFi connection
  Serial.println("");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
  server.on("/", []() {
  page = "<html><head><meta http-equiv=\"refresh\" content=\"0.5\"></head>";
    page += "<body style=\"background-color:Bisque;color: Black;border: 2px solid black;padding:15px\">";
    page += "<h1>SCUBA DIVING PROJECT</h1>";
    page += "<h2>Temperature : " + temperature + "</h2>";
    page += "<h2>Heart Beat : " + hb+ " </h2>";
    page += "<h2>Oxygen Level : " + spo2 + " </h2>";
    page += "</body></html>";
    server.send(200, "text/html", page);
  });

  server.begin();
  Serial.println("Web server started!");

  delay(500);
} 



void loop() 
{   
    serialcheck();
    server.handleClient();
    delay(100);
}  


void serialcheck(){
   if (arduino.available() > 0) {
    data = arduino.readStringUntil('&');
    temperature = data.substring(1, 8);
    
    delay(10);
    hb = data.substring(10, 12);
     delay(10);
    spo2 = data.substring(14, 16);
     delay(10);

    
  Serial.print("Temperature : ");
  Serial.print(temperature);
  Serial.print("°C \t");
  Serial.print("Data : ");
  Serial.print(data);
  Serial.print("  Heart Beat : ");
  Serial.print(hb);
  Serial.print("bpm");
  Serial.print("  Oxygen Level : ");
  Serial.print(spo2);
  Serial.println("%");

  }
  else{
    Serial.println("ERROR ");
  }
}
