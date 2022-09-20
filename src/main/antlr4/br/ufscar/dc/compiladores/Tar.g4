grammar Tar;

programa: configuracao? acao+ EOF;

configuracao: 'CONFIG' configs (',' configs)* 'END_CONFIG';

acao: comprimir | extrair | tamanho | listar | adicionar;

comprimir: 'COMPRIMIR' ARQUIVO+ 'PARA' TAR;

extrair:
	'EXTRAIR' TAR ('SOMENTE' ARQUIVO+)? ('PARA' DIRETORIO)?;

listar: 'LISTAR' TAR;

tamanho: 'TAMANHO' TAR;

adicionar: 'ADICIONAR' ARQUIVO+ 'PARA' TAR;

KEYWORD:
	'VERIFICAR'
	| 'LISTAR'
	| 'ARQUIVO'
	| 'TIPO'
	| 'PARA'
	| 'SOMENTE';


configs: CONFIG_KEYS (':' CONFIG_VALUES)?;

CONFIG_KEYS: 'NIVEL_VERBOSO' | 'INTERATIVO' | 'MANTER_PERMISSOES' | 'FORMATO';

CONFIG_VALUES: VERBOSE_LEVEL | FORMATO;

FORMATO: 'gnu' | 'oldgnu' | 'pax' | 'posix' | 'ustar' | 'v7';

VERBOSE_LEVEL: ('0' .. '3');

VARIABLE: ('a' ..'z' | 'A' ..'Z') (
		'a' ..'z'
		| 'A' ..'Z'
		| '0' ..'9'
		| '_'
		| '-'
	)*;

TAR: VARIABLE '.' TIPO;
ARQUIVO: VARIABLE '.' VARIABLE;
DIRETORIO: VARIABLE '/' VARIABLE*;

TIPO: 'tar' | 'tar.gz' | 'tar.bz2' | 'tar.xz';

WS: ( ' ' | '\t' | '\r' | '\n') {skip();};

UNKNOWN: .;