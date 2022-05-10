import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Diffie_Hellman {
    private static SecureRandom random = new SecureRandom();

    private static BigInteger g = BigInteger.valueOf(5);
    private static BigInteger a = new BigInteger("516415537138565400654460647684754583949691111146492085276709191698808284105442818124657845793813991299243458567271266633566515386293258242513818707961312840");
    private static BigInteger p = new BigInteger("376136317015817541134565651197938274527949467832977278939335518271565236964492889587764292646346828242549008563838449011089273599825220180984524321167802755");

    //Main methods
    @Test
    public void runGetGToTheAModP() {

        BigInteger gToTheAModP = gToTheXModP(g, a, p);

        System.out.println("P:");
        System.out.println(p);
        System.out.println();
        System.out.println("g^a % p");
        System.out.println(gToTheAModP);


    }

    @Test
    public void runGetGToTheABModP() {
        BigInteger gbModP = new BigInteger("322850889154999908355972596386865059906808391207933285476993612550316449871636674488789861664804359232947610465248601322862365921595960140402297595028362160");
        BigInteger BaModP = gToTheXModP(gbModP, a, p);

        System.out.println("g^(a*b) % p");
        System.out.println(BaModP);
    }



    //tests and other methods
    @Test
    public void unitTest() {
        BigInteger p = new BigInteger("509");
        BigInteger a = new BigInteger("1024");
        BigInteger g = new BigInteger("6546");
        BigInteger op = gToTheXModP(g, a, p);
        assertEquals(op, new BigInteger("376"));


        p = new BigInteger("516");
        a = new BigInteger("346");
        g = new BigInteger("8975");
        op = gToTheXModP(g, a, p);
        assertEquals(op, new BigInteger("253"));

        p = new BigInteger("753");
        a = new BigInteger("4");
        g = new BigInteger("75855");
        op = gToTheXModP(g, a, p);
        assertEquals(op, new BigInteger("45"));

        p = new BigInteger("156");
        a = new BigInteger("498");
        g = new BigInteger("1567");
        op = gToTheXModP(g, a, p);
        assertEquals(op, new BigInteger("25"));

        p = new BigInteger("11116546516516");
        a = new BigInteger("453");
        g = new BigInteger("5354534");
        op = gToTheXModP(g, a, p);
        assertEquals(op, new BigInteger("6701686931428"));

        p = new BigInteger("154444444444");
        a = new BigInteger("242");
        g = new BigInteger("2");
        op = gToTheXModP(g, a, p);
        assertEquals(op, new BigInteger("87790262464"));

        p = new BigInteger("41");
        a = new BigInteger("1");
        g = new BigInteger("4");
        op = gToTheXModP(g, a, p);
        assertEquals(op, new BigInteger("4"));

        p = new BigInteger("651");
        a = new BigInteger("2");
        g = new BigInteger("6516");
        op = gToTheXModP(g, a, p);
        assertEquals(op, new BigInteger("36"));

        p = new BigInteger("1");
        a = new BigInteger("1");
        g = new BigInteger("1");
        op = gToTheXModP(g, a, p);
        assertEquals(op, new BigInteger("0"));

        p = PrimeGeneration.getPrime();
        a = getRandomBigInt(512);
        g = getRandomBigInt(512);
        op = gToTheXModP(g, a, p);
        System.out.println(op);

    }

    private static BigInteger gToTheXModP(BigInteger g, BigInteger x, BigInteger p) {

        //find powers of 2 that are less than a
        List<BigInteger> gTable = calcGTable(g, x, p);
        BigInteger remainder = x;
        BigInteger total = BigInteger.ONE;

        while (remainder.compareTo(BigInteger.ZERO) > 0) {
            if (remainder.compareTo(BigInteger.TWO) >= 0) {
                int reduction = getBiggestLessThanOrEqualRemainder(remainder, gTable);
                remainder = remainder.subtract(BigInteger.TWO.pow(reduction));
                total = total.multiply(gTable.get(reduction - 1)).mod(p);
            }

            if (remainder.compareTo(BigInteger.ONE) == 0) {
                total = total.multiply(g.mod(p)).mod(p);
                remainder = remainder.subtract(BigInteger.ONE);
            }
        }

        return total;
    }

    private static BigInteger getRandomBigInt(int numBits) {
        return BigInteger.probablePrime(numBits, random).add(BigInteger.valueOf(random.nextInt())).multiply(BigInteger.valueOf(random.nextInt(100)));
    }



    private static int reduce(BigInteger a) {
        int cntr = 1;
        BigInteger measure = BigInteger.TWO;
        while (a.compareTo(measure) == 0 || a.compareTo(measure) == 1) {
            measure.multiply(BigInteger.TWO);
            cntr++;
        }
        return cntr - 1;
    }

    private static List<BigInteger> calcGTable(BigInteger g, BigInteger a, BigInteger p) {
        List<BigInteger> gTable = new ArrayList<>();
        BigInteger exp = BigInteger.TWO;
        BigInteger current = g.pow(2).mod(p);
        gTable.add(current);
        while (exp.compareTo(a) < 0) {
            exp = exp.add(exp);
            current = current.multiply(current).mod(p);
            gTable.add(current);
        }
        return gTable;
    }

    private static int getBiggestLessThanOrEqualRemainder(BigInteger remainder, List<BigInteger> gTable) {
        int chosenEntry = 1;

        while(BigInteger.TWO.pow(chosenEntry).compareTo(remainder) <= 0) {
            chosenEntry++;
        }
        return chosenEntry - 1;
    }
}
