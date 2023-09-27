import org.junit.Before;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;

class SomeClassOne
{
    @Deprecated
    public void deprecatedMethodOne() {
    }

    @Deprecated
    public void deprecatedMethodTwo() {
    }

    @Test
    public void testMethodOne() {
    }

    @Test
    public void testMethodTwo() {
    }

    @Tags(value = {@Tag("regression"), @Tag("payment_module")})
    public void someMethodWithTags() {
    }
}
