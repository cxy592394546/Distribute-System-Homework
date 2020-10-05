#!/bin/bash
file_name=1853444-hw1-q2.log

if [ -f "./$file_name" ]
then
	rm ./$file_name
fi

touch ./$file_name
times=0
while [[ $times -lt 100 ]]
do 	
	uptime >> ./$file_name
	times=$(($times+1))
	sleep 10
done
