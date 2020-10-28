#! /bin/bash

times=$(cat ./dblp.xml | grep -o Jacques |wc -l)

echo $times
