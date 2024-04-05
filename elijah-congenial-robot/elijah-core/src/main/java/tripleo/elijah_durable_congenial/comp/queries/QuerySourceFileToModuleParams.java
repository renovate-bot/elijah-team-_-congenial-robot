package tripleo.elijah_durable_congenial.comp.queries;

import java.io.InputStream;

public record QuerySourceFileToModuleParams(boolean do_out,
											InputStream inputStream,
											String sourceFilename
) {
}
