
public class Console {
	public static void printPrompt(String prompt){
		System.out.print(prompt + " ");
		System.out.flush();
	}
	
	public static String readLine(){
		int ch;
		String r = "";
		boolean done = false;
		while (!done){
			try{
				ch = System.in.read();
				if (ch < 0 || (char)ch == '\n')
					done = true;
				else if ((char)ch != '\r')
					r = r + (char) ch;
			}
			catch(java.io.IOException e){
				done = true;
			}
		}
		return r;
	}
	public static String readLine(String prompt){
		printPrompt(prompt);
		return readLine();
	}
	
	public static int readInt(){
		while(true){
			try{
				return Integer.valueOf(readLine().trim()).intValue();
			} catch(NumberFormatException e){
				System.out.println("Not an integer. Please try again!");
			}
		}
	}
	
	public static double readDouble(){
		while(true){
			try{
				return Double.valueOf(readLine().trim()).doubleValue ();
			} catch(NumberFormatException e){
				System.out.println("Not a floating point number. Please try again!");
			}
		}
	}
	
	public static void clrScr(){
		for(int x = 0; x < 25; x++){
			System.out.println("");
		}
	}
}
