grammar Tar;

programa: acao+ EOF;

acao: comprimir | extrair;

comprimir: 'COMPRIMIR' ARQUIVO+ 'PARA' TAR;

extrair: 'EXTRAIR' TAR ('SOMENTE' ARQUIVO+)? ('PARA' DIRETORIO)?;

listar: 'LISTAR' TAR;

tamanho: 'TAMANHO' TAR;

KEYWORD: 'VERIFICAR' | 'LISTAR' | 'ARQUIVO' | 'TIPO' | 'PARA' | 'SOMENTE';

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

TIPO: 'tar' | 'tar.gz' | 'tar.bz2';

WS: ( ' ' | '\t' | '\r' | '\n') {skip();};

UNKNOWN: .;