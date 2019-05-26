import csv
import sys
filename = sys.argv[1]
n = int(sys.argv[2])
dim = int(sys.argv[3])
f = open(filename,'w')
w = csv.writer(f,delimiter = ',')
for i in range(0,n):
    r = [j%2 for j in range(0,dim)]
    w.writerow(r)
f.close()
