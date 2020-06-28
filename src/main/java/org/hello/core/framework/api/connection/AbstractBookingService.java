package org.hello.core.framework.api.connection;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hello.core.framework.utils.Environment;
import org.hello.core.framework.utils.PropertyUtils;

public class AbstractBookingService extends BaseService {
    String protocol = PropertyUtils.get(Environment.API_PROTOCOL);
    String host = PropertyUtils.get(Environment.API_HOST);
    String port = PropertyUtils.get(Environment.API_PORT);

    /**
     * @return RequestSpecification
     */
    protected RequestSpecification getRequestSpec(){

        String str = String.format("%s"+"://"+"%s"+":"+"%s",protocol, host, port);

        System.out.println("URL : "+ str);
        return RestAssured.given()
                .baseUri(str)
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