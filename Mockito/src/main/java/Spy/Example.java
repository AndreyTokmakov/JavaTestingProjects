package Spy;

public class Example {
    private final int id;
    public Example(int id) {

        this.id = id;
    }

    public int getId() {
        System.out.println("Example::getId() called");
        return this.id;
    }
}