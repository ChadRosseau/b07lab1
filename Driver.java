import java.io.File;
import java.util.Arrays;

public class Driver {
    public static void main(String[] args) throws Exception {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double[] c1 = { 6, 5 };
        int[] e1 = {0, 3};
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = { -2, -9 };
        int[] e2 = {1, 4};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        System.out.println(Arrays.toString(s.coefficients));
        System.out.println(Arrays.toString(s.exponents));
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        // Expected: -12x - 64x^4 - 45x^7
        Polynomial t = p1.multiply(p2);
        System.out.println(Arrays.toString(t.coefficients));
        System.out.println(Arrays.toString(t.exponents));

        Polynomial x = new Polynomial(new File("./polyTest.txt"));
        System.out.println(Arrays.toString(x.coefficients));
        System.out.println(Arrays.toString(x.exponents));
        x.saveToFile("new.txt");
    }
}