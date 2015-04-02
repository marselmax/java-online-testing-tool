package app.model.dto;

/**
 * @author marsel.maximov
 */
public class CompileResult {

    private Boolean result;

    private String errorMessage;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
