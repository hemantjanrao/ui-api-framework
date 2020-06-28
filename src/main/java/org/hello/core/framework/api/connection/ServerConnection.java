package org.hello.core.framework.api.connection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class ServerConnection {

    protected Client client;
    protected String rootURL;
    protected Map<String, String> customHeader = new HashMap<String, String>();
    private String authenticationToken;
    private static Logger log = LogManager.getLogger(ServerConnection.class);

    /**
     * Method to create Web Target
     * @param path
     * @return
     */
    private WebTarget createWebTarget(String path) {
        UriBuilder uriBuilder = UriBuilder.fromUri(this.rootURL);
        if (path.contains("?")) {
            String basePath = path.split("\\?")[0];
            String query = path.split("\\?")[1];
            String[] params = query.split("&");
            uriBuilder.path(basePath);
            String[] var6 = params;
            int var7 = params.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                String param = var6[var8];
                String key = param.substring(0, param.indexOf("="));
                String value = param.substring(param.indexOf("=") + 1);
                uriBuilder.queryParam(key, new Object[]{value.replace(" ", "%20")});
            }
        } else {
            uriBuilder.path(path);
        }

        URI uri = uriBuilder.build(new Object[0]);
        WebTarget target = this.client.target(uri);
        return target;
    }

    private Builder createRequest(String path, MediaType Type) {
        log.info(String.format("Making request with url:[%s]",path));
        Builder builder = this.createWebTarget(path).request(new MediaType[]{Type});
        if (!this.customHeader.isEmpty()) {
            builder = this.addCustomHeader(builder);
        }
        if(this.authenticationToken != null && !this.authenticationToken.isEmpty()) {
            builder = builder.header("authorization", "bearer "+this.authenticationToken);
        }

        return builder;
    }

    private Builder createRequest(String path) {
        return this.createRequest(path, MediaType.APPLICATION_JSON_TYPE);
    }

    protected <T> T getRequest(String path, Class<T> responseType) {
        return this.createRequest(path).get(responseType);
    }

    public Response getRequest(String path) {
        return this.getRequest(path, Response.class);
    }

    protected Response postRequest(String path, String jsonIP) {
        return this.createRequest(path).post(Entity.entity(jsonIP, "application/json"));
    }


    private Builder addCustomHeader(Builder builder) {
        if(!this.customHeader.isEmpty()) {
            for(Map.Entry<String, String> entry : customHeader.entrySet()) {
                builder = builder.header(entry.getKey(), entry.getValue());
            }
        }
        return builder;
    }


    protected void setAuthenticationToken(String token) {
        this.authenticationToken = token;
    }

    public void setRootUrl(String url) {
        this.rootURL = url;
    }
}
