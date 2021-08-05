package com.abelium.inatrace.components.exceptionhandling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class ExceptionFormatter
{
    public static List<Throwable> getCauses(Throwable exc) {
        Set<Throwable> seen = Collections.newSetFromMap(new IdentityHashMap<>(4));
        ArrayList<Throwable> result = new ArrayList<Throwable>(4);
        while (exc != null && !seen.contains(exc) && result.size() < 100) {
            seen.add(exc);
            result.add(exc);
            exc = exc.getCause();
        }
        return result;
    }
    
    public static String formatExceptionWithCause(Throwable exc) {
        StringBuilder sb = new StringBuilder();
        for (Throwable e : getCauses(exc)) {
            if (e != exc) {
                sb.append("\n  caused by ");
            }
            sb.append(e.toString());
            filterStackTrace(e, sb);
        }
        return sb.toString();
    }
    
    public static String filterStackTrace(Throwable exc) {
        StringBuilder sb = new StringBuilder();
        filterStackTrace(exc, sb);
        return sb.toString();
    }

    private static void filterStackTrace(Throwable exc, StringBuilder sb) {
        StackTraceElement[] trace = exc.getStackTrace();
        boolean start = true;
        for (int i = 0; i < trace.length; i++) {
            // skip over not interesting lines
            int skippedSince = i;
            while (i < trace.length) {
                StackTraceElement elt = trace[i];
                if (i == 0) {
                    break;
                }
                if (shouldAdd(elt)) {
                    start = false;
                    break;
                }
                if (start && i + 1 < trace.length && shouldAdd(trace[i + 1])) {
                    break;
                }
                i++;
            }
            // print out
            if (i > skippedSince) {
                sb.append("\n    ... (skipped ").append(i - skippedSince).append(" lines)");
            }
            if (i < trace.length) {
                sb.append("\n    ").append(trace[i]);
            }
        }
    }
    
    private static boolean shouldAdd(StackTraceElement elt) {
        String classname = elt.getClassName();
        String file = elt.getFileName();
        return classname != null && classname.contains("com.abelium.") && !StringUtils.equals(file, "<generated>");
    }
}
