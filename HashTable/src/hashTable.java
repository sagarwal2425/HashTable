import java.io.File;
import java.util.Scanner;

/**
 * This Class implements Hashing
 * 
 * @author Shubham Agarwal
 *
 */
public class hashTable {
	static String[] T;
	static char[] A;
	static int N;
	static int index, del_index = 0;

	/**
	 * Main method
	 * 
	 * @param args Strings[]
	 */
	public static void main(String[] args) {
		String fileName = null; // input file

		if (args.length == 1) {
			fileName = args[0];

			hashingAlgo(fileName); // Call Hits algorithm method

		} else {
			System.out.println("Invalid number of arguments entered!");
			return;
		}
	}

	/**
	 * Hashing Algorithm
	 * 
	 * @param String fileName
	 */
	@SuppressWarnings("resource")
	public static void hashingAlgo(String fileName) {
		int cmd = 0;
		String wrd = "";

		try {
			Scanner sc = new Scanner(new File(fileName));

			while (sc.hasNext()) {
				cmd = sc.nextInt();

				if (cmd == 14) {
					N = sc.nextInt();
				} else if (cmd == 15) {
					sc.nextLine();
				} else if (cmd != 13 && cmd != 16) {
					wrd = sc.nextLine();
				}

				switch (cmd) {
				case 10: // Insert Command
					insert(wrd);
					continue;
				case 11: // Delete Command
					int x = deleteEntry(wrd);
					if (x == 1)
						System.out.println(wrd.substring(1, wrd.length()) + " not found");
					continue;
				case 12: // Search Command
					searchElement(wrd);
					continue;
				case 13: // Print Command
					printHashTable();
					continue;
				case 14: // Create Command
					T = new String[N];
					A = new char[15 * N];
					continue;
				}
			}
			sc.close();
			Scanner scan = new Scanner(System.in);
			System.out.print("To check if HashTable is full or not press Y or N \n");
			String full = scan.nextLine();
			switch (full) {
			case "y":
			case "Y":
				if (hashTableIsFull() == true)
					System.out.println("HashTable is Full");
				else 
					System.out.println("HashTable is not Full");
				break;
			case "N":
			case "n":
			}
			System.out.print("\nTo empty HashTable press Y or N \n");
			String emp = scan.nextLine();
			switch (emp) {
			case "y":
			case "Y":
				T = new String[N];
				A = new char[N * 15];
				System.out.println("Table Cleared");
				printHashTable();
				break;
			case "N":
			case "n":
				System.out.println("Table Not Cleared");
				printHashTable();
				break;
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

	}

	/**
	 * Searching element in HashTable
	 * 
	 * @param Stringtemp
	 */
	private static void searchElement(String temp) {
		int tempHashVal = hash(temp);
		int i = tempHashVal, h = 1;
		boolean flag = true, flag2 = true;

		do {
			if (T[i] != null) {
				if (A[Integer.parseInt(T[i])] != temp.charAt(1)) {
					flag = false;
					flag2 = false;
				} else {
					for (int k = 0; k < temp.length() - 1; k++) {
						if (A[Integer.parseInt(T[i]) + k] != temp.charAt(k + 1)) {
							flag = false;
							break;
						}
					}
					if (flag != false) {
						System.out.printf("%-8s found at slot %d%n", temp.substring(1, temp.length()), i);
						flag2 = true;
					}
				}
				if (flag == true)
					break;
				flag = true;
			}
			i = (i + h * h) % N;
		} while (i != tempHashVal);
		if (flag2 == false)
			System.out.printf("%-8s not found%n", temp.substring(1, temp.length()));
	}

	/**
	 * Delete element from HashTable
	 * 
	 * @param Stringtemp
	 * @return int
	 */
	private static int deleteEntry(String temp) {
		int tempHashVal = hash(temp);
		int i = tempHashVal, h = 1;
		boolean flag = true;

		do {
			if (T[i] != null) {
				if (A[Integer.parseInt(T[i])] != temp.charAt(1)) {
					flag = false;
				} else {
					for (int k = 0; k < temp.length() - 1; k++) {
						if (A[Integer.parseInt(T[i]) + k] != temp.charAt(k + 1)) {
							flag = false;
							break;
						}
					}
					if (flag != false) {
						for (int k = 0; k < temp.length() - 1; k++) {
							A[Integer.parseInt(T[i]) + k] = '\"';
						}
						System.out.printf("%-8s deleted from slot %d%n", temp.substring(1, temp.length()), i);
						T[i] = null;
						return 0;
					}
				}
				if (flag == true)
					break;
				flag = true;
			}
			i = (i + h * h) % N;
		} while (i != tempHashVal);
		return 1;
	}

	/**
	 * Insert element in HashTable
	 * 
	 * @param String wrd
	 */
	private static void insert(String wrd) {
		int hashval = hash(wrd);
		int i = hashval, h = 1;

		do {
			if (T[i] == null) {
				T[i] = Integer.toString(index);
				addElementInA(wrd);
				break;
			} else {
				if (hashTableIsFull() == true) {
					expandArray();
				}
				i = (i + h * h) % N;
				h++;
			}
		} while (i != hashval);
	}

	/**
	 * Check if HashTable is full
	 * 
	 * @return boolean
	 */
	private static boolean hashTableIsFull() {
		for (int i = 0; i < T.length; i++) {
			if (T[i] == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * To overcome the case where overflow occurs
	 * 
	 */
	private static void expandArray() {
		String[] tempT = new String[2 * N];
		char[] tempA = new char[2 * 15 * N];
		for (int i = 0; i < T.length; i++) {
			tempT[i] = T[i];
		}
		for (int i = 0; i < A.length; i++) {
			tempA[i] = A[i];
		}
		T = new String[2 * N];
		A = new char[2 * 15 * N];
		for (int i = 0; i < T.length; i++) {
			T[i] = tempT[i];
		}
		for (int i = 0; i < A.length; i++) {
			A[i] = tempA[i];
		}
		N = T.length;
	}

	/**
	 * To Print HashTable
	 * 
	 */
	private static void printHashTable() {
		System.out.println();
		System.out.print("    T		A:  ");
		int i = 0;
		for (i = 0; i < A.length; i++) {
			if (A[i] != 0) {
				if (A[i] != '\"')
					System.out.print(A[i]);
				else
					System.out.print("*");
			}
		}
		System.out.println();
		for (i = 0; i < T.length; i++) {
			if (i < 10) {
				if (T[i] != null)
					System.out.println(" " + i + ": " + T[i]);
				else
					System.out.println(" " + i + ": ");
			} else {
				if (T[i] != null)
					System.out.println(i + ": " + T[i]);
				else
					System.out.println(i + ": ");
			}
		}
		System.out.println();
	}

	/**
	 * Add Element in Word Array
	 * 
	 * @param String wrd
	 */
	private static void addElementInA(String wrd) {
		int i, j = 1;
		try {
			for (i = index; i < (index + wrd.length() - 1); i++) {
				A[i] = wrd.charAt(j);
				j++;
			}
			A[i] = '\\';
			index = index + wrd.length();
		} catch (NullPointerException e) {
			char[] tempA = new char[2 * 15 * N];
			for (int k = 0; k < A.length; k++) {
				tempA[k] = A[k];
			}
			A = new char[2 * 15 * N];
			for (int l = 0; l < A.length; l++) {
				A[l] = tempA[l];
			}
			j = 1;
			for (i = index; i < (index + wrd.length() - 1); i++) {
				A[i] = wrd.charAt(j);
				j++;
			}
			A[i] = '\\';
			index = index + wrd.length();

		}
	}

	/**
	 * Hashing Function
	 * 
	 * @param String wrd
	 * @return int
	 */
	private static int hash(String wrd) {
		int lngth = wrd.length();
		int total = 0;
		for (int i = 1; i < lngth; i++) {
			int ascii = wrd.charAt(i);
			total += ascii;
		}
		return total % N;
	}
}