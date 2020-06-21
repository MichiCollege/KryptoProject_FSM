
public class Box {
	private int[] box;
	public Box(int size) {
		box = new int[size];
		initBox();
	}
	
	//Initiates the box with all permutations
	private void initBox() {
		for(int i = 0; i < box.length; i++) {
			box[i] = i;
		}
	}
	public int get(int i) {
		return box[i];
	}
	//Swaps the values in the box with index i and j
	public void swap(int i, int j) {
		int tmp = box[i];
		box[i] = box[j];
		box[j] = tmp;
	}
	//Uses Key scheduling algoritm to scramble the box with the provided key
	public void ksa(int[] key) {
		int j = 0;
		for(int i = 0; i < 256; i++) {
			j = (j + box[i] + key[i % key.length]) % 256;
			swap(i,j);
		}
	}

}
