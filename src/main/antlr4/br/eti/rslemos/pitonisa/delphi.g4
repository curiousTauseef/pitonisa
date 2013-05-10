grammar delphi;

UNIT			: 'UNIT';
INTERFACE		: 'INTERFACE';
USES			: 'USES';
CONST			: 'CONST';
VAR				: 'VAR';
ABSOLUTE		: 'ABSOLUTE';
IMPLEMENTATION	: 'IMPLEMENTATION';

ID	: ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

INT	: '0'..'9'+
	;

FLOAT
	: ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
	| '.' ('0'..'9')+ EXPONENT?
	| ('0'..'9')+ EXPONENT
	;

WS
	: ( ' '
	  | '\t'
	  | '\r'
	  | '\n'
	  ) -> channel(HIDDEN)
	;

COMMENT
	: ( '//' ~('\n'|'\r')* '\r'? '\n' 
	  | '(*' .*? '*)' 
	  | '{' .*? '}' 
	  ) -> channel(HIDDEN)
	;

STRING
	: '\'' ( ESC_SEQ | ~('\\'|'\'') )* '\''
	;

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
	: '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
	| UNICODE_ESC
	| OCTAL_ESC
	;

fragment
OCTAL_ESC
	: '\\' ('0'..'3') ('0'..'7') ('0'..'7')
	| '\\' ('0'..'7') ('0'..'7')
	| '\\' ('0'..'7')
	;

fragment
UNICODE_ESC
	: '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
	;


goal
	: unit
	;

unit
	: UNIT ident ';' interfaceSection? implementationSection? '.'
	;

interfaceSection
	: INTERFACE usesClause? interfaceDecl*
	;

usesClause
	: USES identList ';'
	;

interfaceDecl
	: constSection
	| varSection
	;
	
constSection
	: CONST (constDecl ';')+
	;

constDecl
	: ident '=' constExpr
	| ident ':' typeId '=' typedConstant
	;

varSection
	: VAR (varDecl ';')+
	;

varDecl
	: identList ':' type ((ABSOLUTE (ident | constExpr)) | '=' constExpr)?
	;

implementationSection
	: IMPLEMENTATION
	;

typedConstant
	: constExpr	
	;

constExpr
	: string
	| number
	;

type
	: typeId
	;
	
typeId
	: (unitId '.')? ident
	;

unitId
	: ident
	;

ident
	: ID
	;

identList
	: ident (',' ident)*
	;

string
	: STRING
	;

number
	: INT
	| FLOAT
	;
	