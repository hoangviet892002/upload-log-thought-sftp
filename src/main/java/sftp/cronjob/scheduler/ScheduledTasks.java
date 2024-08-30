package sftp.cronjob.scheduler;

import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sftp.cronjob.config.SftpConfig;
import sftp.cronjob.download.DownloadFiles;
import sftp.cronjob.upload.uploadLogs.UploadLog;



@Component
public class ScheduledTasks {
    private static Logger logger = Logger.getLogger(SftpConfig.class.getName());
    private final DownloadFiles downloadFiles;
    private final UploadLog uploadLog;

    public ScheduledTasks(DownloadFiles downloadFiles, UploadLog uploadLog) {
        this.downloadFiles = downloadFiles;
        this.uploadLog = uploadLog;
    }

    // every 1 hour
    @Scheduled(cron = "0 0 * * * *")
    public void DownloadFile() {
        downloadFiles.downloadMultipleFiles();
    }

    // every 1 hour
    @Scheduled(cron = "0/2 * * * * *")
    public void uploadLog() {
        uploadLog.uploadLog();
    }
}
