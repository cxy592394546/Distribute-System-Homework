#!/bin/bash
file_name=1853444-hw1-q1.log
sum=2
for i in $(seq 3 100)
do
	for j in $(seq 2 $i)
	do
	flag=$(($i%$j))
		if [[ $flag -eq 0 ]]
		then
			break
		fi
		if [[ $j -eq $i-1 ]]
		then
			sum=$(($sum+$i))
		fi
	done
done

touch ./$file_name
echo $sum > ./$file_name
