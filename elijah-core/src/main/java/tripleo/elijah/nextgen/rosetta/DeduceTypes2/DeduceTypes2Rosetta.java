package tripleo.elijah.nextgen.rosetta.DeduceTypes2;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.stages.logging.ElLog;

public class DeduceTypes2Rosetta {
	private final DeduceTypes2Request request;

	public DeduceTypes2Rosetta(final DeduceTypes2Request aRequest) {
		request = aRequest;
	}

	public ElLog createAndAddLog_DeduceTypes2() {
		ElLog log = createLog_DeduceTypes2();

		getDeducePhase().addLog(log);

		return log;
	}

	public ElLog createLog_DeduceTypes2() {
		return new ElLog(getModule().getFileName(), getVerbosity(), DeduceTypes2.PHASE);
	}

	public DeducePhase getDeducePhase() {
		return request.getDeducePhase();
	}

	public ErrSink getErrSink() {
		return request.getModule().getCompilation().getErrSink();
	}

	public OS_Module getModule() {
		return request.getModule();
	}

	private ElLog.@NotNull Verbosity getVerbosity() {
		return request.getVerbosity();
	}
}
