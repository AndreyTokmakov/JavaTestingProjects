package Enumeration;

public class Praktikum_Loop
{
    static enum Currency {
        USD,
        EUR,
        RUB,
        CHF
    }

    public static void main(String[] args)
    {
        for (Currency currency: Currency.values()){
            System.out.println(currency);
        }
    }
}
