package me.zhengjie.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListenerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author bron
 * todo socket log 配置
 */
@Slf4j
@Component
public class SocketIoExceptionListener extends ExceptionListenerAdapter {

//    private static final String SOCKET_IO_LOG_LEVEL = "debug";

    @Override
    public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
        logSocketIoException(e);
        client.disconnect();
    }

    @Override
    public void onDisconnectException(Exception e, SocketIOClient client) {
        logSocketIoException(e);
        client.disconnect();
    }

    @Override
    public void onConnectException(Exception e, SocketIOClient client) {
        logSocketIoException(e);
        client.disconnect();
    }

    @Override
    public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        logSocketIoException(e);
        ctx.close();
        return true;
    }

    private static void logSocketIoException(Throwable e) {
//        SystemConfig systemConfig = (SystemConfig) CacheHelper.getSystemCacheBean().getCacheObject("systemConfig", UKDataContext.SYSTEM_ORGI);
//        if (systemConfig != null && StringUtils.equalsIgnoreCase(SOCKET_IO_LOG_LEVEL, systemConfig.getLoglevel())) {
//            log.debug("error = {}", ExceptionUtils.getStackTrace(e));
//        }
//        if (e instanceof IOException) {
//            log.info(e.getMessage());
//        } else {
        log.error("error = {}", ExceptionUtils.getStackTrace(e));
//        }
    }
}
