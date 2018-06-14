package com.converter.anmu.converter;

import com.converter.anmu.converter.utils.Converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConverterTest {
    @Test
    public void convetrtTest() throws Exception {
        double result = Converter.convert(245, 1.89);
        assertEquals(result, 245*1.89, 0.001);
    }

    @Test
    public void convertTestZero() throws Exception{
        double result = Converter.convert( 0, 1.2);
        assertEquals(result, 0, 0.001);
    }

    @Test
    public void convertTestNegative() throws Exception{
        double result = Converter.convert( -2, 1.2);
        assertEquals(result, -2*1.2, 0.001);
    }

    @Test
    public void crossRateTest()throws Exception{
        double result = Converter.calculateCrossRate( 1.8 , 1.2);
        assertEquals(result, 1.2/1.8, 0.001);
    }


    @Test (expected = ArithmeticException.class)
    public void crossRateTestNegative() throws Exception{
        double result = Converter.calculateCrossRate(-7, -2);
    }

    @Test (expected = ArithmeticException.class)
    public void crossRateTestZeros() throws Exception{
        double result = Converter.calculateCrossRate(0, 0);
    }
}
