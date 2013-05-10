package br.eti.rslemos.pitonisa;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.junit.Test;

import br.eti.rslemos.pitonisa.delphiParser.GoalContext;

public class DelphiParserUnitTest {
	
	@Test
	public void testBootstrapParser() throws Exception {
		TokenSource source = new TokenSource() {

			@Override
			public Token nextToken() {
				return new CommonToken(Token.EOF);
			}

			@Override
			public int getLine() {
				return 0;
			}

			@Override
			public int getCharPositionInLine() {
				return 0;
			}

			@Override
			public CharStream getInputStream() {
				return null;
			}

			@Override
			public String getSourceName() {
				return null;
			}

			@Override
			public void setTokenFactory(TokenFactory<?> factory) {
			}

			@Override
			public TokenFactory<?> getTokenFactory() {
				return null;
			}
			
		};
		TokenStream input = new CommonTokenStream(source);
		delphiParser parser = new delphiParser(input);
		GoalContext context = parser.goal();
		
	}
}
