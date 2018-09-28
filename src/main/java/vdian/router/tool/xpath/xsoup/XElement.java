package vdian.router.tool.xpath.xsoup;

import org.jsoup.nodes.Element;

/**
 * <p>文件名称：NodeStorageHolder.java</p>
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2011-2099</p>
 * <p>公   司： 口袋购物 </p>
 * <p>内容摘要： </p>
 * <p>其他说明： </p>
 * <p>完成日期：2018年7月24日</p>
 *
 * @author dengkui@weidian.com
 * @version 1.0
 */
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
