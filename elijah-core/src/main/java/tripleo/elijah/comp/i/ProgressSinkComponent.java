package tripleo.elijah.comp.i;

import org.jetbrains.annotations.NotNull;

public enum ProgressSinkComponent {
	CCI {
		@Override
		public boolean isPrintErr(final IProgressSink.Codes aCode, final int aType) {
			return aCode.value() == 131 && aType == -1;
		}

		@Override
		public @NotNull String printErr(final IProgressSink.Codes aCode, final int aType, final Object[] aParams) {
			return "*** CCI ->> " + aParams[0]; // ci.getName
		}
	},
	CompilationBus_ {
		@Override
		public boolean isPrintErr(final IProgressSink.Codes aCode, final int aType) {
			return true;
		}

		@Override
		public String printErr(final IProgressSink.Codes aCode, final int aType, final Object[] aParams) {
			return "*** CompilationBus ->> " + aParams[0];
		}
	};

	public abstract boolean isPrintErr(final IProgressSink.Codes aCode, final int aType);

	public abstract String printErr(final IProgressSink.Codes aCode, final int aType, final Object[] aParams);
}
