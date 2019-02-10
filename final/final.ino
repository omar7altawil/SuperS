//#include <SparkFunLSM9DS1.h>
//#include <I2S.h>
//include libraries for BME280
#include <stdint.h>
#include "SparkFunBME280.h"
//include wire librarie(I2C protocal)
#include <Wire.h>
//include libraries for TSL2561
#include <Adafruit_Sensor.h>
#include <Adafruit_TSL2561_U.h>
//LSM9DS1 imu;

//setup TSL2561
Adafruit_TSL2561_Unified tsl = Adafruit_TSL2561_Unified(TSL2561_ADDR_FLOAT, 12345);
//setup BME280
BME280 mySensor;
void setup() {
  //we set the Serial to 115200 baud
  Serial.begin(115200);
//  I2S.begin(I2S_PHILIPS_MODE, 17000, 32);
//  imu.settings.device.commInterface = IMU_MODE_I2C;
//  imu.settings.device.mAddress = LSM9DS1_M;                           
//  imu.settings.device.agAddress = LSM9DS1_AG;
//  imu.begin();
  //we do the settings for BME280(protocol is I2C) 
  mySensor.settings.commInterface = I2C_MODE;
  mySensor.settings.I2CAddress = 0x77;
  mySensor.settings.runMode = 3; //Normal mode
  mySensor.settings.tStandby = 0;
  mySensor.settings.filter = 0;
  mySensor.settings.tempOverSample = 1;
  mySensor.settings.pressOverSample = 1;
  mySensor.settings.humidOverSample = 1;
 //we enable communication for the BME280
  mySensor.begin();}
// int i;
 //variable to store the old light sensor value
 int oldlight=0;
 //variable to store the old flame sensor value
 int oldflame=0;
 //variable to store the last time we got a reading from BME sensor
 int bmeTime=0;
 //variable to store the last time we got a reading from light sensor
 int lightTime=0;
 //variable to store the last time we got a reading from flame sensor
 int flameTime=0;
void loop() {
//**************we did not use the mic and the Lms9ds in the final code becouse the AI part is not done so the mic data right now is useless for us.**************
//int sample = I2S.read();
//imu.readGyro();
//imu.readAccel();
//imu.readMag();
//if ((sample == 0) || (sample == -1) ) {return;}
//sample >>= 14;
//Serial.println("1");
//Serial.println(sample);
//Serial.println("2");
// Serial.print(imu.calcGyro(imu.gx), 2);
// Serial.print(imu.calcGyro(imu.gy), 2);
// Serial.println(imu.calcGyro(imu.gz), 2);
//Serial.println("3");
// Serial.print(imu.calcAccel(imu.ax), 2);
// Serial.print(imu.calcAccel(imu.ay), 2);
// Serial.println(imu.calcAccel(imu.az), 2);
//Serial.println("4");
// Serial.print(imu.calcMag(imu.mx), 2);
// Serial.print(imu.calcMag(imu.my), 2);
// Serial.println(imu.calcMag(imu.mz), 2);
//i++;
//if(i%255==254){
     //create a variable event of type sensors_event_t
     sensors_event_t event;
     tsl.getEvent(&event);
     //get the intensity of the light
     int light=event.light;
     //get the flame sensor value
     int flame=analogRead(A3);
     //check if the value of the light increased or decreased dramatically and 5S pass since the last reading
     if(light> oldlight*2 || oldlight>light*2 ){
        //send 1 so the python know that the next value are for the light
        Serial.println("1");
        //send the light value
        Serial.println(light);
        //update the old value
        oldlight=light;
        //reset the time for light
        lightTime=0;        
     }
     //check if the value of the flame increased or decreased dramatically and 5S pass since the last reading
     if(flame>oldflame*2 || oldflame>flame*2 ){
        //send 2 so the python know that the next value are for the flame
        Serial.println("2");
        //send the flame value
        Serial.println(flame); 
        //update the flame value
        oldflame=flame;
        //rest the time for flame
        flameTime=0;     
     }
     //if 20S pass(every 20S the update these value)
     if(bmeTime==200){
     //send 3 so the python know that the next value are for the TempC,pressure,humidity and altitude
     Serial.println("3");
     //send the the temp
     Serial.println(mySensor.readTempC());
     //send the Pressure
     Serial.println(mySensor.readFloatPressure());
     //send the humidity
     Serial.println(mySensor.readFloatHumidity());
     //send the altitude
     Serial.println(mySensor.readFloatAltitudeMeters());
     //rest the time
     bmeTime=0;
     }
     //increment the time
     bmeTime++;
     lightTime++;
     flameTime++;
     //delay 0.1S(so bmeTime=200 = 20S ,lightTime and flameTime)
     delay(100);
//       }
  }
