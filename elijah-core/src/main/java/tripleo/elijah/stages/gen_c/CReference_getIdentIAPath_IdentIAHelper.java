package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.AliasStatementImpl;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.List;

class CReference_getIdentIAPath_IdentIAHelper {
	private final BaseEvaFunction     generatedFunction;
	private final int                 i;
	private final EvaNode             resolved;
	private final OS_Element          resolved_element;
	private final List<String>        sl;
	private final InstructionArgument ia_next;
	private final int                 sSize;
	private final String              value;
	public        int                 code = -1;

	boolean action(final CRI_Ident aCRI_ident, final @NotNull CReference aCReference) {
		boolean          b               = false;
		final OS_Element resolvedElement = getResolved_element();

		if (resolvedElement instanceof ClassStatement) {
			b = _act_ClassStatement(aCReference, b);
		} else if (resolvedElement instanceof ConstructorDef) {
			_act_ConstructorDef(aCReference);
		} else if (resolvedElement instanceof FunctionDef) {
			_act_FunctionDef(aCReference);
		} else if (resolvedElement instanceof DefFunctionDef) {
			_act_DefFunctionDef(aCReference);
		} else if (resolvedElement instanceof VariableStatementImpl) {
			_act_VariableStatement(aCReference);
		} else if (resolvedElement instanceof PropertyStatement) {
			_act_PropertyStatement(aCReference);
		} else if (resolvedElement instanceof AliasStatementImpl) {
			_act_AliasStatement();
		} else if (resolvedElement instanceof FormalArgListItem) {
			_act_FormalArgListItem(aCReference, (FormalArgListItem) resolvedElement);
		} else {
			// text = idte.getIdent().getText();
			SimplePrintLoggerToRemoveSoon.println_out("1008 " + resolvedElement.getClass().getName());
			throw new NotImplementedException();
		}
		return b;
	}

	@Contract(pure = true)
	public OS_Element getResolved_element() {
		return resolved_element;
	}

	@Contract(pure = true)
	CReference_getIdentIAPath_IdentIAHelper(final InstructionArgument ia_next, final List<String> sl, final int i, final int sSize, final OS_Element resolved_element, final BaseEvaFunction generatedFunction, final EvaNode aResolved, final String aValue) {
		this.ia_next           = ia_next;
		this.sl                = sl;
		this.i                 = i;
		this.sSize             = sSize;
		this.resolved_element  = resolved_element;
		this.generatedFunction = generatedFunction;
		resolved               = aResolved;
		value                  = aValue;
	}

	private boolean _act_ClassStatement(final @NotNull CReference aCReference, boolean b) {
		// Assuming constructor call
		final int code;
		if (getResolved() != null) {
			code = ((EvaContainerNC) getResolved()).getCode();
		} else {
			code = -1;
			SimplePrintLoggerToRemoveSoon.println_err("** 31116 not resolved " + getResolved_element());
		}
		// README might be calling reflect or Type or Name
		// TODO what about named constructors -- should be called with construct keyword
		if (getIa_next() instanceof IdentIA) {
			final IdentTableEntry ite  = ((IdentIA) getIa_next()).getEntry();
			final String          text = ite.getIdent().getText();
			if (text.equals("reflect")) {
				b = true;
				final String text2 = String.format("ZS%d_reflect", code);
				aCReference.addRef(text2, CReference.Ref.FUNCTION);
			} else if (text.equals("Type")) {
				b = true;
				final String text2 = String.format("ZST%d", code); // return a TypeInfo structure
				aCReference.addRef(text2, CReference.Ref.FUNCTION);
			} else if (text.equals("Name")) {
				b = true;
				final String text2 = String.format("ZSN%d", code);
				aCReference.addRef(text2, CReference.Ref.FUNCTION); // TODO make this not a function
			} else {
				assert getI() == getsSize() - 1; // Make sure we are ending with a constructor call
				// README Assuming this is for named constructors
				final String text2 = String.format("ZC%d%s", code, text);
				aCReference.addRef(text2, CReference.Ref.CONSTRUCTOR);
			}
		} else {
			assert getI() == getsSize() - 1; // Make sure we are ending with a constructor call
			final String text2 = String.format("ZC%d", code);
			aCReference.addRef(text2, CReference.Ref.CONSTRUCTOR);
		}
		return b;
	}

	private void _act_ConstructorDef(final @NotNull CReference aCReference) {
		assert getI() == getsSize() - 1; // Make sure we are ending with a constructor call
		final int code;
		if (getResolved() != null) {
			code = ((BaseEvaFunction) getResolved()).getCode();
		} else {
			code = -1;
			SimplePrintLoggerToRemoveSoon.println_err("** 31161 not resolved " + getResolved_element());
		}
		// README Assuming this is for named constructors
		final String text  = ((ConstructorDef) getResolved_element()).name().asString();
		final String text2 = String.format("ZC%d%s", code, text);
		aCReference.addRef(text2, CReference.Ref.CONSTRUCTOR);
	}

	private void _act_FunctionDef(final @NotNull CReference aCReference) {
		final OS_Element parent        = getResolved_element().getParent();
		int              our_code      = -1;
		final EvaNode    resolved_node = getResolved();

		if (resolved_node != null) {
			if (resolved_node instanceof final @NotNull BaseEvaFunction resolvedFunction) {

				resolvedFunction.onGenClass(gc -> {
//						GeneratedNode gc = rf.getGenClass();
					if (gc instanceof EvaContainerNC) // and not another function
						this.code = gc.getCode();
					else
						this.code = -2;
				});

				if (resolvedFunction.getGenClass() instanceof final @NotNull EvaNamespace generatedNamespace) {
					// FIXME sometimes genClass is not called so above wont work,
					//  so check if a code was set and use it here
					final int cc = generatedNamespace.getCode();
					if (cc > 0) {
						this.code = cc;
					}
				}

			} else if (resolved_node instanceof final @NotNull EvaClass generatedClass) {
				this.code = generatedClass.getCode();
			}
		}
		// TODO what about overloaded functions
		assert getI() == getsSize() - 1; // Make sure we are ending with a ProcedureCall
		getSl().clear();

		our_code = this.code;

		if (our_code == -1) {
//				text2 = String.format("ZT%d_%d", enclosing_function._a.getCode(), closure_index);
		}
		final String text2 = String.format("z%d%s", our_code, ((FunctionDef) getResolved_element()).name());
		aCReference.addRef(text2, CReference.Ref.FUNCTION);
	}

	private void _act_DefFunctionDef(final @NotNull CReference aCReference) {
		final OS_Element parent = getResolved_element().getParent();
		final int        code;
		if (getResolved() != null) {
			assert getResolved() instanceof BaseEvaFunction;
			final BaseEvaFunction rf = (BaseEvaFunction) getResolved();
			final EvaNode         gc = rf.getGenClass();
			if (gc instanceof EvaContainerNC) // and not another function
				code = ((EvaContainerNC) gc).getCode();
			else
				code = -2;
		} else {
			if (parent instanceof ClassStatement) {
				code = -3;
			} else if (parent instanceof NamespaceStatement) {
				code = -3;
			} else {
				// TODO what about FunctionDef, etc
				code = -1;
			}
		}
		// TODO what about overloaded functions
		assert getI() == getsSize() - 1; // Make sure we are ending with a ProcedureCall
		getSl().clear();
		if (code == -1) {
//				text2 = String.format("ZT%d_%d", enclosing_function._a.getCode(), closure_index);
		}
		final DefFunctionDef defFunctionDef = (DefFunctionDef) getResolved_element();
		final String         text2          = String.format("z%d%s", code, defFunctionDef.name());
		aCReference.addRef(text2, CReference.Ref.FUNCTION);
	}

	private void _act_VariableStatement(final @NotNull CReference aCReference) {
		final VariableStatementImpl variableStatement = (VariableStatementImpl) getResolved_element();

		GI_VariableStatement givs = (GI_VariableStatement) aCReference._repo().itemFor(variableStatement);
		givs._createReferenceForVariableStatement(aCReference, getGeneratedFunction(), getValue());
	}

	private void _act_PropertyStatement(final @NotNull CReference aCReference) {
		getSl().clear();  // don't we want all the text including from sl?

		final GCS_Property_Get propertyGet = new GCS_Property_Get((PropertyStatement) getResolved_element());
		final String           text2       = propertyGet.getText();

		aCReference.addRef(text2, CReference.Ref.PROPERTY_GET);

		aCReference.__cheat_ret = text2;
	}

	@Contract(pure = true)
	private static void _act_AliasStatement() {
		final int y = 2;
		NotImplementedException.raise();
		//			text = Emit.emit("/*167*/")+((AliasStatementImpl)resolved_element).name();
		//			return _getIdentIAPath_IdentIAHelper(text, sl, i, sSize, _res)
	}

	private void _act_FormalArgListItem(final @NotNull CReference aCReference, final @NotNull FormalArgListItem fali) {
		final int    y     = 2;
		final String text2 = "va" + fali.getNameToken().getText();
		aCReference.addRef(text2, CReference.Ref.LOCAL); // TODO
	}

	@Contract(pure = true)
	public EvaNode getResolved() {
		return resolved;
	}

	@Contract(pure = true)
	public int getI() {
		return i;
	}

	@Contract(pure = true)
	public InstructionArgument getIa_next() {
		return ia_next;
	}

	@Contract(pure = true)
	public int getsSize() {
		return sSize;
	}

	@Contract(pure = true)
	public BaseEvaFunction getGeneratedFunction() {
		return generatedFunction;
	}

	@Contract(pure = true)
	public List<String> getSl() {
		return sl;
	}

	public static class GCS_Property_Get implements EG_Statement {
		private final PropertyStatement p;

		public GCS_Property_Get(final PropertyStatement aP) {
			p = aP;
		}

		@Override
		public @NotNull EX_Explanation getExplanation() {
			return EX_Explanation.withMessage("GCS_Property_Get");
		}

		@Override
		public String getText() {
			final OS_Element parent = p.getParent();
			final int        code;

			if (parent instanceof ClassStatement) {
				code = -3;
			} else if (parent instanceof NamespaceStatement) {
				code = -3;
			} else {
//				code = -1;
				throw new IllegalStateException("PropertyStatement can't have other parent than ns or cls. " + parent.getClass().getName());
			}

			// TODO Don't know if get or set!
			final String text2 = String.format("ZP%dget_%s", code, p.name());

			return text2;
		}

	}

	@Contract(pure = true)
	public String getValue() {
		return value;
	}
}
