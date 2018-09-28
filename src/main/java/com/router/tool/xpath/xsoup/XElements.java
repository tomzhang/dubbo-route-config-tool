package com.router.tool.xpath.xsoup;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
public class XElements extends ArrayList<XElement> {

    /**
	* @Fields serialVersionUID : TODO 
	*/
	private static final long serialVersionUID = 3586208675680896764L;

	private Elements elements;

    private ElementOperator elementOperator;

    public XElements(Elements elements, ElementOperator elementOperator) {
        this.elements = elements;
        this.elementOperator = elementOperator;
        initList();
    }

    private void initList() {
        for (Element element : elements) {
            this.add(new XElement(element, elementOperator));
        }
    }

    public String get() {
        if (size() < 1) {
            return "";
        } else {
        	StringBuilder value = new StringBuilder();
        	for (XElement xElement :this) {
        		value.append(xElement.toString());
        	}
            return value.toString();
        }
    }

    public List<String> list() {
        List<String> resultStrings = new ArrayList<String>();
        for (XElement xElement : this) {
            String text = xElement.get();
            if (text!=null){
                resultStrings.add(text);
            }
        }
        return resultStrings;
    }

    @Override
    public String toString() {
        return get();
    }
}
