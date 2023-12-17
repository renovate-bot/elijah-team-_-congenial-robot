package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.MatchConditionalImpl;
import tripleo.elijah.lang.impl.NumericExpressionImpl;
import tripleo.elijah.lang.types.OS_BuiltinType;
import tripleo.elijah.lang.types.OS_UserType;
import tripleo.elijah.lang2.BuiltInTypes;
import tripleo.elijah.lang2.SpecialFunctions;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.NotImplementedException;
import tripleo.util.range.Range;

import java.util.ArrayList;
import java.util.List;

import static tripleo.elijah.stages.deduce.DeduceTypes2.to_int;
import static tripleo.elijah.util.Helpers.List_of;

class Generate_Item {
	private final GenerateFunctions generateFunctions;

	public Generate_Item(final GenerateFunctions aGenerateFunctions) {
		generateFunctions = aGenerateFunctions;
	}

	void generate_alias_statement(AliasStatement as) {
		throw new NotImplementedException();
	}

	void generate_case_conditional(CaseConditional cc) {
		int y = 2;
		generateFunctions.LOG.err("Skip CaseConditional for now");
//			throw new NotImplementedException();
	}

	public void generate_construct_statement(@NotNull ConstructStatement aConstructStatement, @NotNull BaseEvaFunction gf, @NotNull Context cctx) {
		final IExpression    left = aConstructStatement.getExpr(); // TODO need type of this expr, not expr!!
		final ExpressionList args = aConstructStatement.getArgs();
		//
		InstructionArgument expression_num = generateFunctions.simplify_expression(left, gf, cctx);
		if (expression_num == null) {
			expression_num = gf.get_assignment_path(left, generateFunctions, cctx);
		}
		final int                                i      = generateFunctions.addProcTableEntry(left, expression_num, generateFunctions.get_args_types(args, gf, cctx), gf);
		final @NotNull List<InstructionArgument> l      = new ArrayList<InstructionArgument>();
		final @NotNull ProcIA                    procIA = new ProcIA(i, gf);
		l.add(procIA);
		final @NotNull List<InstructionArgument> args1 = generateFunctions.simplify_args(args, gf, cctx);
		l.addAll(args1);
		final int instruction_number = generateFunctions.add_i(gf, InstructionName.CONSTRUCT, l, cctx);
	}

	void generate_if(@NotNull final IfConditional ifc, final @NotNull BaseEvaFunction gf) {
		final Context                  cctx         = ifc.getContext();
		final @NotNull IdentExpression Boolean_true = Helpers.string_to_ident("true");
		final @NotNull Label           label_next   = gf.addLabel();
		final @NotNull Label           label_end    = gf.addLabel();
		{
			final int                 begin0 = generateFunctions.add_i(gf, InstructionName.ES, null, cctx);
			final IExpression         expr   = ifc.getExpr();
			final InstructionArgument i      = generateFunctions.simplify_expression(expr, gf, cctx);
//				LOG.info("711 " + i);
			final int const_true = generateFunctions.addConstantTableEntry("true", Boolean_true, new OS_BuiltinType(BuiltInTypes.Boolean), gf);
			generateFunctions.add_i(gf, InstructionName.JNE, List_of(i, new ConstTableIA(const_true, gf), label_next), cctx);
			final int begin_1st = generateFunctions.add_i(gf, InstructionName.ES, null, cctx);
			final int begin_2nd = generateFunctions.add_i(gf, InstructionName.ES, null, cctx);
			for (final OS_Element item : ifc.getItems()) {
				generateFunctions.generate_item(item, gf, cctx);
			}
			generateFunctions.add_i(gf, InstructionName.XS, List_of(new IntegerIA(begin_2nd, gf)), cctx);
			if (ifc.getParts().size() == 0) {
				gf.place(label_next);
				generateFunctions.add_i(gf, InstructionName.XS, List_of(new IntegerIA(begin_1st, gf)), cctx);
//					gf.place(label_end);
			} else {
				generateFunctions.add_i(gf, InstructionName.JMP, List_of(label_end), cctx);
				final List<IfConditional> parts = ifc.getParts();
				for (final @NotNull IfConditional part : parts) {
					gf.place(label_next);
//						label_next = gf.addLabel();
					if (part.getExpr() != null) {
						final InstructionArgument ii = generateFunctions.simplify_expression(part.getExpr(), gf, cctx);
						generateFunctions.LOG.info("712 " + ii);
						generateFunctions.add_i(gf, InstructionName.JNE, List_of(ii, new ConstTableIA(const_true, gf), label_next), cctx);
					}
					final int begin_next = generateFunctions.add_i(gf, InstructionName.ES, null, cctx);
					for (final OS_Element partItem : part.getItems()) {
						generateFunctions.LOG.info("709 " + part + " " + partItem);
						generateFunctions.generate_item(partItem, gf, cctx);
					}
					generateFunctions.add_i(gf, InstructionName.XS, List_of(new IntegerIA(begin_next, gf)), cctx);
					gf.place(label_next);
				}
				gf.place(label_end);
			}
			generateFunctions.add_i(gf, InstructionName.XS, List_of(new IntegerIA(begin0, gf)), cctx);
		}
	}

	void generate_loop(@NotNull final Loop loop, final @NotNull BaseEvaFunction gf) {
		final Context cctx = loop.getContext();
		final int     e2   = generateFunctions.add_i(gf, InstructionName.ES, null, cctx);
//			LOG.info("702 "+loop.getType());
		switch (loop.getType()) {
		case FROM_TO_TYPE:
			generate_loop_FROM_TO_TYPE(loop, gf, cctx);
			break;
		case TO_TYPE:
			break;
		case EXPR_TYPE:
			generate_loop_EXPR_TYPE(loop, gf, cctx);
			break;
		case ITER_TYPE:
			break;
		case WHILE:
			break;
		case DO_WHILE:
			break;
		}
		final int            x2 = generateFunctions.add_i(gf, InstructionName.XS, List_of(new IntegerIA(e2, gf)), cctx);
		final @NotNull Range r  = new Range(e2, x2);
		gf.addContext(loop.getContext(), r);
	}

	private void generate_loop_FROM_TO_TYPE(@NotNull Loop loop, @NotNull BaseEvaFunction gf, @NotNull Context cctx) {
		final IdentExpression iterNameToken = loop.getIterNameToken();
		final String          iterName      = iterNameToken.getText();
		final int             iter_temp     = generateFunctions.addTempTableEntry(null, iterNameToken, gf, iterNameToken); // TODO deduce later
		generateFunctions.add_i(gf, InstructionName.DECL, List_of(new SymbolIA("tmp"), new IntegerIA(iter_temp, gf)), cctx);
		final InstructionArgument ia1 = generateFunctions.simplify_expression(loop.getFromPart(), gf, cctx);
		if (ia1 instanceof ConstTableIA)
			generateFunctions.add_i(gf, InstructionName.AGNK, List_of(new IntegerIA(iter_temp, gf), ia1), cctx);
		else
			generateFunctions.add_i(gf, InstructionName.AGN, List_of(new IntegerIA(iter_temp, gf), ia1), cctx);
		final @NotNull Label label_top = gf.addLabel("top", true);
		gf.place(label_top);
		final @NotNull Label label_bottom = gf.addLabel("bottom" + label_top.getIndex(), false);
		generateFunctions.add_i(gf, InstructionName.JE, List_of(new IntegerIA(iter_temp, gf), generateFunctions.simplify_expression(loop.getToPart(), gf, cctx), label_bottom), cctx);
		for (final StatementItem statementItem : loop.getItems()) {
			generateFunctions.LOG.info("705 " + statementItem);
			generateFunctions.generate_item((OS_Element) statementItem, gf, cctx);
		}
		final @NotNull IdentExpression pre_inc_name = Helpers.string_to_ident("__preinc__");
		final @NotNull TypeTableEntry  tte          = gf.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, null, pre_inc_name);
		final int                      pre_inc      = generateFunctions.addProcTableEntry(pre_inc_name, null, List_of(tte/*getType(left), getType(right)*/), gf);
		generateFunctions.add_i(gf, InstructionName.CALLS, List_of(new ProcIA(pre_inc, gf), new IntegerIA(iter_temp, gf)), cctx);
		generateFunctions.add_i(gf, InstructionName.JMP, List_of(label_top), cctx);
		gf.place(label_bottom);
	}

	private void generate_loop_EXPR_TYPE(@NotNull Loop loop, @NotNull BaseEvaFunction gf, @NotNull Context cctx) {
		final int loop_iterator = generateFunctions.addTempTableEntry(null, gf); // TODO deduce later
		generateFunctions.add_i(gf, InstructionName.DECL, List_of(new SymbolIA("tmp"), new IntegerIA(loop_iterator, gf)), cctx);
		final int                          i2  = generateFunctions.addConstantTableEntry("", new NumericExpressionImpl(0), new OS_BuiltinType(BuiltInTypes.SystemInteger), gf);
		final @NotNull InstructionArgument ia1 = new ConstTableIA(i2, gf);
//			if (ia1 instanceof ConstTableIA)
		generateFunctions.add_i(gf, InstructionName.AGNK, List_of(new IntegerIA(loop_iterator, gf), ia1), cctx);
//			else
//				add_i(gf, InstructionName.AGN, List_of(new IntegerIA(loop_iterator), ia1), cctx);
		final @NotNull Label label_top = gf.addLabel("top", true);
		gf.place(label_top);
		final @NotNull Label label_bottom = gf.addLabel("bottom" + label_top.getIndex(), false);
		generateFunctions.add_i(gf, InstructionName.JE, List_of(new IntegerIA(loop_iterator, gf), generateFunctions.simplify_expression(loop.getToPart(), gf, cctx), label_bottom), cctx);
		for (final StatementItem statementItem : loop.getItems()) {
			generateFunctions.LOG.info("707 " + statementItem);
			generateFunctions.generate_item((OS_Element) statementItem,
											gf, cctx);
		}
		final @NotNull String          txt          = SpecialFunctions.of(ExpressionKind.INCREMENT);
		final @NotNull IdentExpression pre_inc_name = Helpers.string_to_ident(txt);
		final @NotNull TypeTableEntry  tte          = gf.newTypeTableEntry(TypeTableEntry.Type.TRANSIENT, null, pre_inc_name);
		final int                      pre_inc      = generateFunctions.addProcTableEntry(pre_inc_name, null, List_of(tte), gf);
		generateFunctions.add_i(gf, InstructionName.CALLS, List_of(new ProcIA(pre_inc, gf), new IntegerIA(loop_iterator, gf)), cctx);
		generateFunctions.add_i(gf, InstructionName.JMP, List_of(label_top), cctx);
		gf.place(label_bottom);
	}

	void generate_match_conditional(@NotNull final MatchConditional mc, final @NotNull BaseEvaFunction gf) {
		final int     y    = 2;
		final Context cctx = mc.getParent().getContext(); // TODO MatchConditional.getContext returns NULL!!!
		{
			final IExpression         expr = mc.getExpr();
			final InstructionArgument i    = generateFunctions.simplify_expression(expr, gf, cctx);
//				LOG.info("710 " + i);

			@NotNull Label       label_next = gf.addLabel();
			final @NotNull Label label_end  = gf.addLabel();

			{
				for (final MatchConditional.MC1 part : mc.getParts()) {
					if (part instanceof final MatchConditionalImpl.@NotNull MatchArm_TypeMatch mc1) {
						final TypeName        tn = mc1.getTypeName();
						final IdentExpression id = mc1.getIdent();

						final int begin0 = generateFunctions.add_i(gf, InstructionName.ES, null, cctx);

						final int                   tmp     = generateFunctions.addTempTableEntry(new OS_UserType(tn), id, gf, id); // TODO no context!
						@NotNull VariableTableEntry vte_tmp = gf.getVarTableEntry(tmp);
						final TypeTableEntry        t       = vte_tmp.getType();
						generateFunctions.add_i(gf, InstructionName.IS_A, List_of(i, new IntegerIA(t.getIndex(), gf), /*TODO not*/new LabelIA(label_next)), cctx);
						final Context context = mc1.getContext();

						generateFunctions.add_i(gf, InstructionName.DECL, List_of(new SymbolIA("tmp"), new IntegerIA(tmp, gf)), context);
						final int cast_inst = generateFunctions.add_i(gf, InstructionName.CAST_TO, List_of(new IntegerIA(tmp, gf), new IntegerIA(t.getIndex(), gf), (i)), context);
						vte_tmp.addPotentialType(cast_inst, t); // TODO in the future instructionIndex may be unsigned

						for (final FunctionItem item : mc1.getItems()) {
							generateFunctions.generate_item(item, gf, context);
						}

						generateFunctions.add_i(gf, InstructionName.JMP, List_of(label_end), context);
						generateFunctions.add_i(gf, InstructionName.XS, List_of(new IntegerIA(begin0, gf)), cctx);
						gf.place(label_next);
						label_next = gf.addLabel();
					} else if (part instanceof final MatchConditionalImpl.@NotNull MatchConditionalPart2 mc2) {
						final IExpression id = mc2.getMatchingExpression();

						final int begin0 = generateFunctions.add_i(gf, InstructionName.ES, null, cctx);

						final InstructionArgument i2 = generateFunctions.simplify_expression(id, gf, cctx);
						generateFunctions.add_i(gf, InstructionName.JNE, List_of(i, i2, label_next), cctx);
						final Context context = mc2.getContext();

						for (final FunctionItem item : mc2.getItems()) {
							generateFunctions.generate_item(item, gf, context);
						}

						generateFunctions.add_i(gf, InstructionName.JMP, List_of(label_end), context);
						generateFunctions.add_i(gf, InstructionName.XS, List_of(new IntegerIA(begin0, gf)), cctx);
						gf.place(label_next);
//							label_next = gf.addLabel();
					} else if (part instanceof MatchConditionalImpl.MatchConditionalPart3) {
						generateFunctions.LOG.err("Don't know what this is");
					}
				}
				gf.place(label_next);
				generateFunctions.add_i(gf, InstructionName.NOP, List_of(), cctx);
				gf.place(label_end);
			}
		}
	}

	void generate_statement_wrapper(final StatementWrapper aStatementWrapper,
									@NotNull IExpression x,
									@NotNull ExpressionKind expressionKind,
									@NotNull BaseEvaFunction gf,
									@NotNull Context cctx) {
//			LOG.err("106-1 "+x.getKind()+" "+x);
		if (x.is_simple()) {
//				int i = addTempTableEntry(x.getType(), gf);
			switch (expressionKind) {
			case ASSIGNMENT:
//					LOG.err(String.format("703.2 %s %s", x.getLeft(), ((BasicBinaryExpressionImpl)x).getRight()));
				generateFunctions.generate_item_assignment(aStatementWrapper, x, gf, cctx);
				break;
			case AUG_MULT: {
				generateFunctions.LOG.info(String.format("801.1 %s %s %s", expressionKind, x.getLeft(), ((BasicBinaryExpression) x).getRight()));
//						BasicBinaryExpressionImpl bbe = (BasicBinaryExpressionImpl) x;
//						final IExpression right1 = bbe.getRight();
				final InstructionArgument           left           = generateFunctions.simplify_expression(x.getLeft(), gf, cctx);
				final InstructionArgument           right          = generateFunctions.simplify_expression(((BasicBinaryExpression) x).getRight(), gf, cctx);
				final @NotNull IdentExpression      fn_aug_name    = Helpers.string_to_ident(SpecialFunctions.of(expressionKind));
				final @NotNull List<TypeTableEntry> argument_types = List_of(gf.getVarTableEntry(to_int(left)).getType(), gf.getVarTableEntry(to_int(right)).getType());
//						LOG.info("801.2 "+argument_types);
				final int fn_aug = generateFunctions.addProcTableEntry(fn_aug_name, null, argument_types, gf);
				final int i      = generateFunctions.add_i(gf, InstructionName.CALLS, List_of(new ProcIA(fn_aug, gf), left, right), cctx);
				//
				// SEE IF CALL SHOULD BE DEFERRED
				//
				for (final @NotNull TypeTableEntry argument_type : argument_types) {
					if (argument_type.getAttached() == null) {
						// still dont know the argument types at this point, which creates a problem
						// for resolving functions, so wait until later when more information is available
						if (!gf.deferred_calls.contains(i))
							gf.deferred_calls.add(i);
						break;
					}
				}
			}
			break;
			default:
				throw new NotImplementedException();
			}
		} else {
			switch (expressionKind) {
			case ASSIGNMENT:
//					LOG.err(String.format("803.2 %s %s", x.getLeft(), ((BasicBinaryExpressionImpl)x).getRight()));
				generateFunctions.generate_item_assignment(aStatementWrapper, x, gf, cctx);
				break;
//				case IS_A:
//					break;
			case PROCEDURE_CALL:
				final @NotNull ProcedureCallExpression pce = (ProcedureCallExpression) x;
				generateFunctions.simplify_procedure_call(pce, gf, cctx);
				break;
			case DOT_EXP:
				final @NotNull DotExpression de = (DotExpression) x;
				generateFunctions.generate_item_dot_expression(null, de.getLeft(), de.getRight(), gf, cctx);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + expressionKind);
			}
		}
	}

	void generate_variable_sequence(@NotNull VariableSequence item, @NotNull BaseEvaFunction gf, @NotNull Context cctx) {
		for (final @NotNull VariableStatement vs : item.items()) {
			int state = 0;
//				LOG.info("8004 " + vs);
			final String               variable_name = vs.getName();
			final @NotNull IExpression initialValue  = vs.initialValue();
			//
			if (vs.getTypeModifiers() == TypeModifiers.CONST) {
				if (initialValue.is_simple()) {
					if (initialValue instanceof IdentExpression) {
						state = 4;
					} else {
						state = 1;
					}
				} else {
					state = 2;
				}
			} else {
				state = 3;
			}
//				final OS_Type type = vs.initialValue().getType();
//				final String stype = type == null ? "Unknown" : getTypeString(type);
//				LOG.info("8004-1 " + type);
//				LOG.info(String.format("8004-2 %s %s;", stype, vs.getName()));
			switch (state) {
			case 1: {
				final int                  ci      = generateFunctions.addConstantTableEntry(variable_name, initialValue, initialValue.getType(), gf);
				final int                  vte_num = generateFunctions.addVariableTableEntry(variable_name, gf.newTypeTableEntry(TypeTableEntry.Type.SPECIFIED, (initialValue.getType()), vs.getNameToken()), gf, vs.getNameToken());
				final @NotNull IExpression iv      = initialValue;
				generateFunctions.add_i(gf, InstructionName.DECL, List_of(new SymbolIA("const"), new IntegerIA(vte_num, gf)), cctx);
				generateFunctions.add_i(gf, InstructionName.AGNK, List_of(new IntegerIA(vte_num, gf), new ConstTableIA(ci, gf)), cctx);
				break;
			}
			case 2: {
				final int vte_num = generateFunctions.addVariableTableEntry(variable_name, gf.newTypeTableEntry(TypeTableEntry.Type.SPECIFIED, (initialValue.getType()), vs.getNameToken()), gf, vs);
				generateFunctions.add_i(gf, InstructionName.DECL, List_of(new SymbolIA("val"), new IntegerIA(vte_num, gf)), cctx);
				final @NotNull IExpression iv = initialValue;
				generateFunctions.assign_variable(gf, vte_num, iv, cctx);
				break;
			}
			case 3: {
				final @NotNull TypeTableEntry tte;
				if (initialValue == IExpression.UNASSIGNED && vs.typeName() != null) {
					tte = gf.newTypeTableEntry(TypeTableEntry.Type.SPECIFIED, new OS_UserType(vs.typeName()), vs.getNameToken());
				} else {
					tte = gf.newTypeTableEntry(TypeTableEntry.Type.SPECIFIED, initialValue.getType(), vs.getNameToken());
				}
				final int vte_num = generateFunctions.addVariableTableEntry(variable_name, tte, gf, vs); // TODO why not vs.initialValue ??
				generateFunctions.add_i(gf, InstructionName.DECL, List_of(new SymbolIA("var"), new IntegerIA(vte_num, gf)), cctx);
				final @NotNull IExpression iv = initialValue;
				generateFunctions.assign_variable(gf, vte_num, iv, cctx);
				break;
			}
			case 4: {
				final int vte_num = generateFunctions.addVariableTableEntry(variable_name, gf.newTypeTableEntry(TypeTableEntry.Type.SPECIFIED, (initialValue.getType()), vs.getNameToken()), gf, vs.getNameToken());
				generateFunctions.add_i(gf, InstructionName.DECL, List_of(new SymbolIA("const"), new IntegerIA(vte_num, gf)), cctx);
				generateFunctions.assign_variable(gf, vte_num, initialValue, cctx);
				break;
			}
			default:
				throw new IllegalStateException();
			}
		}
	}
}
