package sftp.cronjob.upload;

import lombok.RequiredArgsConstructor;
import org.aspectj.util.FileUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final UploadMessageGateway uploadGateway;

    @Override
    public void run(String... args) throws Exception {
        File file = new File("src/main/resources/data.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
//        FileUtil.writeAsString(file, "Hello, World!");
        uploadGateway.upload(file);

    }
}
