package org.hello.core.framework.api.connection;

import com.google.common.collect.ImmutableMap;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;

public abstract class BaseService {

    protected static final Logger log = LogManager.getLogger(BaseService.class);

    /**
     * Used to define the RequestSpecification common to all operations
     * defined in the given service. For example:
     * <pre><code>RestAssured.given().proxy(...)</code></pre>
     *
     * @return the RestAssured RequestSpecification with appropriate defaults
     */
    protected abstract RequestSpecification getRequestSpec();

    /**
     * Performs GET request of the URL.
     *
     * @return The response from the request
     */
    protected Response request(Method method, String url) {
        return request(method, ImmutableMap.of(), url);
    }

    /**
     * Performs GET request of the URL with parameters.
     *
     * @return The response from the request
     */
    @Deprecated
    protected Response request(Map<String, ?> params, String url) {
        return request(Method.GET, params, url);
    }

    /**
     * Performs specified HTTP verb request of the URL with parameters.
     *
     * @param method the HTTP method to request
     * @param params the request parameters
     * @param url    the URL to request
     * @return The response from the request
     */
    protected Response request(Method method, Map<String, ?> params, String url) {
        return getRequestSpec()
                .params(params)
                .when()
                .request(method, url)
                .then()
                .extract().response();
    }

    /**
     * Performs specified HTTP verb request of the URL with the given body.
     *
     * @param method the HTTP method to request
     * @param body   the body of the request
     * @param url    the URL to request
     * @return The response from the request
     */
    protected Response request(Method method, Object body, String url) {
        return getRequestSpec()
                .when()
                .body(body)
                .request(method, url)
                .then()
                .extract().response();
    }
}
