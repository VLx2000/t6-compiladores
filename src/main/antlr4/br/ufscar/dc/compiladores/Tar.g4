grammar Tar;

//descrição mais abstrate de como um programa deve ser
programa: configuracao? acao+ EOF;

//o formato do header de configuração
configuracao: 'CONFIG' configs (',' configs)* 'END_CONFIG';

//descreve todos os tipos de ações que existem no programa
acao: comprimir | extrair | tamanho | listar | adicionar;

//formato aceito para a ação 'comprimir'
comprimir: 'COMPRIMIR' ARQUIVO+ 'PARA' TAR;

//formato aceito para a ação 'extrair'
extrair:
	'EXTRAIR' TAR ('SOMENTE' ARQUIVO+)? ('PARA' DIRETORIO)?;

//formato aceito para a ação 'listar'
listar: 'LISTAR' TAR;

//formato aceito para a ação 'tamanho'
tamanho: 'TAMANHO' TAR;

//formato aceito para a ação 'adicionar'
adicionar: 'ADICIONAR' ARQUIVO+ 'PARA' TAR;

//abaixo as regras léxicas do compilador
KEYWORD:
	'VERIFICAR'
	| 'LISTAR'
	| 'ARQUIVO'
	| 'TIPO'
	| 'PARA'
	| 'SOMENTE';


configs: CONFIG_KEYS (':' CONFIG_VALUES)?;

//as chaves que correspondem aos nomes das configurações aceitas pelo compilador
CONFIG_KEYS: 'NIVEL_VERBOSO' | 'INTERATIVO' | 'MANTER_PERMISSOES' | 'FORMATO';

//os valores aceitos pelo compilador, cada um com seu formato e algumas chaves não aceitam valores
CONFIG_VALUES: VERBOSE_LEVEL | FORMATO;

//todos os formatos de arquivos tar suportados
FORMATO: 'gnu' | 'oldgnu' | 'pax' | 'posix' | 'ustar' | 'v7';

VERBOSE_LEVEL: ('0' .. '3');

VARIABLE: ('a' ..'z' | 'A' ..'Z') (
		'a' ..'z'
		| 'A' ..'Z'
		| '0' ..'9'
		| '_'
		| '-'
	)*;

//regra que descreve como um arquivo TAR deve ser referenciado
TAR: VARIABLE '.' TIPO;

//regra que descreve como um arquivo comum deve ser referenciado
ARQUIVO: VARIABLE '.' VARIABLE;

//regra que descreve como um diretório TAR deve ser referenciado
DIRETORIO: VARIABLE '/' VARIABLE*;

TIPO: 'tar' | 'tar.gz' | 'tar.bz2' | 'tar.xz';

WS: ( ' ' | '\t' | '\r' | '\n') {skip();};

UNKNOWN: .;