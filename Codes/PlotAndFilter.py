import matplotlib.pyplot as plt


f = open("data.csv")
f1 = open("dataKey.csv")
keyTime = []
values = []
time = []
x = []
y = []
z = []
for line in f:
    temp = line.split(",")
    temp1 = float(temp[0])**2 + float(temp[1])**2 + (float(temp[2]))**2
    
    values.append(temp1)
    time.append(float(temp[3]))

for line in f1:
    t = line.split(",")
    keyTime.append(float(t[1]))
    
plt.figure(figsize=(20,7))
y1 = min(values)
y2 = max(values)
plt.plot(time,values)
for i in keyTime:
    plt.plot([i,i],[y1,y2],'k-',lw=2)
plt.show()
plt.figure(figsize=(20,10))
plt.plot(time,values)
plt.show()


