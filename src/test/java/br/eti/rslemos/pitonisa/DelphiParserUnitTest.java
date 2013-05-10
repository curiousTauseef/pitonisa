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

	private GoalContext parse(InputStream input) throws Exception {
		TokenSource lexer = new delphiLexer(new ANTLRInputStream(input));
		delphiParser parser = new delphiParser(new CommonTokenStream(lexer));
		return parser.goal();
	}
}
