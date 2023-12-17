package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.PropertyStatement;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.List;
import java.util.function.Consumer;

class CRI_Ident {
	private final BaseEvaFunction generatedFunction;

	@Contract(value = "_, _ -> new", pure = true)
	public static @NotNull CRI_Ident of(final IdentTableEntry aIdte, final BaseEvaFunction aGf) {
		return new CRI_Ident(aIdte, aGf);
	}

	private final IdentTableEntry ite;

	public CRI_Ident(final IdentTableEntry aIdte, final BaseEvaFunction aGf) {
		ite               = aIdte;
		generatedFunction = aGf;
	}

	boolean _getIdentIAPath_IdentIAHelper(final InstructionArgument ia_next,
										  final List<String> sl,
										  final int i,
										  final int sSize,
										  final OS_Element resolved_element,
										  final BaseEvaFunction generatedFunction,
										  final EvaNode aResolved,
										  final String aValue, final CReference aCReference) {
		return new CReference_getIdentIAPath_IdentIAHelper(ia_next, sl, i, sSize, resolved_element, generatedFunction, aResolved, aValue).action(this, aCReference);
	}

	public @Nullable String getIdentIAPath(int i,
										   final int sSize,
										   final Generate_Code_For_Method.@NotNull AOG aog,
										   final @NotNull List<String> sl,
										   final String aValue,
										   final @NotNull Consumer<CReference.Reference> addRef,
										   final @NotNull List<InstructionArgument> s,
										   final IdentIA ia2,
										   final @NotNull CReference aCReference,
										   final @NotNull CR_ReferenceItem item) {
		final boolean[]  skip             = {false};
		final OS_Element resolved_element = ite.getResolvedElement();
		final String[]   text             = {null};

		final EvaClass _cheat = aCReference._cheat;

		final @Nullable GenerateC_Item repo_element = aCReference._repo().itemFor(resolved_element);


		//item.setInstructionArgument(s.get(i));
		item.setGenerateCItem(repo_element);


		if (resolved_element != null) {
			EvaNode resolved2;

			//assert repo_element != null;
			if (repo_element == null) {
				int y = 2;
				//throw new AssertionError();
			} else {
				if (resolved_element instanceof ClassStatement) {
					((GI_ClassStatement) repo_element).setITE(ite);
				} else if (resolved_element instanceof FunctionDef) {
					@Nullable final ProcTableEntry pte = ite.getCallablePTE();
					resolved2 = ((GI_FunctionDef) repo_element)._re_is_FunctionDef(pte, _cheat, ite);

					repo_element.setEvaNode(resolved2);
				} else if (resolved_element instanceof PropertyStatement) {
					resolved2 = _re_is_PropertyStatement(addRef, aog, sSize, i, aValue, (x) -> skip[0] = true, (x) -> text[0] = x);

					repo_element.setEvaNode(resolved2);

					skip[0] = false;
				} else if (resolved_element instanceof VariableStatementImpl) {
					VariableStatementImpl resolvedElement = (VariableStatementImpl) resolved_element;
					//repo_element.set
					//addRef.accept(new CReference.Reference(resolvedElement.getName(), CReference.Ref.MEMBER/*??*/));

					item.setText(resolvedElement.getName());
					if (aCReference.refs.size() > 0)
						item.setReference(aCReference.refs.get(aCReference.refs.size() - 1));
				}
			}
		}

		if (repo_element != null) {
			final @Nullable EvaNode resolved0 = repo_element.getEvaNode();
			final @Nullable EvaNode resolved;

			//if (resolved0 == null) {
			//	if (item.getTableEntry() instanceof IdentTableEntry ite) {
			//		if (ite.hasResolvedElement()) {
			//			resolved = ite.getResolvedElement();
			//		}
			//	}
			//} else
			{
				resolved = resolved0;
			}

			if (!skip[0]) {
				short state = 1;
				if (ite.externalRef != null) {
					state = 2;
				}
				switch (state) {
				case 1:
					if (resolved == null) {
						SimplePrintLoggerToRemoveSoon.println_err("***88*** resolved is null for " + ite);
					}
					if (sSize >= i + 1) {
						_getIdentIAPath_IdentIAHelper(null, sl, i, sSize, resolved_element, generatedFunction, resolved, aValue, aCReference);
						String x = aCReference.__cheat_ret;
						if (x == null && repo_element instanceof GI_VariableStatement givs) {
							givs.setItem(item);
							x = givs.getText();
						}
						if (x == null && resolved_element instanceof VariableStatementImpl) {
							x = ((VariableStatementImpl) resolved_element).getName();
						}
						text[0] = x;
					} else {
						final boolean b = _getIdentIAPath_IdentIAHelper(s.get(i + 1), sl, i, sSize, resolved_element, generatedFunction, resolved, aValue, aCReference);
						if (b) i++;
					}
					break;
				case 2:
					if ((resolved_element instanceof VariableStatementImpl)) {
						final String text2 = ((VariableStatementImpl) resolved_element).getName();

						final EvaNode externalRef = ite.externalRef;
						if (externalRef instanceof EvaNamespace) {
							final String text3 = String.format("zN%d_instance", ((EvaNamespace) externalRef).getCode());
							addRef.accept(new CReference.Reference(text3, CReference.Ref.LITERAL, null));
						} else if (externalRef instanceof EvaClass) {
							assert false;
							final String text3 = String.format("zN%d_instance", ((EvaClass) externalRef).getCode());
							addRef.accept(new CReference.Reference(text3, CReference.Ref.LITERAL, null));
						} else
							throw new IllegalStateException();
						addRef.accept(new CReference.Reference(text2, CReference.Ref.MEMBER, aValue));
					} else
						throw new NotImplementedException();
					break;
				}
			}
		} else {
			switch (ite.getStatus()) {
			case KNOWN:
				text[0] = Emit.emit("/*194*/") + ite.getIdent().getText();
				//assert false;
				break;
			case UNCHECKED:
				final String path2 = generatedFunction.getIdentIAPathNormal(ia2);
				final String text3 = String.format("<<UNCHECKED ia2: %s>>", path2/*idte.getIdent().getText()*/);
				text[0] = text3;
//						assert false;
				break;
			case UNKNOWN:
				final String path = generatedFunction.getIdentIAPathNormal(ia2);
				final String text1 = ite.getIdent().getText();
//						assert false;
				// TODO make tests pass but I dont like this (should emit a dummy function or placeholder)
				if (sl.size() == 0) {
					text[0] = Emit.emit("/*149*/") + text1; // TODO check if it belongs somewhere else (what does this mean?)
				} else {
					text[0] = Emit.emit("/*152*/") + "vm" + text1;
				}
				SimplePrintLoggerToRemoveSoon.println_err("119 " + ite.getIdent() + " " + ite.getStatus());
				final String text2 = (Emit.emit("/*114*/") + String.format("%s is UNKNOWN", text1));
				addRef.accept(new CReference.Reference(text2, CReference.Ref.MEMBER));
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + ite.getStatus());
			}
		}

		return text[0];
	}

	private @Nullable EvaNode _re_is_PropertyStatement(final @NotNull Consumer<CReference.Reference> addRef,
													   final Generate_Code_For_Method.@NotNull AOG aog,
													   final int sSize,
													   final int i,
													   final String aValue,
													   final @NotNull Consumer<Void> skip,
													   final @NotNull Consumer<String> text) {
		NotImplementedException.raise();
		final EvaNode resolved1 = ite.type.resolved();
		final int     code;
		if (resolved1 != null)
			code = ((EvaContainerNC) resolved1).getCode();
		else
			code = -1;
		short state = 0;
		if (i < sSize - 1) {
			state = 1;
		} else {
			switch (aog) {
			case GET:
				state = 1;
				break;
			case ASSIGN:
				state = 2;
				break;
			}
		}
		switch (state) {
		case 1:
			addRef.accept(new CReference.Reference(String.format("ZP%d_get%s(", code, ite.getIdent().getText()), CReference.Ref.PROPERTY_GET));
			skip.accept(null);
			text.accept(null);
			break;
		case 2:
			addRef.accept(new CReference.Reference(String.format("ZP%d_set%s(", code, ite.getIdent().getText()), CReference.Ref.PROPERTY_SET, aValue));
			skip.accept(null);
			text.accept(null);
			break;
		default:
			throw new IllegalStateException("Unexpected value: " + state);
		}
		return resolved1;
	}
}
