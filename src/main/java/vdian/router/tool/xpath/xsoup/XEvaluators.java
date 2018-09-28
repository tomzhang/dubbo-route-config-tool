package vdian.router.tool.xpath.xsoup;


import org.jsoup.nodes.Element;
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
public abstract class XEvaluators {

    public static class HasAnyAttribute extends Evaluator {

        @Override
        public boolean matches(Element root, Element element) {
            return element.attributes().size() > 0;
        }
    }
}
