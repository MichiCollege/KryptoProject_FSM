import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FSM {
	private ArrayList<int[]> table;

	public FSM(File packageSource) throws FileNotFoundException {
		table = new ArrayList<>();
		initTable(packageSource);
	}

	private void initTable(File file) throws FileNotFoundException {
		Scanner s = new Scanner(file);

		while (s.hasNextLine()) {
			String[] line = s.nextLine().split(";");
			int[] lineArray = new int[4];
			for (int i = 0; i < 4; i++) {
				lineArray[i] = Integer.parseInt(line[i]);
			}
			table.add(lineArray);
		}
		s.close();
	}

	public String recoverKey() {
		int keyLength = table.get(table.size() - 1)[0];
		String proposedKey = "";
		ArrayList<Integer> key = new ArrayList<>();
		key.add(0);
		key.add(0);
		key.add(0);
		for (int A = 0; A < keyLength; A++) {
			int[] distribution = new int[256];
			for (int[] line : table) {

				key.set(0, line[0]);
				key.set(1, line[1]);
				key.set(2, line[2]);

				int j = 0;
				Box b = new Box(256);
				int org0 = -1;
				int org1 = -1;
				for (int i = 0; i < A + 3; i++) {
					j = (j + b.get(i) + key.get(i)) % 256;
					b.swap(i, j);

					if (i == 1) {
						org0 = b.get(0);
						org1 = b.get(1);
					}
				}
				int i = A + 3;
				int z = b.get(1);

				// resolve condition
				if ((z + b.get(z)) == (A + 3)) {
					
					if (org0 == b.get(0) && org1 == b.get(1)) {

						int keyStreamByte = line[3] ^ Integer.valueOf("AA", 16);
						int keyByte = (((keyStreamByte - j - b.get(i)) % 256) + 256)%256;
						distribution[keyByte] += 1;
					}
				}

			}
		
			key.add(getMax(distribution));
		}

		
		for (int i = 3; i < key.size(); i++) {
			proposedKey += Integer.toHexString(key.get(i));
		}
		return proposedKey.substring(0,keyLength+1);
	}

	private int getMax(int[] a) {
		int max = -1;
		int idx = 0;

		for (int i = 0; i < a.length; i++) {
			if (max < a[i]) {
				max = a[i];
				idx = i;
			}

		}
		return idx;
	}

}
