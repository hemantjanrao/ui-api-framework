package org.hello.core.framework.api.connection;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public abstract class AbstractService extends BaseService {

    protected static Logger log = LogManager.getLogger(AbstractService.class);

    /**
     * Constructor
     * @param host Host Name
     * @param port Port Number
     * @param protocol Protocol
     */
    public AbstractService(String host, int port, String protocol) {
        rootURL = protocol +"://"+ host +":"+ port;
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

    protected abstract void login(String username, String password);
}