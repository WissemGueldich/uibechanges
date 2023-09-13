package com.tn.uib.uibechanges.utils;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SSHCommandSanitizer {

    private static final Pattern VALID_PATH_PATTERN = Pattern.compile("^(/[-a-zA-Z0-9_/]+|[a-zA-Z](?::[-a-zA-Z0-9_/]+)?)(?:\\.[a-zA-Z0-9_/]+)*$");
    private static final Pattern VALID_SSH_FILTER_PATTERN = Pattern.compile("^[a-zA-Z0-9_.*-]+$");

    public static boolean sanitizePath(String input) {
        Matcher matcher = VALID_PATH_PATTERN.matcher(input);
        return matcher.matches();
    }

    public static boolean sanitizeFilter(String input) {
        Matcher matcher = VALID_SSH_FILTER_PATTERN.matcher(input);
        return matcher.matches();
    }
}