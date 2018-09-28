package com.router.tool.xpath.xsoup;

import org.jsoup.nodes.Element;
public class XElement {

    private Element element;

    private ElementOperator elementOperator;

    public XElement(Element element, ElementOperator elementOperator) {
        this.element = element;
        this.elementOperator = elementOperator;
    }

    public String get(){
        return get(elementOperator);
    }

    public String get(ElementOperator elementOperator){
        if (elementOperator == null) {
            return element.toString();
        } else {
            return elementOperator.operate(element);
        }
    }

    public String get(String attribute){
       return get(new ElementOperator.AttributeGetter(attribute));
    }

    public String toString() {
         return get();
    }

}
