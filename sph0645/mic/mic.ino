#include <I2S.h>
void setup() {
  Serial.begin(115200);
  I2S.begin(I2S_PHILIPS_MODE, 17000, 32);

}
 int i;
void loop() {
    int sample = I2S.read();
    if ((sample == 0) || (sample == -1) ) {return;}
    sample >>= 14;
    Serial.println(sample);
    i++;
    delay(1);
//    Serial.println(i);
}
