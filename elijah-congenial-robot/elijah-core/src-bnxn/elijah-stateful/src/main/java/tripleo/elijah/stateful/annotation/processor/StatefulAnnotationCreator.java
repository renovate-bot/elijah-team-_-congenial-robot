package tripleo.elijah.stateful.annotation.processor;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class StatefulAnnotationCreator {
	public static final String key = ".init";

	public Map<String, String> getBuilderMap(@NotNull String className, Map<String, String> setterMap) {
		final String packageName;
		int          lastDot = className.lastIndexOf('.');
		if (lastDot > 0) {
			packageName = className.substring(0, lastDot);
		} else {
			packageName = null;
		}

		final String simpleClassName        = className.substring(lastDot + 1);
		final String builderClassName       = className + "__ST";
		final String builderSimpleClassName = builderClassName.substring(lastDot + 1);

		final StringWriter sw = new StringWriter();
		try (PrintWriter out = new PrintWriter(sw)) {
			classToPrintWriter(setterMap, packageName, out, builderSimpleClassName, simpleClassName);
		}

		final Map<String, String> retmap = new HashMap<>();
		retmap.put(key, builderClassName);
		retmap.put(builderClassName, sw.toString());
		return retmap;
	}

	public void classToPrintWriter(final Map<String, String> setterMap,
								   final String packageName,
								   final PrintWriter out,
								   final String builderSimpleClassName,
								   final String simpleClassName) {
		if (packageName != null) {
			out.print("package ");
			out.print(packageName);
			out.println(";");
			out.println();
		}

		out.println("import tripleo.elijah.stateful.*;\n");
		out.println();

		out.print("public class ");
		out.print(builderSimpleClassName);
		out.println(" {");
		//out.println("  ;");
		out.println();
		out.println();

		var s = "public static class " + simpleClassName + "__STX" + " implements State {" +
				"""
							private StateRegistrationToken identity;

						@Override
							public void apply(final DefaultStateful element) {
								//implementation here
							}

							@Override
							public boolean checkState(final DefaultStateful aElement3) {
								return true;
							}

							@Override
							public void setIdentity(final StateRegistrationToken aId) {
								identity = aId;
							}
						}
							""";

		out.println(s);
		out.println();
		out.println();


		out.print("    public static State __");
		out.print(simpleClassName);
		out.println(";");
		out.println();

		out.println("    public static void register(final _RegistrationTarget aRegistrationTarget) {");
		out.print("__");
		out.print(simpleClassName);
		out.print(" = aRegistrationTarget.registerState(new ");

		out.print(simpleClassName);
		out.println("__STX());");
		out.println();
		out.println('}');

		for (Map.Entry<String, String> setter : setterMap.entrySet()) {
			setterToPrintWriter(out, builderSimpleClassName, setter);
		}

		out.println("}");
	}

	private static void setterToPrintWriter(final PrintWriter out, final String builderSimpleClassName, final Map.Entry<String, String> setter) {
		String methodName   = setter.getKey();
		String argumentType = setter.getValue();

		out.print("    public ");
		out.print(builderSimpleClassName);
		out.print("X ");
		out.print(methodName);

		out.print("(");

		out.print(argumentType);
		out.println(" value) {");
		out.print("    " + builderSimpleClassName + "X object = new ");
		out.print(" " + builderSimpleClassName + "X();");
		out.print("        object.");
		//out.print(methodName);
		out.print("apply");
		out.println("(null);");
		out.println("        return object;");
		out.println("    }");
		out.println();
	}

	public void dukakis(final Map<String, String> setterMap) throws FileNotFoundException {
		// TODO?? kctfork, xtend
		final String gson          = new Gson().toJson(setterMap);
		final String date          = gld();
		final String debugFileName = "StatefulProcessor-debug-" + date + ".txt";
		try (PrintWriter out = new PrintWriter(debugFileName)) {
			out.print(gson);
		}
	}

	private String gld() {
		final LocalDateTime     localDateTime = LocalDateTime.now();
		final DateTimeFormatter formatter     = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");
		//noinspection UnnecessaryLocalVariable
		final String date = formatter.format(localDateTime); //2022-02-12_21.43.65
		return date;
	}
}
