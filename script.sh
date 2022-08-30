#!/usr/bin/bash

ARG1="$PWD/target/tar-1.0-SNAPSHOT-jar-with-dependencies.jar"
ARG2="$PWD/casos-de-teste/teste.txt"
ARG3="$PWD/casos-de-teste/saida.txt"
ARG4="Alunos: 769726,769734"

chmod +x $PWD/target/tar-1.0-SNAPSHOT-jar-with-dependencies.jar
java -jar $ARG1 $ARG2 $ARG3

echo $ARG4