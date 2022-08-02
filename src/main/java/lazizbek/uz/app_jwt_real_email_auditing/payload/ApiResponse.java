package lazizbek.uz.app_jwt_real_email_auditing.payload;

public class ApiResponse {
    private String message;
    private Boolean success;
    private Object object;

    public ApiResponse() {
    }

    public ApiResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(String message, Boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
