package br.eti.rslemos.pitonisa;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.junit.Test;

public class DelphiParserUnitTest {
	
	@Test
	public void testBootstrapParser() throws Exception {
		CharStream text = new ANTLRInputStream("UNIT x;");
		TokenSource lexer = new delphiLexer(text);
		TokenStream input = new CommonTokenStream(lexer);
		delphiParser parser = new delphiParser(input);
		parser.goal();
	}
}
