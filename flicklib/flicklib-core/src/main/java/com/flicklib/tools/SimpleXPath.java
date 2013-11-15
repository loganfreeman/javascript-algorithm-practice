package com.flicklib.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.Element;

public class SimpleXPath implements Iterable<Element> {
    private final List<Element> root;

    public SimpleXPath() {
        this.root = new ArrayList<Element>();
    }

    public SimpleXPath(List<Element> root) {
        this.root = root;
    }

    public SimpleXPath(Element element) {
        this();
        if (element != null) {
            root.add(element);
        }
    }

    public void add(Element el) {
	if (el == null) {
	    throw new NullPointerException("Trying to add null!");
	}
        this.root.add(el);
    }

    public void addAll(Collection<Element> el) {
	for (Element e: el) {
	    if (e!=null) {
		root.add(e);
	    } else {
		throw new NullPointerException("Trying to add null!");
	    }
	}
    }

    public SimpleXPath getTags(String tagName) {
        SimpleXPath xp = new SimpleXPath();
        for (Element e : root) {
            xp.addAll(e.getAllElements(tagName));
        }
        return xp;
    }

    public SimpleXPath getAllTagByAttributes(String attribName, String attribValue) {
        SimpleXPath xp = new SimpleXPath();
        for (Element e : root) {
            xp.addAll(e.getAllElements(attribName, attribValue, true));
        }
        return xp;
    }
    
    public SimpleXPath filter(String attribName, String attribValue) {
        SimpleXPath xp = new SimpleXPath();
        if (attribValue != null) {
            for (Element e : root) {
                if (attribValue.equals(e.getAttributeValue(attribName))) {
                    xp.add(e);
                }
            }
        } else {
            for (Element e : root) {
                if (e.getAttributeValue(attribName)==null) {
                    xp.add(e);
                }
            }
        }
        return xp;
    }
    
    public SimpleXPath filterTagName(String tagName) {
        SimpleXPath xp = new SimpleXPath();
        for (Element e : root) {
            if (tagName.equals(e.getName())) {
                xp.add(e);
            }
        }
        return xp;
    }
    
    public SimpleXPath parentTagName(String tagName) {
        SimpleXPath xp = new SimpleXPath();
        for (Element e : root) {
            if (tagName.equals(e.getParentElement().getName())) {
                xp.add(e);
            }
        }
        return xp;
    }
    
    public SimpleXPath children() {
        SimpleXPath xp = new SimpleXPath();
        for (Element e : root) {
            xp.addAll(e.getChildElements());
        }
        return xp;
    }

    public SimpleXPath parent() {
        SimpleXPath xp = new SimpleXPath();
        for (Element e : root) {
            xp.add(e.getParentElement());
        }
        return xp;
    }

    public SimpleXPath first() {
        if (root.size() > 0) {
            return new SimpleXPath(root.get(0));
        }
        return this;
    }
    
    public Element firstElement() {
	if (root.size() > 0) {
	    return root.get(0);
	}
	return null;
    }

    public boolean isEmpty() {
        return root.isEmpty();
    }

    /**
     * @return
     * @see java.util.List#size()
     */
    public int size() {
        return root.size();
    }

    @Override
	public Iterator<Element> iterator() {
        return root.iterator();
    }
    
    public Iterable<Element> iterable() {
	    return root;
    }

    public List<Element> toList() {
        return root;
    }
    
    /**
     * 
     * @see java.util.List#clear()
     */
    public void clear() {
        root.clear();
    }

    /**
     * @param index
     * @return
     * @see java.util.List#get(int)
     */
    public Element get(int index) {
        return root.get(index);
    }
    
    public Element unique() {
	    if (root.size() == 1) {
		    return root.get(0);
	    } else {
		    throw new RuntimeException("Element list is not unique, size:"+root.size()+"\n content:"+root);
	    }
    }
    
    public String getValue() {
	    if (root.size() > 0) {
		    return root.get(0).getTextExtractor().toString();
	    }
	    return null;
    }

    @Override
    public String toString() {

        return "XPath[" + root.toString() + "]";
    }

}
