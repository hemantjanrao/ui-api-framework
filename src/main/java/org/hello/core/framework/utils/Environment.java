package org.hello.core.framework.utils;

public enum Environment {
    WEB_URL("web.url"),
    WEB_BROWSER("web.browser"),
    WEB_IS_GRID_ENABLED("web.isGridEnabled"),
    WEB_SELENIUM_GRID("web.seleniumGrid"),
    WEB_DEFAULT_TIMEOUT("web.defaultTimeout"),
    WEB_DATA_DIR("web.dataDir"),
    API_HOST("api.host"),
    API_PORT("api.port"),
    API_PROTOCOL("api.protocol"),
    ;

    private String key;
    Environment(String key) {
        this.key=key;
    }

    public String getKey(){
        return this.key;
    }
}