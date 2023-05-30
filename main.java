import java.io.*;

public class main {
	public static void main (String args[]) throws IOException {
		int avelambda=Integer.parseInt(args[0]);
		int avemu=Integer.parseInt(args[1]);
		double lambda=1.0/avelambda;
		double mu=1.0/avemu;
		int c=Integer.parseInt(args[2]);
		BufferedWriter writer=new BufferedWriter(new FileWriter(args[3]));
		Model mm1=new Model(lambda, mu, c);
		mm1.createModel();
		mm1.runModel(writer);
	}
}
