# Compiladores: Compilador para utilitário tar

## Integrantes ##

- Eduardo dos Santos Gualberto 769726
- Victor Luís Aguilar Antunes 769734

## Dependências
- Java 11
- Maven
- GCC/MinGW
## Ubuntu
```
sudo apt install openjdk-11-jdk maven
```

### Para executar
#### Compile o programa
```
mvn clean package
```

#### E rode o script com 
```
chmod +x ./script.sh && ./script.sh
```

#### Ou execute o seguinte comando na pasta do projeto
```
java -jar ./target/tar-1.0-SNAPSHOT-jar-with-dependencies.jar casos-de-teste/teste.txt casos-de-teste/saida.txt
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
java -jar .\target\tar-1.0-SNAPSHOT-jar-with-dependencies.jar casos-de-teste\teste.txt casos-de-teste\saida.txt          
```
