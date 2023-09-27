package types;

public enum JUnitAnnotation implements Annotation {
    Test ("Test"),
    After ("After"),
    AfterClass ("AfterClass"),
    Before ("Before"),
    BeforeClass ("BeforeClass"),
    Ignores ("Ignores");

    @Override
    public String getValue() {
        return value;
    }

    JUnitAnnotation(String value) {
        this.value = value;
    }

    final String value;
}