package tripleo.elijah_durable_congenial.stages.gen_c;

import org.apache.commons.lang3.tuple.Pair;
import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah_durable_congenial.comp.Finally;
import tripleo.elijah_durable_congenial.comp.diagnostic.ExceptionDiagnostic;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.diagnostic.Diagnostic.Severity;
import tripleo.elijah_durable_congenial.lang.i.IdentExpression;
import tripleo.elijah.nextgen.outputstatement.EG_SingleStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah_durable_congenial.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.DeduceElement3_ProcTableEntry;
import tripleo.elijah_durable_congenial.stages.instructions.IdentIA;
import tripleo.elijah_durable_congenial.stages.instructions.Instruction;
import tripleo.elijah_durable_congenial.stages.instructions.InstructionArgument;
import tripleo.elijah_durable_congenial.stages.instructions.ProcIA;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah_durable_congenial.stages.gen_fn.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Consumer;

public class GI_ProcIA implements GenerateC_Item {
	private final          GenerateC      gc;
	private final @NotNull ProcTableEntry pte;
	private                EvaNode        _evaNode;
	private final          ProcIA         carrier;

	public GI_ProcIA(final ProcIA aProcIA, final GenerateC aGenerateC) {
		carrier  = aProcIA;
		this.gc  = aGenerateC;
		this.pte = carrier.getEntry();
	}

	public @NotNull Operation2<EG_Statement> action_CONSTRUCT(@NotNull Instruction aInstruction, @NotNull GenerateC gc) {
		final ProcTableEntry       pte = carrier.getEntry();
		final List<TypeTableEntry> x   = pte.getArgs();
		final int                  y   = aInstruction.getArgsSize();
//		InstructionArgument z = instruction.getArg(1);


		final ClassInvocation clsinv = pte.getClassInvocation();
		if (pte.__debug_expression instanceof IdentExpression ie)
			if (ie.getText().equals("f"))
				return Operation2.failure(new ExceptionDiagnostic(new Exception("pte.expression is f")));

		if (clsinv != null) {
			final InstructionArgument target = pte.expression_num;
//			final InstructionArgument value  = instruction;

			if (target instanceof IdentIA) {
				// how to tell between named ctors and just a path?
				@NotNull final IdentTableEntry target2 = ((IdentIA) target).getEntry();
				final String                   str     = target2.getIdent().getText();

				System.out.println("130  " + str);
			}

			final String s = MessageFormat.format("{0}{1};", Emit.emit("/*500*/"), getAssignmentValue(aInstruction, gc));

			return Operation2.success(new EG_SingleStatement(s, EX_Explanation.withMessage("aaa")));
		}

		return Operation2.failure(Diagnostic.withMessage("12900", "no construct possible for GI_Proc", Severity.INFO));
	}

	public String getAssignmentValue(final @NotNull Instruction aInstruction, final @NotNull GenerateC gc) {
		final BaseEvaFunction gf     = carrier.generatedFunction();
		final ClassInvocation clsinv = carrier.getEntry().getClassInvocation();

		//return gc.getAssignmentValue(gf.getSelf(), aInstruction, clsinv, gf);

		GenerateC.GetAssignmentValue gav = /*gc.*/new GenerateC.GetAssignmentValue(gc);
		if (false) {
			return gav.forClassInvocation(aInstruction, clsinv, gf, gc._LOG());
		}

		InstructionArgument     _arg0 = aInstruction.getArg(0);
		@NotNull ProcTableEntry pte   = carrier.getEntry();

		final CtorReference reference = new CtorReference();
		reference.getConstructorPath(pte.expression_num, gf);
		@NotNull List<String> x = gav.getAssignmentValueArgs(aInstruction, gf, gc.elLog()).stringList();
		reference.args(x);
		final String build = reference.build(clsinv);
		return build;
	}

	@Override
	public EvaNode getEvaNode() {
		return _evaNode;
	}

	public void getIdentIAPath(final @NotNull Consumer<Pair<String, CReference.Ref>> addRef) {
		getIdentIAPath_Proc(carrier.getEntry(), addRef);
	}

	public void getIdentIAPath_Proc(final @NotNull ProcTableEntry pte, final @NotNull Consumer<Pair<String, CReference.Ref>> addRef) {
		final String[]           text = new String[1];
		final FunctionInvocation fi   = pte.getFunctionInvocation();

		if (fi == null) {
			SimplePrintLoggerToRemoveSoon.println_err_2("7777777777777777 fi getIdentIAPath_Proc " + pte);

			return;
		}

		/*final*/
		final BaseEvaFunction[]             generated = {fi.getGenerated()};
		final DeduceElement3_ProcTableEntry de_pte    = (DeduceElement3_ProcTableEntry) pte.getDeduceElement3();

		if (generated[0] == null) {
			var c = gc._ce().getCompilation();
			if (c.reports().outputOn(Finally.Outs.Out_120)) {
				System.err.println("6464 " + fi.getPte());
			}

			final WlGenerateCtor wlgf = new WlGenerateCtor(de_pte.deduceTypes2().getGenerateFunctions(de_pte.getPrincipal().getContext().module()),
														   fi,
														   null,
														   de_pte.deduceTypes2().getPhase().codeRegistrar);
			wlgf.run(null);
			wlgf.getResultPromise().then(new DoneCallback<EvaConstructor>() {
				@Override
				public void onDone(final EvaConstructor result) {
					generated[0] = result;
					if (generated[0] instanceof EvaConstructor ec) {
						final WhyNotGarish_Constructor yf = gc.a_lookup(ec);

						final String constructorNameText = yf.getConstructorNameText();

						yf.pt_onGenClass(genClass -> {
							text[0] = String.format("ZC%d%s", genClass.getCode(), constructorNameText);
							addRef.accept(Pair.of(text[0], CReference.Ref.CONSTRUCTOR));
						});
					} else {
						final WhyNotGarish_Function yf     = gc.a_lookup(generated[0]);

						final IdentExpression functionName = yf.pt_getNameNode();
						generated[0].onGenClass(genClass -> {
							text[0] = String.format("z%d%s", genClass.getCode(), functionName.getText());
							addRef.accept(Pair.of(text[0], CReference.Ref.FUNCTION));
						});
					}
				}
			});

			for (IdentTableEntry identTableEntry : generated[0].idte_list) {
				identTableEntry._fix_table(de_pte.deduceTypes2(), de_pte.generatedFunction());
			}
		}
	}

	@Override
	public void setEvaNode(final EvaNode a_evaNaode) {
		_evaNode = a_evaNaode;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
