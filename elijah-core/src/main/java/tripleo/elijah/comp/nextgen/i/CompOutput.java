package tripleo.elijah.comp.nextgen.i;

import tripleo.elijah.nextgen.outputstatement.EG_Statement;

import java.util.List;

public interface CompOutput {
	int countMarkers();

	CK_Marker getMarker(int index);

	// markers
	List<CK_Marker> listMarkers();

	// logs
	Cursor<CK_Log> perFile(CE_Path p);

	// outputTree
	void writeToPath(CE_Path p, EG_Statement stmt);
}
