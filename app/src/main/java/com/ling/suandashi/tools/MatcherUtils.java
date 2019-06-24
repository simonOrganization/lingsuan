package com.ling.suandashi.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zongfi
 * @datetime 2014-2-26 下午4:10:22
 * @email zzf_soft@163.com
 */
public class MatcherUtils {

    /**
     * 验证是否是手机号
     *
     * @param mobiles
     * @return
     * @author Zongfi
     * @datetime 2014-2-27 下午3:16:43
     * @email zzf_soft@163.com
     */
    public static boolean isPhoneNumber(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证手机号是否是纯数字
     * Created by ZHZEPHI at 2015年2月2日 下午6:39:44
     *
     * @param phone
     * @return
     */
    public static boolean isNumber(String phone) {
        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 判断是否是邮箱
     * Created by ZHZEPHI at 2015年1月13日 上午10:52:21
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$").matcher(email).matches();
    }

    /**
     * 判断密码是否合法
     * Created by ZHZEPHI at 2015年1月13日 上午10:56:07
     *
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        return Pattern.compile("\\w{6,32}").matcher(password).matches();
    }

    /**
     * 验证是否是空
     *
     * @param str
     * @return
     * @author Zongfi
     * @datetime 2014-2-27 下午3:17:00
     * @email zzf_soft@163.com
     */
    public static boolean isNull(String str) {
        return str == null || str.equals("") || str.equals("null");
    }

    /**
     * 判断是否是空数字
     *
     * @param str
     * @return
     */
    public static Boolean isEmptyNumber(String str) {
        return str == null || "".equals(str) || "null".equals(str) || "0".equals(str);
    }
}
