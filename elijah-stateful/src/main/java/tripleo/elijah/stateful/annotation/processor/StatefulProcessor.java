package tripleo.elijah.stateful.annotation.processor;

import com.google.auto.service.AutoService;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("tripleo.elijah.stateful.annotation.processor.StatefulProperty")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class StatefulProcessor extends AbstractProcessor {

	@Override
	public boolean process(@NotNull Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (TypeElement annotation : annotations) {

			Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

			Map<Boolean, List<Element>> annotatedMethods = annotatedElements.stream().collect(Collectors.partitioningBy(element -> ((ExecutableType) element.asType()).getParameterTypes().size() == 1 && element.getSimpleName().toString().startsWith("set")));

			List<Element> setters      = annotatedMethods.get(true);
			List<Element> otherMethods = annotatedMethods.get(false);

			otherMethods.forEach(element -> processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "@BuilderProperty must be applied to a setXxx method with a single argument", element));

			if (setters.isEmpty()) {
				continue;
			}

			String className = ((TypeElement) setters.get(0).getEnclosingElement()).getQualifiedName().toString();

			Map<String, String> setterMap = setters.stream().collect(Collectors.toMap(setter -> setter.getSimpleName().toString(), setter -> ((ExecutableType) setter.asType()).getParameterTypes().get(0).toString()));

			try {
				writeBuilderFile(className, setterMap);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return true;
	}

	private void writeBuilderFile(@NotNull String className, Map<String, String> setterMap) throws IOException {

		String packageName = null;
		int    lastDot     = className.lastIndexOf('.');
		if (lastDot > 0) {
			packageName = className.substring(0, lastDot);
		}

		String simpleClassName        = className.substring(lastDot + 1);
		String builderClassName       = className + "__ST";
		String builderSimpleClassName = builderClassName.substring(lastDot + 1);

		JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);
		try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

			extracted(setterMap, packageName, out, builderSimpleClassName, simpleClassName);

		}
	}

	private static void extracted(final Map<String, String> setterMap,
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

		out.print("/*public*/ enum ");
		out.print(builderSimpleClassName);
		out.println(" {");
		out.println("  ;");
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
		out.println("());");

		setterMap.entrySet().forEach(setter -> {
			String methodName   = setter.getKey();
			String argumentType = setter.getValue();

			out.print("    public ");
			out.print(builderSimpleClassName);
			out.print(" ");
			out.print(methodName);

			out.print("(");

			out.print(argumentType);
			out.println(" value) {");
			out.print("        object.");
			out.print(methodName);
			out.println("(value);");
			out.println("        return this;");
			out.println("    }");
			out.println();
		});

		out.println("}");
		out.println("}");
	}

}
