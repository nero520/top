package com.shopkeeper.service.request.converter;

import com.rop.request.RopConverter;
import com.shopkeeper.service.domain.TimeSlot;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午9:26
 */
public class TimeSlotsConverter implements RopConverter<String, LinkedList<TimeSlot>>
{
    @Override
    public String unconvert(LinkedList<TimeSlot> target) {
        StringBuilder sb = new StringBuilder();
        for (TimeSlot timeSlot : target) {
            String week = timeSlot.getWeek();
            String hour = timeSlot.getHour();
            if (week != null && hour != null) {
                sb.append(week + "-" + hour + ",");
            }
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        }
        else {
            return null;
        }
    }

    @Override
    public Class<String> getSourceClass() {
        return String.class;
    }

    @Override
    public Class<LinkedList<TimeSlot>> getTargetClass() {
	    LinkedList<TimeSlot> list = new LinkedList<TimeSlot>();
        return (Class<LinkedList<TimeSlot>>) list.getClass();
    }

    @Override
    public LinkedList<TimeSlot> convert(String source) {
        if (source == null) {
            return null;
        }
	    LinkedList<TimeSlot> timeSlotList = new LinkedList<TimeSlot>();
        String[] strTimeSlots = StringUtils.split(source, ",");
        for (String strTimeSlot : strTimeSlots) {
            String[] weekHour = StringUtils.split(strTimeSlot, "-");
            if (weekHour.length == 2) {
                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setWeek(weekHour[0]);
                timeSlot.setHour(weekHour[1]);
                timeSlotList.add(timeSlot);
            }
        }
        return timeSlotList;
    }
}
