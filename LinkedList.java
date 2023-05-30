   
public class LinkedList {
	int length=0;
	EventNode head;
	EventNode tail;
	
	LinkedList(){
		head=new EventNode(-1,0,0,Double.MAX_VALUE);
		tail=head;
	}
	
	void add(EventNode n) {
		length++;
		tail.next=n;
		tail=tail.next;
	}

	void removeFirst() {
		length--;
		if(head.next!=tail) {
			EventNode temp=head.next;
			head.next=head.next.next;
			temp.next=null;
		}else {
			EventNode temp=head.next;
			head.next=head.next.next;
			temp.next=null;
			tail=head;
		}
	}
}
