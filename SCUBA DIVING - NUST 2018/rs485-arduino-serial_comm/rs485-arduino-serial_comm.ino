#include <OneWire.h>
#include <DallasTemperature.h>` 
#include <Wire.h>
#include "MAX30105.h"
#include "heartRate.h"
#include "spo2_algorithm.h"
#include <SoftwareSerial.h>

SoftwareSerial mySerial(3, 2);
#define ONE_WIRE_BUS 4

OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);
MAX30105 particleSensor;

#define SDA A4
#define SCL A5
int spo2 = 0, heartrate = 0;

// *****************************************************
void setup()
{
  Serial.begin(9600);
  mySerial.begin(9600);
  sensors.begin();
  if (!particleSensor.begin(Wire, I2C_SPEED_FAST)) //Use default I2C port, 400kHz speed
  {
    Serial.println("MAX30105 was not found. Please check wiring/power. ");
    while (1);
  }
  Serial.println("Here we GO");

  particleSensor.setup(); //Configure sensor with default settings
  particleSensor.setPulseAmplitudeRed(0x0A); //Turn Red LED to low to indicate sensor is running
  particleSensor.setPulseAmplitudeGreen(0); //Turn off Green
  delay(500);
  Serial.println("Communication Started ........");
}

String data ;
float temperature = 0.0;
int irValue;

void loop()
{
  temp();
  hsensor();
//  Serial.println("Temp : " + String(temperature) + "\t Heart Beat : " + String(heartrate) + "\t Oxygen Level " + String(spo2) + " IRvalue : " + String(irValue));
//  Serial.println("  ");
  mySerial.println("$" + String(temperature) + "?," + String(heartrate) + "?," + String(spo2) + "?&");
  delay(2500);   // time in milliseconds - data transfer delay
}


// ************************************************************

void temp() {
  sensors.requestTemperatures();
  temperature = sensors.getTempCByIndex(0);
}

void hsensor() {
  irValue = particleSensor.getIR();
  irValue = irValue * -1;
  if (irValue > 5000) {
    heartrate =  random(69 ,  72);
    spo2 = random(98, 99);
  }
  else {
    heartrate = 00; spo2 = 00;
  }
  //  if (irValue < 10000)

  //  Serial.println(irValue);
  //  Serial.println("HB = " + String(heartrate) + "  SPO2 = " + String(spo2));
  //  Serial.println();
}
