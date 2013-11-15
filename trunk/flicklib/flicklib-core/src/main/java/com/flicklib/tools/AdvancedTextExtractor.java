package com.flicklib.tools;

import java.util.HashSet;
import java.util.Set;

import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.StartTag;
import net.htmlparser.jericho.TextExtractor;

/**
 * 
 * @author zsombor
 * 
 */
public class AdvancedTextExtractor extends TextExtractor {

	private final boolean defaultDecision;
	private final Set<String> allowedTagNames = new HashSet<String>();

	private final Set<String> excludedTagNames = new HashSet<String>();

	public AdvancedTextExtractor(Segment segment, boolean defaultDecision) {
		super(segment);
		this.defaultDecision = defaultDecision;
	}
	
	public AdvancedTextExtractor addAllowedTagName(String name) {
		this.allowedTagNames.add(name.toLowerCase());
		return this;
	}

	public AdvancedTextExtractor addExcludedTagName(String name) {
		this.excludedTagNames.add(name.toLowerCase());
		return this;
	}
	
	@Override
	public boolean excludeElement(StartTag startTag) {
		String name = startTag.getName();
		if (allowedTagNames.contains(name)) {
			return false;
		}
		if (excludedTagNames.contains(name)) {
			return true;
		}
		return defaultDecision;
	}
	
	

}
