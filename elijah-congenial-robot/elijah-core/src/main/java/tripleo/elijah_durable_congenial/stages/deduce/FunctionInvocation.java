/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.stages.deduce;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.util.EventualRegister;
import tripleo.elijah.work.WorkList;
import tripleo.elijah_durable_congenial.lang.LangGlobals;
import tripleo.elijah_durable_congenial.lang.i.ConstructorDef;
import tripleo.elijah_durable_congenial.lang.i.FunctionDef;
import tripleo.elijah_durable_congenial.lang.i.OS_Element;
import tripleo.elijah_durable_congenial.lang.i.OS_Module;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.DeduceElement3_ProcTableEntry;
import tripleo.elijah_durable_congenial.stages.gen_fn.*;

import java.util.List;

import static tripleo.elijah_durable_congenial.util.Helpers.List_of;

/**
 * Created 1/21/21 9:04 PM
 */
public class FunctionInvocation implements IInvocation {
	@Getter
	private final     ProcTableEntry            pte;
	@Getter
	private final     FunctionDef               fd;
	private final     Eventual<BaseEvaFunction> generateDeferred = new Eventual<>();
	@Getter
	private           CI_Hint                   hint;
	private @Nullable BaseEvaFunction           _generated       = null;
	private           NamespaceInvocation       namespaceInvocation;
	private           ClassInvocation           classInvocation;

	public FunctionInvocation(FunctionDef aFunctionDef, ProcTableEntry aProcTableEntry, @NotNull IInvocation invocation, GeneratePhase phase) {
		this.fd  = aFunctionDef;
		this.pte = aProcTableEntry;
		assert invocation != null;
		invocation.setForFunctionInvocation(this);
//		setPhase(deducePhase);
	}

/*
	public void setPhase(final GeneratePhase generatePhase) {
		if (pte != null)
			pte.completeDeferred().then(new DoneCallback<ProcTableEntry>() {
				@Override
				public void onDone(ProcTableEntry result) {
					makeGenerated(generatePhase, null);
				}
			});
		else
			makeGenerated(generatePhase, null);
	}
*/

	public @NotNull Eventual<BaseEvaFunction> generateDeferred() {
		return generateDeferred;
	}

	public WlGenerateFunction generateFunction(final DeduceTypes2 aDeduceTypes2, final OS_Element aBest) {
		throw new IllegalStateException("Error");
	}

	public Eventual<BaseEvaFunction> generatePromise() {
		return generateDeferred;
	}

	public List<TypeTableEntry> getArgs() {
		if (pte == null)
			return List_of();
		return pte.args;
	}

	public ClassInvocation getClassInvocation() {
		return classInvocation;
	}

	public void setClassInvocation(@NotNull ClassInvocation aClassInvocation) {
		classInvocation = aClassInvocation;
	}

	public @Nullable BaseEvaFunction getEva() {
		return null; // TODO 04/15
	}

	public FunctionDef getFunction() {
		return fd;
	}

	public @Nullable BaseEvaFunction getGenerated() {
		return _generated;
	}

	public void setGenerated(BaseEvaFunction aGeneratedFunction) {
		_generated = aGeneratedFunction;
	}

	public NamespaceInvocation getNamespaceInvocation() {
		return namespaceInvocation;
	}

	public void setNamespaceInvocation(NamespaceInvocation aNamespaceInvocation) {
		namespaceInvocation = aNamespaceInvocation;
	}

	@Override
	public void setForFunctionInvocation(final FunctionInvocation aFunctionInvocation) {
		throw new IllegalStateException("maybe this shouldn't be done?");
	}

	@Override
	public String asString() {
		var sb = new StringBuilder();
		sb.append("[FunctionInvocation ");
		sb.append(fd.name().asString());
		sb.append(" ");
		if (_generated != null)
			sb.append(_generated.identityString());
		else sb.append("null");
		sb.append("]");
		return sb.toString();
	}

	public Eventual<BaseEvaFunction> makeGenerated__Eventual(final @NotNull Deduce_CreationClosure cl, final EventualRegister register) {
		final DeduceTypes2              deduceTypes2 = cl.deduceTypes2();
		final Eventual<BaseEvaFunction> eef          = new Eventual<>();

		if (register != null) {
			eef.register(register);
		}

		@Nullable OS_Module module = null;
		if (fd != null && fd.getContext() != null)
			module = fd.getContext().module();
		if (module == null)
			module = classInvocation.getKlass().getContext().module(); // README for constructors

		final DeduceElement3_ProcTableEntry.__LFOE_Q q        = new DeduceElement3_ProcTableEntry.__LFOE_Q(null, new WorkList(), deduceTypes2);
		final DeduceTypes2.DeduceTypes2Injector      injector = deduceTypes2._inj();

		if (fd == LangGlobals.defaultVirtualCtor) {
			xxx___forDefaultVirtualCtor(cl, injector, module).then(eef::resolve);
			return eef;
		} else if (fd instanceof ConstructorDef cd) {
			xxxForConstructorDef(cl, cd, injector, module).then(eef::resolve);
			return eef;
		} else {
			eef.resolve(xxx__forFunction(cl, injector, module));
			return eef;
		}
	}

	private Eventual<BaseEvaFunction> xxx___forDefaultVirtualCtor(final Deduce_CreationClosure cl,
																  final DeduceTypes2.@NotNull DeduceTypes2Injector injector,
																  final @NotNull OS_Module module) {
		@NotNull WlGenerateDefaultCtor wlgdc = injector.new_WlGenerateDefaultCtor(module, this, cl);
		wlgdc.run(null);
		return wlgdc.getResultPromise();
	}

	private Eventual<EvaConstructor> xxxForConstructorDef(final Deduce_CreationClosure cl,
														  final @NotNull ConstructorDef cd,
														  final DeduceTypes2.@NotNull DeduceTypes2Injector injector,
														  final @NotNull OS_Module module) {
		@NotNull WlGenerateCtor wlgc = injector.new_WlGenerateCtor(module, cd.getNameNode(), this, cl);
		wlgc.run(null);
		return wlgc.getResultPromise();
	}

	@NotNull
	private BaseEvaFunction xxx__forFunction(final @NotNull Deduce_CreationClosure cl,
											 final DeduceTypes2.@NotNull DeduceTypes2Injector injector,
											 final @NotNull OS_Module module) {

		final GeneratePhase generatePhase = cl.generatePhase();
		final DeducePhase   deducePhase   = cl.deducePhase();

		@NotNull WlGenerateFunction wlgf = injector.new_WlGenerateFunction(module, this, cl);

		wlgf.run(null);

		EvaFunction gf = wlgf.getResult();

		if (gf.getGenClass() == null) {
			if (namespaceInvocation != null) {
				//namespaceInvocation = deducePhase.registerNamespaceInvocation(namespaceInvocation.getNamespace());

				@NotNull WlGenerateNamespace wlgn = injector.new_WlGenerateNamespace(generatePhase.getGenerateFunctions(module),
																					 namespaceInvocation,
																					 deducePhase.generatedClasses,
																					 deducePhase.codeRegistrar);
				wlgn.run(null);
				int y = 2;
			}
		}

		return gf;
	}

	@Override
	public String toString() {
		return asString();
	}

	public void setHint(CI_Hint aHint) {
		hint = aHint;
	}
}
