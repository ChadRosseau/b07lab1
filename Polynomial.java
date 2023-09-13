import java.util.Arrays;

public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[1];
        this.coefficients[0] = 0;
    }

    public Polynomial(double[] coeffs) {
        this.coefficients = coeffs;
    }

    public Polynomial add(Polynomial poly) {
        int coeffCount = Math.max(this.coefficients.length, poly.coefficients.length);
        double[] newCoefficients = new double[coeffCount];
        Arrays.fill(newCoefficients, 0);

        for (int i = 0; i < coeffCount; i++) {
            if (i < this.coefficients.length) newCoefficients[i] += this.coefficients[i];
            if (i < poly.coefficients.length) newCoefficients[i] += poly.coefficients[i];
        } 

        return new Polynomial(newCoefficients);
    }

    public double evaluate(double x) {
        double res = 0;
        for (int i = 0; i < coefficients.length; i++) {
            res += coefficients[i] * Math.pow(x, i);
        }
        return res;
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }
}