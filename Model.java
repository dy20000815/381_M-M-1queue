
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

public class Model {
	EventNode nextArrival;
	EventNode nextDeparture;
	EventNode service;
	double serviceEnd=0;
	int customerCode=0;
	double idlet=0;
	double totalLength=0;
	int state;//0 means idle, 1 means busy
	double clock;
	double lambda;
	double mu;
	int customers;
	LinkedList ll;
	LinkedList delayL;
	
	Model(double l, double m, int c){
		nextArrival=new EventNode(-2,0,0,Double.MAX_VALUE);
		nextDeparture=new EventNode(-2,0,0,Double.MAX_VALUE);
		service=new EventNode(-2,0,0,Double.MAX_VALUE);
		state=0;
		clock=0;
		lambda=l;
		mu=m;
		customers=c;
		ll=new LinkedList();
		delayL=new LinkedList();
	}
	
	double getRandomInterarrival() {
		double u=new Random().nextDouble();
		double ans=(-Math.log(1-u))/lambda;
		return ans;
	}
	
	double getRandomService() {
		double u=new Random().nextDouble();
		double ans=(-Math.log(1-u))/mu;
		return ans;
	}
	
	void createModel() {
		double curr=0;
		for(int i=0;i<customers; i++) {
			double t=getRandomInterarrival();
			curr+=t;
			EventNode n=new EventNode(i+1,1,t,curr);
			ll.add(n);
		}
	}
	
	void printTrace(BufferedWriter writer) throws IOException {
		if(nextDeparture.customerCode==-2) {
			writer.write("Clock: "+clock+"\nNow servicing: last customer departured, system idle."+"\nNextArrival: "+nextArrival.printNode()+"\nDelay List: ");
			printDelayL(writer);
			writer.write("\n");
		}else {	
			writer.write("Clock: "+clock+"\nNow servicing: "+ nextDeparture.printNode()+"\nNextArrival: "+nextArrival.printNode()+"\nDelay List: ");
			printDelayL(writer);
			writer.write("\n");
		}
	}
	
	
	
	void printDelayL(BufferedWriter writer) throws IOException {
		EventNode curr=delayL.head;
		while(curr.next!=null) {
			curr=curr.next;
			writer.write(curr.printNode()+"->");
		}writer.write("end\n");
	}
	
	
	void runModel(BufferedWriter writer) throws IOException {
		int count=0;
		boolean skipPrint=false;
		writer.write("Clock: "+clock+"\nNextArrival: "+ll.head.next.printNode()+"\nDelay List: ");
		printDelayL(writer);
		writer.write("\n");
		while(ll.head.next!=null||delayL.head.next!=null) {
			if(state==0) {
				if(delayL.head.next==null) {
					idlet+=ll.head.next.arriveTime-clock;
					clock=ll.head.next.arriveTime;
					service=ll.head.next;
					ll.removeFirst();
					if(ll.head.next!=null) {
						nextArrival=ll.head.next;
					}else {
						nextArrival=new EventNode(-2,0,0,Double.MAX_VALUE);
					}
					double t= getRandomService();
					nextDeparture=new EventNode(service.customerCode,2,t,clock+t);
					state=1;
					serviceEnd=clock+t;
				}else if(delayL.head.next!=null) {
					service=delayL.head.next;
					delayL.removeFirst();
					double t=getRandomService();
					nextDeparture=new EventNode(service.customerCode,2,t,clock+t);
					state=1;
					serviceEnd=clock+t;
				}
			}else if(state==1) {
				if(serviceEnd<=nextArrival.arriveTime) {
					count++;
					clock=serviceEnd;
					service=new EventNode(-2,0,0,Double.MAX_VALUE);
					serviceEnd=0;
					nextDeparture=new EventNode(-2,0,0,Double.MAX_VALUE);
					state=0;
					if(delayL.head.next!=null) {
						skipPrint=true;
					}
				}else if(serviceEnd>nextArrival.arriveTime) {
					totalLength+=delayL.length;
					clock=nextArrival.arriveTime;
					delayL.add(nextArrival);
					ll.removeFirst();
					if(ll.head.next!=null) {
						nextArrival=ll.head.next;
					}else {
						nextArrival=new EventNode(-2,0,0,Double.MAX_VALUE);
					}
				}
			}
			if(skipPrint==false&&count<31) {
				printTrace(writer);
			}skipPrint=false;
		}
		writer.write("\naverage idle time: "+idlet/customers);
		writer.write("\naverage queue length: "+totalLength/customers);
		writer.close();
	}
}
