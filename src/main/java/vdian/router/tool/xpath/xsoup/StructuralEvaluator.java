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
abstract class StructuralEvaluator extends Evaluator {
    Evaluator evaluator;

    static class Root extends Evaluator {
        public boolean matches(Element root, Element element) {
            return root == element;
        }

        public String toString() {
            return ":root";
        }
    }

    static class Has extends StructuralEvaluator {
        public Has(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        public boolean matches(Element root, Element element) {
            for (Element e : element.getAllElements()) {
                if (e != element && evaluator.matches(root, e))
                    return true;
            }
            return false;
        }

        public String toString() {
            return String.format(":has(%s)", evaluator);
        }
    }

    static class Not extends StructuralEvaluator {
        public Not(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        public boolean matches(Element root, Element node) {
            return !evaluator.matches(root, node);
        }

        public String toString() {
            return String.format(":not%s", evaluator);
        }
    }

    static class Parent extends StructuralEvaluator {
        public Parent(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        public boolean matches(Element root, Element element) {
            Element parent = element.parent();
            while (parent != null) {
                if (evaluator.matches(root, parent))
                    return true;
                parent = parent.parent();
            }
            return false;
        }

        public String toString() {
            return String.format(":parent%s", evaluator);
        }
    }

    static class ImmediateParent extends StructuralEvaluator {
        public ImmediateParent(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        public boolean matches(Element root, Element element) {
            Element parent = element.parent();
            return parent != null && evaluator.matches(root, parent);
        }

        public String toString() {
            return String.format(":ImmediateParent%s", evaluator);
        }
    }

    static class PreviousSibling extends StructuralEvaluator {
        public PreviousSibling(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        public boolean matches(Element root, Element element) {
            if (root == element)
                return false;

            Element prev = element.previousElementSibling();

            while (prev != null) {
                if (evaluator.matches(root, prev))
                    return true;

                prev = prev.previousElementSibling();
            }
            return false;
        }

        public String toString() {
            return String.format(":prev*%s", evaluator);
        }
    }

    static class ImmediatePreviousSibling extends StructuralEvaluator {
        public ImmediatePreviousSibling(Evaluator evaluator) {
            this.evaluator = evaluator;
        }

        public boolean matches(Element root, Element element) {
            if (root == element)
                return false;

            Element prev = element.previousElementSibling();
            return prev != null && evaluator.matches(root, prev);
        }

        public String toString() {
            return String.format(":prev%s", evaluator);
        }
    }
}
