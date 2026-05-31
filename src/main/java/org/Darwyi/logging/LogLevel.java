package org.Darwyi.logging;

public enum LogLevel {
    CRITICAL("[CRITICAL]", 0, "Критичний"),
    ERROR("[ERROR]", 1, "Помилковий"),
    WARNING("[WARNING]", 2, "Потребує уваги"),
    INFO("[INFO]", 3, "Інформаційний"),
    DEBUG("[DEBUG]", 4, "Відладочний");

    private final String tag;
    private final int severity;
    private final String displayName;

    LogLevel(String tag, int severity, String displayName) {
        this.tag = tag;
        this.severity = severity;
        this.displayName = displayName;
    }

    public String getTag()         { return tag; }
    public int    getSeverity()    { return severity; }
    public String getDisplayName() { return displayName; }

    public boolean isAtLeastAsSevereAs(LogLevel other) {
        return this.severity <= other.severity;
    }

    public static LogLevel fromLine(String line) {
        for (LogLevel l : values()) {
            if (line.contains(l.tag)) return l;
        }
        return null;
    }
}
