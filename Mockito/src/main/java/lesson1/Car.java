package lesson1;

public class Car {
    Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public int getEnginePower() {
        return engine.getPower(); // использует метод другого класса
    }
}