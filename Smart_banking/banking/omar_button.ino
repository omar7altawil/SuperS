
#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#define FIREBASE_HOST "smart-banking-d46e6.firebaseio.com"
#define FIREBASE_AUTH "BUwntR3gepiTfiqU43ZKc0Uh3hywUnrbLaX8YMh0"
#define WIFI_SSID "Omar"
#define WIFI_PASSWORD "sinx*cosx"
#define password "1111099711446971081169711910510864104111116109971051084699111109/password"
#define auth "1111099711446971081169711910510864104111116109971051084699111109/auth"

int count=0;
int timeout=0;
void setup() {
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  pinMode(2, INPUT);
  pinMode(0, OUTPUT);
  attachInterrupt(2, button, RISING);

}
void button(){
count++;
if(Firebase.getInt(password)==count){
      Firebase.setInt(auth,2);
      digitalWrite(0,LOW);
      count=0;
      timeout=0;
    }
}

void loop() {
if(Firebase.getInt(auth)==1){
  digitalWrite(0,HIGH);
  timeout++;
  delay(1);
   if(timeout >=200){
    Firebase.setInt(auth,3);
    digitalWrite(0,LOW);
    count=0;
    timeout=0;
    }
}
}
