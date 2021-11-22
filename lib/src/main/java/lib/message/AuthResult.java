package lib.message;

public class AuthResult extends Message {
    private boolean success;
    public AuthResult(String content, boolean success) {
        super(content);
        this.success=success;
    }
public boolean isSuccessful(){
        return this.success;
}

}
