#!/bin/bash

#脚本运行前提为已在远程机器上配置完成免密登陆所需条件！！！
scriptfile1=1853444-hw1-q2.sh
scriptfile2=1853444-hw1-q3.sh
log_file_name=1853444-hw1-q3.log
new_file_name=1853444-hw1-q4.log
path=/mnt/e/分布式系统/hw1/temp
cpdir1=cp1
cpdir2=cp2
cpdir3=cp3

if [ ! -f "./$new_file_name" ]
then
	touch ./$new_file_name
elif [ -f "./$new_file_name" ]
then
	rm ./$new_file_name
	touch ./$new_file_name
fi

sum=0.00
ip_addr=(100.68.120.250 192.168.91.140 192.168.91.141) 
#数组ip_addr中元素为实验中使用的另外三台机器的ip地址，其中第一台为centos7系统（数组中ip地址为实验时的ip地址），后两台为win10系统上运行的centos8虚拟机

if [ ! -d "$path/$cpdir1" ]
then
	mkdir $path/$cpdir1
elif [ -f "$path/$cpdir1/*" ]
then
	rm $path/$cpdir1/*
fi
ssh xinyu@${ip_addr[0]} <$scriptfile1
ssh xinyu@${ip_addr[0]} <$scriptfile2
scp xinyu@${ip_addr[0]}:/home/xinyu/$log_file_name $path/$cpdir1
val1=$(awk 'NR == 4 {print $NF}' $path/$cpdir1/$log_file_name)
echo -n "平均值1 $val1 " >> ./$new_file_name
sum=$(echo "scale=2;$sum+$val1" | bc)

if [ ! -d "$path/$cpdir2" ]
then
	mkdir $path/$cpdir2
elif [ -f "$path/$cpdir2/*" ]
then
	rm $path/$cpdir2/*
fi
ssh xinyu@${ip_addr[1]} < $scriptfile1
ssh xinyu@${ip_addr[1]} < $scriptfile2
scp xinyu@${ip_addr[1]}:/home/xinyu/$log_file_name $path/$cpdir2
val2=$(awk 'NR == 4 {print $6}' $path/$cpdir2/$log_file_name)
echo -n "平均值2 $val2 " >> ./$new_file_name
sum=$(echo "scale=2;$sum+$val2" | bc)

if [ ! -d "$path/$cpdir3" ]
then
	mkdir $path/$cpdir3
elif [ -f "$path/$cpdir3/*" ]
then
	rm $path/$cpdir3/*
fi
ssh xinyu@${ip_addr[2]} < $scriptfile1
ssh xinyu@${ip_addr[2]} < $scriptfile2
scp xinyu@${ip_addr[2]}:/home/xinyu/$log_file_name $path/$cpdir3
val3=$(awk 'NR == 4 {print $6}' $path/$cpdir3/$log_file_name)
echo "平均值3 $val3 " >> ./$new_file_name
sum=$(echo "scale=2;$sum+$val3" | bc)

aval=$(echo "scale=2;$sum/3" | bc)
echo "平均值 0$aval" >> ./$new_file_name
echo "平均值 0$aval"
