/**
 *
 */
package tripleo.util.io;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.NotImplementedException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Tripleo(sb)
 * <p>
 * Created 	Dec 9, 2019 at 3:23:57 PM
 */
public class FileCharSink implements DisposableCharSink {

	private final OutputStream fos;

	public FileCharSink(final OutputStream fos) {
		super();
		this.fos = fos;
	}

	/* (non-Javadoc)
	 * @see tripleo.util.io.CharSink#accept(char)
	 */
	@Override
	public void accept(final char char1) {
		try {
			fos.write(char1);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see tripleo.util.io.CharSink#accept(java.lang.String)
	 */
	@Override
	public void accept(final @NotNull String string1) {
		try {
			fos.write(string1.getBytes());
		} catch (final IOException e) {
			NotImplementedException.raise();
			//e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			fos.close();
		} catch (final IOException aE) {
			NotImplementedException.raise();
			//aE.printStackTrace();
		}
	}

	@Override
	public void dispose() {
		close();
	}
}

//
//
//
