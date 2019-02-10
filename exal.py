#import xlwt(a library for developers to use to generate spreadsheet files compatible with Microsoft Excel)
from xlwt import Workbook
#import Serial(it allow as to collact data  from the serial)
import serial
#initialize a variable arduino that will be listing on COM8
arduino = serial.Serial("COM8", timeout=1)
#initialize Workbook object
wb=Workbook()
#we add a new sheet called "wataer sound" to the exal sheet
data_set_sheet=wb.add_sheet("wataer sound",cell_overwrite_ok=True)
#will run throw the 256 column  that we have in the exal sheet
for i in range(255):
 #will name each feature by a number
 data_set_sheet.write(0,i,i+1)
#last column  will be for the label
data_set_sheet.write(0,256,"label")

#c is number of row we will have on each run
c=0
#count is number of column
count = 0
#run for ever
while True:
 #reset the count to 0
 count = 0
 #if count less than 256
 while count < 256:
    #read from the serial and stor it in value
    value=str(arduino.readline())
    # we check if value is not empty
    if value != "":
     #we print value(not important)
     print(value)
     # we write value to the spicific loaction in the exal sheet
     data_set_sheet.write(c, count, int(value))
     #we move to the next column
     count += 1
 #we move to the next row
 c +=1
 #check if row ==10
 if c==10:
  #we save the file
  wb. save("AI wataer")
  #we exit the program
  break
