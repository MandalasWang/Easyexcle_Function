package ink.boyuan.util.easyexcel.exception;

import ink.boyuan.util.easyexcel.response.RetResult;


/**
 * @author wyy
 * @version 1.0
 * @date 2019/9/26 9:39
 * @description
 **/
public class MyException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 提供无参数的构造方法
     */
    public MyException() {
    }

    /**
     * 提供一个有参数的构造方法，可自动生成
     */
    public <T> MyException(RetResult<T> retResult) {
        super(retResult.getMsg());
    }

}
