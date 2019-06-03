import random
import csv
import sys
filename = sys.argv[1]
n = int(sys.argv[2])
dim = int(sys.argv[3])
f = open(filename,'w')
w = csv.writer(f,delimiter = ',')
for i in range(0,n):
    if(i==0):
        r = ["v" if(j== 0) else j for j in range(0,dim)]
    else:
        r = [random.randrange(0,2)*int(i <= 4) if(j!= 0) else i-1 for j in range(0,dim)]
    w.writerow(r)
f.close()
