package tripleo.elijah.stages.deduce;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created 8/3/20 8:41 AM
 */
public class DeduceUtils {
	static class IsConstructor implements Predicate<OS_Element> {
		@Override
		public boolean test(@Nullable final OS_Element input) {
			return input instanceof ConstructorDef;
		}
	}

	static class MatchArgs implements Predicate<OS_NamedElement> {
		private final ExpressionList args;

		public MatchArgs(final ExpressionList args) {
			this.args = args;
		}

		@Override
		public boolean test(@Nullable final OS_NamedElement input) {
			if (input instanceof final FunctionDef functionDef) {//
				final FormalArgList fal = functionDef.fal();
				if (args == null && fal.falis().isEmpty()) {
					return true;
				} else {
					NotImplementedException.raise();
					return false; // TODO implement me
				}
			} else {
				return false;
			}
		}
	}

	static class MatchConstructorArgs implements Predicate<OS_Element> {
		private final ProcedureCallExpression pce;

		public MatchConstructorArgs(final ProcedureCallExpression pce) {
			this.pce = pce;
		}

		@Override
		public boolean test(final @NotNull OS_Element o) {
			final ExpressionList args = pce.getArgs();
			// See if candidate matches args
			if (((LookupResult) o).getElement() instanceof final @NotNull ClassStatement klass) {
				//o filter isCtor each (each args isCompat)

				var ctors  = (klass.getItems().stream().filter(_inj().new_IsConstructor()));
				var ctors2 = (ctors.filter(_inj().new_MatchFunctionArgs(pce)));

				return !Lists.newArrayList(ctors2).isEmpty();
			}
			SimplePrintLoggerToRemoveSoon.println_out_2(String.valueOf(o));
			return false;
		}

		private DeduceUtilsInjector _inj() {
			return __inj;
		}

		public boolean apply(@NotNull OS_Element input) {
			return test(input);
		}
	}

	static class MatchFunctionArgs implements Predicate<OS_Element> {
		private final ProcedureCallExpression pce;

		public MatchFunctionArgs(final ProcedureCallExpression pce) {
			this.pce = pce;
		}

		@Override
		public boolean test(final OS_Element o) {
			assert o instanceof ClassItem;
			//  TODO what about __call__ and __ctor__ for ClassStatement?
//			tripleo.elijah.util.Stupidity.println_out_2("2000 "+o);
			if (!(o instanceof FunctionDef)) return false;
			//
			final ExpressionList args = pce.getArgs();
			// See if candidate matches args
			/*if (((LookupResult)o).getElement() instanceof FunctionDef)*/
			{
				//o filter isCtor each (each args isCompat)
				final @NotNull FunctionDef fd = (FunctionDef) (/*(LookupResult)*/o)/*.getElement()*/;
				final List<OS_NamedElement> matching_functions = fd.items()
						.stream()
						.filter(_inj().new_MatchArgs(pce.getArgs()))
						.collect(Collectors.toList());
				return matching_functions.size() > 0;
			}
//			return false;
		}

		private DeduceUtilsInjector _inj() {
			return __inj;
		}
	}

	private final static DeduceUtilsInjector __inj = new DeduceUtilsInjector();

	static class DeduceUtilsInjector {
		public Predicate<OS_Element> new_IsConstructor() {
			return new IsConstructor();
		}

		public Predicate<OS_Element> new_MatchFunctionArgs(final ProcedureCallExpression aPce) {
			return new MatchFunctionArgs(aPce);
		}

		public Predicate<OS_NamedElement> new_MatchArgs(ExpressionList aExpressionList) {
			return new MatchArgs(aExpressionList);
		}
	}
}

//
//
//
