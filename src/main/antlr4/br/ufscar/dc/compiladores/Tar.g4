grammar Tar;

programa: acao acao*;

acao: comprimir | extrair;

comprimir: 'COMPRIMIR' ARQUIVO+ 'PARA' TAR;

extrair: 'EXTRAIR' TAR 'ARQUIVOS' ARQUIVO+ 'PARA DIRETORIO' VARIABLE
		|'EXTRAIR' TAR ('PARA DIRETORIO' VARIABLE)?;

KEYWORD: 'VERIFICAR' | 'LISTAR' | 'ARQUIVO' | 'TIPO' | 'PARA';

VARIABLE: ('a' ..'z' | 'A' ..'Z') (
		'a' ..'z'
		| 'A' ..'Z'
		| '0' ..'9'
		| '_'
	)*;

TAR: VARIABLE '.' TIPO;
ARQUIVO: VARIABLE '.' VARIABLE;

TIPO: 'tar' | 'tar.gz' | 'tar.bz2';

WS: ( ' ' | '\t' | '\r' | '\n') {skip();};

UNKNOWN: .;