import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PrimeGeneration {
    private static Random random = new SecureRandom();




    public static BigInteger getPrime() {
        boolean isPrime = false;
        BigInteger randomPrime = BigInteger.TWO;
        System.out.println("Generating Prime:");
        int cntr = 0;
        while (!isPrime && randomPrime.compareTo(BigInteger.ZERO) >= 0) {
            randomPrime = BigInteger.probablePrime(512, random);
            boolean oneP = randomPrime.isProbablePrime(1);
            boolean checkP = randomPrime.subtract(BigInteger.ONE).divide(BigInteger.TWO).isProbablePrime(1);
            isPrime = oneP && checkP;
            if (cntr % 100 == 0) {
                System.out.print(".");
            }
            cntr++;
        }
        System.out.println();
        return randomPrime;
    }

}
