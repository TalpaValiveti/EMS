package model;

import java.sql.Timestamp;

public class AuditLog {
    private int auditId;
    private String action;
    private String username;
    private Timestamp actionTime;

    public AuditLog() {}

    public AuditLog(int auditId, String action, String username, Timestamp actionTime) {
        this.auditId = auditId;
        this.action = action;
        this.username = username;
        this.actionTime = actionTime;
    }

    public int getAuditId() { return auditId; }
    public void setAuditId(int auditId) { this.auditId = auditId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Timestamp getActionTime() { return actionTime; }
    public void setActionTime(Timestamp actionTime) { this.actionTime = actionTime; }
}
