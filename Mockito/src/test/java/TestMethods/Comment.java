package TestMethods;

public class Comment {

    private final int id;
    private final String author;
    private String text;

    public Comment(int id, String author, String text) {
        this.id = id;
        this.author = author;
        this.text = text;
    }

    public void updateText(String text) {
        this.text = text;
    }

}
