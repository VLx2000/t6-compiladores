grammar Tar;

programa: acao+ EOF;

acao: comprimir | extrair | tamanho | listar | adicionar;

comprimir: 'COMPRIMIR' ARQUIVO+ 'PARA' TAR;

extrair:
	'EXTRAIR' TAR ('SOMENTE' ARQUIVO+)? ('PARA' DIRETORIO)?
	| 'EXTRAIR' TAR 'ARQUIVOS' LIST_EXTRACT;

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

VARIABLE: ('a' ..'z' | 'A' ..'Z') (
		'a' ..'z'
		| 'A' ..'Z'
		| '0' ..'9'
		| '_'
		| '-'
	)*;

LIST_EXTRACT:
	ARQUIVO 'PARA' DIRETORIO ('E' ARQUIVO 'PARA' DIRETORIO)*;

TAR: VARIABLE '.' TIPO;
ARQUIVO: VARIABLE '.' VARIABLE;
DIRETORIO: VARIABLE '/' VARIABLE*;

TIPO: 'tar' | 'tar.gz' | 'tar.bz2' | 'tar.xz';

WS: ( ' ' | '\t' | '\r' | '\n') {skip();};

UNKNOWN: .;