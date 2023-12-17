/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;
import tripleo.elijah.EventualRegister;
import tripleo.elijah.lang.LangGlobals;
import tripleo.elijah.lang.i.ConstructorDef;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_ProcTableEntry;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.work.WorkList;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * Created 1/21/21 9:04 PM
 */
public class FunctionInvocation implements IInvocation {
	public final      ProcTableEntry                              pte;
	public            CI_Hint                                     hint;
	private @Nullable BaseEvaFunction                             _generated       = null;
	final             FunctionDef                                 fd;
	private final     DeferredObject<BaseEvaFunction, Void, Void> generateDeferred = new DeferredObject<BaseEvaFunction, Void, Void>();
	private           NamespaceInvocation                         namespaceInvocation;
	private           ClassInvocation                             classInvocation;

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

	public @NotNull DeferredObject<BaseEvaFunction, Void, Void> generateDeferred() {
		return generateDeferred;
	}

	public WlGenerateFunction generateFunction(final DeduceTypes2 aDeduceTypes2, final OS_Element aBest) {
		throw new IllegalStateException("Error");
	}

	public Promise<BaseEvaFunction, Void, Void> generatePromise() {
		return generateDeferred.promise();
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

	public NamespaceInvocation getNamespaceInvocation() {
		return namespaceInvocation;
	}

	public void setGenerated(BaseEvaFunction aGeneratedFunction) {
		_generated = aGeneratedFunction;
	}

	public void setNamespaceInvocation(NamespaceInvocation aNamespaceInvocation) {
		namespaceInvocation = aNamespaceInvocation;
	}

	@Override
	public void setForFunctionInvocation(final FunctionInvocation aFunctionInvocation) {
		throw new IllegalStateException("maybe this shouldn't be done?");
	}

	public Eventual<BaseEvaFunction> makeGenerated__Eventual(final @NotNull Deduce_CreationClosure cl, final EventualRegister register) {
		final DeduceTypes2          deduceTypes2  = cl.deduceTypes2();

		final Eventual<BaseEvaFunction> eef = new Eventual<>();

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
			eef.resolve(xxx___forDefaultVirtualCtor(cl, injector, module));
			return eef;
		} else if (fd instanceof ConstructorDef cd) {
			eef.resolve(xxxForConstructorDef(cl, cd, injector, module));
			return eef;
		} else {
			eef.resolve(xxx__forFunction(cl, injector, module));
			return eef;
		}

		//{
		//	eef.fail(null);
		//	return eef;
		//}
	}

	@NotNull
	private BaseEvaFunction xxx___forDefaultVirtualCtor(final Deduce_CreationClosure cl,
														final DeduceTypes2.@NotNull DeduceTypes2Injector injector,
														final @NotNull OS_Module module) {
		@NotNull WlGenerateDefaultCtor wlgdc = injector.new_WlGenerateDefaultCtor(module, this, cl);
		wlgdc.run(null);
		BaseEvaFunction gf = wlgdc.getResult();
		return gf;
	}

	@NotNull
	private BaseEvaFunction xxxForConstructorDef(final Deduce_CreationClosure cl,
												 final @NotNull ConstructorDef cd,
												 final DeduceTypes2.@NotNull DeduceTypes2Injector injector,
												 final @NotNull OS_Module module) {
		@NotNull WlGenerateCtor wlgf = injector.new_WlGenerateCtor(module, cd.getNameNode(), this, cl);
		wlgf.run(null);
		BaseEvaFunction gf = wlgf.getResult();
		return gf;
	}

	@NotNull
	private BaseEvaFunction xxx__forFunction(final @NotNull Deduce_CreationClosure cl,
											 final DeduceTypes2.@NotNull DeduceTypes2Injector injector,
											 final @NotNull OS_Module module) {

		final GeneratePhase generatePhase = cl.generatePhase();
		final DeducePhase deducePhase = cl.deducePhase();

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
}

//
//
//
