package tripleo.elijah.nextgen;

// FIXME 12/17 !! next two
//import tripleo.elijah.comp.nextgen.CP_Path;
//import tripleo.elijah.comp.nextgen.i.CE_Path;

import tripleo.elijah.nextgen.outputstatement.EG_Statement;

import java.nio.file.Path;

/**
 * See {@link tripleo.elijah.comp.nextgen.i.CompOutput#writeToPath(CE_Path, EG_Statement)}
 */
// TODO 09/04 Duplication madness
public interface ER_Node {
	Path getPath();

	EG_Statement getStatement();
}
