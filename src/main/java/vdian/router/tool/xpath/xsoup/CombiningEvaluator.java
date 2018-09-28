package vdian.router.tool.xpath.xsoup;


import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
abstract class CombiningEvaluator extends Evaluator {
    final List<Evaluator> evaluators;

    CombiningEvaluator() {
        super();
        evaluators = new ArrayList<Evaluator>();
    }

    CombiningEvaluator(Collection<Evaluator> evaluators) {
        this();
        this.evaluators.addAll(evaluators);
    }

    Evaluator rightMostEvaluator() {
        return evaluators.size() > 0 ? evaluators.get(evaluators.size() - 1) : null;
    }
    
    void replaceRightMostEvaluator(Evaluator replacement) {
        evaluators.set(evaluators.size() - 1, replacement);
    }

    static final class And extends CombiningEvaluator {
        And(Collection<Evaluator> evaluators) {
            super(evaluators);
        }

        And(Evaluator... evaluators) {
            this(Arrays.asList(evaluators));
        }

        @Override
        public boolean matches(Element root, Element node) {
            for (int i = 0; i < evaluators.size(); i++) {
                Evaluator s = evaluators.get(i);
                if (!s.matches(root, node))
                    return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return StringUtil.join(evaluators, " ");
        }
    }

    static final class Or extends CombiningEvaluator {
        /**
         * Create a new Or evaluator. The initial evaluators are ANDed together and used as the first clause of the OR.
         * @param evaluators initial OR clause (these are wrapped into an AND evaluator).
         */
        Or(Collection<Evaluator> evaluators) {
            super();
            if (evaluators.size() > 1)
                this.evaluators.add(new CombiningEvaluator.And(evaluators));
            else // 0 or 1
                this.evaluators.addAll(evaluators);
        }

        Or(Evaluator... evaluators) {
            this(Arrays.asList(evaluators));
        }

        Or() {
            super();
        }

        public void add(Evaluator e) {
            evaluators.add(e);
        }

        @Override
        public boolean matches(Element root, Element node) {
            for (int i = 0; i < evaluators.size(); i++) {
                Evaluator s = evaluators.get(i);
                if (s.matches(root, node))
                    return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format(":or%s", evaluators);
        }
    }
}
