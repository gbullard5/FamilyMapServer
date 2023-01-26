package Response;

/**Parent Response class*/
public class ParentResponse {
  private String message;
  private boolean success;

  public ParentResponse(boolean success, String message) {
    this.message=message;
    this.success=success;
  }

  public ParentResponse(String message, boolean success) {
    this.success = success;
    this.message = message;
  }



  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success=success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
