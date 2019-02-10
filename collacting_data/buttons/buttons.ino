void setup() {
Serial.begin(9600);
pinMode(A0,INPUT);
pinMode(A1,INPUT);
pinMode(A2,INPUT);
}

void loop() {
if(digitalRead(A0)){
  if(digitalRead(A1)){
    if(digitalRead(A2)){
        Serial.println("open full::2");
    }
    else
      Serial.println("open half::1");
  }
  else
  Serial.println("closed::0");
}
}
