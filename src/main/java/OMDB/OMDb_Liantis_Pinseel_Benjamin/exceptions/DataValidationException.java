package OMDB.OMDb_Liantis_Pinseel_Benjamin.exceptions;

import java.util.Set;

public class DataValidationException extends RuntimeException {
    private final Set<String> violations;

    public DataValidationException(Set<String> violations) {
        this.violations = violations;
    }

    public Set<String> getViolations() {
        return violations;
    }
}