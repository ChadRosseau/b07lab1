import java.io.*;
import java.util.*;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        this.coefficients = new double[1];
        this.coefficients[0] = 0;
        this.exponents = new int[1];
        this.exponents[0] = 0;
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws Exception {
        // Read into scanner
        Scanner scanner = new Scanner(file);
        String polyString = scanner.nextLine();
        scanner.close();

        // Split on + or -
        String[] segments = polyString.split("[\\+-]");

        // Offset if beginning of string has sign
        int offset = 0;
        if (polyString.charAt(0) == '+' || polyString.charAt(0) == '-') offset = 1;

        double[] newCoefficients = new double[segments.length - offset];
        int[] newExponents = new int[segments.length - offset];

        // Fill in info as positives only
        for (int i = offset; i < segments.length; i++) {
            String[] info = segments[i].split("x", -1);
            newCoefficients[i - offset] = Double.parseDouble(info[0]);
            // Assigns exponent as 0, 1 or e depending on info given.
            newExponents[i - offset] = info.length > 1 ? (info[1] == "" ? 1 : Integer.parseInt(info[1])) : 0;
        }

        // Correct signs
        int idx = 0;
        for (int i = 0; i < polyString.length(); i++) {
            if (polyString.charAt(i) == '-') {
                newCoefficients[idx] *= -1;
                idx++;
            } else if (polyString.charAt(i) == '+') {
                idx++;
            }
        }

        // Assign to properties
        this.coefficients = newCoefficients;
        this.exponents = newExponents;
    }

    public Polynomial add(Polynomial poly) {
        // Create tracking array of where exponents are needed. A needed exponent will have 1 at its index, otherwise 0.
        int largestLen = Math.max(this.exponents[this.exponents.length - 1], poly.exponents[poly.exponents.length - 1]);
        int[] newExponentTracker = new int[largestLen + 1];
        Arrays.fill(newExponentTracker, 0);
        int needed = 0;

        // Populate tracker
        for (int i : this.exponents) {
            newExponentTracker[i] = 1;
            needed++;
        }
        for (int i : poly.exponents) {
            if (newExponentTracker[i] == 0) {
                newExponentTracker[i] = 1;
                needed++;
            }
        }

        // Populate new exponents
        int[] newExponents = new int[needed];
        int j = 0;
        for (int i = 0; i < newExponentTracker.length; i++) {
            if (newExponentTracker[i] == 1) {
                newExponents[j] = i;
                j++;
            }
        }

        // Populate new coefficients
        double[] newCoefficients = new double[needed];
        Arrays.fill(newCoefficients, 0);

        for (int i = 0; i < largestLen; i++) {
            // Get index of exponent in newExponents
            // Add coefficients to newCoefficients
            if (i < this.coefficients.length) {
                int idx = Arrays.binarySearch(newExponents, this.exponents[i]);
                newCoefficients[idx] += this.coefficients[i];
            }
            if (i < poly.coefficients.length) {
                int idx = Arrays.binarySearch(newExponents, poly.exponents[i]);
                newCoefficients[idx] += poly.coefficients[i];
            }
        }

        return new Polynomial(newCoefficients, newExponents);
    }

    public Polynomial multiply(Polynomial poly) {
        int largestPower = this.exponents[this.exponents.length - 1] * poly.exponents[poly.exponents.length - 1];
        int[] newExponentTracker = new int[largestPower + 1];
        Arrays.fill(newExponentTracker, 0);
        int needed = 0;

        // Populate tracker
        for (int i : this.exponents) {
            for (int j : poly.exponents) {
                int idx = i + j;
                if (newExponentTracker[idx] == 0) {
                    newExponentTracker[idx] = 1;
                    needed++;
                }
            }
        }

        // Populate new exponents
        int[] newExponents = new int[needed];
        int idx = 0;
        for (int i = 0; i < newExponentTracker.length; i++) {
            if (newExponentTracker[i] == 1) {
                newExponents[idx] = i;
                idx++;
            }
        }

        // Populate new coefficients
        double[] newCoefficients = new double[needed];
        Arrays.fill(newCoefficients, 0);

        for (int i = 0; i < this.exponents.length; i++) {
            for (int j = 0; j < poly.exponents.length; j++) {
                int expIdx = Arrays.binarySearch(newExponents, this.exponents[i]+poly.exponents[j]);
                newCoefficients[expIdx] += this.coefficients[i] * poly.coefficients[j];
            }
        }

        return new Polynomial(newCoefficients, newExponents);
    }

    public double evaluate(double x) {
        double res = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            res += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }
        return res;
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }

    public void saveToFile(String filename) {
        String output = "";
        for (int i = 0; i < coefficients.length; i++) {
            double coeff = coefficients[i];
            int exp = exponents[i];

            if (i > 0 && coeff >= 0) output += "+";
            
            // Converts integer double to just integer.
            if (coeff % 1 == 0) output += (int)coeff;
            else output += coeff;

            if (exp > 1) {
                output += "x";
                output += exp;
            }
        }

        System.out.println(output);

        try {
            FileWriter file = new FileWriter(filename);
            file.write(output);
            file.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }


}