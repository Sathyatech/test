package com.util;



import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceWriter { 
    private static org.apache.log4j.Logger mLogger  =       org.apache.log4j.Logger.getLogger(StackTraceWriter.class.getPackage().getName()); 


    public static String getStackTrace(Throwable t)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }


    public static void printStackTrace(Throwable t) {
	mLogger.error(getStackTrace(t));
    }
}
