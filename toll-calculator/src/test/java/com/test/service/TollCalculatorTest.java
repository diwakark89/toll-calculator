package com.test.service;

import com.test.model.Car;
import com.test.model.Motorbike;
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
    void shouldReturnTollFee(){
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,17);
        cal.set(Calendar.MINUTE,30);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        cal.set(Calendar.DATE,0);

        Date d = cal.getTime();

        int val=calculator.getTollFee(new Motorbike(),d);
        assertEquals(val,0);
    }

}

