package ksy.medichat.notification.domain;

public enum NotificationCategory {
    COMMON("공통"),
    HOSPITAL("병원"),
    PHARMACY("약국");
    
    private final String label;
    
    NotificationCategory(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
