import java.math.BigInteger;
import java.util.*;

public class Main {

    private static int[] primesFirst = new int[]{2, 3, 5, 7};

    public static void main(String[] args) {

        BigInteger p = generatePrime(); // Случайное простое число

        //Первообразный корень p (обычно из превого десятка)
        BigInteger g = BigInteger.valueOf(7);

        //Число собеседника (его секретый ключ)
        int b = new Random(10000).nextInt(999999);

        System.out.println("Введите ваш секретный ключ (приблизительно 10^5)");
        Scanner in = new Scanner(System.in);
        //Ваше число (ваш секретный ключ)
        int a = in.nextInt();

        // Число для отправки собеседнику
        BigInteger A = modularExponentiation(g, a, p)/*g.pow(a).mod(p)*/;

        // Число для отправки вам
        BigInteger B = modularExponentiation(g, b, p)/*g.pow(b).mod(p)*/;

        //Ваш ключ
        BigInteger keyA = modularExponentiation(B, a, p)/*B.pow(a).mod(p)*/;

        //Ключ собеседника
        BigInteger keyB = modularExponentiation(A, b, p)/*A.pow(b).mod(p)*/;
    }

    // Генерация простого числа
    private static BigInteger generatePrime() {
        BigInteger bigInt = new BigInteger(256, new Random());

        // Для большей точности тест выполняется k = 5 раз
        while (!MillerRabinTest(bigInt, 5)) {
            return generatePrime();
        }

        return bigInt;
    }

    // производится k раундов проверки числа n на простоту
    private static boolean MillerRabinTest(BigInteger n, int k) {
        // если n == 2 или n == 3 - эти числа простые, возвращаем true
       /* if (n == 2 || n == 3)
            return true;*/

        // если n < 2 или n четное - возвращаем false
        if (/*n < 2 || */n.mod(BigInteger.TWO).equals(BigInteger.ZERO))
            return false;

        // представим n − 1 в виде (2^s)·t, где t нечётно, это можно сделать последовательным делением n - 1 на 2
        BigInteger t = n.subtract(BigInteger.ONE);

        int s = 0;

        while (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            t = t.divide(BigInteger.TWO);
            s += 1;
        }

        // повторить k раз
        for (int i = 0; i < k; i++) {
            // выберем случайное целое число a в отрезке [2, n − 2]
            BigInteger a = BigInteger.ZERO;
            while (a.compareTo(BigInteger.TWO) < 0 || a.compareTo(n.subtract(BigInteger.TWO)) > 0) {
                a = new BigInteger(256, new Random());
            }

            // x ← a^t mod n, вычислим с помощью возведения в степень по модулю
            BigInteger x = a.modPow(t, n);

            // если x == 1 или x == n − 1, то перейти на следующую итерацию цикла
            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
                continue;

            // повторить s − 1 раз
            for (int r = 1; r < s; r++) {
                // x ← x^2 mod n
                x = x.modPow(BigInteger.TWO, n);

                // если x == 1, то вернуть "составное"
                if (x.equals(BigInteger.ONE))
                    return false;

                // если x == n − 1, то перейти на следующую итерацию внешнего цикла
                if (x.equals(n.subtract(BigInteger.ONE)))
                    break;
            }

            if (!x.equals(n.subtract(BigInteger.ONE)))
                return false;
        }

        // вернуть "вероятно простое"
        return true;
    }

    // Побитовый сдвиг
    private static BigInteger modularExponentiation(BigInteger g, int ab, BigInteger p) {
        BigInteger res = BigInteger.ONE;

        while (ab > 0) {

            if ((ab & 1) > 0)
                res = (res.multiply(g)).mod(p);

            g = g.multiply(g);
            ab >>= 1;
        }
        return res;
    }

    // Рекурсивное возведение в степень
    private static BigInteger modularExponentiationRecursive(BigInteger g, int ab, BigInteger p) {

        if (ab == 0)
            return BigInteger.ONE.mod(p);

        if (ab % 2 == 1) {
            return modularExponentiationRecursive(g, ab - 1, p)
                    .multiply(g);
        } else {
            BigInteger ba = modularExponentiationRecursive(g, ab / 2, p);
            return ba.multiply(ba);
        }

    }
/*
    private static BigInteger modularExponentiation(BigInteger g, int ab, BigInteger p) {
        BigInteger c = BigInteger.ONE;

        for (int i = 0; i < ab; i++)
            c = (c.multiply(g)).mod(p);

        return c;
    }
*/

    // Получение первообразного корня, который тоже должен быть простым
/*
    private static BigInteger GetPRoot(BigInteger p) {
        ArrayList<Integer> roots = new ArrayList<>();
        for (int i : primesFirst)
            if (IsPRoot(p, i))
                roots.add(i);

        return BigInteger.valueOf(roots.get(new Random(roots.size()).nextInt()));
    }
*/

/*
    private static boolean IsPRoot(BigInteger p, int a) {

        */
/*if (a == 0 || a == 1)
            return false;*//*

        long last = 1;

        Set<Long> set = new HashSet<>();
        for (long i = 0; i < p - 1; i++) {
            last = (last * a) % p;
            if (set.contains(last)) // Если повтор
                return false;
            set.add(last);
        }
        return true;
    }
*/


}
