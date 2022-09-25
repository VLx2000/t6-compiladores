# Compiladores: Compilador para utilitário tar

## Integrantes ##

- Eduardo dos Santos Gualberto 769726
- Victor Luís Aguilar Antunes 769734

### Descrição
Tar é um aplicativo capaz de armazenar vários arquivos em um só. Muito utilizado em ambientes Linux, esse trabalho busca criar um compilador de uma linguagem mais acessível e de fácil uso, para os comandos tar.

![meme tar](https://imgs.xkcd.com/comics/tar.png)

### Vídeo com demonstração

![video]()

### Sobre a linguagem
A linguagem suporta os comandos mais usados do tar como:

O que faz | Palavra
--------- | ---------
Compressão de arquivos | COMPRIMIR
Extração de arquivos | EXTRAIR
Listagem de arquivos | LISTAR
Visualização de tamanho de arquivo | TAMANHO
Adicionar à arquivo compactado | ADICIONAR

Além de suportar também alguns parâmetros para melhor uso:
O que faz | Palavra
--------- | ---------
Nível de informações durante execução | NIVEL_VERBOSO : (0 à 3)
Pedir confirmação para ações | INTERATIVO (flag booleana) 
Manter permissões de grupo, acesso e proprietário dos arquivos | MANTER_PERMISSOES (flag booleana) 
Formato do arquivo | FORMATO : ('gnu', 'oldgnu', 'pax', 'posix', 'ustar', 'v7')

Nela, é possível gerar, à partir de parâmetros pré definidos, vários comandos de uma só vez

Para isso, o formato da linguagem é o seguinte:
```
CONFIG (um ou mais parâmetros aqui) END_CONFIG
(açoes entre comprimir | extrair | tamanho | listar | adicionar)
```
Ex:
```
CONFIG NIVEL_VERBOSO : 2 END_CONFIG
COMPRIMIR a1.txt a2.txt a3.txt PARA teste.tar.gz
EXTRAIR teste.tar.gz SOMENTE a1.txt
LISTAR teste.tar.gz
```

e, com isso, a saída seria a seguinte:
```
tar -czvv -f teste.tar.gz a1.txt a2.txt a3.txt
tar -xzvv -f teste.tar.gz a1.txt
tar -tvvf teste.tar.gz
```

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
