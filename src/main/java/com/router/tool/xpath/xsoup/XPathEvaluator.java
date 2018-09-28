package com.router.tool.xpath.xsoup;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

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
