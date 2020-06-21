import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Wep {

	int[] key;
	public Wep(String key) {
		this.key = parseKey(key);
	}
	
	public void printPackages() throws IOException {
		FileWriter fw = new FileWriter("WepPackages.txt", false);
		fw = new FileWriter("WepPackages.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		int[] iv = {3,255,0};
		int[] sessionKey = new int[key.length + 3];
		System.arraycopy(iv, 0, sessionKey, 0, iv.length);
		System.arraycopy(key, 0, sessionKey, iv.length, key.length);
		String snapHeader = "AA";
		
		for(int A = 0; A < key.length; A++) {
			sessionKey[0] = A + 3;
			for(int count = 0; count < 256; count++) {
				sessionKey[2] = count;
				Box b = new Box(256);
				b.ksa(sessionKey);
				
				int i = 0;
				int j = 0;
		        i = (i + 1) % 256;
		        j = (j + b.get(i)) % 256;
		        b.swap(i, j);
		        int keyStreamByte = b.get((b.get(i) + b.get(j)) % 256);
		        int cipherByte = Integer.valueOf(snapHeader, 16) ^ keyStreamByte;
		        bw.write(sessionKey[0] + ";" + sessionKey[1] + ";" + sessionKey[2] + ";" + cipherByte + "\n");
			}
		}
		bw.close();
		fw.close();
	}
	private int[] parseKey(String key) {
		int[] parsedKey = new int[key.length()/2];
		int j = 0;
		for(int i = 0; i < key.length()-1; i = i+2) {
			String b = key.substring(i,i+1) + key.substring(i+1,i+2);
			parsedKey[j] = Integer.valueOf(b, 16);
			j++;
		}
	
		return parsedKey;
	}
}
