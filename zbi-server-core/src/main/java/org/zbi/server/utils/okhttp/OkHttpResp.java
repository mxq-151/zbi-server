package org.zbi.server.utils.okhttp;

public class OkHttpResp {

    /** 响应状态码 */
    int respStatusCode;

    /** 响应体 */
    String respBody;

    /** 响应信息 */
    String respMsg;

    public OkHttpResp(int respStatusCode, String respBody, String respMsg) {

        this.respStatusCode = respStatusCode;
        this.respBody = respBody;
        this.respMsg = respMsg;
    }

    public int getRespStatusCode() {
        return respStatusCode;
    }
    public void setRespStatusCode(int respStatusCode) {
        this.respStatusCode = respStatusCode;
    }
    public String getRespBody() {
        return respBody;
    }
    public void setRespBody(String respBody) {
        this.respBody = respBody;
    }
    public String getRespMsg() {
        return respMsg;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
    @Override public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{respStatusCode:").append(respStatusCode)
                .append(",respBody:").append(respBody)
                .append(",respMsg:").append(respMsg)
                .append("}");

        return sb.toString();
    }
}
