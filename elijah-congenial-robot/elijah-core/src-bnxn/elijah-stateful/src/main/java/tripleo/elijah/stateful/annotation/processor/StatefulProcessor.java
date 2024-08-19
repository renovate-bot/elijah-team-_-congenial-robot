package tripleo.elijah.stateful.annotation.processor;

//import com.google.auto.service.AutoService;

import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
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
//@AutoService(Processor.class)
public class StatefulProcessor extends AbstractProcessor {

	@Override
	public boolean process(@NotNull Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		final StatefulAnnotationCreator potatoe = new StatefulAnnotationCreator();

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
				potatoe.dukakis(setterMap);

				final Map<String, String> outmap           = potatoe.getBuilderMap(className, setterMap);

				final String              builderClassName = outmap.get(StatefulAnnotationCreator.key);
				final JavaFileObject      builderFile      = processingEnv.getFiler().createSourceFile(builderClassName);

				final String requested = outmap.get(builderClassName);
				final PrintWriter writer = new PrintWriter(builderFile.openWriter());
				pushTextToWriter(writer, requested);
			} catch (IOException e) {
				// TODO stacktrace?
				processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
			}

		}

		return true;
	}

	private static void pushTextToWriter(final PrintWriter writer, final String requested) {
		try (PrintWriter out = writer) {
			out.print(requested);
		}
	}
}
