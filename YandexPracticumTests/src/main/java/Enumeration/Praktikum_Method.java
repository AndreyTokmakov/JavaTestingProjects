package Enumeration;

public class Praktikum_Method
{

    enum Currency {
        USD,
        EUR,
        RUB,
        CHF;

        @Override
        public String toString() {
            return "Валюта " + this.name();
        }
    }

    public static void main(String[] args) {
        Currency usd = Currency.USD;
        System.out.println(usd.toString());
    }
}
