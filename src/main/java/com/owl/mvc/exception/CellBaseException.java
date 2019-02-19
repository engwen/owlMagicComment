package com.owl.mvc.exception;

/**
 * 警告，正在使用未重写的方法
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/08/22.
 */
public class CellBaseException extends Exception{
    private static String message = "使用错误，您正在使用的方法在继承过程中并没有被重写；";

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     */
    public CellBaseException() {
        super(message);
    }
}
