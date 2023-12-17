package tripleo.elijah.comp.nextgen;

import org.apache.commons.codec.digest.DigestUtils;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.nextgen.ER_Node;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.util.Operation;
import tripleo.util.io.DisposableCharSink;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_256;

public class CP_OutputPath implements CP_Path, _CP_RootPath {

	private final Compilation                      c;
	private final DeferredObject<Path, Void, Void> _pathPromise = new DeferredObject<>();
	private       File                             root; // COMP/...
	private       String                           _calc;
	private       String                           date;
	private       boolean                          _testShim;

	public CP_OutputPath(final Compilation cc) {
		c = cc;
	}

	@Override
	public @NotNull CP_SubFile subFile(final String aFile) { // s ;)
		return new CP_SubFile(this, aFile);
	}

	@Override
	public CP_Path child(final String aSubPath) {
		return new CP_SubFile(this, aSubPath).getPath();
	}

	@Override
	public @NotNull Path getPath() {
		//assert (root != null);
		if (root == null)
			return Path.of("zero");
		return root.toPath();
	}

	@Override
	public @NotNull Promise<Path, Void, Void> getPathPromise() {
		return _pathPromise;
	}

	@Override
	public @NotNull File toFile() {
		return getPath().toFile();
	}

	@Override
	public @NotNull File getRootFile() {
		return new File("COMP");
	}

	@Override
	public @Nullable CP_Path getParent() {
		return null;
	}

	@Override
	public @Nullable String getName() {
		return null;
	}

	@Override
	public @NotNull _CP_RootPath getRootPath() {
		return this;
	}

	public File getRoot() {
		return root;
	}

	public void renderNodes() {

	}

	public void _renderNodes(final @NotNull List<ER_Node> nodes) {
		signalCalculateFinishParse();
		nodes.stream()
				.map(this::renderNode)
				.collect(Collectors.toList());
	}

	public void signalCalculateFinishParse() {
		if (_pathPromise.isPending()) {
			final String calc = getCalc();

			__PathPromiseCalculator ppc = new __PathPromiseCalculator();
			ppc.calc(calc);
			CP_Path p = ppc.getP(this);

			final String root = c.paths().outputRoot().getRootFile().toString();
			final String one  = ppc.c_name();
			final String two  = ppc.date();

			Path px = Path.of(root, one, _testShim ? "<date>" : two);
			logProgress(117117, "OutputPath = " + px);

			//assert p.equals(px); // FIXME "just return COMP" instead of zero

			_pathPromise.resolve(px);

			CP_Path pp = ppc.getP(this);
			//assert pp.equals(px); // FIXME "just return COMP" instead of zero


			this.root = px.toFile();

			CP_Path p3 = ppc.getP(this);
			//assert p3.equals(px); // FIXME "just return COMP" instead of zero

/*
			final List<Object> objects = List_of(px, p, pp, p3);
			for (Object object : objects) {
				logProgress(117133, "" + object);
			}
*/
		}
	}

	private void logProgress(final int code, final String message) {
		if (code == 117117) return;
		System.err.println(String.format("%d %s", code, message));
	}

	public @NotNull Operation<Boolean> renderNode(final @NotNull ER_Node node) {
		final Path         path = node.getPath();
		final EG_Statement seq  = node.getStatement();

		if (c.reports().outputOn(Finally.Outs.Out_401b)) {
			System.out.println("401b Writing path: " + path.toFile());
		}

		path.getParent().toFile().mkdirs();

		try (final DisposableCharSink xx = c.getIO().openWrite(path)) {
			xx.accept(seq.getText());

			return Operation.success(true);
		} catch (Exception aE) {
			return Operation.failure(aE);
		}
	}

	private String getCalc() {
		if (_calc == null) {
			final List<File> recordedreads = c.getIO().recordedreads;

			final DigestUtils   digestUtils = new DigestUtils(SHA_256);
			final StringBuilder sb1         = new StringBuilder();

			recordedreads.stream()
					.map(File::toString)
					.sorted()
					.map(digestUtils::digestAsHex)
					.forEach(sha256 -> {
						sb1.append(sha256);
						sb1.append('\n');
					});

			final String c_name = digestUtils.digestAsHex(sb1.toString());
			_calc = c_name;
		} else {
		}
		return _calc;
	}

	public void testShim() {
		_testShim = true;
	}

	private class __PathPromiseCalculator {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");

		private String date;
		private String c_name;

		public CP_Path getP(final @NotNull CP_OutputPath aCPOutputPath) {
			final CP_Path outputRoot = aCPOutputPath.c.paths().outputRoot();

			return outputRoot
					.child(c_name)
					.child(date);
		}

		public String c_name() {
			return c_name;
		}

		public String date() {
			return date;
		}

		public void calc(String c_name) {
			final LocalDateTime localDateTime = LocalDateTime.now();
			final String        date          = formatter.format(localDateTime); //15-02-2022 12:43

			this.c_name = c_name;
			this.date   = date;
		}
	}
}
