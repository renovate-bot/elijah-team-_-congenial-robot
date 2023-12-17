package tripleo.elijah.comp;

import com.google.common.base.MoreObjects;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.util.Maybe;

import java.io.File;

public class CompilerInput {
	private final String                           inp;
	private       Maybe<ILazyCompilerInstructions> accept_ci;
	private       File                             dir_carrier;
	private       Ty                               ty;
	private       String                           hash;

	public String getInp() {
		return inp;
	}

	public CompilerInput(final String aS) {
		inp = aS;
		ty  = Ty.NULL;
	}

	public void accept_ci(final Maybe<ILazyCompilerInstructions> aM3) {
		accept_ci = aM3;
	}

	public Maybe<ILazyCompilerInstructions> acceptance_ci() {
		return accept_ci;
	}

	public boolean isNull() {
		return ty == Ty.NULL;
	}

	public boolean isSourceRoot() {
		return ty == Ty.SOURCE_ROOT;
	}

	public void setSourceRoot() {
		ty = Ty.SOURCE_ROOT;
	}

	public void setDirectory(File f) {
		ty          = Ty.SOURCE_ROOT;
		dir_carrier = f;
	}

	public void setArg() {
		ty = Ty.ARG;
	}

	public void accept_hash(final String hash) {
		this.hash = hash;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("ty", ty)
				.add("inp", inp)
				.add("accept_ci", accept_ci.toString())
				.add("dir_carrier", dir_carrier)
				.add("hash", hash)
				.toString();
	}

	public enum Ty {NULL, SOURCE_ROOT, ARG}

	public Ty ty() {
		return ty;
	}
}
