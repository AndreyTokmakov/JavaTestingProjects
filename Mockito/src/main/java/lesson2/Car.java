package lesson2;

public class Car {
    Wheel wheel;

    public Car(Wheel wheel) {
        this.wheel = wheel;
    }

    public int getWheelsCount(int frontWheels,
                              int backWheels) {
        return wheel.countWheels(frontWheels, backWheels);
    }
}