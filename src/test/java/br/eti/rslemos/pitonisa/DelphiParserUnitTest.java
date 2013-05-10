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
package br.eti.rslemos.pitonisa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DelphiParserUnitTest extends AbstractDelphiParserUnitTest {
	@Test
	public void testUnit001() throws Exception {
		parse(getClass().getResourceAsStream("Unit001.pas"));
		
		enterGoal();
		 enterUnit();
		  enterIdent();
		  assertThat(exitIdent().toStringTree(ruleNames), is(equalTo("(ident Unit001)")));
		 exitUnit();
		exitGoal();
	}

	@Test
	public void testUnit002() throws Exception {
		parse(getClass().getResourceAsStream("Unit002.pas"));
		
		enterGoal();
		 enterUnit();
		  enterIdent();
		  exitIdent();
		  enterInterfaceSection();
		  exitInterfaceSection();
		 exitUnit();
		exitGoal();
	}

	@Test
	public void testUnit003() throws Exception {
		parse(getClass().getResourceAsStream("Unit003.pas"));
		
		enterGoal();
		 enterUnit();
		  enterIdent();
		  exitIdent();
		  enterImplementationSection();
		  exitImplementationSection();
		 exitUnit();
		exitGoal();
	}

	@Test
	public void testUnit004() throws Exception {
		parse(getClass().getResourceAsStream("Unit004.pas"));
		
		enterInterfaceSection();
			enterUsesClause();
				enterIdentList();
					enterIdent();
					assertThat(exitIdent().toStringTree(ruleNames), is(equalTo("(ident uses0)")));
				exitIdentList();
			exitUsesClause();
		exitInterfaceSection();
	}

	@Test
	public void testUnit005() throws Exception {
		parse(getClass().getResourceAsStream("Unit005.pas"));
		
		enterUsesClause();
			enterIdentList();
				enterIdent();
				assertThat(exitIdent().toStringTree(ruleNames), is(equalTo("(ident uses0)")));
				enterIdent();
				assertThat(exitIdent().toStringTree(ruleNames), is(equalTo("(ident uses1)")));
			exitIdentList();
		exitUsesClause();
	}

	@Test
	public void testUnit006() throws Exception {
		parse(getClass().getResourceAsStream("Unit006.pas"));
		
		// (interfaceSection INTERFACE (interfaceDecl (constSection CONST (constDecl (ident const0) = (constExpr (number 10))) ; (constDecl (ident const1) = (constExpr (string 'value'))) ;)))
		enterInterfaceSection();
			enterInterfaceDecl();
				enterConstSection();
					enterConstDecl();
						enterIdent();
						assertThat(exitIdent().toStringTree(ruleNames), is(equalTo("(ident const0)")));
						enterConstExpr();
							enterNumber();
							assertThat(exitNumber().toStringTree(ruleNames), is(equalTo("(number 10)")));
						exitConstExpr();
					exitConstDecl();
					enterConstDecl();
						enterIdent();
						assertThat(exitIdent().toStringTree(ruleNames), is(equalTo("(ident const1)")));
						enterConstExpr();
							enterString();
							assertThat(exitString().toStringTree(ruleNames), is(equalTo("(string 'value')")));
						exitConstExpr();
					exitConstDecl();
				exitConstSection();
			exitInterfaceDecl();
		exitInterfaceSection();
	}

	@Test
	public void testUnit007() throws Exception {
		parse(getClass().getResourceAsStream("Unit007.pas"));
		
		// (interfaceDecl (varSection VAR (varDecl (identList (ident var0) , (ident var1)) : (type (simpleType (ordinalType (ordIdent INTEGER)))) = (constExpr (number 10))) ; (varDecl (identList (ident var2)) : (type (typeId (ident string)))) ; (varDecl (identList (ident var3)) : (type (typeId (ident Byte))) ABSOLUTE (ident var2)) ;)))
		enterInterfaceSection();
			enterInterfaceDecl();
				enterVarSection();
					enterVarDecl();
						enterIdentList();
						assertThat(exitIdentList().toStringTree(ruleNames), is(equalTo("(identList (ident var0) , (ident var1))")));
						enterType();
							enterSimpleType();
								enterOrdinalType();
									enterOrdIdent();
									assertThat(exitOrdIdent().toStringTree(ruleNames), is(equalTo("(ordIdent INTEGER)")));
								exitOrdinalType();
							exitSimpleType();
						exitType();
						enterConstExpr();
							enterNumber();
							assertThat(exitNumber().toStringTree(ruleNames), is(equalTo("(number 10)")));
						exitConstExpr();
					exitVarDecl();
					enterVarDecl();
						enterIdentList();
						assertThat(exitIdentList().toStringTree(ruleNames), is(equalTo("(identList (ident var2))")));
						enterType();
							enterTypeId();
							// BAD
							assertThat(exitTypeId().toStringTree(ruleNames), is(equalTo("(typeId (ident string))")));
						exitType();
					exitVarDecl();
					enterVarDecl();
						enterIdentList();
						assertThat(exitIdentList().toStringTree(ruleNames), is(equalTo("(identList (ident var3))")));
						enterType();
							enterTypeId();
							// BAD?
							assertThat(exitTypeId().toStringTree(ruleNames), is(equalTo("(typeId (ident Byte))")));
						exitType();
						enterIdent();
						assertThat(exitIdent().toStringTree(ruleNames), is(equalTo("(ident var2)")));
					exitVarDecl();
				exitVarSection();
			exitInterfaceDecl();
		exitInterfaceSection();
		
	}

	@Test
	public void testUnit008() throws Exception {
		parse(getClass().getResourceAsStream("Unit008.pas"));
		// (interfaceSection INTERFACE (interfaceDecl (exportedHeading (functionHeading FUNCTION (ident function0) (formalParms ( (formalParm (parameter (identList (ident parm0) , (ident parm1)) : STRING)) ; (formalParm (parameter (identList (ident parm2)) : (simpleType (realType CURRENCY)))) ; (formalParm (parameter (identList (ident parm3) , (ident parm4) , (ident parm5)) : (simpleType (ordinalType (ordIdent INTEGER))))) )) : (simpleType (ordinalType (ordIdent BOOLEAN)))) ;)) (interfaceDecl (exportedHeading (procedureHeading PROCEDURE (ident procedure0) (formalParms ( (formalParm (parameter (identList (ident parm0) , (ident parm1)) : (simpleType (ordinalType (ordIdent INTEGER))))) ))) ;)))
		enterInterfaceSection();
			enterInterfaceDecl();
				enterExportedHeading();
				exitExportedHeading();
			exitInterfaceDecl();
		exitInterfaceSection();
		
	}

}
