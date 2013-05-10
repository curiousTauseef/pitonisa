package br.eti.rslemos.pitonisa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenSource;
import org.junit.Test;

import br.eti.rslemos.pitonisa.delphiParser.GoalContext;

public class DelphiParserUnitTest {
	private static final List<String> ruleNames = Arrays.asList(delphiParser.ruleNames);
	
	@Test
	public void testUnit001() throws Exception {
		GoalContext context = parse(getClass().getResourceAsStream("Unit001.pas"));
		assertThat(context.toStringTree(ruleNames), is(equalTo("(goal (unit UNIT (ident Unit001) ; .))")));
	}

	@Test
	public void testUnit002() throws Exception {
		GoalContext context = parse(getClass().getResourceAsStream("Unit002.pas"));
		assertThat(context.toStringTree(ruleNames), is(equalTo("(goal (unit UNIT (ident Unit002) ; (interfaceSection INTERFACE) .))")));
	}

	@Test
	public void testUnit003() throws Exception {
		GoalContext context = parse(getClass().getResourceAsStream("Unit003.pas"));
		assertThat(context.toStringTree(ruleNames), is(equalTo("(goal (unit UNIT (ident Unit003) ; (implementationSection IMPLEMENTATION) .))")));
	}

	@Test
	public void testUnit004() throws Exception {
		GoalContext context = parse(getClass().getResourceAsStream("Unit004.pas"));
		assertThat(context.toStringTree(ruleNames), is(equalTo("(goal (unit UNIT (ident Unit004) ; (interfaceSection INTERFACE (usesClause USES (identList (ident uses0)) ;)) .))")));
	}

	@Test
	public void testUnit005() throws Exception {
		GoalContext context = parse(getClass().getResourceAsStream("Unit005.pas"));
		assertThat(context.toStringTree(ruleNames), is(equalTo("(goal (unit UNIT (ident Unit005) ; (interfaceSection INTERFACE (usesClause USES (identList (ident uses0) , (ident uses1)) ;)) .))")));
	}

	@Test
	public void testUnit006() throws Exception {
		GoalContext context = parse(getClass().getResourceAsStream("Unit006.pas"));
		assertThat(context.toStringTree(ruleNames), is(equalTo("(goal (unit UNIT (ident Unit006) ; (interfaceSection INTERFACE (interfaceDecl (constSection CONST (constDecl (ident const0) = (constExpr (number 10))) ; (constDecl (ident const1) : (typeId (ident STRING)) = (typedConstant (constExpr (string 'value')))) ;))) .))")));
	}

	@Test
	public void testUnit007() throws Exception {
		GoalContext context = parse(getClass().getResourceAsStream("Unit007.pas"));
		assertThat(context.toStringTree(ruleNames), is(equalTo("(goal (unit UNIT (ident Unit007) ; (interfaceSection INTERFACE (interfaceDecl (varSection VAR (varDecl (identList (ident var0) , (ident var1)) : (type (typeId (ident INTEGER))) = (constExpr (number 10))) ; (varDecl (identList (ident var2)) : (type (typeId (ident string)))) ; (varDecl (identList (ident var3)) : (type (typeId (ident Byte))) ABSOLUTE (ident var2)) ;))) .))")));
	}

	private GoalContext parse(InputStream input) throws Exception {
		TokenSource lexer = new delphiLexer(new ANTLRInputStream(input));
		delphiParser parser = new delphiParser(new CommonTokenStream(lexer));
		return parser.goal();
	}
}
