package vdian.router.tool.xpath;

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
public class XpathResult
{
    private boolean hasXpathContent ;
    
    private String content ;
    

    public XpathResult()
    {
        this(false, null);
    }
    
    public XpathResult(String cont)
    {
        this(false, cont);
    }


    public XpathResult(boolean hasContent, String content)
    {
        super();
        this.hasXpathContent = hasContent;
        this.content = content;
    }

    
    public boolean isHasXpathContent()
    {
        return hasXpathContent;
    }

    public void setHasXpathContent(boolean hasXpathContent)
    {
        this.hasXpathContent = hasXpathContent;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    } 
    
    

}
