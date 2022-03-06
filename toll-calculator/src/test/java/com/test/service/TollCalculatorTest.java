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
     void setup(){
        calculator=new TollCalculator();
    }

    @Test
    void shouldReturnTollFeeZero_forMotorbike(){
        Date dateTime=getDefaultTime();

        int val=calculator.getTollFee(new Motorbike(),dateTime);
        assertEquals(val,0);
    }
    @Test
    void shouldReturnTollFeeZero_forDiplomat(){
        Date dateTime=getDefaultTime();

        int val=calculator.getTollFee(new Diplomat(),dateTime);
        assertEquals(val,0);
    }
    @Test
    void shouldReturnTollFeeZero_forEmergency(){
        Date dateTime=getDefaultTime();

        int val=calculator.getTollFee(new Emergency(),dateTime);
        assertEquals(val,0);
    }
    @Test
    void shouldReturnTollFeeZero_forForeign(){
        Date dateTime=getDefaultTime();

        int val=calculator.getTollFee(new Foreign(),dateTime);
        assertEquals(val,0);
    }
    @Test
    void shouldReturnTollFeeZero_forMilitary(){
        Date dateTime=getDefaultTime();
        System.out.println(dateTime);
        int val=calculator.getTollFee(new Military(),dateTime);
        assertEquals(val,0);
    }

    private Date getDefaultTime(){
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,17);
        cal.set(Calendar.MINUTE,30);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        cal.set(Calendar.DATE,10);
        cal.set(Calendar.MONTH,0);
        cal.set(Calendar.YEAR,2003);

        return cal.getTime();
    }

}

