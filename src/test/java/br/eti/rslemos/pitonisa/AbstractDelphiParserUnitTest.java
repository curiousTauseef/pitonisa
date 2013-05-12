package br.eti.rslemos.pitonisa;

import static org.junit.Assert.fail;

import java.io.PrintStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenSource;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractDelphiParserUnitTest {

	protected static final List<String> ruleNames = Arrays.asList(delphiParser.ruleNames);
	private boolean stderrUsed = false;
	
	@Before
	public void setup() {
		System.setErr(new PrintStream(System.err) {
	
			@Override
			public void write(int b) {
				stderrUsed = true;
				super.write(b);
			}
	
			@Override
			public void write(byte[] buf, int off, int len) {
				stderrUsed = true;
				super.write(buf, off, len);
			}
	
		});
	}

	@After
	public void teardown() {
		if (stderrUsed) fail("message sent to stderr");
	}

	protected ParserRuleContext xpath(ParserRuleContext context, String path) {
		return ANTLRXPath.xpath(context, path, ruleNames);
	}

	protected ParserRuleContext parse(String text) throws Exception {
		TokenSource lexer = new delphiLexer(new ANTLRInputStream(new StringReader(text)));
		delphiParser parser = new delphiParser(new CommonTokenStream(lexer));
		return parser.goal();
	}

}
