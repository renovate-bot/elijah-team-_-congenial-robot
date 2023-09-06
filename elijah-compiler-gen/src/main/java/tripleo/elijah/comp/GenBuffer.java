/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import tripleo.elijah.gen.CompilerContext;
import tripleo.elijah.gen.nodes.ImportNode;
import tripleo.elijah.gen.nodes.LocalDeclAgnNode;
import tripleo.util.NotImplementedException;
import tripleo.util.buffer.FileBackedBuffer;
import tripleo.util.buffer.TextBuffer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenBuffer {

	private final Map<String, TextBuffer> hdr_bufs = new HashMap<String, TextBuffer>();
	private final Map<String, TextBuffer> reg_bufs = new HashMap<String, TextBuffer>();

	public void GenImportStmt(final CompilerContext cctx, final ImportNode impn) {
		// TODO Auto-generated method stub
		NotImplementedException.raise();

	}

	public void GenLocalAgn(final CompilerContext cctx, final LocalDeclAgnNode ldan_f1) {
		// TODO Auto-generated method stub
		NotImplementedException.raise();
	}

	public void GenLocalDeclAgn(final CompilerContext cctx, final LocalDeclAgnNode ldan1) {
		// TODO Auto-generated method stub
		NotImplementedException.raise();
	}

	public void InitMod(final CompilerContext cctx, final String string) {
		// TODO Auto-generated method stub
		NotImplementedException.raise();

	}

//	public CodeGen getCodeGen() {
//		// TODO Auto-generated method stub
//		return new CodeGen() {
//
//			@Override
//			public void appendHeader(String module, String build) {
//				// TODO Auto-generated method stub
//				super.appendHeader(module, build);
//			}
//
//		};
//	}

	public TextBuffer moduleBufHdr(final String module) {
//		NotImplementedException.raise();
		if (hdr_bufs.containsKey(module)) {
			return hdr_bufs.get(module);
		} else {
			final TextBuffer buf = new FileBackedBuffer(module + ".h");
			hdr_bufs.put(module, buf);
			return buf;
		}
	}

	public TextBuffer moduleBufImpl(final String module) {
		// TODO Auto-generated method stub
//		NotImplementedException.raise();
		if (reg_bufs.containsKey(module)) {
			return reg_bufs.get(module);
		} else {
			final TextBuffer buf = new FileBackedBuffer(module + ".c");
			reg_bufs.put(module, buf);
			return buf;
		}
	}

	public void writeBuffers() throws IOException {
		for (final Map.Entry<String, TextBuffer> entry : reg_bufs.entrySet()) {
			final String     module = entry.getKey();
			final TextBuffer build  = entry.getValue();
			//
			final FileOutputStream fileOutputStream;
			fileOutputStream = new FileOutputStream(module + ".c", true); // append
			final String buildText = build.getText();
			fileOutputStream.write(buildText.getBytes());
			fileOutputStream.close();

		}
	}
}

//
//
//
