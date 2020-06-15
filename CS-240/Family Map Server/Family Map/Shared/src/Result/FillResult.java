package Result;

public class FillResult {
    private String message = "";
    private boolean success = false;
    public FillResult(){

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

}
