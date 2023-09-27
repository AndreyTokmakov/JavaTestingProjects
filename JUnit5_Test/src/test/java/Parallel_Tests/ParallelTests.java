package Parallel_Tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

final class Worker {
    public static final String name =
            Thread.currentThread().getName() + ":" + Thread.currentThread().getId();

    @Override
    public String toString() {
        return String.format("Worker{%s}", name);
    }
}

@Execution(ExecutionMode.CONCURRENT)
public class ParallelTests {

    private Worker worker = new Worker();

    @Test
    void testOne() {
        System.out.println("testOne: " + Thread.currentThread().getName());
        System.out.println("testOne: " + worker);
    }

    @Test
    void testTwo() {
        System.out.println("testTwo: " + Thread.currentThread().getName());
        System.out.println("testTwo: " + worker);
    }

    @Test
    void testThree() {
        System.out.println("testThree: " + Thread.currentThread().getName());
        System.out.println("testThree: " + worker);
    }
}


// TODO: To make this work we need to create file junit-platform.properties
//       in src/test/resources and add '@Execution(ExecutionMode.CONCURRENT)'
/*
junit.jupiter.execution.parallel.enabled=true
junit.jupiter.execution.parallel.config.strategy=dynamic
*/