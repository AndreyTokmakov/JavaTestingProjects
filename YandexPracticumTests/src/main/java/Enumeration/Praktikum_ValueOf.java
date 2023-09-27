package Enumeration;

public class Praktikum_ValueOf
{
    static enum Currency {
        USD,
        EUR,
        RUB,
        CHF
    }

    public static void main(String[] args) {
        String rub = "RUB";
        System.out.println(Currency.valueOf(rub));
    }
}
