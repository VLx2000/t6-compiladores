grammar Tar;

programa
	:	.;

KEYWORD:
	'DESCOMPRIMIR'
	| 'COMPRIMIR'
	| 'VERIFICAR'
	| 'LISTAR'
	| 'ARQUIVO'
	| 'TIPO'
	| 'PARA';

VARIABLE: ('a' ..'z' | 'A' ..'Z') (
		'a' ..'z'
		| 'A' ..'Z'
		| '0' ..'9'
		| '_'
	)*;
ARQUIVO: VARIABLE '.' TIPO;
TIPO: 'tar' | 'tar.gz' | 'tar.bz2';

WS: ( ' ' | '\t' | '\r' | '\n') {skip();};

UNKNOWN: .;