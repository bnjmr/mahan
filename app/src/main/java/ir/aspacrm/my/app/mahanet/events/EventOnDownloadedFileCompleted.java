package ir.aspacrm.my.app.mahanet.events;

/**
 * Created by Microsoft on 4/5/2016.
 */
public class EventOnDownloadedFileCompleted {
    int downloadId;
    public EventOnDownloadedFileCompleted(int downloadId) {
        this.downloadId = downloadId;
    }
    public int getDownloadId() {
        return downloadId;
    }
}
