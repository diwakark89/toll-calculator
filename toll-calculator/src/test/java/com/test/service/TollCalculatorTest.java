package com.test.service;

import com.test.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TollCalculatorTest {

    private TollCalculator calculator;

    @BeforeEach
    void setup() {
        calculator = new TollCalculator();
    }

    @Test
    void shouldReturnTollFeeZero_forMotorbike() {
        Date dateTime = getDefaultTime();

        int val = calculator.getTollFee(new Motorbike(), dateTime);
        assertEquals( 0,val);
    }

    @Test
    void shouldReturnTollFeeZero_forDiplomat() {
        Date dateTime = getDefaultTime();

        int val = calculator.getTollFee(new Diplomat(), dateTime);
        assertEquals( 0,val);
    }

    @Test
    void shouldReturnTollFeeZero_forEmergency() {
        Date dateTime = getDefaultTime();

        int val = calculator.getTollFee(new Emergency(), dateTime);
        assertEquals( 0,val);
    }

    @Test
    void shouldReturnTollFeeZero_forForeign() {
        Date dateTime = getDefaultTime();

        int val = calculator.getTollFee(new Foreign(), dateTime);
        assertEquals( 0,val);
    }

    @Test
    void shouldReturnTollFeeZero_forMilitary() {
        Date dateTime = getDefaultTime();

        int val = calculator.getTollFee(new Military(), dateTime);
        assertEquals( 0,val);
    }

    @Test
    void shouldReturnTollFeeZero_forCar_onWeekends() {
        Date dateTime = getTimeBasedOn_GivenDate(1, 1, 2003);
        int val = calculator.getTollFee(new Car(), dateTime);
        assertEquals( 0,val);
    }

    @Test
    void shouldReturnTollFeeZero_forCar_onHolidays() {
        Date dateTime = getTimeBasedOn_GivenDate(1, 1, 2003);
        int val = calculator.getTollFee(new Car(), dateTime);
        assertEquals( 0,val);
    }

    @Test
    void shouldReturn_Total_Toll_FeeZero_forCar_in_July() {

        Date[] dates = {
                getTimeBasedOn_GivenDate(1, 6, 2003),
                getTimeBasedOn_GivenDate(2, 6, 2003),
                getTimeBasedOn_GivenDate(3, 6, 2003)
        };

        int val = calculator.getTollFee(new Car(), dates);
        assertEquals( 0,val);
    }

    @Test
    void shouldReturn_Total_Toll_Fee_forCar_if_Entries_in_MoreThanHour() {

        Date[] dates = {
                getTimeBasedOn_Time(2, 3, 2003, 12, 0, 0),
                getTimeBasedOn_Time(2, 3, 2003, 13, 30, 0),
                getTimeBasedOn_Time(2, 3, 2003, 14, 15, 0)
        };

        int val = calculator.getTollFee(new Car(), dates);
        System.out.println(val);
        assertEquals( 24,val);
    }

    @Test
    void shouldReturn_Total_Toll_Fee_forCar_if_Entries_in_EveryHour() {

        Date[] dates = {
                getTimeBasedOn_Time(2, 3, 2003, 12, 0, 0),
                getTimeBasedOn_Time(2, 3, 2003, 13, 0, 0),
                getTimeBasedOn_Time(2, 3, 2003, 14, 0, 0),
                getTimeBasedOn_Time(2, 3, 2003, 15, 0, 0)
        };

        int val = calculator.getTollFee(new Car(), dates);
        System.out.println(val);
        assertEquals(29,val );
    }

    @Test
    void shouldReturn_Total_Toll_Fee_forCar_if_Entries_in_Between_14And16_Hour() {

        Date[] dates = {
                getTimeBasedOn_Time(2, 3, 2003, 14, 0, 0),
                getTimeBasedOn_Time(2, 3, 2003, 14, 15, 0),
                getTimeBasedOn_Time(2, 3, 2003, 15, 45, 0),
                getTimeBasedOn_Time(2, 3, 2003, 16, 5, 0),
                getTimeBasedOn_Time(2, 3, 2003, 16, 58, 0)
        };

        int val = calculator.getTollFee(new Car(), dates);
        System.out.println(val);
        assertEquals( 39,val);
    }

    private Date getDefaultTime() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DATE, 10);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2003);

        Date dateObj= cal.getTime();
        System.out.println(dateObj);
        return dateObj;
    }

    private Date getTimeBasedOn_Time(int date, int month, int year, int hour, int minute, int seconds) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, seconds);
        cal.set(Calendar.DATE, date);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);

        Date dateObj= cal.getTime();
        System.out.println(dateObj);
        return dateObj;
    }

    private Date getTimeBasedOn_GivenDate(int date, int month, int year) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DATE, date);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);

        Date dateObj= cal.getTime();
        System.out.println(dateObj);
        return dateObj;
    }

}

