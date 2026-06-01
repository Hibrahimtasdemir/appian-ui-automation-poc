package testdata;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LeaveRequestTestData {

    private final String employeeName;
    private final String reason;
    private final String startDate;
    private final String endDate;

    private LeaveRequestTestData(String employeeName, String reason, String startDate, String endDate) {
        this.employeeName = employeeName;
        this.reason = reason;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static LeaveRequestTestData validLeaveRequest() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        return new LeaveRequestTestData(
                "Ibrahim Auto Test " + timestamp,
                "Automation test request",
                "06/15/2026",
                "06/19/2026"
        );
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getReason() {
        return reason;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}