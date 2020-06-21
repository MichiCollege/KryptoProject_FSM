import java.io.File;
import java.io.IOException;

public class main {

	public static void main(String[] args) throws IOException {
		
		Wep testObject = new Wep("AF6414");
		testObject.printPackages();
		
		
		FSM keyRecoverer = new FSM(new File("WepPackages.txt"));
		System.out.println(keyRecoverer.recoverKey());
	
	}
}
