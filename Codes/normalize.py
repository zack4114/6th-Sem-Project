#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Apr 30 12:27:54 2018

@author: krishna
"""

#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Feb 25 14:24:33 2018

@author: krishna
"""
import copy
#f = open("data_gyroscope.csv","r")
#f = open("data_accelerometer.csv","r")
f = open("dataset1.csv","r")

Mf = 0
Sf = 0
dataMatrix = []

for line in f:
    temp = line.split(',')
    dataMatrix.append(temp)
    
    
normalizeMatrix = copy.deepcopy(dataMatrix)
for indC in range(len(dataMatrix[0])-16):
    minimum = 0
    maximum = 0
    for indR in range(1,len(dataMatrix)):
        if(minimum > float(dataMatrix[indR][indC])):
            minimum = float(dataMatrix[indR][indC])
        if(maximum < float(dataMatrix[indR][indC])):
            maximum = float(dataMatrix[indR][indC])
    
    for indR in range(1,len(dataMatrix)):
        normalizeMatrix[indR][indC] = str((float(normalizeMatrix[indR][indC]) - minimum)/(maximum - minimum))
        
f1 = open("normalizedataset1.csv","w")
for x in normalizeMatrix:
    temp1 = ",".join(x)
    f1.write(temp1)
f.close()
f1.close()
        
    