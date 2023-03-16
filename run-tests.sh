#!/usr/bin/env bash

RED="\033[0;31m"
GREEN="\033[0;32m"
NC="\033[0m"

javaProgram=$1

testCasesDir=testing/test-cases
testResultsDir=testing/test-results

numTests=0
passedTests=0

for file in ${testCasesDir}/*
do
    numTests=$((  numTests + 1  ))

    echo "====$(echo $file | sed -e "s/./=/g")===="
    echo "=== $file ==="
    echo "====$(echo $file | sed -e "s/./=/g")===="

    calculatedResult=$(cat $file | java $javaProgram | sed -e "s/\s\+$//g")
    expectedResult=$(cat ${testResultsDir}/${file##*/})

    echo "=== Test result ==="
    if [ "$calculatedResult" == "$expectedResult" ]
    then
        passedTests=$((  passedTests + 1 ))

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

echo "Passed ${passedTests} / ${numTests} tests"

