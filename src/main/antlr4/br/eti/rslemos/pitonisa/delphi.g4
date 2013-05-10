grammar delphi;

UNIT			: 'UNIT';
INTERFACE		: 'INTERFACE';


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
	: UNIT ident ';' interfaceSection? '.'
	;

interfaceSection
	: INTERFACE
	;
	
ident
	: ID
	;