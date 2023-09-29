package juliomesquita.projectcore.exception;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

import java.net.URI;

public class PersistenceConversionProblem extends AbstractThrowableProblem {
    static final URI TYPE = URI.create("");
    public PersistenceConversionProblem() {
        super(
                TYPE,
                "Internal Server Error.",
                Status.INTERNAL_SERVER_ERROR,
                "Could not read data from database."
        );
    }
}
