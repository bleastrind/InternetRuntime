package cn.org.act.internetos.persist;

public class Pair<T1, T2> {
	private T1 item1;
	private T2 item2;
	
	public Pair(T1 item1,T2 item2)
	{
		this.item1 = item1;
		this.item2 = item2;
	}
	
	public void setItem1(T1 item1) {
		this.item1 = item1;
	}
	public T1 getItem1() {
		return item1;
	}
	public void setItem2(T2 item2) {
		this.item2 = item2;
	}
	public T2 getItem2() {
		return item2;
	}
	
	public String toString(){
		return item1.toString()+"#"+item2.toString();
	}
	
	public int hashCode(){
		return item1.hashCode()<<1 + item2.hashCode();
	}
	
	public boolean equals(Object p2){
		if(p2 instanceof Pair){
			Pair<?,?> pair = (Pair<?,?>)p2;
			return equal(item1,pair.item1) && equal(item2,pair.item2);
		}else
			return false;
	}
	
	private static boolean equal(Object o1,Object o2){
		if( o1 == null )
			return o2 == null;
		else
			return o1.equals(o2);
	}
	
}
