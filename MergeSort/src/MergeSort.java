import java.lang.reflect.Array;

public class MergeSort {

	public static void main(String[] args) {
		int v[] = { 12, 5, 7, 0, 41, 5, 2, 8 };

		v = mergeSort(v);

		for (int i = 0; i < v.length; i++)
			System.out.print(v[i] + " ");
	}

	/**
	 * A method that sorts an array using the merge sort algorithm The first
	 * part of this method splits the array and then calls the merge method to
	 * order the elements of each splitted array
	 * 
	 * @param v
	 *            the array to be sorted
	 * @return a sorted array
	 */
	private static int[] mergeSort(int[] v) {

		if (v.length == 1)
			return v;

		int[] v1 = new int[v.length / 2];
		int[] v2 = new int[v.length / 2];
		for (int i = 0; i < v1.length; i++) {
			v1[i] = v[i];
			v2[i] = v[(v.length / 2) + i];
		}

		v1 = mergeSort(v1);
		v2 = mergeSort(v2);

		return merge(v1, v2);
	}

	/**
	 * A method that sorts two arrays into one sorted array
	 * 
	 * @param v1
	 *            part of the array to be sorted
	 * @param v2
	 *            other part to be sorted
	 * @return a single sorted array
	 */
	private static int[] merge(int[] v1, int[] v2) {

		int[] res = new int[v1.length + v2.length];

		int itr = 0;
		int itr1 = 0;
		int itr2 = 0;

		while (itr1 < v1.length && itr2 < v2.length) {

			if (v1[itr1] < v2[itr2]) {
				res[itr] = v1[itr1];
				itr1++;
			} else {
				res[itr] = v2[itr2];
				itr2++;
			}
			itr++;

		}
		while (itr1 < v1.length) {
			res[itr] = v1[itr1];
			itr1++;
			itr++;
		}
		while (itr2 < v2.length) {
			res[itr] = v2[itr2];
			itr2++;
			itr++;
		}

		return res;
	}

}
