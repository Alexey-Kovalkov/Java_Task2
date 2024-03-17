package task2reflection;

public class Main {
    public static void main(String[] args) {
        Fraction fr = new Fraction(2,3);
        Fractionable num = (Fractionable) Utils.cache(fr);
        double d;
        num.doubleValue(); // sout сработал
        num.doubleValue(); // sout молчит
        num.doubleValue(); // sout молчит
        num.setNum(5);
        System.out.println("=========");
        num.doubleValue();              // sout сработал
        d = (double) num.doubleValue(); // sout молчит
        System.out.println("d = " + d);
    }
}
