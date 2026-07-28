import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Report implements Printable, Serializable {

    private static final long serialVersionUID = 1L;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String reportID;
    private final String reportType;
    private final Date generatedOn;
    private final String data;

    public Report(String reportID, String reportType, String data) {
        this.reportID = reportID;
        this.reportType = reportType;
        this.data = data;
        this.generatedOn = new Date();
    }

    public String getReportText() {
        return "===== REPORT =====\n"
                + "Report ID   : " + reportID + "\n"
                + "Report Type : " + reportType + "\n"
                + "Generated On: " + FORMAT.format(generatedOn) + "\n\n"
                + data;
    }

    @Override
    public void print() {
        System.out.println(getReportText());
    }

    public String getReportID() {
        return reportID;
    }
}
