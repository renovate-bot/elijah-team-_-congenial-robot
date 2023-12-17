package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.Mode;
import tripleo.elijah.stages.gen_generic.DoubleLatch;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.Operation;
import tripleo.util.buffer.DefaultBuffer;

import java.util.concurrent.Executor;

/*
 * intent: HashBuffer
 *  - contains 3 sub-buffers: hash, space, and filename
 *  - has all logic to update and present hash
 *    - codec: MTL sha2 here
 *    - encoding: reg or multihash (hint hint...)
 */
public class HashBuffer extends DefaultBuffer {
	final DoubleLatch<String> dl = new DoubleLatch<>(aFilename -> {
		final HashBuffer outputBuffer = this;

		final @NotNull String            hh;
		final @NotNull Operation<String> hh2 = Helpers.getHashForFilename(aFilename);

		if (hh2.mode() == Mode.SUCCESS) {
			hh = hh2.success();

			if (hh != null) {
				outputBuffer.append(hh);
				outputBuffer.append(" ");
				outputBuffer.append_ln(aFilename);
			}
		} else {
			throw new RuntimeException(hh2.failure());
		}
	});

	private final @Nullable HashBufferList parent;


	public HashBuffer(final String string) {
		super(string);

		parent = null;

		dl.notifyData(string);
	}

	//public String getText() {
	//	dl.notifyLatch(true);
	//
	//	return dl.
	//}

	public HashBuffer(final String aFileName, final HashBufferList aHashBufferList, final Executor aExecutor) {
		super("");

		parent = aHashBufferList;
		//parent.setNext(this);

		dl.notifyData(aFileName);
	}
}
