package cn.org.act.internetos.manage.app;

import org.dom4j.Element;

import cn.org.act.internetos.signal.ContainsHeaderMatchRule;
import cn.org.act.internetos.signal.MatchRule;
import cn.org.act.internetos.signal.UrlRegexMatchRule;

public class MatchRuleFactory {
	public static MatchRule createMatchRule(Element matchrule)
	{
		if("urlregex".equals(matchrule.attributeValue("type")))
			return new UrlRegexMatchRule(matchrule.getTextTrim());
		else if("containsHeader".equals(matchrule.attributeValue("type")))
			return new ContainsHeaderMatchRule(matchrule.getTextTrim());
		else
			return MatchRule.MatchAll;
	}
}
