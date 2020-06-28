package org.hello.core.framework.api.connection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.client.ClientBuilder;

public abstract class AbstractServer extends ServerConnection {

    private String host;
    private String protocol;
    private String username;
    private String password;
    private int port;
    protected static Logger log = LogManager.getLogger(AbstractServer.class);

    /**
     * Constructor
     * @param host
     * @param port
     * @param protocol
     */
    public AbstractServer(String host, int port, String protocol) {
        this.host = host;
        this.port = port;
        this.protocol = protocol;
        rootURL = this.protocol+"://"+this.host+":"+this.port;

        client = ClientBuilder.newBuilder().register(JacksonFeature.class).register(MultiPartFeature.class).build();
    }

    protected abstract void login(String username, String password);
}
