/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_c;

import com.google.common.base.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.DebugFlags;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.NormalTypeName;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.lang.types.OS_UnitType;
import tripleo.elijah.nextgen.outputstatement.EG_SingleStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.util.Mode;
import tripleo.elijah.stages.deduce.nextgen.DR_Ident;
import tripleo.elijah.stages.deduce.nextgen.DR_Item;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_VariableTableEntry;
import tripleo.elijah.stages.deduce.post_bytecode.GCFM_Diagnostic;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.util.BufferTabbedOutputStream;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.work.WorkList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static tripleo.elijah.stages.deduce.DeduceTypes2.to_int;

/**
 * Created 6/21/21 5:53 AM
 */
public class Generate_Code_For_Method {
	final         BufferTabbedOutputStream tos    = new BufferTabbedOutputStream();
	final         BufferTabbedOutputStream tosHdr = new BufferTabbedOutputStream();
	final         ElLog                    LOG;
	private final GenerateC                gc;
	boolean is_constructor = false, is_unit_type = false;

	public Generate_Code_For_Method(@NotNull final GenerateC aGenerateC, final ElLog aLog) {
		gc  = aGenerateC;
		LOG = aLog; // use log from GenerateC
	}

	void action_invariant(final @NotNull WhyNotGarish_BaseFunction yf, final Generate_Method_Header aGmh) {
		tos.incr_tabs();
		//
		@NotNull final List<Instruction> instructions = yf.instructions();
		for (int i = 0, instructionsSize = instructions.size(); i < instructionsSize; i++) {
			final Instruction instruction = instructions.get(i);
			//			LOG.err("8999 "+instruction);
			final Label label = yf.findLabel(instruction.getIndex());
			if (label != null) {
				tos.put_string_ln_no_tabs(label.getName() + ":");
			}

			switch (instruction.getName()) {
			case E:
				action_E(yf, aGmh);
				break;
			case X:
				action_X(aGmh);
				break;
			case ES:
				action_ES();
				break;
			case XS:
				action_XS();
				break;
			case AGN:
				action_AGN(yf, instruction);
				break;
			case AGNK:
				action_AGNK(yf, instruction);
				break;
			case AGNT:
				break;
			case AGNF:
				break;
			case JE:
				action_JE(yf, instruction);
				break;
			case JNE:
				action_JNE(yf, instruction);
				break;
			case JL:
				action_JL(yf, instruction);
				break;
			case JMP:
				action_JMP(instruction);
				break;
			case CONSTRUCT:
				action_CONSTRUCT(yf, instruction);
				break;
			case CALL:
				action_CALL(yf, instruction);
				break;
			case CALLS:
				action_CALLS(yf, instruction);
				break;
			case RET:
				break;
			case YIELD:
				throw new NotImplementedException();
			case TRY:
				throw new NotImplementedException();
			case PC:
				break;
			case IS_A:
				action_IS_A(instruction, tos, yf);
				break;
			case DECL:
				action_DECL(instruction, tos, yf);
				break;
			case CAST_TO:
				action_CAST(instruction, tos, yf);
				break;
			case NOP:
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + instruction.getName());
			}
		}
		tos.dec_tabs();
		tos.put_string_ln("}");
	}

	private void action_E(final @NotNull WhyNotGarish_BaseFunction gf, final @NotNull Generate_Method_Header aGmh) {
		tos.put_string_ln("bool vsb;");
		int state = 0;

		if (gf.pointsToConstructor())
			state = 2;
		else if (aGmh.tte == null)
			state = 3;
		else if (aGmh.tte.isResolved())
			state = 1;
		else if (aGmh.tte.getAttached() instanceof OS_UnitType)
			state = 4;

		switch (state) {
		case 0:
			tos.put_string_ln("Error_TTE_Not_Resolved " + aGmh.tte);
			break;
		case 1:
			final String ty = gc.getTypeName(aGmh.tte);
			tos.put_string_ln(String.format("%s* vsr;", ty));
			break;
		case 2:
			is_constructor = true;
			break;
		case 3:
			// TODO don't know what this is for now
			// Assuming ctor
			is_constructor = gf.pointsToConstructor2();

			final EvaNode genClass = gf.getGenClass();
			final String ty2 = gc.getTypeNameForGenClass(genClass);

			final String return_type1 = aGmh.__find_return_type(gf, gc.elLog());

			String s;
			if (return_type1 != null) {
				s = String.format("%s vsr;", return_type1);
			} else {
				s = String.format("// *171* %s vsr;", ty2);
			}

			tos.put_string_ln(s);
			break;
		case 4:
			// don't print anything
			is_unit_type = true;
			break;
		}
		tos.put_string_ln("{");
		tos.incr_tabs();
	}

	private void action_X(final @NotNull Generate_Method_Header aGmh) {
		// TODO functions are being marked as constructor when they are not

		if (is_constructor) {
			tos.dec_tabs();
			tos.put_string_ln("}");
			return;
		}

		tos.dec_tabs();
		tos.put_string_ln("}");
		if (!is_unit_type) {
			if (aGmh.tte != null && aGmh.tte.isResolved()) {
				tos.put_string_ln("return vsr;");
			}
		}
	}

	private void action_ES() {
		tos.put_string_ln("{");
		tos.incr_tabs();
	}

	private void action_XS() {
		tos.dec_tabs();
		tos.put_string_ln("}");
	}

	private void action_AGN(final WhyNotGarish_BaseFunction gf, final @NotNull Instruction aInstruction) {
		GCFM_Inst_AGN inst = new GCFM_Inst_AGN(this, gc, gf, aInstruction);

		final String s = inst.getText();

		tos.put_string_ln(s);
	}

	private void action_AGNK(final WhyNotGarish_BaseFunction gf, final @NotNull Instruction aInstruction) {
		GCFM_Inst_AGNK inst = new GCFM_Inst_AGNK(this, gc, gf, aInstruction);

		final String s = inst.getText();

		tos.put_string_ln(s);
	}

	private void action_JE(final WhyNotGarish_BaseFunction gf, final @NotNull Instruction aInstruction) {
		GCFM_Inst_JE inst = new GCFM_Inst_JE(this, gc, gf, aInstruction);

		final String s = inst.getText();

		tos.put_string_ln(s);
	}

	private void action_JNE(final @NotNull WhyNotGarish_BaseFunction gf, final @NotNull Instruction aInstruction) {
		final InstructionArgument lhs    = aInstruction.getArg(0);
		final InstructionArgument rhs    = aInstruction.getArg(1);
		final InstructionArgument target = aInstruction.getArg(2);

		final Label realTarget = (Label) target;

		final VariableTableEntry vte = gf.getVarTableEntry(((IntegerIA) lhs).getIndex());
		assert rhs != null;

		if (rhs instanceof ConstTableIA) {
			final ConstantTableEntry cte            = gf.getConstTableEntry(((ConstTableIA) rhs).getIndex());
			final String             realTargetName = gc.getRealTargetName(gf, (IntegerIA) lhs, AOG.GET);
			tos.put_string_ln(String.format("vsb = %s != %s;", realTargetName, gc.getAssignmentValue(gf.getSelf(), rhs, gf)));
			tos.put_string_ln(String.format("if (!vsb) goto %s;", realTarget.getName()));
		} else {
			//
			// TODO need to lookup special __ne__ function ??
			//
			final String realTargetName = gc.getRealTargetName(gf, (IntegerIA) lhs, AOG.GET);
			tos.put_string_ln(String.format("vsb = %s != %s;", realTargetName, gc.getAssignmentValue(gf.getSelf(), rhs, gf)));
			tos.put_string_ln(String.format("if (!vsb) goto %s;", realTarget.getName()));

			final int y = 2;
		}
	}

	private void action_JL(final @NotNull WhyNotGarish_BaseFunction gf, final @NotNull Instruction aInstruction) {
		final InstructionArgument lhs    = aInstruction.getArg(0);
		final InstructionArgument rhs    = aInstruction.getArg(1);
		final InstructionArgument target = aInstruction.getArg(2);

		final Label realTarget = (Label) target;

		final VariableTableEntry vte = gf.getVarTableEntry(((IntegerIA) lhs).getIndex());
		assert rhs != null;

		var bt = new BT();

		if (rhs instanceof ConstTableIA) {
			final ConstantTableEntry cte = gf.getConstTableEntry(((ConstTableIA) rhs).getIndex());

			bt.text0("vsb = ");
			bt.text0(gc.getRealTargetName(gf, (IntegerIA) lhs, AOG.GET));
			bt.text0(" < ");
			bt.text0(gc.getAssignmentValue(gf.getSelf(), rhs, gf));
			bt.text(";");

			bt.text0("if (!vsb) goto ");
			bt.text0(realTarget.getName());
			bt.text(";");
		} else {
			//
			// TODO need to lookup special __lt__ function
			//

			bt.text0("vsb = ");
			bt.text0(gc.getRealTargetName(gf, (IntegerIA) lhs, AOG.GET));
			bt.text0(" < ");
			bt.text0(gc.getAssignmentValue(gf.getSelf(), rhs, gf));
			bt.text(";");

			bt.text0("if (!vsb) goto ");
			bt.text0(realTarget.getName());
			bt.text(";");
		}

		tos.put_string(bt.getText());
	}

	private void action_JMP(final @NotNull Instruction aInstruction) {
		final InstructionArgument target = aInstruction.getArg(0);
//		InstructionArgument value  = instruction.getArg(1);

		final Label realTarget = (Label) target;

		var bt = new BT();

		bt.text0("goto ");
		bt.text0(realTarget.getName());
		bt.text(";");

		tos.put_string_ln(bt.getText());
	}

	private void action_CONSTRUCT(final WhyNotGarish_BaseFunction gf, final @NotNull Instruction aInstruction) {
		final InstructionArgument _arg0 = aInstruction.getArg(0);
		assert _arg0 instanceof ProcIA;

		final GI_ProcIA gi_proc = gc.get_repo().itemFor((ProcIA) _arg0);

		final GCX_Construct gcx_construct = new GCX_Construct(gi_proc, aInstruction, gc);

		tos.put_string_ln(gcx_construct.getText());
	}

	private void action_CALL(final WhyNotGarish_BaseFunction gf, final @NotNull Instruction aInstruction) {
		//LOG.err("9000 "+inst.getName());

		final GCX_FunctionCall gcx_fc = new GCX_FunctionCall(gf, gc, aInstruction);

		tos.put_string_ln(gcx_fc.getText());
	}

	private void action_CALLS(final @NotNull WhyNotGarish_BaseFunction gf, final @NotNull Instruction aInstruction) {
		final InstructionArgument x = aInstruction.getArg(0);

		assert x instanceof ProcIA;

		final ProcTableEntry pte = gf.getProcTableEntry(to_int(x));

		final GCX_FunctionCall_Special gcx_fc = new GCX_FunctionCall_Special(pte, gf, gc, aInstruction);

		tos.put_string_ln(gcx_fc.getText());
	}

	private void action_IS_A(final @NotNull Instruction instruction, final @NotNull BufferTabbedOutputStream tos, final @NotNull WhyNotGarish_BaseFunction gf) {
		final IntegerIA testing_var_  = (IntegerIA) instruction.getArg(0);
		final IntegerIA testing_type_ = (IntegerIA) instruction.getArg(1);
		final Label     target_label  = ((LabelIA) instruction.getArg(2)).label;

		final VariableTableEntry testing_var    = gf.getVarTableEntry(testing_var_.getIndex());
		final TypeTableEntry     testing_type__ = gf.getTypeTableEntry(testing_type_.getIndex());

		final EvaNode testing_type = testing_type__.resolved();
		final int     z            = ((EvaContainerNC) testing_type).getCode();

		var bt = new BT();

		bt.text0("vsb = ");

		//  ZS%d_is_a(%s
		bt.text0("ZS");
		bt.text0("" + z);
		bt.text0("_is_a");

		bt.text("(");
		bt.text0(gc.getRealTargetName(gf, testing_var_, AOG.GET));
		bt.text(");");

		bt.text0("if (!vsb) goto ");
		bt.text0(target_label.getName());
		bt.text(";");

		tos.put_string_ln(bt.getText());
	}

	private void action_DECL(final @NotNull Instruction instruction, final @NotNull BufferTabbedOutputStream tos, final @NotNull WhyNotGarish_BaseFunction gf) {
		final Operation2<EG_Statement> op = _action_DECL(instruction, gf);

		if (op.mode() == Mode.SUCCESS) {
			tos.put_string_ln(op.success().getText());
		} else {
			//throw new
			// ignore
		}
	}

	private void action_CAST(final @NotNull Instruction instruction, final @NotNull BufferTabbedOutputStream tos, final @NotNull WhyNotGarish_BaseFunction gf) {
		final IntegerIA      vte_num_     = (IntegerIA) instruction.getArg(0);
		final IntegerIA      vte_type_    = (IntegerIA) instruction.getArg(1);
		final IntegerIA      vte_targ_    = (IntegerIA) instruction.getArg(2);
		final String         target_name  = gc.getRealTargetName(gf, vte_num_, AOG.GET);
		final TypeTableEntry target_type_ = gf.getTypeTableEntry(vte_type_.getIndex());
//		final String target_type = gc.getTypeName(target_type_.getAttached());
		final String target_type   = gc.getTypeName(target_type_.genType.getNode());
		final String source_target = gc.getRealTargetName(gf, vte_targ_, AOG.GET);

		tos.put_string_ln(String.format("%s = (%s)%s;", target_name, target_type, source_target));
	}

	private @NotNull Operation2<EG_Statement> _action_DECL(final @NotNull Instruction instruction, final @NotNull WhyNotGarish_BaseFunction gf) {
		final SymbolIA  decl_type = (SymbolIA) instruction.getArg(0);
		final IntegerIA vte_num   = (IntegerIA) instruction.getArg(1);

		final GCR_VTE_Target target = new GCR_VTE_Target(gf, vte_num);
		target.feed(AOG.GET, gc);

		final String target_name = target.target_name;

		final VariableTableEntry vte = vte_num.getEntry();

		tripleo.elijah.stages.deduce.DeduceTypes2 dt2 = null;
		BaseEvaFunction                           gf1 = null;
		boolean                                   qqq = false;
		{
			List<DR_Item> x = gf.getGf().drs;

			if (x.size() > 0) {
				if (x.get(0) instanceof DR_Ident ident) {
					dt2 = ident.identTableEntry()._deduceTypes2();
					gf1 = ident.identTableEntry().__gf;

					qqq = true;
				}
			}
		}

		final DeduceElement3_VariableTableEntry de_vte;

		if (qqq)
			de_vte = vte.getDeduceElement3(dt2, gf1);
		else
			de_vte = vte.getDeduceElement3();

		final Operation2<OS_Type>               diag1  = de_vte.decl_test_001(gf.cheat());

		if (diag1.mode() == Mode.FAILURE) {
			final Diagnostic      diag_ = diag1.failure();
			final GCFM_Diagnostic diag  = (GCFM_Diagnostic) diag_;

			switch (diag.severity()) {
			case INFO:
				LOG.info(diag._message());
				break;
			case ERROR:
				LOG.err(diag._message());
				break;
			case LINT:
			case WARN:
			default:
				throw new NotImplementedException();
			}

			return Operation2.failure(diag_);
		}

		final EvaNode res = vte.resolvedType();
		if (res instanceof EvaClass) {
			final String z = GenerateC.GetTypeName.forGenClass((EvaClass) res);
			final String s = String.format("%s* %s;", z, target_name);
			return Operation2.success(new EG_SingleStatement(s, EX_Explanation.withMessage("actionDECL with resolved type")));
		}

		final OS_Type x = diag1.success(); //vte.type.getAttached();

		if (x != null) {
			switch (x.getType()) {
			case USER_CLASS:
				final String z = GenerateC.GetTypeName.forOSType(x, LOG);
				final String s = String.format("%s* %s;", z, target_name);
				return Operation2.success(new EG_SingleStatement(s, EX_Explanation.withMessage("actionDECL with USER_CLASS")));
			case USER:
				final TypeName typeName = x.getTypeName();
				if (typeName instanceof NormalTypeName) {
					final String z2;
					if (((NormalTypeName) typeName).getName().equals("Any"))
						z2 = "void *";  // TODO Technically this is wrong
					else
						z2 = GenerateC.GetTypeName.forTypeName(typeName, gc._errSink());
					final String s1 = String.format("%s %s;", z2, target_name);
					return Operation2.success(new EG_SingleStatement(s1, EX_Explanation.withMessage("actionDECL with USER")));
				}

				if (typeName != null) {
					//
					// VARIABLE WASN'T FULLY DEDUCED YET
					//
					return Operation2.failure(new Diagnostic_8887(typeName));
				}
				break;
			case BUILT_IN:
				return Operation2.success(new actionDECL_with_BUILT_IN(gf, target_name, x, gc));
			case FUNC_EXPR:
				return Operation2.success(new EG_Statement() {
					@Override
					public @NotNull EX_Explanation getExplanation() {
						return EX_Explanation.withMessage("gcfm:type:func_decl");
					}

					@Override
					public @NotNull String getText() {
						return "void (*fun)()";
					}
				});
			}
		}

		//
		// VARIABLE WASN'T FULLY DEDUCED YET
		// MTL A TEMP VARIABLE
		//
		@NotNull final Collection<TypeTableEntry> pt_ = vte.potentialTypes();
		final List<TypeTableEntry>                pt  = new ArrayList<TypeTableEntry>(pt_);
		if (pt.size() == 1) {
			final TypeTableEntry ty = pt.get(0);
			if (ty.genType.getNode() != null) {
				final EvaNode node1 = ty.genType.getNode();
				if (node1 instanceof EvaFunction ef) {
					var node = gc.a_lookup(ef);

					final var y = node;
//					((EvaFunction)node).typeDeferred()
					// get signature
					final String z = /*Emit.emit("/ *552* /") + */ "bool (*%s)(char*)".formatted(target_name);
					final String s = String.format("/*8889*/%s;", z);
					return Operation2.success(new EG_SingleStatement(s, null));
				}
			} else {
//				LOG.err("8885 " +ty.attached);
				final @Nullable OS_Type attached = ty.getAttached();
				final String            z;
				if (attached != null)
					z = gc.getTypeName(attached);
				else
					z = Emit.emit("/*763*/") + "Unknown";
				final String s = String.format("/*8890*/Z<%s> %s;", z, target_name);
				return Operation2.success(new EG_SingleStatement(s, null));
			}
		}

		return Operation2.failure(new Diagnostic_8886());
	}

	void generateCodeForConstructor(final @NotNull EvaConstructor gf,
									final GenerateResult gr__,
									final WorkList aWorkList__,
									final @NotNull GenerateResultEnv fileGen
								   ) {

		var gr        = fileGen.gr();
		var aWorkList = fileGen.wl();

		var yf = gc.a_lookup(gf);

		final C2C_CodeForConstructor cfm = new C2C_CodeForConstructor(this, gf, fileGen, yf);

		//cfm.calculate();
		var rs = cfm.getResults();

//		GenerateResult gr = cfm.getGenerateResult();

		final GCFC gcfc = new GCFC(rs, gf, gr);

		gf.reactive().add(gcfc);

		if (!DebugFlags.GCFM_MANUAL_DISABLED) {
			gcfc.respondTo(this.gc);
		}
	}

	void generateCodeForMethod(final @NotNull BaseEvaFunction gf, final @NotNull GenerateResultEnv aFileGen) {
		// TODO separate into method and method_header??
		C2C_CodeForMethod cfm = new C2C_CodeForMethod(this, gf, aFileGen);

		//cfm.calculate();
		var rs = cfm.getResults();

		GenerateResult gr = cfm.getGenerateResult();

		final GCFM gcfm = new GCFM(rs, gf, gr);

		gf.reactive().add(gcfm);

		if (!DebugFlags.GCFM_MANUAL_DISABLED) {
			gcfm.respondTo(this.gc);
		}

		// FIXME 06/17
		final GenerateResultSink sink = aFileGen.resultSink();

		if (sink != null)
			sink.addFunction(gf, rs, gc);
		else
			System.err.println("sink failed");
	}

	public GenerateC _gc() {
		return gc;
	}

	public class GCR_VTE_Target implements EG_Statement {
		private final WhyNotGarish_BaseFunction gf;
		private final IntegerIA                 vteNum;
		String target_name;

		public GCR_VTE_Target(final WhyNotGarish_BaseFunction aGf, final IntegerIA aVteNum) {
			gf     = aGf;
			vteNum = aVteNum;
		}

		@Override
		public @NotNull EX_Explanation getExplanation() {
			return EX_Explanation.withMessage("GCR_VTE_Target");
		}

		@Override
		public @Nullable String getText() {
			return null;
		}

		public void feed(final AOG aAOG, final @NotNull GenerateC gc) {
			target_name = gc.getRealTargetName(gf, vteNum, aAOG);
		}
	}

	class BT {
		@NotNull BufferTabbedOutputStream btos = new BufferTabbedOutputStream();

		public String getText() {
			return btos.getBuffer().getText();
		}

		public void text0(String s) {
			btos.put_string(s);
		}

		public void text(String s) {
			btos.put_string_ln(s);
		}

		public void text2(@NotNull Supplier<String> s) {
			btos.put_string_ln(s.get());
		}
	}

	public enum AOG {
		ASSIGN, GET
	}

	interface C2C_Results {
		List<C2C_Result> getResults();
	}

}

//
//
//
