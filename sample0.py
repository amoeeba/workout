#!/usr/bin/python

fiboSeq=[]
a,b=0,1
while (b<1000): fiboSeq.append(a)
a,b=b,a+b
print (fiboSeq)
