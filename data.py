#import Serial(it allow as to collact data  from the serial)
import serial
#import firebas(it allow us read and write to firebase)
from firebase.firebase import FirebaseApplication, FirebaseAuthentication
#firebase setting
SECRET = 'pyDYJf7bXrujgAysluibQyuZnRm4qRvV504HbJq6'
DSN = 'https://supers-87d35.firebaseio.com'
EMAIL = 'omar.y.altawil@gmail.com'
authentication = FirebaseAuthentication(SECRET,EMAIL, True, True)
firebase = FirebaseApplication(DSN, authentication)
#initialize a variable arduino that will be listing on COM5
arduino = serial.Serial("COM5", 9600)
#initialize youer gmail that you will acsses from
username="omar7altawil"
#initialize the room that you will setup the super sensor in
# location ="LivingRoom"
location ="BedRoom"
# location ="BathToom"
#location ="Kitchen"
"""Receiving data and storing it in a list"""
#run for ever
while True:
    #***************we are supposed to receive my information here and do analize for the data and send to the data base the label**************************
                                        #***************but AI part is not complete yet **************************


 #read the value coming from the arduino
 value=arduino.readline().strip()
 print (value)
 #if we recive 1
 if value=="1":
   #we will store the next value in light variable
   light=arduino.readline().strip()
   #we will post light to firebase to the specific user and location
   snapshot = firebase.patch('/users/'+username+'/'+location, {'light':light})
 #if we recive 2
 elif value=="2":
     # we will store the next value in flame variable
     flame =arduino.readline().strip()
     # we will post flame to firebase to the specific user and location
     snapshot = firebase.patch('/users/' + username + '/' + location, {'flame': flame})
# if we recive 1
 elif value=="3":
     # we will store the next value in TempC variable
     TempC=arduino.readline().strip()
     # we will store the next value in Pressure variable
     Pressure=arduino.readline().strip()
     # we will store the next value in Humidity variable
     Humidity=arduino.readline().strip()
     # we will store the next value in Altitude variable
     Altitude=arduino.readline().strip()
     # we will post all the variable to firebase to the specific user and location
     snapshot = firebase.patch('/users/' + username + '/' + location, {'TempC': TempC})
     snapshot = firebase.patch('/users/' + username + '/' + location, {'Pressure': Pressure})
     snapshot = firebase.patch('/users/' + username + '/' + location, {'Humidity': Humidity})
     snapshot = firebase.patch('/users/' + username + '/' + location, {'Altitude': Altitude})

