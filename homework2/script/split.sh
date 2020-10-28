#! /bin/bash

file_name=dblp.xml
lines=$(wc -l ./$file_name | awk -F " " '{print $1}')
page_1To5=$(($lines/6+1))
page_6=$(($lines/6))

split -l $page_1To5 dblp.xml dblp_split_

echo $page_1To5
echo $page_6
