package com.converter.anmu.converter.utils;

public class Converter {
    public static double convert(double amount, double rate){
        return amount*rate;
    }

    public static double calculateCrossRate(double firstRate, double secondRate) throws ArithmeticException {
        if(firstRate < 0 || secondRate < 0){
            throw new ArithmeticException("negative values");
        }
        double result = secondRate / firstRate;
        if (firstRate < Double.MIN_VALUE)
            throw new ArithmeticException("division by zero");
        else return result;
    }
}
