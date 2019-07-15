package com.example.notset;

public class allreports {
    String reportID;
    String reportTitle;
    String reportDescription;

    public allreports(String reportID, String reportTitle, String reportDescription) {
        this.reportID = reportID;
        this.reportTitle = reportTitle;
        this.reportDescription = reportDescription;
    }
    public allreports(){

    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public String getReportID() {
        return reportID;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public String getReportDescription() {
        return reportDescription;
    }

}
