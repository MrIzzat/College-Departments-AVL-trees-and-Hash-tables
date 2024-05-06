
public class LinkedQueue<T> implements Queues {

	private QNode<T> first;
	private QNode<T> last;

	public LinkedQueue() {

	}

	public void enqueue(Object data) {

		QNode<T> curr = new QNode(data,null);

		if(first==null) {
			first=curr;
			last=curr;
		}else {
			if(first.getNext()==null) {
				first.setNext(curr);
				last=curr;
			}else {
				last.setNext(curr);
				last=curr;
			}	
		}	
	}

	public Object dequeue() {

		if(isEmpty()) {
			return null;
		}else {
			QNode temp = first;
			if(first.getNext()==null) {
				first=null;
				last=null;
				return temp;
			}else {
				first=first.getNext();
				return temp;
			}
		
		}
	}

	public Object getFront() {

		if(!isEmpty())
			return first;
		return null;
	}

	public boolean isEmpty() {
		return first==null&&last==null;
	}

	public void clear() {
		first=null;
		last=null;	
	}
	
	public String toString() {
		
		QNode curr=first;
		
		String str="";
		while(curr!=null) {
			str+=curr.getData()+" ";
			curr=curr.getNext();	
		}
		
		str+="null";
		return str;
	}
	
	public int Length() {
		
		
		QNode curr=first;
		
		
		int x=0;
		while(curr!=null) {
			x++;	
			curr=curr.getNext();
		}
		return x;
	}

	
}
