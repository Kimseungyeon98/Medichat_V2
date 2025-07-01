package ksy.medichat.notification.domain;

public enum NotificationCategory {
    COMMON("공통"),
    HOSPITAL("병원"),
    PHARMACY("약국"),
    CONSULTING("상담"),
    COMMUNITY("커뮤니티");
    
    private final String label;
    
    NotificationCategory(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
