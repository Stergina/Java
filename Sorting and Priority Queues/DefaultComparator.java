import java.util.Comparator;

final class DefaultComparator < T extends Comparable < T >> implements Comparator < T > {
    public int compare(T a, T b) {
        return a.compareTo(b);
    }
}
