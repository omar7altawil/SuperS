
#include <stdint.h>
#include "SparkFunBME280.h"
#include "Wire.h"

BME280 mySensor;

void setup()
{
	mySensor.settings.commInterface = I2C_MODE;
	mySensor.settings.I2CAddress = 0x77;
	

	mySensor.settings.runMode = 3; //Normal mode

	mySensor.settings.tStandby = 0;
	

	mySensor.settings.filter = 0;

	mySensor.settings.tempOverSample = 1;

  mySensor.settings.pressOverSample = 1;
	
	mySensor.settings.humidOverSample = 1;
	
	Serial.begin(57600);
	
	delay(10); 
	Serial.println(mySensor.begin(), HEX);

}

void loop()
{
	
	Serial.print("Temperature: ");
	Serial.print(mySensor.readTempC(), 2);
	Serial.println(" degrees C");

	Serial.print("Pressure: ");
	Serial.print(mySensor.readFloatPressure(), 2);
	Serial.println(" Pa");

	Serial.print("Altitude: ");
	Serial.print(mySensor.readFloatAltitudeMeters(), 2);
	Serial.println("m");


	Serial.print("Humidity: ");
	Serial.print(mySensor.readFloatHumidity(), 2);
	Serial.println(" %");
	
	Serial.println();
	
	delay(10000);

}
