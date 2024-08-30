package sftp.cronjob.download.impl;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.sshd.sftp.client.SftpClient;
import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;
import sftp.cronjob.config.SftpConfig;
import sftp.cronjob.download.DownloadFiles;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;



@Service
public class DownloadFileImpl implements DownloadFiles {

    private static Logger logger = Logger.getLogger(SftpConfig.class.getName());

    @Override
    public void saveFileToDisk(String originalFileName, SftpSession session) throws IOException {
        // Save file
        String fileLocation = "/C:/Users/Administrator/Documents/" + originalFileName;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        session.read(fileLocation, outputStream);
        String data = outputStream.toString();
        File download = new File(String.format("src/main/resources/downloaded/%s", originalFileName));
        FileUtils.write(download, data, StandardCharsets.UTF_8);
    }

    @Override
    public void downloadMultipleFiles() {
        // Download multiple files from SFTP server
        SftpSession session = new SftpConfig().sftpSessionFactory().getSession();
        logger.info("Downloading files from SFTP server");
        try {

            SftpClient.DirEntry[] list = session.list("/C:/Users/Administrator/Documents");
            // filter file list *.txt
            SftpSimplePatternFileListFilter filter = new SftpSimplePatternFileListFilter("*.txt");
            list = filter.filterFiles(list).toArray(new SftpClient.DirEntry[0]);

            logger.info("Files found: " + list.length);
            for (SftpClient.DirEntry file : list) {
                logger.info("Downloading file: " + file.getFilename());
                saveFileToDisk(file.getFilename(), session);
                logger.info("File downloaded: " + file.getFilename());
            }
        } catch (IOException e) {
            logger.error("Error downloading file: " + e.getMessage());
        }
    }
}
