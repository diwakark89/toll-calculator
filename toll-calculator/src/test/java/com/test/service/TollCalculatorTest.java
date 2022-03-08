package com.test.service;

import com.test.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TollCalculatorTest {

    private TollCalculator calculator;

    @BeforeEach
    void setup() {
        calculator = new TollCalculator();
    }

    @DisplayName("Should Return Zero for special vehicles")
    @ParameterizedTest(name = "{index}: {0}, {1}, {2}")
    @MethodSource("classParameters")
    void shouldReturnTollFeeZero_forSpecial_Vehicle(Vehicle vehicle, Date date, int expectedValue) {
        int val = calculator.getTollFee(vehicle, date);
        assertEquals(expectedValue, val);
    }

    @DisplayName("Should Return zero for Car holidays")
    @ParameterizedTest(name = "{index}: {0}, {1}, {2}")
    @MethodSource("dateParameters")
    void shouldReturnTollFeeZero_forCar_onHolidays(int year, int month, int date) {
        Date dateTime = getTimeBasedOn_GivenDate(date, month, year);
        int val = calculator.getTollFee(new Car(), dateTime);
        assertEquals(0, val);
    }

    @DisplayName("Should Return zero for holidays")
    @ParameterizedTest(name = "{index}: {0}, {1}, {2}")
    @MethodSource("multiDateParameters")
    void shouldReturn_Total_Toll_FeeZero_Holidays(Date[] dateTime, int expectedValue) {

        int val = calculator.getTollFee(new Car(), dateTime);
        assertEquals(expectedValue, val);
    }

    @DisplayName("Should return Correct Toll Fee")
    @ParameterizedTest(name = "{index}: {0}, {1}")
    @MethodSource("multiDateTimeParameters")
    void shouldReturnTollFeeBasedOn_Travel(Date[] dateTime, int expectedValue) {
        int val = calculator.getTollFee(new Car(), dateTime);
        assertEquals(expectedValue, val);
    }

    static Stream<Arguments> multiDateParameters() {
        return Stream.of(
                Arguments.of(new Date[]{
                        getTimeBasedOn_GivenDate(1, 6, 2003),
                        getTimeBasedOn_GivenDate(2, 6, 2003),
                        getTimeBasedOn_GivenDate(3, 6, 2003)
                }, 0)
        );
    }

    static Stream<Arguments> multiDateTimeParameters() {
        return Stream.of(
                Arguments.of(new Date[]{
                        getTimeBasedOn_Time(2, 3, 2003, 6, 0, 0),
                        getTimeBasedOn_Time(2, 3, 2003, 10, 15, 0),
                        getTimeBasedOn_Time(2, 3, 2003, 17, 58, 0)
                }, 29),
                Arguments.of(new Date[]{
                        getTimeBasedOn_Time(2, 3, 2003, 12, 0, 0),
                        getTimeBasedOn_Time(2, 3, 2003, 13, 30, 0),
                        getTimeBasedOn_Time(2, 3, 2003, 14, 15, 0)
                }, 24),

                Arguments.of(new Date[]{
                        getTimeBasedOn_Time(2, 3, 2003, 6, 0, 0),
                        getTimeBasedOn_Time(2, 3, 2003, 10, 15, 0),
                        getTimeBasedOn_Time(2, 3, 2003, 17, 58, 0)
                }, 29),
                Arguments.of(new Date[]{
                        getTimeBasedOn_Time(2, 3, 2003, 14, 0, 0),
                        getTimeBasedOn_Time(2, 3, 2003, 14, 15, 0),
                        getTimeBasedOn_Time(2, 3, 2003, 15, 45, 0),
                        getTimeBasedOn_Time(2, 3, 2003, 16, 5, 0),
                        getTimeBasedOn_Time(2, 3, 2003, 16, 58, 0)

                }, 60),
                Arguments.of(new Date[]{
                        getTimeBasedOn_Time(2, 3, 2003, 12, 0, 0),
                        getTimeBasedOn_Time(2, 3, 2003, 13, 0, 0),
                        getTimeBasedOn_Time(2, 3, 2003, 14, 0, 0),
                        getTimeBasedOn_Time(2, 3, 2003, 15, 0, 0)
                }, 29)
        );
    }

    static Stream<Arguments> classParameters() {
        return Stream.of(
                Arguments.of(new Diplomat(), getDefaultTime(), 0),
                Arguments.of(new Motorbike(), getDefaultTime(), 0),
                Arguments.of(new Tractor(), getDefaultTime(), 0),
                Arguments.of(new Emergency(), getDefaultTime(), 0),
                Arguments.of(new Foreign(), getDefaultTime(), 0),
                Arguments.of(new Military(), getDefaultTime(), 0)
        );
    }

    static Stream<Arguments> dateParameters() {
        return Stream.of(
                Arguments.of(2003, 0, 1),
                Arguments.of(2003, 2, 20),
                Arguments.of(2003, 1, 21),
                Arguments.of(2003, 11, 24),
                Arguments.of(2003, 11, 25),
                Arguments.of(2003, 11, 26),
                Arguments.of(2003, 11, 31)
        );
    }

    private static Date getDefaultTime() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DATE, 10);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2003);

        Date dateObj = cal.getTime();
        System.out.println(dateObj);
        return dateObj;
    }

    private static Date getTimeBasedOn_Time(int date, int month, int year, int hour, int minute, int seconds) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, seconds);
        cal.set(Calendar.DATE, date);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);

        Date dateObj = cal.getTime();
        System.out.println(dateObj);
        return dateObj;
    }

    private static Date getTimeBasedOn_GivenDate(int date, int month, int year) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DATE, date);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);

        Date dateObj = cal.getTime();
        System.out.println(dateObj);
        return dateObj;
    }
}

