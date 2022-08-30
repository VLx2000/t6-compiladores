# Compiladores: Desenvolvimento de analisadores

## Integrantes ##

- Eduardo dos Santos Gualberto 769726
- Victor Luís Aguilar Antunes 769734

## Dependências
- Java 11
- Maven
- GCC/MinGW
## Ubuntu
```
sudo apt install openjdk-11-jdk maven gcc
```

### Para executar
#### Compile o programa
```
mvn clean package
```

#### E rode o script com 
```
chmod +x ./script.sh && ./script.sh tx
```
> Sendo x o número do trabalho

#### Ou execute o seguinte comando na pasta do projeto
```
chmod +x ./target/analisador-1.0-SNAPSHOT-jar-with-dependencies.jar &&
java -jar ./corretor-automatico/corretor-automatico.jar \
"java -jar $PWD/target/analisador-1.0-SNAPSHOT-jar-with-dependencies.jar" \
gcc \
./tmp \
./casos-de-teste \
"769699,769681,769734" \
t1,t2,t3,t4,t5
```
> É possível que o comando ou script não funcione corretamente devido a espaços no diretório do arquivo

## Windows
> Considerando que o java, maven e gcc estão no PATH

### Para executar
#### Compile o programa
```
mvn clean package
```

#### E execute no diretório ```compiladores```
```
java -jar .\corretor-automatico\corretor-automatico.jar  "java -jar .\target\analisador-1.0-SNAPSHOT-jar-with-dependencies.jar" gcc ./tmp  .\casos-de-teste\ "769699,769681,769734" t1,t2,t3,t4,t5
```
## Outros
```
mvn clean package
```
```
java -jar ARG1 "java -jar ARG2" ARG3 ARG4 ARG5 "769699,769681,769734" t1,t2,t3,t4,t5
```
Substituindo:
- ARG1 pelo caminho absoluto de corretor-automatico.jar
- ARG2 pelo caminho absoluto de analisador-1.0-SNAPSHOT-jar-with-dependencies.jar
- ARG3 pelo compilador gcc
- ARG4 pelo caminho absoluto da pasta tmp dentro do projeto
- ARG5 pelo caminho absoluto da pasta casos-de-teste dentro do projeto

> Obs1:
> java -jar ./target/analisador-1.0-SNAPSHOT-jar-with-dependencies.jar algum_arquivo_de_teste.txt saida.txt para executar somente um caso

> Obs2: Os casos de teste e o corretor automático já estão inclusos no projeto
# t6-compiladores
