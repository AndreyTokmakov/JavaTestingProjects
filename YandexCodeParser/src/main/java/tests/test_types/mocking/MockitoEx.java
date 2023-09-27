package tests.test_types.mocking;

public class MockitoEx
{
    public static class OngoingStubbing<T> {
        public void thenReturn(int v) {

        }
    }

    public static <T> OngoingStubbing<T> when(T methodCall) {
        return new OngoingStubbing<T>();
    }
}
