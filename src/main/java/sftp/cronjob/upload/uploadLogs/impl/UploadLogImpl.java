package sftp.cronjob.upload.uploadLogs.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import sftp.cronjob.config.SftpConfig;
import sftp.cronjob.upload.UploadMessageGateway;
import sftp.cronjob.upload.uploadLogs.UploadLog;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadLogImpl implements UploadLog {

    private final UploadMessageGateway uploadGateway;

    private static Logger logger = Logger.getLogger(SftpConfig.class.getName());
    @Override
    public void uploadLog() {
        logger.info("Uploading logs");
        // get all files from the directory logs
        File folder = new File("src/main/resources/logs");
        File[] listOfFiles = folder.listFiles();
        logger.info("Logs uploaded");
        // upload all files to the sftp server
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                uploadGateway.upload(file);
            }
        }
        logger.info("Logs uploaded");
    }
}
