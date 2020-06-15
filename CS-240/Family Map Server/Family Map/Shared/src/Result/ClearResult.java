package Result;

public class ClearResult {
    private String message;
    private boolean success = false;
    public ClearResult(){

    }
    public void setMessage(String message){
        this.message = message;
    }
    public void setSuccess(boolean success){
        this.success = success;
    }
    public String getMessage(){
        return message;
    }
    public boolean getSuccess() {
        return success;
    }
}
