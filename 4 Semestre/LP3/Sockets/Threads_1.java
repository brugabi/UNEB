
public class Threads_1 {
	public static void main(String args[]) {
		Thread t1 = new Thread();
		MeuRunnable r = new MeuRunnable();
		System.out.println(t1.currentThread().
				getName());
		
		Thread t2 = new Thread(r); 
		t2.start();
		Thread t3 = new Thread(r); 
		t3.start();
		Thread t4 = new Thread(r); 
		t4.start();
		Thread t5 = new Thread(r); 
		t5.start();
		
		
	}
}
