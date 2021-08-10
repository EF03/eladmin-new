package me.zhengjie.config;

import io.undertow.UndertowOptions;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * @author bron
 */
@Component
public class UndertowConfig implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

    @Override
    public void customize(UndertowServletWebServerFactory factory) {
        factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
            deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
        });
        //url配置
        factory.addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ALLOW_UNESCAPED_CHARACTERS_IN_URL, Boolean.TRUE));
        factory.addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ALLOW_EQUALS_IN_COOKIE_VALUE, Boolean.TRUE));
        factory.addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ALLOW_ENCODED_SLASH, Boolean.TRUE));
    }

//    @Bean
//    public ServletWebServerFactory webServerFactory() {
//        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
//        //url配置
//        factory.addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ALLOW_UNESCAPED_CHARACTERS_IN_URL, Boolean.TRUE));
//        factory.addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ALLOW_EQUALS_IN_COOKIE_VALUE, Boolean.TRUE));
//        factory.addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ALLOW_ENCODED_SLASH, Boolean.TRUE));
//
//        factory.addDeploymentInfoCustomizers(deploymentInfo -> {
//            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
//            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
//            deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
//        });
//
//        return factory;
//    }

//    @Bean
//    public ServletWebServerFactory webServerFactory() {
//        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
//        fa.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
//        return fa;
//    }
}
