import java.util.Arrays;

abstract class Sorter {
  private final int[] arr;
  public Sorter(int[] arr) {
    this.arr = arr;
  }
  public int[] getArray() {
    return this.arr;
  }
  public abstract boolean needToSwap(int a, int b);
  public void sort() {
    for (int i = 0; i < arr.length - 1; i++) {
      for (int j = i + 1; j < arr.length; j++) {
        if (needToSwap(arr[i], arr[j])) {
          int tmp = arr[i];
          arr[i] = arr[j];
          arr[j] = tmp;
        }
      }
    }
  }
}

class SorterA extends Sorter {
  public SorterA(int[] arr) {
    super(arr);
  }

  @Override
  public boolean needToSwap(int a, int b) {
    return a > b;
  }
}

class SorterB extends Sorter {
  public SorterB(int[] arr) {
    super(arr);
  }

  @Override
  public boolean needToSwap(int a, int b) {
    return b > a;
  }
}

class SorterC extends Sorter {
  public SorterC(int[] arr) {
    super(arr);
  }

  @Override
  public boolean needToSwap(int a, int b) {
    return Math.abs(a) > Math.abs(b);
  }
}

class SorterD extends Sorter {
  public SorterD(int[] arr) {
    super(arr);
  }

  @Override
  public boolean needToSwap(int a, int b) {
    if (a % 2 == 0 && b % 2 == 0 && a > b) return true;
    if (a % 2 != 0 && b % 2 != 0 && a < b) return true;
    return a % 2 != 0 && b % 2 == 0;
  }
}

class SorterE extends Sorter {
  public SorterE(int[] arr) {
    super(arr);
  }

  @Override
  public boolean needToSwap(int a, int b) {
    return (a % 2 == 0 && b % 2 == 0 && a > b) || (a % 2 != 0 && b % 2 != 0 && a < b);
  }
}

class Finder {
  public boolean criteria(int a, int cur) {
    return false;
  }
  public int find(int[] A) {
    int index = -1;
    int current = A[0];
    for (int i = 0; i < A.length; i++) {
      if (criteria(A[i], current)) {
        index = i;
        current = A[index];
      }
    }
    return index;
  }
}

class FinderA extends Finder {
  @Override
  public boolean criteria(int a, int cur) {
    return a > cur;
  }
}

class FinderB extends Finder {
  @Override
  public boolean criteria(int a, int cur) {
    return a <= cur;
  }
}

class FinderC extends Finder {
  @Override
  public boolean criteria(int a, int cur) {
    return a % 2 == 0 && a <= cur;
  }
}

class FinderD extends Finder {
  @Override
  public boolean criteria(int a, int cur) {
    return isPrime(a) && a > cur;
  }
  private boolean isPrime(int a) {
    for (int i = 2; i < a; i++) {
      if (a % i == 0) return false;
    }
    return true;
  }
}

public class Main {
  public static void main(String[] args) {
    int[] A = { 5,1,2,7,10,12,3,4,16,9,6,17,19,8,20,15,11,13,18,14 };
    Sorter sortE = new SorterE(A);
    sortE.sort();
    System.out.println("Cau e: " + Arrays.toString(sortE.getArray()));
    Sorter sortA = new SorterA(A);
    sortA.sort();
    System.out.println("Cau a: " + Arrays.toString(sortA.getArray()));
    Sorter sortB = new SorterB(A);
    sortB.sort();
    System.out.println("Cau b: " + Arrays.toString(sortB.getArray()));
    Sorter sortC = new SorterC(A);
    sortC.sort();
    System.out.println("Cau c: " + Arrays.toString(sortC.getArray()));
    Sorter sortD = new SorterD(A);
    sortD.sort();
    System.out.println("Cau d: " + Arrays.toString(sortD.getArray()));

    System.out.println("=============================================");

    int[] B = { 5,1,2,7,10,12,3,4,16,9,6,17,19,8,20,15,11,13,18,14 };
    Finder finderA = new FinderA();
    System.out.println("Cau a: " + finderA.find(B));
    Finder finderB = new FinderB();
    System.out.println("Cau b: " + finderB.find(B));
    Finder finderC = new FinderC();
    System.out.println("Cau c: " + finderC.find(B));
    Finder finderD = new FinderD();
    System.out.println("Cau d: " + finderD.find(B));
  }
}