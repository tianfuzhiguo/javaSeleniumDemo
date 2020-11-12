package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类 可获取当前年份，去年，当前月份，上个月，下个月
 *
 * @author dujianxiao
 *
 */
public class TimeUtil {
	static String lastDay = FileUtil.getValue("sql/global.properties", "lastDay");// 昨天
	static String last7Day = FileUtil.getValue("sql/global.properties", "last7Day");// 前7天
	static String last30Day = FileUtil.getValue("sql/global.properties", "last30Day");// 前30天
	static String last365Day = FileUtil.getValue("sql/global.properties", "last365Day");// 前365天
	static String thisMonthFirstDay = FileUtil.getValue("sql/global.properties", "thisMonthFirstDay");// 本月第一天
	static String currentYM = FileUtil.getValue("sql/global.properties", "currentYM");// 当前年月
	static String lastYCurrentM = FileUtil.getValue("sql/global.properties", "lastYCurrentM");// 当前年月
	static String thisYearSql = FileUtil.getValue("sql/global.properties", "thisYear");// 今年
	static String lastYearSql = FileUtil.getValue("sql/global.properties", "lastYear");// 去年
	static String thisMonthSql = FileUtil.getValue("sql/global.properties", "thisMonth");// 当前月
	static String lastMonthSql = FileUtil.getValue("sql/global.properties", "lastMonth");// 上个月
	static String lastYM = FileUtil.getValue("sql/global.properties", "lastYM");// 上个月
	static String nextMonthSql = FileUtil.getValue("sql/global.properties", "nextMonth");// 下个月

	/**
	 * 获取当前日期
	 * @param date
	 * @return
	 */
	public static String getDate(){
		Date dt=new Date();
		SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
		return String.valueOf(matter1.format(dt));
	}

	/**
	 * 获取当前时间
	 * @param date
	 * @return
	 */
	public static String getTime(){
		Date dt=new Date();
		SimpleDateFormat matter1=new SimpleDateFormat("HH:mm:ss");
		return String.valueOf(matter1.format(dt));
	}

	/**
	 * 获取昨天  YYYY-MM-DD
	 *
	 * @return
	 */
	public static String getLastDay() {
		return DBUtil.getStrFiled(lastDay);
	}

	/**
	 * 前7天  YYYY-MM-DD
	 *
	 * @return
	 */
	public static String getLast7Day() {
		return DBUtil.getStrFiled(last7Day);
	}

	/**
	 * 前30天  YYYY-MM-DD
	 *
	 * @return
	 */
	public static String getLast30Day() {
		return DBUtil.getStrFiled(last30Day);
	}

	/**
	 * 本月第1天  YYYY-MM-DD
	 *
	 * @return
	 */
	public static String getthisMonthFirstDay() {
		return DBUtil.getStrFiled(thisMonthFirstDay);
	}

	/**
	 * 前365天  YYYY-MM-DD
	 *
	 * @return
	 */
	public static String getLast365Day() {
		return DBUtil.getStrFiled(last365Day);
	}
	/**
	 * 获取当前年月 YYYY-MM
	 *
	 * @return
	 */
	public static String getCurrentYM() {
		return DBUtil.getStrFiled(currentYM);
	}

	/**
	 * 获取去年今月 YYYY-MM
	 *
	 * @return
	 */
	public static String getLastYCurrentM() {
		return DBUtil.getStrFiled(lastYCurrentM);
	}

	/**
	 * 获取当前年份 YYYY
	 *
	 * @return
	 */
	public static String getThisYear() {
		return DBUtil.getStrFiled(thisYearSql);
	}

	/**
	 * 获取去年年份 YYYY
	 *
	 * @return
	 */
	public static String getLastYear() {
		return DBUtil.getStrFiled(lastYearSql);
	}

	/**
	 * 获取当前月份 MM
	 *
	 * @return
	 */
	public static String getThisMonth() {
		return DBUtil.getStrFiled(thisMonthSql);
	}

	/**
	 * 获取上个月 MM
	 *
	 * @return
	 */
	public static String getLastMonth() {
		return DBUtil.getStrFiled(lastMonthSql);
	}

	/**
	 * 获取上个月 YYYY-MM
	 *
	 * @return
	 */
	public static String getLastYM() {
		return DBUtil.getStrFiled(lastYM);
	}

	/**
	 * 获取下个月 MM
	 *
	 * @return
	 */
	public static String getNextMonth() {
		return DBUtil.getStrFiled(nextMonthSql);
	}

	/**
	 * 比较时间大小 time1大于等于time2则返回true，否则返回false
	 *
	 * @param time1
	 * @param time2
	 * @return true / false
	 */
	public static boolean compareTime(String time1, String time2) {
		Date date1 = null;
		Date date2 = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date1 = sdf.parse(time1);
			date2 = sdf.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date1.getTime() >= date2.getTime();
	}
}