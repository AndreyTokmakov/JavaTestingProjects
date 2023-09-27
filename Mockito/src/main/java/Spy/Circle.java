package Spy;

public class Circle {
    private Color color;

    public Color getColour() {
        return color;
    }

    private void setColour(Color color) {
        this.color = color;
    }

    public void setColour(int radius) {
        if (radius > 5) {
            setColour(Color.BLUE);
        }
        else {
            setColour(Color.RED);
        }
    }

    public void setColour() {
        setColour(1);
    }
}