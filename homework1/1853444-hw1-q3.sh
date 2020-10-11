#!/bin/bash
file_name=1853444-hw1-q2.log
new_file_name=1853444-hw1-q3.log

if [ -f "./$new_file_name" ]
then
	rm ./$new_file_name
fi
touch ./$new_file_name

echo -n "总行数:" >> ./$new_file_name
wc -l ./$file_name | awk -F " " '{print $1}' >> ./$new_file_name
echo -n "总字符数:" >> ./$new_file_name
wc -m ./$file_name | awk -F " " '{print $1}' >> ./$new_file_name

time1=$(awk 'NR == 1 {print $1}' ./$file_name)
time2=$(awk 'NR == 100 {print $1}' ./$file_name)

timeStamp1=`date -d "$time1" +%s`
timeStamp2=`date -d "$time2" +%s`

Interval=$(($timeStamp2-$timeStamp1))
minutes=$(($Interval/60))
seconds=$(($Interval%60))
echo "相差时间:$minutes:$seconds" >> ./$new_file_name

n=0
sum=0
aval=0
flag=0
awk 'NR > 0 {print $NF}' ./$file_name | while read val
do
	if [[ $(echo "$val > 0.00" | bc) -eq 1 ]]
	then
		sum=$(echo "$sum + $val"|bc)
		n=$(($n+1))
	fi
	flag=$(($flag+1))
	if [[ $flag -eq 100 ]]
	then
		echo -n "非零项总次数 $n 总和 $sum 平均值 " >> ./$new_file_name
		aval=$(echo "scale=2;$sum/$n" | bc)
		if [ $(echo "$aval < 1.00"| bc) -eq 1 ]
		then
			echo "0$aval" >> ./$new_file_name
		else
			echo "$aval" >> ./$new_file_name
		fi
	fi
done
