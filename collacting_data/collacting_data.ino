
//we include I2S(standard used for connecting digital audio devices togethe)
#include <I2S.h>
void setup() {
  //we set the Serial to 115200 baud
  Serial.begin(115200);
  //we enable communication and pass (mode, sampleRate, bitsPerSample)
  I2S.begin(I2S_PHILIPS_MODE, 17000, 32);
  //we initialize pin A0(button) as Input
  pinMode(A0,INPUT);

}
 int i;
void loop() {
    //check if the button pressed
    if(digitalRead(A0)){
       //we start reading the value from the sensors
       int sample = I2S.read();
       //we filter 0 and -1 from the collected samples
       if ((sample == 0) || (sample == -1) ) {return;}
       // convert to 18 bit signed
       sample >>= 14;
       //we print the sample so the pySerial can read it
       Serial.println(sample);
       //increment i(i is number of sample capture)
        i++;
    //check if number of sample capture is 255
       if(i%255==254){
        //if the first swith is ON
         if(!digitalRead(A1)){
            //send label as 0 (water OFF)
            Serial.println(0);
            //wait 2S befor capture other data
            delay(2000);
         }
         //if the first swith is OFF
         else{
         //if the second switch is ON
         if(! digitalRead(A2)){
            //send label as 1 (water half ON)
            Serial.println(1);
            //wait 2S befor capture other data
            delay(2000);
         }
         //if the second switch is OFF
         else{
            //send label as 2 (water full ON)
            Serial.println(2);
            //wait 2S befor capture other data
            delay(2000);
         }
         }
       }
         
    }
}
