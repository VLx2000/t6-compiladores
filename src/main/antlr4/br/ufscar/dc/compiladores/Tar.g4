grammar Tar;

programa: acao+ EOF;

acao: comprimir | extrair;

comprimir: 'COMPRIMIR' ARQUIVO+ 'PARA' TAR;

extrair: 'EXTRAIR' TAR 'ARQUIVOS' ARQUIVO+ 'PARA DIRETORIO' DIRETORIO
		|'EXTRAIR' TAR ('PARA DIRETORIO' DIRETORIO)?;

KEYWORD: 'VERIFICAR' | 'LISTAR' | 'ARQUIVO' | 'TIPO' | 'PARA';

VARIABLE: ('a' ..'z' | 'A' ..'Z') (
		'a' ..'z'
		| 'A' ..'Z'
		| '0' ..'9'
		| '_'
	)*;

TAR: VARIABLE '.' TIPO;
ARQUIVO: VARIABLE '.' VARIABLE;
DIRETORIO: VARIABLE ('/' VARIABLE)*;

TIPO: 'tar' | 'tar.gz' | 'tar.bz2';

WS: ( ' ' | '\t' | '\r' | '\n') {skip();};

UNKNOWN: .;