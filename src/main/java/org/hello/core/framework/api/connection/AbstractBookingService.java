package org.hello.core.framework.api.connection;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AbstractBookingService extends BaseService {

    private String host;
    private String protocol;
    private String username;
    private String password;
    private int port;
    protected static Logger log = LogManager.getLogger(AbstractBookingService.class);

    /**
     * Constructor
     * @param host
     * @param port
     * @param protocol
     */
    public AbstractBookingService(String host, int port, String protocol) {
        this.host = host;
        this.port = port;
        this.protocol = protocol;
        rootURL = this.protocol+"://"+this.host+":"+this.port;
    }

    /**
     * @return RequestSpecification
     */
    protected RequestSpecification getRequestSpec(){
        return RestAssured.given()
                .baseUri(rootURL)
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .accept("application/json");
    }

    protected Response getRequest(String url){
        return request(Method.GET, url);
    }

    /**
     * @param body Body
     * @param url URL
     * @return Response
     */
    protected Response postRequest(String url, Object body) {
        return request(Method.POST, body, url);
    }
}