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

##### 1. Comando COMPRIMIR
O comando **COMPRIMIR** faz a compressão de uma série de arquivos e é aceito contanto que siga o padrão: 
```
COMPRIMIR arq.ext1, arq2.ext2, ..., arqN.extN PARA  arqComp.extTar
```
Em que *arq1* até *arqN* fazem referëncia a **lista de arquivos** a serem comprimidos, assim como *ext1* até *extN* são **suas variadas extensões**, além disso *arqComp* faz jus ao nome dado ao **arquivo comprimido** e *extTar* é uma dentre as **extensões suportadas**, sendo elas: *.tar*, *.tar.gz*, *tar.bz2* e *tar.xz*.

Ex.:
```
COMPRIMIR a1.txt a2.txt a3.txt PARA teste.tar
```

##### 2. Comando EXTRAIR
O comando **EXTRAIR** realiza a extração de arquivos comprimidos e possui algumas variantes, e são elas **(a)** sua forma simples, **(b)** sua forma que específica os arquivos a serem descompactados e **(c)** sua forma que especifíca o diretório para qual os arquivos descompactados devem ir
- **(a)**:
```
EXTRAIR teste.tar
```

- **(b)**:
```
EXTRAIR teste.tar.gz SOMENTE a1.txt
```

- **(c)**:
```
EXTRAIR teste.tar.gz PARA casos-de-teste/
EXTRAIR teste.tar.gz SOMENTE a1.txt a2.txt PARA casos-de-teste/
```

##### 3. Comando LISTAR
O comando **LISTAR** realiza a listagem dos arquivos presentes em uma compressão dada e segue regras simples que basta digitar: 
```
LISTAR arqComp.extTar
```
Em que **arqComp** é o nome do arquivo compactado e **extTar** é uma dentre as extensões aceitas pelo compilador, sendo elas: *.tar*, *.tar.gz*, *tar.bz2* e *tar.xz*.

##### 4. Comando TAMANHO
O comando **TAMANHO** devolve um comando que sumariza o tamanho do arquivo compactado quanto ao número de bytes. Para utilizá-lo basta seguir o padroão:
```
TAMANHO arqComp.extTar
```
Em que **arqComp** é o nome do arquivo compactado e **extTar** é uma dentre as extensões aceitas pelo compilador, sendo elas: *.tar*, *.tar.gz*, *tar.bz2* e *tar.xz*.

##### 5. Comando ADICIONAR
O comando **ADICIONAR** é responsável por adicionar um arquivo qualquer a um compactado sem a necessidade de descompactá-lo e então compactá-lo novamente para então ser possível incluir o novo arquivo. 

O comando é bem explicado a partir de exemplos, como abaixo:
```
ADICIONAR a1.txt PARA teste.tar
ADICIONAR a1.txt a2.txt PARA teste.tar.xz
```

------------


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
#### Adicione dentro de ```casos-de-teste/teste.txt``` os comandos que deseja compilar
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
#### Em ```casos-de-teste/saida.txt``` estará a saida compilada

> É possível que o comando ou script não funcione corretamente devido a espaços no diretório do arquivo
