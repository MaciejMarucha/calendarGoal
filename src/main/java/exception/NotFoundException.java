package exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Nie można znaleźć ");
    }
}
