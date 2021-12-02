package com.hamsterbusters.squeaker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SqueakerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void exampleTrue() {
        assertTrue(2 > 1);
    }

    @Test
    void exampleThrows() {
        assertThrows(ArithmeticException.class, () -> {
            int i = 5 / 0;
        });
    }

}
