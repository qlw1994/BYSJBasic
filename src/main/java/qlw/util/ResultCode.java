package qlw.util;

/**
 * Created by qlw on 2017/1/20 0020.
 *
 * @Date 2017/1/20 0020 14:18
 */
public class ResultCode {
    public static final int SUCCESS = 1;
    public static final int ERROR = -100;
    public static final int METHOD_MUST_POST = 0;
    public static final int PARAMETERS_MISSING = -1;
    public static final int PARAMETERS_EMPTY = -2;
    public static final int PARAMETERS_NOTLEGAL = -3;
    public static final int SIGN_ERROR = -4;
    public static final int METHOD_FORBID = -5;
    public static final int AUTHORITY_FORBID = -6;
    public static final int BUS_NOT_SUPPORT = -7;
    public static final int PARAMETERS_PUSH_EMPTY = -10;
    public static final int ACCOUNT_NOT_EXIST = -11;
    public static final int REQCODE_DEVICE_DISAFFINITY = -500;
    public static final int REQCODE_PASSWORD_CHANGED = -501;
    public static final int REQCODE_TIME_EXPIRED = -202;

    public ResultCode() {
    }
}
