package com.angela.cumulativeReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Angela on 5/12/2017.
 */
class CumRetCalculatorTest {
    private CumRetCalculator cumRetCalculator;
    private Date baseDate;
    @BeforeEach
    void setUp() {
        Map<Date, Double> dailyReturnsInput = new HashMap<Date, Double>();
        dailyReturnsInput.put(getDate("2015-01-10"), 0.10);
        dailyReturnsInput.put(getDate("2015-02-10"), 0.05);
        dailyReturnsInput.put(getDate("2015-04-10"), 0.15);
        dailyReturnsInput.put(getDate("2015-04-15"), -0.10);
        dailyReturnsInput.put(getDate("2015-06-10"), -0.12);
        cumRetCalculator = new CumRetCalculator(dailyReturnsInput);
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