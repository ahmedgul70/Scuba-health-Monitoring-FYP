#include <SoftwareSerial.h>

SoftwareSerial arduino(3, 4);
SoftwareSerial BT(7, 6);


String data = " ";
String temperature = "1";
String hb = " ";
String spo2 = " ";

void setup()
{
  Serial.begin(9600);
  arduino.begin(9600);
  delay(500);
  Serial.println("RS-485 Module Connected ........... ");
}



void loop()
{
  ////  temperature = String(random(31.55 , 75.55));
  //  spo2 = String(random(90 , 99));
  //  hb = String(random(68 , 75));

  if (arduino.available() > 0  ) {
    
    data = arduino.readStringUntil('&');
//    if(data.length() > 0){
    temperature = data.substring(1, 6);
    delay(10);
    hb = data.substring(8, 10);
    delay(10);
    spo2 = data.substring(12, 14);
    delay(10);
    Serial.print("Data : ");
    Serial.print(data);
    Serial.println("  ");
    Serial.print("Temperature : ");
    Serial.print(temperature);
    Serial.print("°C \t");
    Serial.print("  Heart Beat : ");
    Serial.print(hb);
    Serial.print("bpm \t");
    Serial.print("  Oxygen Level : ");
    Serial.print(spo2);
    Serial.println("%");
//    }
  }
  
  delay(100);
  arduino.end();
  delay(100);
  BT.begin(9600);
  BT.println(temperature + "," + spo2 + "," + hb + "  >");
  delay(200);
  BT.end();
  delay(100);  
  arduino.begin(9600);
  delay(500);
}
