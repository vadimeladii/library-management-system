package library.md.exception;

public class ReferencedEntityException extends RuntimeException {

    public ReferencedEntityException(String message) {
        super(message);
    }

    public ReferencedEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
