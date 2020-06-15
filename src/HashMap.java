import java.util.LinkedList;


public class HashMap {

	private LinkedList index = new LinkedList();
	private LinkedList value = new LinkedList();
	
	public void add(Object index, Object value){
		this.index.add(index);
		this.value.add(index);
	}
	
	public Object get(Object index){
		int indexOf = this.index.indexOf(index);
		return this.value.get(indexOf);
	}
}
