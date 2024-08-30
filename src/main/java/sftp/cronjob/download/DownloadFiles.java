package sftp.cronjob.download;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.sftp.session.SftpSession;

import java.io.IOException;

public interface DownloadFiles {
     void saveFileToDisk(String originalFileName, SftpSession session) throws IOException;
     void downloadMultipleFiles();


}
