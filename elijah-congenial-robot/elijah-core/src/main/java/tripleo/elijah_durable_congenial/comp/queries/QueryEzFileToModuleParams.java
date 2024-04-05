package tripleo.elijah_durable_congenial.comp.queries;

import java.io.InputStream;

public class QueryEzFileToModuleParams {

	public InputStream inputStream;
	public String      sourceFilename;

	public QueryEzFileToModuleParams(final String aSourceFilename, final InputStream aInputStream) {
		sourceFilename = aSourceFilename;
		inputStream    = aInputStream;
	}
}
