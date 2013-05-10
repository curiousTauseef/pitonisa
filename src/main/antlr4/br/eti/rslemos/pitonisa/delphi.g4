grammar delphi;

UNIT			: 'UNIT';
INTERFACE		: 'INTERFACE';
USES			: 'USES';
IMPLEMENTATION	: 'IMPLEMENTATION';

ID	: ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
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


goal
	: unit
	;

unit
	: UNIT ident ';' interfaceSection? implementationSection? '.'
	;

interfaceSection
	: INTERFACE usesClause?
	;

usesClause
	: USES identList ';'
	;
	
implementationSection
	: IMPLEMENTATION
	;
	
ident
	: ID
	;

identList
	: ident (',' ident)*
	;
