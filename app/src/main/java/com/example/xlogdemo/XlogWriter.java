package com.example.xlogdemo;

import com.tencent.mars.xlog.Xlog;

public class XlogWriter {

    static {
        System.loadLibrary("c++_shared");
        System.loadLibrary("marsxlog");
    }

    public static String sPaths;
    public static String sNamePrefix;
    public static int sMode;
    public static int sFileSize;
    public static int sFileLife;

    private Xlog xlog;
    private final long xlogPtr;

    public XlogWriter(Xlog xlog, long xlogPtr) {
        this.xlog = xlog;
        this.xlogPtr = xlogPtr;
    }

    /**
     * ZLogWriter创建
     * @param paths log路径，一般SD下file/Logs
     * @param namePrefix log文件名头，如设置Log最后文件名:Log_日期_index.xlog
     * @param mode  LOG_MODE_ASYNC LOG_MODE_SYNC 异步和同步，同步可支持写入时文件分片，但异步不支持
     * @param fileSizeByte  文件最大限制，单位B，仅在同步模式支持
     * @param fileLifeSeconds   文件时间限制，单位秒
     * @return  ZLogWriter
     */
    public static XlogWriter createWriter(String paths, String namePrefix, int mode, int fileSizeByte, int fileLifeSeconds) {
        sPaths = paths;
        sNamePrefix = namePrefix;
        sMode = mode;
        sFileSize = fileSizeByte;
        sFileLife = fileLifeSeconds;
        Xlog xlog = new Xlog();
        long ptr = 0;
        xlog.setConsoleLogOpen(ptr, false);
        xlog.setMaxFileSize(ptr, sFileSize);
        xlog.setMaxAliveTime(ptr, sFileLife);
        xlog.setConsoleLogOpen(ptr, false);
        xlog.appenderOpen(
                Xlog.LEVEL_DEBUG,
                sMode,
                "",
                sPaths,
                sNamePrefix,
                7);

        return new XlogWriter(xlog, ptr);
    }

    /**
     * 无格式msg文件输入
     * @param msg 日志
     */
    public void print(String msg) {
        xlog.logD(xlogPtr, null, null, null, 0, 0, 0, 0, msg);
    }
}
