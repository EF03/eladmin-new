package me.zhengjie.socketio;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.constants.ElAdminConstant;
import me.zhengjie.utils.EncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author bron
 */
@Slf4j
@org.springframework.context.annotation.Configuration
public class SocketServerConfig {

    @Value("${htech.im.server.host}")
    private String host;

    @Value("${htech.im.server.origin}")
    private String origin;

    @Value("${htech.im.server.ping}")
    private Integer ping;

    @Value("${htech.im.server.pingtimeout}")
    private Integer pingtimeout;

    @Value("${htech.im.server.port}")
    private Integer port;

    @Value("${htech.im.port}")
    private Integer imPort;

    @Value("${web.upload-path}")
    private String path;

    @Value("${htech.im.server.threads}")
    private String threads;

//    @Value("${htech.im.server.isLinux}")
//    private boolean isLinux;

    @Value("${htech.im.server.boss}")
    private String boss;

    private static SocketIOServer server;

    @Bean(name = "webimport")
    public Integer getWebIMPort() {
//        UKDataContext.setWebIMPort(imPort);
        return port;
    }

    @Bean
    public SocketIOServer socketIOServer() throws IOException {
        boolean isLinux = false;
        Configuration config = new Configuration();

        config.setPort(port);
        config.setTransports(Transport.POLLING, Transport.WEBSOCKET);
        // linux 提高效能
        String os = System.getProperty("os.name");
        if (!os.toLowerCase().startsWith(ElAdminConstant.MAC) && !os.toLowerCase().startsWith(ElAdminConstant.WIN)) {
            config.setUseLinuxNativeEpoll(true);
            isLinux = true;
        }

        config.setExceptionListener(new SocketIoExceptionListener());

        File sslFile = new File(path, "ssl/https.properties");
        if (sslFile.exists()) {
            Properties sslProperties = new Properties();
            FileInputStream in = new FileInputStream(sslFile);
            sslProperties.load(in);
            in.close();
            if (!StringUtils.isBlank(sslProperties.getProperty("key-store")) && !StringUtils.isBlank(sslProperties.getProperty("key-store-password")) && new File(path, "ssl/" + sslProperties.getProperty("key-store")).exists()) {
                config.setKeyStorePassword(EncryptUtil.decryption(sslProperties.getProperty("key-store-password")));
                InputStream stream = new FileInputStream(new File(path, "ssl/" + sslProperties.getProperty("key-store")));
                config.setKeyStore(stream);
            }
        }

        int bossThreads = (Runtime.getRuntime().availableProcessors() / 5) + 1;
        int workThreads = Runtime.getRuntime().availableProcessors() * 2;


        config.setBossThreads(bossThreads);
        config.setWorkerThreads(workThreads);


//        config.setStoreFactory(new HazelcastStoreFactory());

        config.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData data) {
                return true;
            }
        });
        config.getSocketConfig().setReuseAddress(true);
        config.getSocketConfig().setTcpKeepAlive(true);

        config.setPingInterval(ping != null ? ping : 10000);
        config.setPingTimeout(pingtimeout != null ? pingtimeout : 60000);

        log.info("socket io 启动参数 Port = {},imPort ={},UseLinuxNativeEpoll = {},bossThreads = {},workThreads = {},ping = {},pingtimeout = {}",
                port, imPort, isLinux, bossThreads, workThreads, ping, pingtimeout);
        server = new SocketIOServer(config);
        return server;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }

//    @PreDestroy
//    public void destroy() {
//        server.stop();
//    }
}  