package br.eti.rslemos.pitonisa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

public class ANTLRXPath {

	public static ParserRuleContext xpath(ParserRuleContext context, String path, List<String> rulenames) {
		List<String> parts = new ArrayList<String>(Arrays.asList(path.split("/")));
	
		while (parts.remove(""));
		
		if (!parts.isEmpty() && parts.get(0).equals(rulenames.get(context.getRuleIndex())))
			parts.remove(0);
		
	outer:
		for (String part : parts) {
			int index = 0;
			
			if (part.matches("^.*\\[[0-9]+\\]$")) {
				String indexPart = part.substring(part.lastIndexOf('[') + 1, part.length() - 1);
				index = Integer.parseInt(indexPart);
				part = part.substring(0, part.lastIndexOf('['));
			}
				
			for (int i = 0; i < context.getChildCount()	; i++) {
				ParserRuleContext candidate = context.getChild(ParserRuleContext.class, i);
				if (part.equals(rulenames.get(candidate.getRuleIndex()))) {
					if (index-- == 0) { 
						context = candidate;
						continue outer;
					}
				}
			}
			
			throw new IllegalArgumentException();
			
		}
		
		return context;
	}

}
