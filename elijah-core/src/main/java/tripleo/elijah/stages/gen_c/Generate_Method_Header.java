package tripleo.elijah.stages.gen_c;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.FormalArgListItem;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.lang.types.OS_GenericTypeNameType;
import tripleo.elijah.nextgen.outputstatement.EG_DottedStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Rule;
import tripleo.elijah.stages.gen_c.c_ast1.C_HeaderString;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.VariableTableType;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.util.Helpers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Generate_Method_Header {

	final                  String       args_string;
	final @NotNull         String       header_string;
	private final @NotNull EG_Statement args_statement;
	private final @NotNull GenerateC    gc;
	private final          String       name;
	private final @NotNull String       return_type;
	@Nullable              OS_Type      type;
	TypeTableEntry tte;

	public Generate_Method_Header(final @NotNull BaseEvaFunction gf, @NotNull final GenerateC aGenerateC, final @NotNull ElLog LOG) {
		gc   = aGenerateC;
		name = gf.getFD().name().asString();
		//
		return_type    = find_return_type(gf, LOG);
		args_statement = find_args_statement(gf);
		args_string    = args_statement.getText();
		header_string  = find_header_string(gf, LOG);
	}

	@NotNull String find_return_type(final BaseEvaFunction gf, final ElLog LOG) {
		return discriminator(gf, LOG, gc)
				.find_return_type(this);
	}

	@NotNull EG_Statement find_args_statement(final @NotNull BaseEvaFunction gf) {

		final String rule = "gen_c:gcfm:Generate_Method_Header:find_args_statement";

		// TODO EG_Statement, rule
		final List<String> args_list = gf.vte_list
				.stream()
				.filter(input -> input.getVtt() == VariableTableType.ARG)

				//rule=vte:args_at
				.map(input -> String.format("%s va%s", GenerateC.GetTypeName.forVTE(input), input.getName()))
				.collect(Collectors.toList());
		final EG_Statement args = new EG_DottedStatement(", ", args_list, new EX_Rule(rule));

		return args;
	}

	@NotNull String find_header_string(final @NotNull BaseEvaFunction gf, final @NotNull ElLog LOG) {
		// NOTE getGenClass is always a class or namespace, getParent can be a function
		final EvaContainerNC parent = (EvaContainerNC) gf.getGenClass();

		final String         s2;
		final C_HeaderString headerString;

		if (parent instanceof EvaClass st) {
			var st2 = gc.a_lookup(st);

			headerString = C_HeaderString.forClass(st, () -> st2.getTypeNameString(), return_type, name, args_string, LOG);
		} else if (parent instanceof EvaNamespace st) {
			var st2 = gc.a_lookup(st);

			headerString = C_HeaderString.forNamespace(st, () -> st2.getTypeNameString(), return_type, name, args_string, LOG);
		} else {
			headerString = C_HeaderString.forOther(parent, return_type, name, args_string);
		}
		s2 = headerString.getResult();
		return s2;
	}

	static @NotNull GCM_D discriminator(final BaseEvaFunction bgf, final ElLog aLOG, final GenerateC aGc) {
		if (bgf instanceof EvaConstructor) {
			return new GCM_GC((EvaConstructor) bgf, aLOG, aGc);
		} else if (bgf instanceof EvaFunction) {
			return new GCM_GF((EvaFunction) bgf, aLOG, aGc);
		}

		throw new IllegalStateException();
	}

	String __find_header_string(final @NotNull BaseEvaFunction gf, final @NotNull ElLog LOG) {
		final String result;
		// TODO buffer for gf.parent.<element>.locatable

		// NOTE getGenClass is always a class or namespace, getParent can be a function
		final EvaContainerNC parent = (EvaContainerNC) gf.getGenClass();

		if (parent instanceof EvaClass) {
			final EvaClass st = (EvaClass) parent;

			@NotNull final C_HeaderString chs = C_HeaderString.forClass(st,
																		() -> GenerateC.GetTypeName.forGenClass(st),
																		return_type,
																		name,
																		args_string,
																		LOG);

			result = chs.getResult();
		} else if (parent instanceof EvaNamespace) {
			final EvaNamespace st = (EvaNamespace) parent;

			@NotNull final C_HeaderString chs = C_HeaderString.forNamespace(st,
																			() -> GenerateC.GetTypeName.forGenNamespace(st),
																			return_type,
																			name,
																			args_string,
																			LOG);
			result = chs.getResult();
		} else {
			@NotNull final C_HeaderString chs = C_HeaderString.forOther(parent, return_type, name, args_string);
			//result = String.format("%s %s(%s)", return_type, name, args_string);
			result = chs.getResult();
		}

		return result;
	}

	@NotNull String __find_return_type(final @NotNull WhyNotGarish_BaseFunction gf, final @NotNull ElLog LOG) {
		String returnType = null;
		if (gf.pointsToConstructor2()) {
			// Get it from resolved
			tte = gf.tte_for_self();
			final EvaNode res = tte.resolved();
			if (res instanceof final @NotNull EvaContainerNC nc) {
				final int code = nc.getCode();
				return String.format("Z%d*", code);
			}

			// Get it from type.attached
			type = tte.getAttached();

			LOG.info("228-1 " + type);
			if (type.isUnitType()) {
				assert false;
			} else if (type != null) {
				returnType = String.format("/*267*/%s*", gc.getTypeName(type));
			} else {
				LOG.err("655 Shouldn't be here (type is null)");
				returnType = "void/*2*/";
			}
		} else {
			var p = gf.tte_for_result();

			if (p.getLeft() != null)
				return p.getLeft();

			tte = p.getRight();

			final EvaNode res = tte.resolved();
			if (res instanceof final @NotNull EvaContainerNC nc) {
				final int code = nc.getCode();

				// HACK
				if (res instanceof EvaClass ec) {
					var classStatement = ec.getKlass();

					final OS_Module module             = classStatement.getContext().module();
					final String    classStatementName = classStatement.getName();

					if (module.isPrelude() && classStatementName.equals("Integer64")) {
						return "/*190*/int";
					}
				}

				return String.format("Z%d*", code);
			}

			// Get it from type.attached
			type = tte.getAttached();

			LOG.info("228 " + type);
			if (type == null) {
				LOG.err("655 Shouldn't be here (type is null)");
				returnType = "ERR_type_attached_is_null/*2*/";
			} else if (type.isUnitType()) {
				returnType = "void/*Unit-197*/";
			} else if (type != null) {
				if (type instanceof final @NotNull OS_GenericTypeNameType genericTypeNameType) {
					final TypeName tn = genericTypeNameType.getRealTypeName();

					final @Nullable Map<TypeName, OS_Type> gp = gf.classInvcationGenericPart();

					OS_Type realType = null;

					for (final Map.Entry<TypeName, OS_Type> entry : gp.entrySet()) {
						if (entry.getKey().equals(tn)) {
							realType = entry.getValue();
							break;
						}
					}

					assert realType != null;
					returnType = String.format("/*267*/%s*", gc.getTypeName(realType));
				} else
					returnType = String.format("/*267*/%s*", gc.getTypeName(type));
			} else {
				throw new IllegalStateException();
//					LOG.err("656 Shouldn't be here (can't reason about type)");
//					returnType = "void/*656*/";
			}
		}
		return returnType;
	}

	@NotNull String find_args_string(final @NotNull BaseEvaFunction gf) {
		final String args;
		if (false) {
			args = Helpers.String_join(", ", Collections2.transform(gf.getFD().fal().falis(), new Function<FormalArgListItem, String>() {
				@Nullable
				@Override
				public String apply(@Nullable final FormalArgListItem input) {
					assert input != null;
					return String.format("%s va%s", gc.getTypeName(input.typeName()), input.name());
				}
			}));
		} else {
			final Collection<VariableTableEntry> x = Collections2.filter(gf.vte_list, new Predicate<VariableTableEntry>() {
				@Override
				public boolean apply(@Nullable final VariableTableEntry input) {
					assert input != null;
					return input.getVtt() == VariableTableType.ARG;
				}
			});
			args = Helpers.String_join(", ", Collections2.transform(x, new Function<VariableTableEntry, String>() {
				@Nullable
				@Override
				public String apply(@Nullable final VariableTableEntry input) {
					assert input != null;
					return String.format("%s va%s", gc.getTypeNameForVariableEntry(input), input.getName());
				}
			}));
		}
		return args;
	}

}
