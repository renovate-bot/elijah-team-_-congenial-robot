package tripleo.elijah.comp.functionality.f203;

import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_256;

public class ChooseHashDirectoryNameBehavior implements ChooseDirectoryNameBehavior {
	private final Compilation   c;
	private final LocalDateTime localDateTime;

	@Contract(pure = true)
	public ChooseHashDirectoryNameBehavior(final Compilation aC, final LocalDateTime aLocalDateTime) {
		c             = aC;
		localDateTime = aLocalDateTime;
	}

	private @NotNull File choose_dir_name() {
		final List<File> recordedreads = c.getIO().recordedreads;
		final List<String> recordedread_filenames = recordedreads.stream()
				.map(File::toString)
				.collect(Collectors.toList());

		final DigestUtils digestUtils = new DigestUtils(SHA_256);

		final StringBuilder sb1 = new StringBuilder();

		recordedread_filenames.stream()
				.sorted()
				.map(digestUtils::digestAsHex)
				.forEach(sha256 -> {
					sb1.append(sha256);
					sb1.append('\n');
				});

		final String c_name = digestUtils.digestAsHex(sb1.toString());

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");

		final String date = formatter.format(localDateTime); //15-02-2022 12:43

		final File fn00 = new File("COMP", c_name);
		final File fn0  = new File(fn00, date);
		System.err.println("mkdirs 71 " + fn0);
		fn0.mkdirs();

		final String fn1 = new File(fn0, "inputs.txt").toString();
		return fn0;
	}

	@Override
	public @NotNull File chooseDirectory() {
		return choose_dir_name();
	}

}
