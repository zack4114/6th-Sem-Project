#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Feb 25 14:24:33 2018

@author: krishna
"""
import copy
#f = open("data_gyroscope.csv","r")
#f = open("data_accelerometer.csv","r")
f = open("normalizedataset1.csv","r")


Mf = 0
Sf = 0
dataMatrix = []

for line in f:
    temp = line.split(',')
    dataMatrix.append(temp)
    
disMatrix = copy.deepcopy(dataMatrix)
for indC in range(len(dataMatrix[0])-16):
    minimum = 0
    maximum = 0
    total = 0
    for indR in range(1,len(dataMatrix)):
        total = total + float(dataMatrix[indR][indC])
        if(minimum > float(dataMatrix[indR][indC])):
            minimum = float(dataMatrix[indR][indC])
        if(maximum < float(dataMatrix[indR][indC])):
            maximum = float(dataMatrix[indR][indC])
    mean = total/(len(dataMatrix)-1)
    total1=0
    total2 = 0
    count1 = 0
    count2 = 0
    for indR in range(1,len(dataMatrix)):
        if(float(dataMatrix[indR][indC])>=minimum and float(dataMatrix[indR][indC]) <= mean):
            total1 = total1+float(dataMatrix[indR][indC])
            count1 += 1
        if(float(dataMatrix[indR][indC])>=mean and float(dataMatrix[indR][indC]) <= maximum):
            total2 = total2 + float(dataMatrix[indR][indC])
            count2 += 1
    
    mean1 = total1/count1
    mean2 = total2/count2
    
    for indR in range(1,len(dataMatrix)):
        if (float(dataMatrix[indR][indC]) >= minimum and float(dataMatrix[indR][indC]) < mean1):
            disMatrix[indR][indC] = str(1)
        elif (float(dataMatrix[indR][indC]) >= mean1 and float(dataMatrix[indR][indC]) < mean):
            disMatrix[indR][indC] = str(2)
        elif (float(dataMatrix[indR][indC]) >= mean and float(dataMatrix[indR][indC]) < mean2):
            disMatrix[indR][indC] = str(3)
        elif (float(dataMatrix[indR][indC]) >= mean2 and float(dataMatrix[indR][indC]) <= maximum):
            disMatrix[indR][indC] = str(4)

        
#f1 = open("zData_accelerometer.csv","w")
#f1 = open("zData_gyroscope.csv","w")
f1 = open("Discretizedataset1.csv","w")
for x in disMatrix:
    temp1 = ",".join(x)
    f1.write(temp1)
f.close()
f1.close()
        
    