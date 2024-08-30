package sftp.cronjob.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.messaging.MessageHandler;
import java.io.File;
@Configuration
@Slf4j
@EnableIntegration

public class SftpConfig {

    private static Logger logger = Logger.getLogger(SftpConfig.class.getName());
    @Bean
    public DefaultSftpSessionFactory sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost("103.214.8.153");
        factory.setPort(22);
        factory.setUser("administrator");
        factory.setPassword("%E-NE1e6WS");
        factory.setAllowUnknownKeys(true);
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "toSftpChannel")
    public MessageHandler handler() {
        SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
        handler.setRemoteDirectoryExpression(new LiteralExpression("/C:/Users/Administrator/Documents"));
        handler.setFileNameGenerator(message -> {
            if (message.getPayload() instanceof File) {
                return ((File) message.getPayload()).getName();
            } else {
                throw new IllegalArgumentException("File expected as payload.");
            }
        });
        logger.info("File uploaded");
        return handler;
    }

}
