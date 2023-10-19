package OMDB.OMDb_Liantis_Pinseel_Benjamin.Exceptions;

import java.util.List;

public class DataValidationException extends RuntimeException {
    private final List<String> violations;

    public DataValidationException(List<String> violations) {
        this.violations = violations;
    }

    public List<String> getViolations() {
        return violations;
    }
}