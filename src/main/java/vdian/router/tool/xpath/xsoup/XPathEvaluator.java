package vdian.router.tool.xpath.xsoup;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

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
public class XPathEvaluator {

    private Evaluator evaluator;

    private ElementOperator elementOperator;

    public XPathEvaluator(Evaluator evaluator, ElementOperator elementOperator) {
        this.evaluator = evaluator;
        this.elementOperator = elementOperator;
    }

    public XElements evaluate(Element element) {
        Elements elements = Collector.collect(evaluator, element);
        return new XElements(elements, elementOperator);
    }

    public XElements evaluate(String html) {
        Elements elements = Collector.collect(evaluator, Jsoup.parse(html));
        return new XElements(elements, elementOperator);
    }

    public Evaluator getEvaluator() {
        return evaluator;
    }

    public String getAttribute() {
        if (elementOperator == null) {
            return null;
        }
        return elementOperator.toString();
    }

    public ElementOperator getElementOperator() {
        return elementOperator;
    }
}
