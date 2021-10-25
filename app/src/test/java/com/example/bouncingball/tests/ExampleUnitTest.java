package com.example.bouncingball.tests;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    public void test_esto_siempre_pasa(){
        assertTrue(5>1);
    }
    public void test_esto_nunca_pasa(){
        assertTrue(5<1);
    }
}