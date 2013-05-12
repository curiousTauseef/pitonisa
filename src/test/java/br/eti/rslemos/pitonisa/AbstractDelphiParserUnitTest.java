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
