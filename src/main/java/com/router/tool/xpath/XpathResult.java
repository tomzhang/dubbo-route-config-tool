package com.router.tool.xpath;

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
