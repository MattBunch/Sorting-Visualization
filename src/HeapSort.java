
public class HeapSort {

	public static void runHeapSort(int[] input) {

		// building the heap
		for (int i = input.length / 2 - 1; i >= 0; i--)
			heapify(input, input.length, i);

		// one by one extract
		for (int i = input.length - 1; i > 0; i--) {

			// move current root to end
			int temp = input[0];
			input[0] = input[i];
			input[i] = temp;

			heapify(input, i, 0);
		}

	}

	// node i is an index in array
	private static void heapify(int[] array, int length, int i) {
		// TODO Auto-generated method stub
		int largest = i;
		int left = 2 * i + 1;
		int right = 2 * i + 1;

		// if left is larger than root
		if (left < length && array[left] > array[largest])
			largest = left;

		// if right is larger than node
		if (right < length && array[right] > array[largest])
			largest = right;

		// if largest is not current node
		// exchange it with max of left and right child
		if (largest != i) {
			int temp = array[i];
			array[i] = array[largest];
			array[largest] = temp;

			// recursively heapify the affected sub-tree
			heapify(array, length, largest);
		}

	}

}
