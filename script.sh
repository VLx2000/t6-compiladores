#!/usr/bin/bash

ARG1="$PWD/corretor-automatico/corretor-automatico.jar"
ARG2="java -jar $PWD/target/analisador-1.0-SNAPSHOT-jar-with-dependencies.jar"
ARG3="gcc"
ARG4="$PWD/tmp"
ARG5="$PWD/casos-de-teste"
ARG6="769699,769681,769734"
ARG7="$1"

chmod +x $PWD/target/analisador-1.0-SNAPSHOT-jar-with-dependencies.jar
java -jar $ARG1 "$ARG2" $ARG3 $ARG4 $ARG5 $ARG6 $ARG7
