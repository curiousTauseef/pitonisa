/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "pitonisa"
 * Copyright 2013  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
grammar delphi;

UNIT			: 'UNIT';
INTERFACE		: 'INTERFACE';
USES			: 'USES';
CONST			: 'CONST';
VAR				: 'VAR';
ABSOLUTE		: 'ABSOLUTE';
PROCEDURE		: 'PROCEDURE';
FUNCTION		: 'FUNCTION';
OUT				: 'OUT';
ARRAY			: 'ARRAY';
OF				: 'OF';
FILE			: 'FILE';
REAL48			: 'REAL48';
REAL			: 'REAL';
SINGLE			: 'SINGLE';
DOUBLE			: 'DOUBLE';
EXTENDED		: 'EXTENDED';
CURRENCY		: 'CURRENCY';
COMP			: 'COMP';
SHORTINT		: 'SHORTINT';
SMALLINT		: 'SMALLINT';
INTEGER			: 'INTEGER';
BYTE			: 'BYTE';
LONGINT			: 'LONGINT';
INT64			: 'INT64';
WORD			: 'WORD';
BOOLEAN			: 'BOOLEAN';
CHAR			: 'CHAR';
WIDECHAR		: 'WIDECHAR';
LONGWORD		: 'LONGWORD';
PCHAR			: 'PCHAR';
STRING			: 'STRING';
ANSISTRING		: 'ANSISTRING';
WIDESTRING		: 'WIDESTRING';

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

LITERAL
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
	| exportedHeading
	;
	
constSection
	: CONST (constDecl ';')+
	;

constDecl
	: ident '=' constExpr
//	| ident ':' typeId '=' typedConstant
	;

varSection
	: VAR (varDecl ';')+
	;

varDecl
	: identList ':' type ((ABSOLUTE (ident | constExpr)) | '=' constExpr)?
	;

exportedHeading
	: procedureHeading ';'
	| functionHeading ';'
	;

implementationSection
	: IMPLEMENTATION
	;

procedureHeading
	: PROCEDURE ident formalParms?
	;

functionHeading
	: FUNCTION ident formalParms? ':' simpleType
	;

formalParms
	: '(' (formalParm (';' formalParm)*)? ')'
	;

formalParm
	: (VAR | CONST | OUT)? parameter
	;

parameter
	: identList (':' ((ARRAY OF)? parameterType | STRING | FILE))?
	| ident ':' simpleType '=' constExpr
	;	

parameterType
	: simpleType
	| qualId
	;

simpleType
	: ordinalType | realType
	;

ordinalType
	: ordIdent
//	| subrangeType 
//	| enumeratedType 
	;

ordIdent
	: SHORTINT
	| SMALLINT
	| INTEGER
	| BYTE
	| LONGINT
	| INT64
	| WORD
	| BOOLEAN
	| CHAR
	| WIDECHAR
	| LONGWORD
	| PCHAR
	;

realType
	: REAL48
	| REAL
	| SINGLE
	| DOUBLE
	| EXTENDED
	| CURRENCY
	| COMP
	;

typedConstant
	: constExpr	
	;

constExpr
	: string
	| number
	;

stringType
	: STRING
	| ANSISTRING
	| WIDESTRING
	| STRING '[' constExpr ']'
	;

type
	: typeId
	| simpleType
	| stringType
	;
	
typeId
	: (unitId '.')? ident
	;

qualId
	: ident ('.' ident)*
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
	: LITERAL
	;

number
	: INT
	| FLOAT
	;
	