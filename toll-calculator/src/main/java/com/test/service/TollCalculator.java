package com.test.service;

import com.test.constant.TollFreeVehicles;
import com.test.model.Vehicle;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class TollCalculator {

    /**
     * Calculate the total toll fee for one day
     *
     * @param vehicle - the vehicle
     * @param dates   - date and time of all passes on one day
     * @return - the total toll fee for that day
     */
    public int getTollFee(Vehicle vehicle, Date... dates) {
        Date intervalStart = dates[0];
        int totalFee = 0;

        for (Date date : dates) {
            int nextFee = getTollFee(date, vehicle);
            int tempFee = getTollFee(intervalStart, vehicle);

            TimeUnit timeUnit = TimeUnit.MINUTES;

            long diffInMillies = date.getTime() - intervalStart.getTime();
            long minutes = timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (minutes <= 60) {
                if (totalFee > 0) totalFee -= tempFee;
                if (nextFee >= tempFee) tempFee = nextFee;
                totalFee += tempFee;
            } else {
                totalFee += nextFee;
            }
        }
        if (totalFee > 60) totalFee = 60;
        return totalFee;
    }

    private boolean isTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        String vehicleType = vehicle.getType();
        return vehicleType.equals(TollFreeVehicles.MOTORBIKE.getType()) ||
                vehicleType.equals(TollFreeVehicles.TRACTOR.getType()) ||
                vehicleType.equals(TollFreeVehicles.EMERGENCY.getType()) ||
                vehicleType.equals(TollFreeVehicles.DIPLOMAT.getType()) ||
                vehicleType.equals(TollFreeVehicles.FOREIGN.getType()) ||
                vehicleType.equals(TollFreeVehicles.MILITARY.getType());
    }

    /**
     * Toll fees would be calculated for the rush hour between 6 AM -18 PM
     */

    public int getTollFee(final Date date, Vehicle vehicle) {

        if (isTollFreeDate(date) || isTollFreeVehicle(vehicle)) return 0;

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if (hour == 6 && minute <= 29) return 8;
        else if (hour == 6) return 13;
        else if (hour == 7) return 18;
        else if (hour == 8 && minute <= 29) return 13;
        else if (hour == 8) return 8;
        else if (hour >= 9 && hour <= 14) return 8;
        else if (hour == 15 && minute <= 29) return 13;
        else if (hour == 15 || hour == 16) return 18;
        else if (hour == 17) return 13;
        else if (hour == 18 && minute <= 29) return 8;

        else return 0;
    }

    private Boolean isTollFreeDate(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) return true;

        return isHoliday(year, month, day);
    }

    /**
     * We are calculating the year for 2013 and 2015 only
     */
    private boolean isHoliday(int year, int month, int day) {
        if (year == 2013) {
            return month == Calendar.JANUARY && day == 1 ||
                    month == Calendar.MARCH && (day == 28 || day == 29) ||
                    month == Calendar.APRIL && (day == 1 || day == 30) ||
                    month == Calendar.MAY && (day == 1 || day == 8 || day == 9) ||
                    month == Calendar.JUNE && (day == 5 || day == 6 || day == 21) ||
                    month == Calendar.JULY ||
                    month == Calendar.NOVEMBER && day == 1 ||
                    month == Calendar.DECEMBER && (day == 24 || day == 25 || day == 26 || day == 31);
        } else if (year == 2015) {
            return month == Calendar.JANUARY && day == 5 ||
                    month == Calendar.MARCH && (day == 29 || day == 30) ||
                    month == Calendar.APRIL && (day == 1 || day == 29) ||
                    month == Calendar.MAY && (day == 1 || day == 8 || day == 9) ||
                    month == Calendar.JUNE && (day == 5 || day == 6 || day == 21) ||
                    month == Calendar.JULY ||
                    month == Calendar.NOVEMBER && day == 1 ||
                    month == Calendar.DECEMBER && (day == 23 || day == 24 || day == 25 || day == 26 || day == 31);
        }
        return false;
    }

}

