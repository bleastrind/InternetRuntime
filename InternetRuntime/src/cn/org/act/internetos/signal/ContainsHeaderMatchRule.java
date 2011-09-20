package cn.org.act.internetos.signal;


public class ContainsHeaderMatchRule extends MatchRule {

	private String headerKey;
	public ContainsHeaderMatchRule(String headerkey){
		this.headerKey = headerkey;
	}
	@Override
	public boolean match(Signal signal) {
		return signal.getHeaders().containsKey(headerKey);
	}

}
