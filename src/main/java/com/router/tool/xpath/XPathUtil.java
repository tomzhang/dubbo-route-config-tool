/**
 * This document and its contents are protected by copyright 2005 and owned by Vobile Inc. The
 * copying and reproduction of this document and/or its content (whether wholly or partly) or any
 * incorporation of the same into any other material in any media or format of any kind is strictly
 * prohibited. All rights are reserved.
 *
 * Copyright (c) Vobile Inc. 2014
 */
package com.router.tool.xpath;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.JDomSerializer;
import org.htmlcleaner.TagNode;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.support.AbstractXMLOutputProcessor;
import org.jdom2.output.support.FormatStack;
import org.jdom2.util.NamespaceStack;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import com.router.tool.xpath.jsoup.JSoupQuerySynax;
import com.router.tool.xpath.xsoup.Xsoup;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
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
public class XPathUtil {

    private static final String TEXT_SIGN = "/text()";

    private static final String SEPARATOR = "\"";

    public static XpathResult selectSpecifiedAreas(List<String> selectList, String pageContent) {
        if (selectList == null || selectList.size() == 0) {
            System.out.println("select list is empty!");
            return new XpathResult(false, pageContent);
        }
        XpathResult result = null;
        StringBuilder value = new StringBuilder();
        try {
            for (String select : selectList) {
                value.append(Xsoup.select(pageContent, select).get());
            }
        } catch (Exception e) {
            System.out.println("parser xpath error!");
            return new XpathResult(false, pageContent);
        }

        if (value.length() > 0) {
            result = new XpathResult(true, value.toString());
        } else {
            result = new XpathResult(pageContent);
        }
        return result;
    }

    public static List<String> getXpath(String select, String content) {
        List<String> result = new ArrayList<String>();
        if (StringUtils.isEmpty(select) || StringUtils.isEmpty(content)) {
            System.out.println("empty xpath or content");
            return result;
        }
        for (String selectString : select.split(SEPARATOR)) {
            result.addAll(routeToSelet(selectString, content));
        }
        return result;
    }

    /**
     * check to use jsoup or xpath
     *
     * @param select
     * @param content
     * @return
     */
    private static List<String> routeToSelet(String select, String content) {

        boolean selectSynax = JSoupQuerySynax.isValid(select);
        List<String> resList = new ArrayList<String>();
        if (selectSynax) {
            resList.addAll(useJsoupSelect(select, content));
            System.out.println("route to jsoup " + select);
        } else {
            resList.addAll(useXpathSelect(select, content));
            System.out.println("route to xpath " + select);
        }
        if (CollectionUtils.isEmpty(resList)) {
            System.out.println("empty select content!" + select);
        }
        return resList;
    }

    /**
     *
     * @param select
     * @param content
     * @return
     */
    private static Collection<? extends String> useJsoupSelect(String select, String content) {
        List<String> splits = new LinkedList<String>();
        try {
            Elements eles = Jsoup.parse(content).select(select);
            if (eles != null) {
                int size = eles.size();
                for (int i = 0; i < size; i++) {
                    org.jsoup.nodes.Element element = eles.get(i);
                    splits.add(element.outerHtml());
                }
            }
        } catch (Exception e) {
            System.out.println("select use jsoup error! select " + select);
        }
        return splits;
    }

    private static Collection<? extends String> useXpathSelect(String xpath, String content) {
        List<String> splits = new LinkedList<String>();
        boolean extractText = xpath.endsWith(TEXT_SIGN);
        if (extractText) {
            xpath = xpath.substring(0, xpath.indexOf(TEXT_SIGN));
        }
        try {
            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode node = cleaner.clean(content);
            XMLOutputter out = new XMLOutputter();
            out.setFormat(Format.getCompactFormat().setEncoding("utf-8"));
            out.setXMLOutputProcessor(new CustomProcessor());
            JDomSerializer jdomSerializer = new JDomSerializer(cleaner.getProperties(), false);
            Object[] elements = node.evaluateXPath(xpath);
            if (ArrayUtils.isNotEmpty(elements)) {
                for (Object obj : elements) {
                    //  System.out.println("clazz..." + obj.getClass());
                    if (obj instanceof TagNode) {
                        TagNode resultNode = (TagNode) obj;
                        if (extractText) {
                            splits.add(resultNode.getText().toString());
                        } else {
                            Document doc = jdomSerializer.createJDom(resultNode);
                            splits.add(out.outputString(doc.getRootElement()));
                        }

                    } else if (obj instanceof CharSequence) {
                        splits.add(String.valueOf(obj));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("select use xpath error! xpath " + xpath);
        }
        return splits;

    }

    private String getSpecifiedAreaWithXpaths(List<String> xPath, String pageContent, String url, boolean routeSign) {
        StringBuilder value = new StringBuilder();
        System.out.println(pageContent);
        try {
            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode node = cleaner.clean(pageContent);

            XMLOutputter out = new XMLOutputter();
            out.setFormat(Format.getCompactFormat().setEncoding("utf-8"));
            out.setXMLOutputProcessor(new CustomProcessor());

            JDomSerializer jdomSerializer = new JDomSerializer(cleaner.getProperties(), false);

            // get the limit area
            for (int i = 0; i < xPath.size(); i++) {
                Object[] elements = node.evaluateXPath(xPath.get(i));
                if (ArrayUtils.isNotEmpty(elements)) {
                    for (Object obj : elements) {
                        if (obj instanceof TagNode) {
                            System.out.println(((TagNode) obj).getAttributes());
                            TagNode resultNode = (TagNode) obj;
                            Document doc = jdomSerializer.createJDom(resultNode);
                            System.out.println(out.outputString(doc.getRootElement()));
                            value.append(out.outputString(doc.getRootElement()));
                        }
                    }
                } else {
                    System.out.println("URL  <" + url + "> no elements match for pagecontent xpath:" + xPath.get(i));
                }
            }
        } catch (Exception e) {
            System.out.println("URL <" + url + "> execute getSpecifiedAreaWithXpaths error");
            return pageContent;
        }

        if (CollectionUtils.isNotEmpty(xPath) && (value.length() == 0)) {
            System.out.println("URL <" + url + "> + use Xpath " + xPath + "parse p ageContent is empty！，" + "please recheck your Xpath config！");
            System.out.println("The pageContent is : " + pageContent);
        }
        return value.toString();// change in colander2.4.0.3b
    }

    static final class CustomProcessor extends AbstractXMLOutputProcessor {

        @Override public void process(final Writer out, final Format format, final Element element) throws IOException {

            FormatStack fStack = new FormatStack(format);
            fStack.setEscapeOutput(false);
            // If this is the root element we could pre-initialize the
            // namespace stack with the namespaces
            printElement(out, fStack, new NamespaceStack(), element);
            out.flush();
        }

    }

}
