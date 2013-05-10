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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenSource;
import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.eti.rslemos.pitonisa.delphiParser.ConstDeclContext;
import br.eti.rslemos.pitonisa.delphiParser.ConstExprContext;
import br.eti.rslemos.pitonisa.delphiParser.ConstSectionContext;
import br.eti.rslemos.pitonisa.delphiParser.ExportedHeadingContext;
import br.eti.rslemos.pitonisa.delphiParser.FormalParmContext;
import br.eti.rslemos.pitonisa.delphiParser.FormalParmsContext;
import br.eti.rslemos.pitonisa.delphiParser.FunctionHeadingContext;
import br.eti.rslemos.pitonisa.delphiParser.GoalContext;
import br.eti.rslemos.pitonisa.delphiParser.IdentContext;
import br.eti.rslemos.pitonisa.delphiParser.IdentListContext;
import br.eti.rslemos.pitonisa.delphiParser.ImplementationSectionContext;
import br.eti.rslemos.pitonisa.delphiParser.InterfaceDeclContext;
import br.eti.rslemos.pitonisa.delphiParser.InterfaceSectionContext;
import br.eti.rslemos.pitonisa.delphiParser.NumberContext;
import br.eti.rslemos.pitonisa.delphiParser.OrdIdentContext;
import br.eti.rslemos.pitonisa.delphiParser.OrdinalTypeContext;
import br.eti.rslemos.pitonisa.delphiParser.ParameterContext;
import br.eti.rslemos.pitonisa.delphiParser.ProcedureHeadingContext;
import br.eti.rslemos.pitonisa.delphiParser.RealTypeContext;
import br.eti.rslemos.pitonisa.delphiParser.SimpleTypeContext;
import br.eti.rslemos.pitonisa.delphiParser.StringContext;
import br.eti.rslemos.pitonisa.delphiParser.StringTypeContext;
import br.eti.rslemos.pitonisa.delphiParser.TypeContext;
import br.eti.rslemos.pitonisa.delphiParser.TypeIdContext;
import br.eti.rslemos.pitonisa.delphiParser.TypedConstantContext;
import br.eti.rslemos.pitonisa.delphiParser.UnitContext;
import br.eti.rslemos.pitonisa.delphiParser.UnitIdContext;
import br.eti.rslemos.pitonisa.delphiParser.UsesClauseContext;
import br.eti.rslemos.pitonisa.delphiParser.VarDeclContext;
import br.eti.rslemos.pitonisa.delphiParser.VarSectionContext;

/*
 * use these to implement new rules
 * 	
 * from: public void (exit.*)\((.*) ctx\) \{\n\s*listener.\1\(ctx\);\n\s*\}
 * to: private void $1($2 ctx) {\n		v.$1(ctx);\n	}\n	\n	protected $2 $1() {\n		ArgumentCaptor<$2> captor = ArgumentCaptor.forClass($2.class);\n		$1(captor.capture());\n		return captor.getValue();\n	}
 * 
 * from: public void (enter.*)\((.*) ctx\) \{\n\s*listener.\1\(ctx\);\n\s*\}
 * to: private void $1($2 ctx) {\n		v.$1(ctx);\n	}\n	\n	protected void $1() {\n		$1(any($2.class));\n	}
 */
public class AbstractDelphiParserUnitTest {

	protected static final List<String> ruleNames = Arrays.asList(delphiParser.ruleNames);
	
	@Mock
	public delphiListener listener;
	
	protected InOrder order;

	private delphiListener v;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		order = inOrder(listener);
	}

	protected void parse(InputStream input) throws Exception {
		TokenSource lexer = new delphiLexer(new ANTLRInputStream(input));
		delphiParser parser = new delphiParser(new CommonTokenStream(lexer));
		parser.addParseListener(listener);
		parser.goal();
		
		v = order.verify(listener);
	}

	private void enterConstDecl(ConstDeclContext ctx) {
		v.enterConstDecl(ctx);
	}
	
	protected void enterConstDecl() {
		enterConstDecl(any(ConstDeclContext.class));
	}

	private void exitConstDecl(ConstDeclContext ctx) {
		v.exitConstDecl(ctx);
	}
	
	protected ConstDeclContext exitConstDecl() {
		ArgumentCaptor<ConstDeclContext> captor = ArgumentCaptor.forClass(ConstDeclContext.class);
		exitConstDecl(captor.capture());
		return captor.getValue();
	}

	private void enterStringType(StringTypeContext ctx) {
		v.enterStringType(ctx);
	}
	
	protected void enterStringType() {
		enterStringType(any(StringTypeContext.class));
	}

	private void exitStringType(StringTypeContext ctx) {
		v.exitStringType(ctx);
	}
	
	protected StringTypeContext exitStringType() {
		ArgumentCaptor<StringTypeContext> captor = ArgumentCaptor.forClass(StringTypeContext.class);
		exitStringType(captor.capture());
		return captor.getValue();
	}

	private void enterConstExpr(ConstExprContext ctx) {
		v.enterConstExpr(ctx);
	}
	
	protected void enterConstExpr() {
		enterConstExpr(any(ConstExprContext.class));
	}

	private void exitConstExpr(ConstExprContext ctx) {
		v.exitConstExpr(ctx);
	}
	
	protected ConstExprContext exitConstExpr() {
		ArgumentCaptor<ConstExprContext> captor = ArgumentCaptor.forClass(ConstExprContext.class);
		exitConstExpr(captor.capture());
		return captor.getValue();
	}

	private void enterVarDecl(VarDeclContext ctx) {
		v.enterVarDecl(ctx);
	}
	
	protected void enterVarDecl() {
		enterVarDecl(any(VarDeclContext.class));
	}

	private void exitVarDecl(VarDeclContext ctx) {
		v.exitVarDecl(ctx);
	}
	
	protected VarDeclContext exitVarDecl() {
		ArgumentCaptor<VarDeclContext> captor = ArgumentCaptor.forClass(VarDeclContext.class);
		exitVarDecl(captor.capture());
		return captor.getValue();
	}

	private void enterFormalParms(FormalParmsContext ctx) {
		v.enterFormalParms(ctx);
	}
	
	protected void enterFormalParms() {
		enterFormalParms(any(FormalParmsContext.class));
	}

	private void exitFormalParms(FormalParmsContext ctx) {
		v.exitFormalParms(ctx);
	}
	
	protected FormalParmsContext exitFormalParms() {
		ArgumentCaptor<FormalParmsContext> captor = ArgumentCaptor.forClass(FormalParmsContext.class);
		exitFormalParms(captor.capture());
		return captor.getValue();
	}

	private void enterTypedConstant(TypedConstantContext ctx) {
		v.enterTypedConstant(ctx);
	}
	
	protected void enterTypedConstant() {
		enterTypedConstant(any(TypedConstantContext.class));
	}

	private void exitTypedConstant(TypedConstantContext ctx) {
		v.exitTypedConstant(ctx);
	}
	
	protected TypedConstantContext exitTypedConstant() {
		ArgumentCaptor<TypedConstantContext> captor = ArgumentCaptor.forClass(TypedConstantContext.class);
		exitTypedConstant(captor.capture());
		return captor.getValue();
	}

	private void enterType(TypeContext ctx) {
		v.enterType(ctx);
	}
	
	protected void enterType() {
		enterType(any(TypeContext.class));
	}

	private void exitType(TypeContext ctx) {
		v.exitType(ctx);
	}
	
	protected TypeContext exitType() {
		ArgumentCaptor<TypeContext> captor = ArgumentCaptor.forClass(TypeContext.class);
		exitType(captor.capture());
		return captor.getValue();
	}

	private void enterOrdinalType(OrdinalTypeContext ctx) {
		v.enterOrdinalType(ctx);
	}
	
	protected void enterOrdinalType() {
		enterOrdinalType(any(OrdinalTypeContext.class));
	}

	private void exitOrdinalType(OrdinalTypeContext ctx) {
		v.exitOrdinalType(ctx);
	}
	
	protected OrdinalTypeContext exitOrdinalType() {
		ArgumentCaptor<OrdinalTypeContext> captor = ArgumentCaptor.forClass(OrdinalTypeContext.class);
		exitOrdinalType(captor.capture());
		return captor.getValue();
	}

	private void enterParameter(ParameterContext ctx) {
		v.enterParameter(ctx);
	}
	
	protected void enterParameter() {
		enterParameter(any(ParameterContext.class));
	}

	private void exitParameter(ParameterContext ctx) {
		v.exitParameter(ctx);
	}
	
	protected ParameterContext exitParameter() {
		ArgumentCaptor<ParameterContext> captor = ArgumentCaptor.forClass(ParameterContext.class);
		exitParameter(captor.capture());
		return captor.getValue();
	}

	private void enterIdent(IdentContext ctx) {
		v.enterIdent(ctx);
	}
	
	protected void enterIdent() {
		enterIdent(any(IdentContext.class));
	}

	private void exitIdent(IdentContext ctx) {
		v.exitIdent(ctx);
	}
	
	protected IdentContext exitIdent() {
		ArgumentCaptor<IdentContext> captor = ArgumentCaptor.forClass(IdentContext.class);
		exitIdent(captor.capture());
		return captor.getValue();
	}

	private void enterInterfaceDecl(InterfaceDeclContext ctx) {
		v.enterInterfaceDecl(ctx);
	}
	
	protected void enterInterfaceDecl() {
		enterInterfaceDecl(any(InterfaceDeclContext.class));
	}

	private void exitInterfaceDecl(InterfaceDeclContext ctx) {
		v.exitInterfaceDecl(ctx);
	}
	
	protected InterfaceDeclContext exitInterfaceDecl() {
		ArgumentCaptor<InterfaceDeclContext> captor = ArgumentCaptor.forClass(InterfaceDeclContext.class);
		exitInterfaceDecl(captor.capture());
		return captor.getValue();
	}

	private void enterRealType(RealTypeContext ctx) {
		v.enterRealType(ctx);
	}
	
	protected void enterRealType() {
		enterRealType(any(RealTypeContext.class));
	}

	private void exitRealType(RealTypeContext ctx) {
		v.exitRealType(ctx);
	}
	
	protected RealTypeContext exitRealType() {
		ArgumentCaptor<RealTypeContext> captor = ArgumentCaptor.forClass(RealTypeContext.class);
		exitRealType(captor.capture());
		return captor.getValue();
	}

	private void enterExportedHeading(ExportedHeadingContext ctx) {
		v.enterExportedHeading(ctx);
	}
	
	protected void enterExportedHeading() {
		enterExportedHeading(any(ExportedHeadingContext.class));
	}

	private void exitExportedHeading(ExportedHeadingContext ctx) {
		v.exitExportedHeading(ctx);
	}
	
	protected ExportedHeadingContext exitExportedHeading() {
		ArgumentCaptor<ExportedHeadingContext> captor = ArgumentCaptor.forClass(ExportedHeadingContext.class);
		exitExportedHeading(captor.capture());
		return captor.getValue();
	}

	private void enterProcedureHeading(ProcedureHeadingContext ctx) {
		v.enterProcedureHeading(ctx);
	}
	
	protected void enterProcedureHeading() {
		enterProcedureHeading(any(ProcedureHeadingContext.class));
	}

	private void enterEveryRule(ParserRuleContext ctx) {
		v.enterEveryRule(ctx);
	}
	
	protected void enterEveryRule() {
		enterEveryRule(any(ParserRuleContext.class));
	}

	private void exitProcedureHeading(ProcedureHeadingContext ctx) {
		v.exitProcedureHeading(ctx);
	}
	
	protected ProcedureHeadingContext exitProcedureHeading() {
		ArgumentCaptor<ProcedureHeadingContext> captor = ArgumentCaptor.forClass(ProcedureHeadingContext.class);
		exitProcedureHeading(captor.capture());
		return captor.getValue();
	}

	private void exitEveryRule(ParserRuleContext ctx) {
		v.exitEveryRule(ctx);
	}
	
	protected ParserRuleContext exitEveryRule() {
		ArgumentCaptor<ParserRuleContext> captor = ArgumentCaptor.forClass(ParserRuleContext.class);
		exitEveryRule(captor.capture());
		return captor.getValue();
	}

	private void enterFormalParm(FormalParmContext ctx) {
		v.enterFormalParm(ctx);
	}
	
	protected void enterFormalParm() {
		enterFormalParm(any(FormalParmContext.class));
	}

	private void exitFormalParm(FormalParmContext ctx) {
		v.exitFormalParm(ctx);
	}
	
	protected FormalParmContext exitFormalParm() {
		ArgumentCaptor<FormalParmContext> captor = ArgumentCaptor.forClass(FormalParmContext.class);
		exitFormalParm(captor.capture());
		return captor.getValue();
	}

	private void enterNumber(NumberContext ctx) {
		v.enterNumber(ctx);
	}
	
	protected void enterNumber() {
		enterNumber(any(NumberContext.class));
	}

	private void exitNumber(NumberContext ctx) {
		v.exitNumber(ctx);
	}
	
	protected NumberContext exitNumber() {
		ArgumentCaptor<NumberContext> captor = ArgumentCaptor.forClass(NumberContext.class);
		exitNumber(captor.capture());
		return captor.getValue();
	}

	private void enterOrdIdent(OrdIdentContext ctx) {
		v.enterOrdIdent(ctx);
	}
	
	protected void enterOrdIdent() {
		enterOrdIdent(any(OrdIdentContext.class));
	}

	private void exitOrdIdent(OrdIdentContext ctx) {
		v.exitOrdIdent(ctx);
	}
	
	protected OrdIdentContext exitOrdIdent() {
		ArgumentCaptor<OrdIdentContext> captor = ArgumentCaptor.forClass(OrdIdentContext.class);
		exitOrdIdent(captor.capture());
		return captor.getValue();
	}

	private void enterFunctionHeading(FunctionHeadingContext ctx) {
		v.enterFunctionHeading(ctx);
	}
	
	protected void enterFunctionHeading() {
		enterFunctionHeading(any(FunctionHeadingContext.class));
	}

	private void exitFunctionHeading(FunctionHeadingContext ctx) {
		v.exitFunctionHeading(ctx);
	}
	
	protected FunctionHeadingContext exitFunctionHeading() {
		ArgumentCaptor<FunctionHeadingContext> captor = ArgumentCaptor.forClass(FunctionHeadingContext.class);
		exitFunctionHeading(captor.capture());
		return captor.getValue();
	}

	private void enterGoal(GoalContext ctx) {
		v.enterGoal(ctx);
	}
	
	protected void enterGoal() {
		enterGoal(any(GoalContext.class));
	}

	private void exitGoal(GoalContext ctx) {
		v.exitGoal(ctx);
	}
	
	protected GoalContext exitGoal() {
		ArgumentCaptor<GoalContext> captor = ArgumentCaptor.forClass(GoalContext.class);
		exitGoal(captor.capture());
		return captor.getValue();
	}

	private void enterUnit(UnitContext ctx) {
		v.enterUnit(ctx);
	}
	
	protected void enterUnit() {
		enterUnit(any(UnitContext.class));
	}

	private void exitUnit(UnitContext ctx) {
		v.exitUnit(ctx);
	}
	
	protected UnitContext exitUnit() {
		ArgumentCaptor<UnitContext> captor = ArgumentCaptor.forClass(UnitContext.class);
		exitUnit(captor.capture());
		return captor.getValue();
	}

	private void enterIdentList(IdentListContext ctx) {
		v.enterIdentList(ctx);
	}
	
	protected void enterIdentList() {
		enterIdentList(any(IdentListContext.class));
	}

	private void exitIdentList(IdentListContext ctx) {
		v.exitIdentList(ctx);
	}
	
	protected IdentListContext exitIdentList() {
		ArgumentCaptor<IdentListContext> captor = ArgumentCaptor.forClass(IdentListContext.class);
		exitIdentList(captor.capture());
		return captor.getValue();
	}

	private void enterUsesClause(UsesClauseContext ctx) {
		v.enterUsesClause(ctx);
	}
	
	protected void enterUsesClause() {
		enterUsesClause(any(UsesClauseContext.class));
	}

	private void exitUsesClause(UsesClauseContext ctx) {
		v.exitUsesClause(ctx);
	}
	
	protected UsesClauseContext exitUsesClause() {
		ArgumentCaptor<UsesClauseContext> captor = ArgumentCaptor.forClass(UsesClauseContext.class);
		exitUsesClause(captor.capture());
		return captor.getValue();
	}

	private void enterInterfaceSection(InterfaceSectionContext ctx) {
		v.enterInterfaceSection(ctx);
	}
	
	protected void enterInterfaceSection() {
		enterInterfaceSection(any(InterfaceSectionContext.class));
	}

	private void exitInterfaceSection(InterfaceSectionContext ctx) {
		v.exitInterfaceSection(ctx);
	}
	
	protected InterfaceSectionContext exitInterfaceSection() {
		ArgumentCaptor<InterfaceSectionContext> captor = ArgumentCaptor.forClass(InterfaceSectionContext.class);
		exitInterfaceSection(captor.capture());
		return captor.getValue();
	}

	private void enterString(StringContext ctx) {
		v.enterString(ctx);
	}
	
	protected void enterString() {
		enterString(any(StringContext.class));
	}

	private void exitString(StringContext ctx) {
		v.exitString(ctx);
	}
	
	protected StringContext exitString() {
		ArgumentCaptor<StringContext> captor = ArgumentCaptor.forClass(StringContext.class);
		exitString(captor.capture());
		return captor.getValue();
	}

	private void enterImplementationSection(ImplementationSectionContext ctx) {
		v.enterImplementationSection(ctx);
	}
	
	protected void enterImplementationSection() {
		enterImplementationSection(any(ImplementationSectionContext.class));
	}

	private void exitImplementationSection(ImplementationSectionContext ctx) {
		v.exitImplementationSection(ctx);
	}
	
	protected ImplementationSectionContext exitImplementationSection() {
		ArgumentCaptor<ImplementationSectionContext> captor = ArgumentCaptor.forClass(ImplementationSectionContext.class);
		exitImplementationSection(captor.capture());
		return captor.getValue();
	}

	private void enterSimpleType(SimpleTypeContext ctx) {
		v.enterSimpleType(ctx);
	}
	
	protected void enterSimpleType() {
		enterSimpleType(any(SimpleTypeContext.class));
	}

	private void exitSimpleType(SimpleTypeContext ctx) {
		v.exitSimpleType(ctx);
	}
	
	protected SimpleTypeContext exitSimpleType() {
		ArgumentCaptor<SimpleTypeContext> captor = ArgumentCaptor.forClass(SimpleTypeContext.class);
		exitSimpleType(captor.capture());
		return captor.getValue();
	}

	private void enterConstSection(ConstSectionContext ctx) {
		v.enterConstSection(ctx);
	}
	
	protected void enterConstSection() {
		enterConstSection(any(ConstSectionContext.class));
	}

	private void exitConstSection(ConstSectionContext ctx) {
		v.exitConstSection(ctx);
	}
	
	protected ConstSectionContext exitConstSection() {
		ArgumentCaptor<ConstSectionContext> captor = ArgumentCaptor.forClass(ConstSectionContext.class);
		exitConstSection(captor.capture());
		return captor.getValue();
	}

	private void enterTypeId(TypeIdContext ctx) {
		v.enterTypeId(ctx);
	}
	
	protected void enterTypeId() {
		enterTypeId(any(TypeIdContext.class));
	}

	private void exitTypeId(TypeIdContext ctx) {
		v.exitTypeId(ctx);
	}
	
	protected TypeIdContext exitTypeId() {
		ArgumentCaptor<TypeIdContext> captor = ArgumentCaptor.forClass(TypeIdContext.class);
		exitTypeId(captor.capture());
		return captor.getValue();
	}

	private void enterUnitId(UnitIdContext ctx) {
		v.enterUnitId(ctx);
	}
	
	protected void enterUnitId() {
		enterUnitId(any(UnitIdContext.class));
	}

	private void exitUnitId(UnitIdContext ctx) {
		v.exitUnitId(ctx);
	}
	
	protected UnitIdContext exitUnitId() {
		ArgumentCaptor<UnitIdContext> captor = ArgumentCaptor.forClass(UnitIdContext.class);
		exitUnitId(captor.capture());
		return captor.getValue();
	}

	private void enterVarSection(VarSectionContext ctx) {
		v.enterVarSection(ctx);
	}
	
	protected void enterVarSection() {
		enterVarSection(any(VarSectionContext.class));
	}

	private void exitVarSection(VarSectionContext ctx) {
		v.exitVarSection(ctx);
	}
	
	protected VarSectionContext exitVarSection() {
		ArgumentCaptor<VarSectionContext> captor = ArgumentCaptor.forClass(VarSectionContext.class);
		exitVarSection(captor.capture());
		return captor.getValue();
	}
	

}
