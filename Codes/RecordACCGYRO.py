#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Apr 28 15:55:23 2018

@author: krishna
"""

f = open("data_accelerometer.csv","r")
f1 = open("data_gyroscope.csv","r")
f2 = open("dataKey.csv","r")
f3 = open("outputTemp1.csv","w")

timeSpan = 800
noOfRecord = timeSpan/10

key = []
time = []

data_acc = []
data_gyro = []

for line in f:
    temp = line.split(",")
    data_acc.append(temp)

for line in f1:
    temp = line.split(",")
    data_gyro.append(temp)    

for line in f2:
    temp = line.split(",")
    key.append(int(temp[0]))
    time.append(int(temp[1]))

print(key)
print(time)
for i in range(int(noOfRecord)):
    if i != 0:
        f3.write(",")
    f3.write("xa"+str(i)+",ya"+str(i)+",za"+str(i)+",xg"+str(i)+",yg"+str(i))
f3.write(",Class\n")
for i in range(len(time)):
    for j in range(len(data_acc)):
        if int(data_acc[j][3]) > time[i]:
            start = j-int(noOfRecord/2)
            end = j+int(noOfRecord/2)
            for k in range(start,end):
                if k != start:
                    f3.write(",")
                f3.write(data_acc[k][0]+","+data_acc[k][1]+","+data_acc[k][2]+","+data_gyro[k][0]+","+data_gyro[k][1])
            f3.write(",c"+str(key[i])+"\n")
            break
    
            
            
        
    
    