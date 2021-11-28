import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayUtilTest {

    /*
     * Test is applied for the if loop in lines 5 to 7 of ArrayUtil class.
     * Tests if the input array is null.
     * If the input array is null/empty, IllegalArgumentException is thrown
     * since no subarray can be constructed for an empty array.
     */
    @Test
    public void emptyArray() {
        ArrayUtil arrayUtil = new ArrayUtil();
        int[] array = null;
        int startInclusive = 0;
        int endExclusive = 0;
        assertThrows(IllegalArgumentException.class, ()-> {
            arrayUtil.subarray(array,startInclusive,endExclusive);});
    }

    /*
     * Test is applied for the if loop in lines 9 to 11 of ArrayUtil class.
     * Tests if the starting position of the subarray is negative.
     * If the starting position of the subarray is negative, IllegalArgumentException is thrown
     * since no subarray can be initialized with a negative starting position.
     */
    @Test
    public void negStart() {
        ArrayUtil arrayUtil = new ArrayUtil();
        int[] array = {1,2,3,5,6};
        int startInclusive = -1;
        int endExclusive = 0;
        assertThrows(IllegalArgumentException.class, ()-> {
            arrayUtil.subarray(array,startInclusive,endExclusive);});
    }

    /*
     * Test is applied for the if loop in lines 13 to 15 of ArrayUtil class.
     * Tests if the ending exclusive position (position - 1) of the subarray is greater than the array's length.
     * If the ending exclusive position is greater than the array's length, IllegalArgumentException is thrown
     * since the size of a subarray can't be greater than the original array's length.
     */
    @Test
    public void illegalEnd() {
        ArrayUtil arrayUtil = new ArrayUtil();
        int[] array = {1, 2, 3, 5, 6};
        int startInclusive = 1;
        int endExclusive = 10;
        assertThrows(IllegalArgumentException.class, () -> {
            arrayUtil.subarray(array, startInclusive, endExclusive);});
    }

    /*
     * Test is applied for the if loop in lines 18 to 21 of ArrayUtil class.
     * Tests if the size of the subarray is less than or equal to zero.
     * This specific test is implemented in cases where the starting subarray position
     * is greater than the ending exclusive position. In such cases, subarray can't be
     * constructed due to illegal positions; thus the assertion is true only if the
     * condition emptyArray.length>0 is false.
     */
    @Test
    public void startGTend() {
        ArrayUtil arrayUtil = new ArrayUtil();
        int[] array = {1, 2, 3, 5, 6};
        int startInclusive = 2;
        int endExclusive = 1;
        int[] emptyArray = arrayUtil.subarray(array, startInclusive, endExclusive);
        assertFalse(emptyArray.length>0);
    }

    /*
     * Test is applied for the if loop in lines 18 to 21 of ArrayUtil class.
     * Tests if the size of the subarray is less than or equal to zero.
     * This specific test is implemented in cases where the starting subarray position
     * equals the ending exclusive position. In such cases, subarray can't be
     * constructed due to illegal positions; thus the assertion is true only if the
     * condition emptyArray.length>0 is false.
     */
    @Test
    public void startEQend() {
        ArrayUtil arrayUtil = new ArrayUtil();
        int[] array = {1, 2, 3, 5, 6};
        int startInclusive = 1;
        int endExclusive = 1;
        int[] emptyArray = arrayUtil.subarray(array, startInclusive, endExclusive);
        assertFalse(emptyArray.length>0);
    }

    /*
     * Test is applied when all above legal conditions are met.
     * Tests if the size of the subarray equals endExclusive-startInclusive.
     * The assertion is true only if the condition is true, thus when
     * the size of the constructed subarray equals the number given by
     * endExclusive-startInclusive.
     */
    @Test
    public void pass() {
        ArrayUtil arrayUtil = new ArrayUtil();
        int[] array = {1, 2, 3, 5, 6};
        int startInclusive = 1;
        int endExclusive = 4;
        int[] subarray = arrayUtil.subarray(array, startInclusive, endExclusive);
        assertTrue(subarray.length==endExclusive-startInclusive);
    }
}