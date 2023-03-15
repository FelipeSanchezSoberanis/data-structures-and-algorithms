#!/usr/bin/env bash

RED="\033[0;31m"
GREEN="\033[0;32m"
NC="\033[0m"

javaProgram=$1

for file in tests/*
do
    echo "====$(echo $file | sed -e "s/./=/g")===="
    echo "=== $file ==="
    echo "====$(echo $file | sed -e "s/./=/g")===="

    calculatedResult=$(cat $file | java $javaProgram)
    expectedResult=$(cat tests-results/${file##*/}.a)

    echo "=== Test result ==="
    if [ $calculatedResult == $expectedResult ]
    then
        echo -e "${GREEN}Correct result${NC}"
    else
        echo -e "${RED}Incorrect result${NC}"
        echo "Expected: "
        echo $expectedResult
        echo "Got: "
        echo $calculatedResult
    fi

    echo
done

