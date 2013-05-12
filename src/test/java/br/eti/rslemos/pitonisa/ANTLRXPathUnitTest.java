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

import static br.eti.rslemos.pitonisa.ANTLRXPath.xpath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

public class ANTLRXPathUnitTest {
	private static final List<String> RULESNAMES = Arrays.asList("A", "B", "C");
	
	private ParserRuleContext root_A;
	private ParserRuleContext root_A_B0;
	private ParserRuleContext root_A_B0_C0;
	private ParserRuleContext root_A_B0_C1;
	private ParserRuleContext root_A_B0_C2;
	private ParserRuleContext root_A_B1;
	private ParserRuleContext root_A_B1_C0;
	private ParserRuleContext root_A_B1_C1;
	private ParserRuleContext root_A_B1_C2;
	private ParserRuleContext root_A_B2;
	private ParserRuleContext root_A_B2_C0;
	private ParserRuleContext root_A_B2_C1;
	private ParserRuleContext root_A_B2_C2;
	
	@Before
	public void setup() {
		root_A = buildParserRuleContext("A");
		root_A_B0 = buildParserRuleContext("B");
		root_A_B1 = buildParserRuleContext("B");
		root_A_B2 = buildParserRuleContext("B");
		root_A.children = Arrays.asList((ParseTree)root_A_B0, root_A_B1, root_A_B2);
		root_A_B0_C0 = buildParserRuleContext("C");
		root_A_B0_C1 = buildParserRuleContext("C");
		root_A_B0_C2 = buildParserRuleContext("C");
		root_A_B0.children = Arrays.asList((ParseTree)root_A_B0_C0, root_A_B0_C1, root_A_B0_C2);
		root_A_B1_C0 = buildParserRuleContext("C");
		root_A_B1_C1 = buildParserRuleContext("C");
		root_A_B1_C2 = buildParserRuleContext("C");
		root_A_B1.children = Arrays.asList((ParseTree)root_A_B1_C0, root_A_B1_C1, root_A_B1_C2);
		root_A_B2_C0 = buildParserRuleContext("C");
		root_A_B2_C1 = buildParserRuleContext("C");
		root_A_B2_C2 = buildParserRuleContext("C");
		root_A_B2.children = Arrays.asList((ParseTree)root_A_B2_C0, root_A_B2_C1, root_A_B2_C2);
	}

	private static ParserRuleContext buildParserRuleContext(String token) {
		return buildParserRuleContext(RULESNAMES.indexOf(token));
	}

	private static ParserRuleContext buildParserRuleContext(final int ruleIndex) {
		return new ParserRuleContext() { @Override public int getRuleIndex() { return ruleIndex; }  };
	}

	@Test
	public void testEmptyPath() throws Exception {
		assertThat(xpath(root_A, "", RULESNAMES), is(sameInstance(root_A)));
	}
	
	@Test
	public void testSingleSlashPath() throws Exception {
		assertThat(xpath(root_A, "/", RULESNAMES), is(sameInstance(root_A)));
	}
	
	@Test
	public void testSingleRootPath() throws Exception {
		assertThat(xpath(root_A, "/A", RULESNAMES), is(sameInstance(root_A)));
	}
	
	@Test
	public void testTwoPartPath() throws Exception {
		assertThat(xpath(root_A, "/A/B", RULESNAMES), is(sameInstance(root_A_B0)));
	}
	
	@Test
	public void testThreePartPath() throws Exception {
		assertThat(xpath(root_A, "/A/B/C", RULESNAMES), is(sameInstance(root_A_B0_C0)));
	}
	
	@Test
	public void testIndexedLastPartPath() throws Exception {
		assertThat(xpath(root_A, "/A/B[0]", RULESNAMES), is(sameInstance(root_A_B0)));
		assertThat(xpath(root_A, "/A/B[1]", RULESNAMES), is(sameInstance(root_A_B1)));
		assertThat(xpath(root_A, "/A/B[2]", RULESNAMES), is(sameInstance(root_A_B2)));
	}
	
	@Test
	public void testIndexedLastOfThreePartPath() throws Exception {
		assertThat(xpath(root_A, "/A/B/C[0]", RULESNAMES), is(sameInstance(root_A_B0_C0)));
		assertThat(xpath(root_A, "/A/B/C[1]", RULESNAMES), is(sameInstance(root_A_B0_C1)));
		assertThat(xpath(root_A, "/A/B/C[2]", RULESNAMES), is(sameInstance(root_A_B0_C2)));
	}

	@Test
	public void testIndexedMiddleOfThreePartPath() throws Exception {
		assertThat(xpath(root_A, "/A/B[0]/C", RULESNAMES), is(sameInstance(root_A_B0_C0)));
		assertThat(xpath(root_A, "/A/B[1]/C", RULESNAMES), is(sameInstance(root_A_B1_C0)));
		assertThat(xpath(root_A, "/A/B[2]/C", RULESNAMES), is(sameInstance(root_A_B2_C0)));
	}

	@Test
	public void testMultiIndexedOfThreePartPath() throws Exception {
		assertThat(xpath(root_A, "/A/B[0]/C[0]", RULESNAMES), is(sameInstance(root_A_B0_C0)));
		assertThat(xpath(root_A, "/A/B[0]/C[1]", RULESNAMES), is(sameInstance(root_A_B0_C1)));
		assertThat(xpath(root_A, "/A/B[0]/C[2]", RULESNAMES), is(sameInstance(root_A_B0_C2)));
		assertThat(xpath(root_A, "/A/B[1]/C[0]", RULESNAMES), is(sameInstance(root_A_B1_C0)));
		assertThat(xpath(root_A, "/A/B[1]/C[1]", RULESNAMES), is(sameInstance(root_A_B1_C1)));
		assertThat(xpath(root_A, "/A/B[1]/C[2]", RULESNAMES), is(sameInstance(root_A_B1_C2)));
		assertThat(xpath(root_A, "/A/B[2]/C[0]", RULESNAMES), is(sameInstance(root_A_B2_C0)));
		assertThat(xpath(root_A, "/A/B[2]/C[1]", RULESNAMES), is(sameInstance(root_A_B2_C1)));
		assertThat(xpath(root_A, "/A/B[2]/C[2]", RULESNAMES), is(sameInstance(root_A_B2_C2)));
	}
}
