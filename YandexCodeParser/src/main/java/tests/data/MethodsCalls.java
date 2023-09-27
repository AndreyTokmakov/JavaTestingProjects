package tests.data;

public class MethodsCalls {

    private void info() {
    }

    private void print(int a, String s) {
    }

    private String getText() {
        return "text";
    }

    void func1()
    {
        String str = "";
        int len = str.length();

        info();

        this.info();
        this.print(1, "Test");


        int i = getText().length();
    }
}
