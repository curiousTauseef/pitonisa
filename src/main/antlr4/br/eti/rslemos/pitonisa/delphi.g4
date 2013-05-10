grammar delphi;

UNIT			: 'UNIT';


ID	: ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

WS
	: ( ' '
	  | '\t'
	  | '\r'
	  | '\n'
	  ) -> channel(HIDDEN)
	;

goal
	: unit
	;

unit
	: UNIT ident ';'
	;

ident
	: ID
	;