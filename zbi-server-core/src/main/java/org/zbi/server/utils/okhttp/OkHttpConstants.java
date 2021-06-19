package org.zbi.server.utils.okhttp;

/**
 * Created by ChenJiaHao on 2017/10/12.
 */
public class OkHttpConstants {

    public static final String MIME_APPLICATION_JSON = "application/json";
    public static final String MIME_APPLICATION_PROTOBUF = "application/x-protobuf";
    public static final String MIME_APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";


    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_ACCEPT = "Accept";

    public enum MimeType {
        X_PROTOBUF("application/x-protobuf"),
        JSON("application/json"),
        X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded");

        private String name;
        MimeType(String type) {
            this.name = type;
        }
        public String getName() {
            return name;
        }
    }
}
