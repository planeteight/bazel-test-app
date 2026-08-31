public class ExampleUnitTest {
    public static void main(String[] args) {
        int total = 2 + 3;
        if (total != 5) {
            throw new AssertionError("Expected 2 + 3 to equal 5 but got " + total);
        }
        System.out.println("ExampleUnitTest passed");
    }
}
