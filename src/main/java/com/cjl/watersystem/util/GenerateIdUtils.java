package com.cjl.watersystem.util;

import java.util.Calendar;

/**
 * 定义生成id的规则
 */
public class GenerateIdUtils {

    private static int customerIndex = 0;
    private static int staffIndex = 0;
    private static int commodityFlag = 1;
    private static int orderFlag = 1;

    /**
     * 获取系统当前年份
     * @return 返回string
     */
    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.YEAR));
    }

    /**
     * 返回当前系统时间的时间戳
     * @return String类型
     */
    public static String getSysTimeInMillis() {
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.getTimeInMillis());
    }

    /**
     * 生成customer ID
     * @return 'C' + year + (10000 + customerIndex)
     */
    public static String generateCustID() {
        return "C" + getSysYear() + 10000 + customerIndex++;
    }

    /**
     * 生成customer service id
     * @return 'CS' + (1000 + staffIndex)
     */
    public static String generateTicketID() {
        return "T" + 10000 + staffIndex++;
    }

    /**
     * 生成派送员id
     * @return 'CD' + getSysTimeInMillis() + 0~9
     */
    public static String generateCourierID() {
        return "CD" + 10000 + commodityFlag++;
    }

    public static String generateOrderID() {
        return "O" + 10000 + orderFlag++;
    }

    public static String generateComplaintID() {
        return "CM" + getSysTimeInMillis();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(generateCourierID());
        }
    }
}
