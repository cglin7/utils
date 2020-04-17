package org.zero.utils.support;

import org.apache.http.util.TextUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    public static String getNewNow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return new SimpleDateFormat("yyyy-MM").format(cal.getTime());
    }

    public static String getshangMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 1);
        cale.add(Calendar.DATE, -1);

        String lastday = format.format(cale.getTime());
        return lastday;

    }

    public static String getNow() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    public static String getStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    public static String getEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    /**
     * 获取昨天零点时间：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getYestoryStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);          //TODO 明天时间：cal.add(Calendar.DATE,1);后天：后天就是把1改成2 ，以此类推。
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    /**
     * 获取昨天结束时间：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getYestoryEnd() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString 时间戳 如："1473048265";
     * @param formats         要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        if (TextUtils.isEmpty(formats)) {
            formats = "yyyy-MM-dd HH:mm:ss";
        }
        Long timestamp = Long.parseLong(timestampString) * 1000;
        return new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
    }

    /**
     * 获取指定格式的当前时间
     *
     * @param formats
     * @return String
     */
    public static String getCurrentFormatDate(String formats) {
        if (TextUtils.isEmpty(formats)) {
            formats = "yyyy-MM-dd HH:mm:ss";
        }
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(formats);
        return dateFormat.format(date);
    }

    /**
     * 获取指定格式的当前时间--返回Timestamp类型
     *
     * @param formats
     * @return Timestamp
     */
    public static Timestamp getCurrentTimeStampFormat(String formats) {
        if (TextUtils.isEmpty(formats)) {
            formats = "yyyy-MM-dd HH:mm:ss";
        }
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(formats);
        return Timestamp.valueOf(dateFormat.format(date));
    }

    /**
     * 获取两个时间的时间差，返回差距多少秒
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @throws ParseException
     */
    public static int getTimeDiffSecond(Timestamp startTime, Timestamp endTime) throws ParseException {

        long diff = startTime.getTime() - endTime.getTime();
        return (int) (diff / (1000));
    }

    /**
     * 获取某日期往前多少天的日期
     *
     * @param nowDate
     * @param beforeNum
     * @return
     * @CreateTime 2016-1-13
     */
    public static Date getBeforeDate(Date nowDate, Integer beforeNum) {
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(nowDate);// 把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -beforeNum); // 设置为前beforeNum天
        return calendar.getTime(); // 得到前beforeNum天的时间
    }

    /**
     * 返回需要的日期形式
     * </p>
     * 如：style=“yyyy年MM月dd日 HH时mm分ss秒SSS毫秒”。 返回“xxxx年xx月xx日xx时xx分xx秒xxx毫秒”
     * </p>
     *
     * @param date
     * @param style
     * @return
     */
    public static String getNeededDateStyle(Date date, String style) {
        if (date == null) {
            date = new Date();
        }
        if (style == null || "".equals(style)) {
            style = "yyyy年MM月dd日";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(style);
        return sdf.format(date);
    }

    /**
     * 获取过去12个月的月份
     *
     * @return
     */
    public static String[] getLast12Months() {

        String[] last12Months = new String[12];

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1); //要先+1,才能把本月的算进去</span>
        for (int i = 0; i < 12; i++) {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1); //逐次往前推1个月
            last12Months[11 - i] = cal.get(Calendar.YEAR) + String.format("%02d", (cal.get(Calendar.MONTH) + 1));
        }
        return last12Months;
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate;
        Date endDate;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
            //System.out.println("相隔的天数="+day);
        } catch (ParseException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        return day + 1;
    }

    /**
     * <li>功能描述：是否同一天
     *
     * @param begin
     * @param end
     * @return long
     * @author Administrator
     */
    public static boolean isSameDay(Date begin, Date end) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(begin);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(end);

        if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)) {
            return true;
        }
        return false;
    }

    /**
     * 获取两个日期之间的日期
     *
     * @param startStr 开始日期
     * @param endStr   结束日期
     * @return 日期集合
     */
    public static List<String> getBetweenDates(String startStr, String endStr) {
        try {
            List<String> result = new ArrayList<String>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = dateFormat.parse(startStr);
            Date end = dateFormat.parse(endStr);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            while (tempStart.before(tempEnd)) {
                result.add(simpleDateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
            result.add(simpleDateFormat.format(tempStart.getTime()));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取两个日期之间相隔几个月
     *
     * @param startStr 开始日期
     * @param endStr   结束日期
     * @return 相隔月数(包括当月)
     */
    public static Integer getBetweenDate(String startStr, String endStr) {
        try {
            Integer start = Integer.valueOf(startStr.substring(5, 7));
            Integer end = Integer.valueOf(endStr.substring(5, 7));

            return end - start + 1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前日期是星期几
     *
     * @param dtStr
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(String dtStr) {
        try {
            String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = dateFormat.parse(dtStr);
            cal.setTime(dt);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0) {
                w = 0;
            }
            return weekDays[w];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前日期是星期几
     *
     * @param dtStr
     * @return 当前日期是星期几
     */
    public static String getLastMonth(String dtStr) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");

            Date start = simpleDateFormat.parse(dtStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);

            cal.add(Calendar.MONTH, -1);
            return simpleDateFormat.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getCurrentDayStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date getCurrentDayEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static Date getWeekStart() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    public static Date getWeekEnd() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    public static Date getMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date getYearStart() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }

    public static Date getYearEnd() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
        return cal.getTime();
    }

    /***
     *获取某天的0点
     * @Author : zgh
     * @Descripton :
     * @Date : 2020/1/13 10:07
     * @param date:
     * @return java.util.Date
     */
    public static Date getDateStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /***
     *获取某天的23点59分59秒
     * @Author : zgh
     * @Descripton :
     * @Date : 2020/1/13 10:07
     * @param date:
     * @return java.util.Date
     */
    public static Date getDateEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 取得时间相加后的结果
     *
     * @param date:原始日期
     * @param interval: 增量
     * @param timeUnit: 时间单位, 只能传calendar合法时间单位
     * @return : java.util.Date
     * @author : cgl
     * @version : 1.0
     * @since 2019/5/8 14:52
     **/
    public static Date dateAdd(Date date, int interval, int timeUnit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(timeUnit, interval);
        return calendar.getTime();
    }

}
