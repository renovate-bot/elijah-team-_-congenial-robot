package tripleo.elijah.comp.functionality.f291;

import tripleo.elijah.comp.nextgen.CP_Path;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;

@SuppressWarnings({"UtilityClassCanBeEnum", "ClassWithOnlyPrivateConstructors", "NonFinalUtilityClass", "DuplicateBranchesInSwitch"})
public class U {
	private U() {
	}

	public static CP_Path getPathForOutputFile(final EOT_OutputFile outputFile,
											   final CP_Path outputRoot,
											   final String childPath) {
		CP_Path pp;
		switch (outputFile.getType()) {
		case SOURCES -> pp = outputRoot.child("code2").child(childPath);
		case LOGS -> pp = outputRoot.child("logs").child(childPath);
		case INPUTS, BUFFERS -> pp = outputRoot.child(childPath);
		case DUMP -> pp = outputRoot.child("dump").child(childPath);
		case BUILD -> pp = outputRoot.child(childPath);
		case SWW -> pp = outputRoot.child("sww").child(childPath);
		default -> throw new IllegalStateException("Unexpected value: " + outputFile.getType());
		}
		return pp;
	}
}
