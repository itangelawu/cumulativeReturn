package com.angela.cumulativeReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Angela on 5/12/2017.
 */
class CumRetCalculatorTest {
    private CumRetCalculator cumRetCalculator;
    private Date baseDate;
    @BeforeEach
    void setUp() {
        cumRetCalculator = new CumRetCalculator("C://workspace//cumulativeReturn//src//test//resources//test1.txt");
        baseDate = getDate("2015-02-01");
    }

    @Test
    void testFindCumReturn() {
        double test0 = cumRetCalculator.findCumReturn(getDate("2015-01-31"), baseDate);
        assertEquals(Double.NaN, test0);
        double test1 = cumRetCalculator.findCumReturn(getDate("2015-02-28"), baseDate);
        assertEquals(0.05, test1);
        double test2 = cumRetCalculator.findCumReturn(getDate("2015-03-13"), baseDate);
        assertEquals(0.05, test2);
        double test3 = cumRetCalculator.findCumReturn(getDate("2015-04-30"), baseDate);
        assertEquals(0.08675, test3);
        double test4 = cumRetCalculator.findCumReturn(getDate("2015-05-08"), baseDate);
        assertEquals(0.08675, test4);
        double test5 = cumRetCalculator.findCumReturn(getDate("2015-06-30"), baseDate);
        assertEquals(-0.04366, test5);
    }

    private Date getDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date result = formatter.parse(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}