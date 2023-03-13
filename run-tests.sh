#!/usr/bin/env bash

javaProgram=$1

for file in tests/*
do
    echo === $file ===
    cat $file | java $javaProgram
    echo
done

