package Experiments;

public class Program {

    public static void main(String[] args) {
        Soundable cat = () -> System.out.println("Myaoooo");
        Soundable dog = () -> System.out.println("Uafffff");

        cat.sound();
        dog.sound();
    }
}

interface Soundable {
    void sound();
}