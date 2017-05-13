package com.angela.cumulativeReturn;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Angela on 5/12/2017.
 */
public class CumRetCalculator {

    private TreeMap<Date, Double> dailyReturnsMap; //Storage holds daily returns for various days from B
    private Map<DateRange, Double> dailyReturnCache; //cache which hold calculation result for a range of asof date to base date

    /**
     * constructor to initialize dailyReturnsMap and the cache
     * @param dailyReturnsInput inputMap
     */
    public CumRetCalculator(Map<Date, Double> dailyReturnsInput) {
        DailyReturns dailyReturns = DailyReturns.getDailyReturns(dailyReturnsInput);
        this.dailyReturnsMap = dailyReturns.getDailyReturnsMap();
        dailyReturnCache = new ConcurrentHashMap<DateRange, Double>();
    }

    /**
     * method to get cumulative return for asof date from base date
     * look up calculation result in cache first, if not in cache, calculate new value, put into cache, then return the value
     * If it is in cache, fetch and return
     * @param asOf asOf date
     * @param base base date
     * @return double cumulative return for asof date from base date
     */
    double findCumReturn(Date asOf, Date base){
        if(asOf.before(base))
            return Double.NaN;
        DateRange range = new DateRange(asOf, base);
        Double result = dailyReturnCache.putIfAbsent(range, computeCumReturn(range));

        if(result==null)
            return dailyReturnCache.get(range);
        else
            return result;
    }

    /**
     * method to calculate cumulative return for asof date from base date
     * @param range asOf date and base date object
     * @return double cumulative return for asof date from base date
     */
    double computeCumReturn(DateRange range) {
        Map<Date, Double> rangeReturn = this.dailyReturnsMap.subMap(range.getBase(), false, range.getAsOf(), true);
        BigDecimal result = new BigDecimal(1);
        for (Double cumReturn: rangeReturn.values()){
            result = result.multiply(new BigDecimal(cumReturn).add(new BigDecimal(1)));
        }

        return (result.subtract(new BigDecimal(1))).setScale(8, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    /**
     * class for asOf date and base date
     * served as a key for cahce
     */
    class DateRange{
        private Date asOf;
        private Date base;
        public DateRange(Date asOf, Date base) {
            this.asOf = asOf;
            this.base = base;
        }

        public Date getAsOf() {
            return asOf;
        }

        public void setAsOf(Date asOf) {
            this.asOf = asOf;
        }

        public Date getBase() {
            return base;
        }

        public void setBase(Date base) {
            this.base = base;
        }

        @Override
        public int hashCode()
        {
            return new HashCodeBuilder()
                    .append(this.asOf)
                    .append(this.base)
                    .toHashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof DateRange == false) {
                return false;
            }
            if (this == obj) {
                return true;
            }

            return new EqualsBuilder()
                    .append(this.asOf, ((DateRange)obj).getAsOf())
                    .append(this.base, ((DateRange)obj).getBase())
                    .isEquals();
        }
    }
}
