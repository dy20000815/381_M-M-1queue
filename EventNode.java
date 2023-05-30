
public class EventNode {
	EventNode next;
	int type;//0 for dummy, 1 for arrive, 2 for departure
	double Interarrivet;
	int customerCode;
	double arriveTime=0;
	
	EventNode(int i, int t,double a,double b){
		customerCode=i;
		next=null;
		type=t;
		Interarrivet=a;
		arriveTime=b;
	}
	
	String printNode() {
		if(customerCode==-2) {
			return " ";
		}else {
			String ans;
			if(type==1) {
				StringBuilder s=new StringBuilder(customerCode+"(A): "+arriveTime);
				ans =s.toString();
			}else {
				StringBuilder s=new StringBuilder(customerCode+"(D): "+arriveTime);
				ans =s.toString();
			}
			return ans;
		}
	}
}
