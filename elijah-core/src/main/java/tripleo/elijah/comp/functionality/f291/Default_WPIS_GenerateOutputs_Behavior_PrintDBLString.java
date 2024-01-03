package tripleo.elijah.comp.functionality.f291;

class Default_WPIS_GenerateOutputs_Behavior_PrintDBLString implements WPIS_GenerateOutputs_Behavior_PrintDBLString {
	@Override
	public void print(final String sps) {
		System.err.println(sps);
	}
}
