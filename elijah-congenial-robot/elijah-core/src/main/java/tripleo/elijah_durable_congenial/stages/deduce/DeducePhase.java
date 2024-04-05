package tripleo.elijah_durable_congenial.stages.deduce;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.tuple.Pair;
import org.jdeferred2.DoneCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.nextgen.reactive.ReactiveDimension;
import tripleo.elijah.stages.deduce.declarations.DeferredMember;
import tripleo.elijah.stateful._RegistrationTarget;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.util.EventualRegister;
import tripleo.elijah.util.Maybe;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.work.WorkManager;
import tripleo.elijah_congenial_durable.deduce2.*;
import tripleo.elijah_durable_congenial.comp.i.Compilation;
import tripleo.elijah_durable_congenial.comp.i.CompilationEnclosure;
import tripleo.elijah_durable_congenial.comp.i.ICompilationAccess;
import tripleo.elijah_durable_congenial.comp.i.IPipelineAccess;
import tripleo.elijah_durable_congenial.lang.i.*;
import tripleo.elijah_durable_congenial.lang.types.OS_UnknownType;
import tripleo.elijah_durable_congenial.nextgen.ClassDefinition;
import tripleo.elijah_durable_congenial.nextgen.rosetta.DeducePhase.DeducePhase_deduceModule_Request;
import tripleo.elijah_durable_congenial.nextgen.rosetta.DeduceTypes2.DeduceTypes2Rosetta;
import tripleo.elijah_durable_congenial.nextgen.rosetta.DeduceTypes2.DeduceTypes2_deduceFunctions_Request;
import tripleo.elijah_durable_congenial.nextgen.rosetta.Rosetta;
import tripleo.elijah_durable_congenial.sanaa.ElijahInternal;
import tripleo.elijah_durable_congenial.stages.deduce.declarations.DeferredMemberFunction;
import tripleo.elijah_durable_congenial.stages.deduce.nextgen.DR_Ident;
import tripleo.elijah_durable_congenial.stages.deduce.nextgen.DR_Item;
import tripleo.elijah_durable_congenial.stages.deduce.nextgen.DR_ProcCall;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah_durable_congenial.stages.deduce.post_bytecode.DeduceElement3_VariableTableEntry;
import tripleo.elijah_durable_congenial.stages.deduce_c.ResolvedVariables;
import tripleo.elijah_durable_congenial.stages.gen_fn.*;
import tripleo.elijah_durable_congenial.stages.gen_fn_r.RegisterClassInvocation_env;
import tripleo.elijah_durable_congenial.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah_durable_congenial.stages.logging.ElLog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static tripleo.elijah_durable_congenial.util.Helpers.List_of;

/**
 * Created 12/24/20 3:59 AM
 */
public class DeducePhase extends _RegistrationTarget implements ReactiveDimension, EventualRegister {
	private @NotNull
	final                DeducePhaseInjector __inj = new DeducePhaseInjector();
	private static final String              PHASE = "DeducePhase";


	public final @NotNull  ICodeRegistrar                               codeRegistrar;
	public final @NotNull  GeneratedClasses                             generatedClasses;
	public final @NotNull  GeneratePhase                                generatePhase;
	public final @NotNull  List<IFunctionMapHook>                       functionMapHooks;
	final                  Multimap<OS_Module, Consumer<DeduceTypes2>>  iWantModules;
	private final @NotNull ICompilationAccess                           ca;
	private final          Map<NamespaceStatement, NamespaceInvocation> namespaceInvocationMap;
	private final          ExecutorService                              classGenerator;
	private final          Country1                                     country;
	private final          List<DeferredMemberFunction>                 deferredMemberFunctions = _inj().new_ArrayList__DeferredaMemberFunction();
	private final          List<FoundElement>                           foundElements           = _inj().new_ArrayList__FoundElement();
	private final          Multimap<FunctionDef, EvaFunction>           functionMap             = ArrayListMultimap.create();
	private final          Map<IdentTableEntry, OnType>                 idte_type_callbacks     = _inj().new_HashMap__IdentTableEntry();
	private final @NotNull ElLog                                        LOG;
	private final @NotNull PipelineLogic                                pipelineLogic;
	private final          List<DE3_Active>                             _actives;
	private final @NotNull Multimap<ClassStatement, ClassInvocation>    classInvocationMultimap;
	private final @NotNull List<DeferredMember>                         deferredMembers;
	private final @NotNull Multimap<ClassStatement, OnClass>            onclasses;
	private final @NotNull Multimap<OS_Element, ResolvedVariables>      resolved_variables;
	private final @NotNull DRS                                          drs;
	private final @NotNull WAITS                                        waits;
	public                 IPipelineAccess                              pa;

	public DeducePhase(final @NotNull CompilationEnclosure ace) {
		this(ace.getCompilationAccess(), ace.getPipelineAccess(), ace.getPipelineLogic());
	}

	public DeducePhase(final @NotNull ICompilationAccess aca,
	                   final @NotNull IPipelineAccess pa0,
	                   final @NotNull PipelineLogic aPipelineLogic) {
		// given
		pipelineLogic = aPipelineLogic;
		ca            = aca;
		pa            = pa0;

		// transitive
		generatePhase = pipelineLogic.generatePhase;

		// created
		codeRegistrar = _inj().new_DefaultCodeRegistrar(ca.getCompilation());
		LOG           = _inj().new_ElLog("(DEDUCE_PHASE)", pipelineLogic.getVerbosity(), PHASE);

		// using
		pa.getCompilationEnclosure().getPipelineLogic().addLog(LOG);
		pa.getCompilationEnclosure().addReactiveDimension(this);

		// create more
		generatedClasses = _inj().new_GeneratedClasses(this);

		extracted_ST_registers_for_annotation();
		functionMapHooks        = _inj().new_ArrayList__IFunctionMapHook();
		iWantModules            = ArrayListMultimap.create();
		namespaceInvocationMap  = _inj().new_HashMap__NamespaceInvocationMap();
		classGenerator          = Executors.newCachedThreadPool();
		country                 = _inj().new_Country1(this);
		resolved_variables      = ArrayListMultimap.create();
		onclasses               = ArrayListMultimap.create();
		deferredMembers         = _inj().new_ArrayList__DeferredMember();
		classInvocationMultimap = ArrayListMultimap.create();
		_actives                = _inj().new_ArrayList__DE3_Active();
		drs                     = _inj().new_DRS();
		waits                   = _inj().new_WAITS();
	}

	public @NotNull DeducePhaseInjector _inj() {
		return this.__inj;
	}

	private void extracted_ST_registers_for_annotation() {
		IStateRunnable.ST.register(this);
		DeduceElement3_VariableTableEntry.ST.register(this);
		DeduceElement3_IdentTableEntry.ST.register(this);
	}

	public Compilation _compilation() {
		return ca.getCompilation();
	}

	public void addDeferredMember(final @NotNull DeferredMember aDeferredMember) {
		deferredMembers.add(aDeferredMember);
	}

	public void addFunction(EvaFunction generatedFunction, FunctionDef fd) {
		functionMap.put(fd, generatedFunction);
	}

	public @NotNull ICompilationAccess ca() {
		return ca;
	}

	public ClassInvocation registerClassInvocation(final RegisterClassInvocation_env env) {
		var rci = new RegisterClassInvocation(env.phase());
		return rci.registerClassInvocation(env);
	}

	public ICodeRegistrar getCodeRegistrar() {
		return codeRegistrar;
	}

	public boolean equivalentGenericPart(@NotNull ClassInvocation first, @NotNull ClassInvocation second) {
		final ClassInvocation.CI_GenericPart secondGenericPart1 = second.genericPart();
		final ClassInvocation.CI_GenericPart firstGenericPart1  = first.genericPart();

		if (second.getKlass() == first.getKlass() /*&& secondGenericPart1 == null*/) return true;

		final Map<TypeName, OS_Type> secondGenericPart = secondGenericPart1.getMap();
		final Map<TypeName, OS_Type> firstGenericPart  = firstGenericPart1.getMap();

		int i = secondGenericPart.entrySet().size();
		for (Map.@NotNull Entry<TypeName, OS_Type> entry : secondGenericPart.entrySet()) {
			final OS_Type entry_type = firstGenericPart.get(entry.getKey());
			//assert !(entry_type instanceof OS_UnknownType);

			if (entry_type instanceof OS_UnknownType) continue;

			if (entry_type.equals(entry.getValue()))
				i--;
//				else
//					return aClassInvocation;
		}
		return i == 0;
	}

	public void forFunction(DeduceTypes2 deduceTypes2, @NotNull FunctionInvocation fi, @NotNull ForFunction forFunction) {
//		LOG.err("272 forFunction\n\t"+fi.getFunction()+"\n\t"+fi.pte);
		fi.generateDeferred()
				.then(result -> result.typePromise()
						.then(forFunction::typeDecided));
	}

	public @NotNull Country country() {
		return country;
	}

	public void addDeferredMember(final DeferredMemberFunction aDeferredMemberFunction) {
		deferredMemberFunctions.add(aDeferredMemberFunction);
	}

	public void addFunctionMapHook(final IFunctionMapHook aFunctionMapHook) {
		functionMapHooks.add(aFunctionMapHook);
	}

	public void addLog(ElLog aLog) {
		pipelineLogic.addLog(aLog);
	}

	public @NotNull DeduceTypes2 deduceModule(DeducePhase_deduceModule_Request aRequest) {
//		OS_Module         m   = aRequest.getModule();
		Iterable<EvaNode> lgf = aRequest.getListOfEvaFunctions();

		final @NotNull DeduceTypes2 deduceTypes2 = DeducePhase_deduceModule_Request.Companion.createDeduceTypes2Singleton(aRequest);

		logProgress(DeducePhaseProvenance.DeduceTypes_create, List.of(deduceTypes2, lgf, deduceTypes2._phase().generatedClasses.getList()));

		var df = new DeduceTypes2_deduceFunctions_Request(aRequest, lgf, true, this);
		Rosetta.create_call(df);

		return deduceTypes2;
	}

	private void logProgress(final @NotNull DeducePhaseProvenance aProvenance, final Object o) {
		switch (aProvenance) {
			case DeduceTypes_create -> {
				List<? extends Object> l            = (List<? extends Object>) o;
				DeduceTypes2           deduceTypes2 = (DeduceTypes2) l.get(0);
				List<EvaNode>          lgf          = (List<EvaNode>) l.get(2);
				LOG.info("196 DeduceTypes " + deduceTypes2.getFileName());
				{
					final List<EvaNode> p = _inj().new_ArrayList__EvaNode();
					Iterables.addAll(p, lgf);
					LOG.info("197 lgf.size " + p.size());
				}
			}
			default -> throw new IllegalStateException("Unexpected value: " + aProvenance);
		}
	}

	@ElijahInternal
	public void __DeduceTypes2_deduceFunctions_Request__run(final boolean b,
	                                                        final DeducePhase_deduceModule_Request aRequest) {


		// TODO @ElijahInternal annotation prob a smell:
		//  - create an object, call out of package, then come back in...
		//    firgive me, trying something new


		final @NotNull DeduceTypes2 deduceTypes2 = DeducePhase_deduceModule_Request.Companion.createDeduceTypes2Singleton(aRequest);

		OS_Module         m   = aRequest.getModule();
		Iterable<EvaNode> lgf = aRequest.getListOfEvaFunctions();

		deduceTypes2.deduceFunctions(lgf);

		if (b) {
//		deduceTypes2.deduceClasses(generatedClasses.copy().stream()
//				.filter(c -> c.module() == m)
//				.collect(Collectors.toList()));

			for (EvaNode evaNode : generatedClasses.copy()) {
				if (evaNode.module() != m) continue;

				if (evaNode instanceof final @NotNull EvaClass evaClass) {

					evaClass.fixupUserClasses(deduceTypes2, evaClass.getKlass().getContext());
					deduceTypes2.deduceOneClass(evaClass);
				}
			}

			for (EvaNode evaNode : lgf) {
				final BaseEvaFunction bef;

				if (evaNode instanceof BaseEvaFunction) {
					bef = (BaseEvaFunction) evaNode;
				} else continue;
				for (final IFunctionMapHook hook : functionMapHooks) {
					if (hook.matches(bef.getFD())) {
						hook.apply(List_of((EvaFunction) bef));
					}
				}
			}
		}
	}

//	public List<ElLog> deduceLogs = new ArrayList<ElLog>();

	public Eventual<ClassDefinition> generateClass(final GenerateFunctions gf, final @NotNull ClassInvocation ci) {
		WorkManager wm = _inj().new_WorkManager();
		// par { return promise ; finishQueue { wm.drain() ; } }
		final Eventual<ClassDefinition> x = generateClass(gf, ci, wm);
		wm.drain();
		return x;
	}

	public @NotNull Eventual<ClassDefinition> generateClass(final GenerateFunctions gf, final @NotNull ClassInvocation ci, final WorkManager wm) {
		final Eventual<ClassDefinition> ret = new Eventual<>();

		classGenerator.submit(new Runnable() {
			@Override
			public void run() {
				WlGenerateClass gen = _inj().new_WlGenerateClass(gf, ci, generatedClasses, codeRegistrar);
				gen.run(wm);

				final ClassDefinition cd = _inj().new_ClassDefinition(ci);

				gen.resultPromise(genclass -> {
					if (genclass != null) {
						cd.setNode(genclass);
						ret.resolve(cd);
					} else {
						ret.reject(_inj().new_CouldntGenerateClass(cd, gf, ci));
					}
				});

				gen.resultPromise(genclass -> {
					int y = 2;
				});
			}
		});

		return ret;
	}

	public void onClass(ClassStatement aClassStatement, OnClass callback) {
		onclasses.put(aClassStatement, callback);
	}

	public @NotNull FunctionInvocation newFunctionInvocation(final FunctionDef f, final @Nullable ProcTableEntry aO, final @NotNull IInvocation ci) {
		return _inj().new_FunctionInvocation(f, aO, ci, this.generatePhase);
	}

	public void onType(IdentTableEntry entry, OnType callback) {
		idte_type_callbacks.put(entry, callback);
	}

	public void registerFound(FoundElement foundElement) {
		foundElements.add(foundElement);
	}

	public void registerResolvedVariable(IdentTableEntry identTableEntry, OS_Element parent, String varName) {
		final ResolvedVariables resolvedVariable = _inj().new_ResolvedVariables(identTableEntry, parent, varName);
		resolved_variables.put(parent, resolvedVariable);
	}

	public @NotNull ClassInvocation registerClassInvocation(final @NotNull ClassStatement aClassStatement, final DeduceTypes2 aDeduceTypes2) {
		final Supplier<DeduceTypes2> deduceTypes2Supplier = () -> aDeduceTypes2;
		final ClassInvocation        classInvocation      = _inj().new_ClassInvocation(aClassStatement, null, deduceTypes2Supplier);
		return registerClassInvocation(classInvocation);
	}

	public @NotNull ClassInvocation registerClassInvocation(@NotNull ClassInvocation aClassInvocation) {
		final RegisterClassInvocation rci = _inj().new_RegisterClassInvocation(this);
		return rci.registerClassInvocation(aClassInvocation);
	}

	public void addDrs(final BaseEvaFunction aGeneratedFunction, final @NotNull List<DR_Item> aDrs) {
		aDrs.forEach(dr -> addDr(aGeneratedFunction, dr));
	}

	private void addDr(final BaseEvaFunction aGeneratedFunction, final DR_Item aDr) {
		drs.add(aGeneratedFunction, aDr);
	}

	public void waitOn(final DeduceTypes2 aDeduceTypes2) {
		waits.add(aDeduceTypes2);
	}

	public void finish() {
		setGeneratedClassParents();
		/*
		for (GeneratedNode generatedNode : generatedClasses) {
			if (generatedNode instanceof EvaClass) {
				final EvaClass generatedClass = (EvaClass) generatedNode;
				final ClassStatement cs = generatedClass.getKlass();
				Collection<ClassInvocation> cis = classInvocationMultimap.get(cs);
				for (ClassInvocation ci : cis) {
					if (equivalentGenericPart(generatedClass.ci, ci)) {
						final DeferredObject<EvaClass, Void, Void> deferredObject = (DeferredObject<EvaClass, Void, Void>) ci.promise();
						deferredObject.then(new DoneCallback<EvaClass>() {
							@Override
							public void onDone(EvaClass result) {
								assert result == generatedClass;
							}
						});
//						deferredObject.resolve(generatedClass);
					}
				}
			}
		}
*/
		handleOnClassCallbacks();
		handleIdteTypeCallbacks();
/*
		for (Map.Entry<EvaFunction, OS_Type> entry : typeDecideds.entrySet()) {
			for (Triplet triplet : forFunctions) {
				if (triplet.gf.getGenerated() == entry.getKey()) {
					synchronized (triplet.deduceTypes2) {
						triplet.forFunction.typeDecided(entry.getValue());
					}
				}
			}
		}
*/
/*
		for (Map.Entry<FunctionDef, EvaFunction> entry : functionMap.entries()) {
			FunctionInvocation fi = _inj().new_FunctionInvocation(entry.getKey(), null);
			for (Triplet triplet : forFunctions) {
//				Collection<EvaFunction> x = functionMap.get(fi);
				triplet.forFunction.finish();
			}
		}
*/
		handleFoundElements();
		handleResolvedVariables();
		resolveAllVariableTableEntries();
		handleDeferredMemberFunctions();
		handleDeferredMembers();
		sanityChecks();
		handleFunctionMapHooks();

		for (DE3_Active de3_Active : _actives) {
			pa.getCompilationEnclosure().addReactive(de3_Active);
		}

		for (Pair<BaseEvaFunction, DR_Item> pair : drs.iterator()) {
			final BaseEvaFunction ef = pair.getLeft();
			final DR_Item         dr = pair.getRight();

			//System.err.println("611a " + ef);
			//System.err.println("611b " + dr);

			if (dr instanceof DR_ProcCall drpc) {
				var fi = drpc.getFunctionInvocation();
				if (fi != null) {
					final BaseEvaFunction[] ef1 = new BaseEvaFunction[1];
					fi.generatePromise().then(x -> ef1[0] = x);

					if (ef1[0] == null) {
						//throw new AssertionError();
						System.err.println("****************************** no function generated");
					} else {
						pa.activeFunction(ef1[0]);
					}
				}
			} else if (dr instanceof DR_Ident drid) {
				//System.err.println(String.format("***** 623623 -- %s %b", drid.name(), drid.isResolved()));

				for (DR_Ident.Understanding understanding : drid.u) {
					//System.err.println(String.format("**** 623626 -- %s", understanding.asString()));
				}
			}
		}

		for (DeduceTypes2 wait : waits.iterator()) {
			for (Map.Entry<OS_Module, Collection<Consumer<DeduceTypes2>>> entry : iWantModules.asMap().entrySet()) {
				if (entry.getKey() == wait.module) {
					for (Consumer<DeduceTypes2> deduceTypes2Consumer : entry.getValue()) {

						// README I like this, but by the time we get here, everything is already done...
						//  and I mean the callback to DT_External2::actualise
						//  - everything being resolution and setStatus, etc

						deduceTypes2Consumer.accept(wait);
					}
				}
			}
		}
	}

	public void setGeneratedClassParents() {
		// TODO all EvaFunction nodes have a genClass member
		for (EvaNode evaNode : generatedClasses) {
			if (evaNode instanceof final @NotNull EvaClass evaClass) {
				@NotNull Collection<EvaFunction> functions = evaClass.functionMap.values();
				for (@NotNull EvaFunction generatedFunction : functions) {
					generatedFunction.setParent(evaClass);
				}
			} else if (evaNode instanceof final @NotNull EvaNamespace generatedNamespace) {
				@NotNull Collection<EvaFunction> functions = generatedNamespace.functionMap.values();
				for (@NotNull EvaFunction generatedFunction : functions) {
					generatedFunction.setParent(generatedNamespace);
				}
			}
		}
	}

	public void handleOnClassCallbacks() {
		// TODO rewrite with classInvocationMultimap
		for (ClassStatement classStatement : onclasses.keySet()) {
			for (EvaNode evaNode : generatedClasses) {
				if (evaNode instanceof final @NotNull EvaClass evaClass) {
					if (evaClass.getKlass() == classStatement) {
						Collection<OnClass> ks = onclasses.get(classStatement);
						for (@NotNull OnClass k : ks) {
							k.classFound(evaClass);
						}
					} else {
						@NotNull Collection<EvaClass> cmv = evaClass.classMap.values();
						for (@NotNull EvaClass aClass : cmv) {
							if (aClass.getKlass() == classStatement) {
								Collection<OnClass> ks = onclasses.get(classStatement);
								for (@NotNull OnClass k : ks) {
									k.classFound(evaClass);
								}
							}
						}
					}
				}
			}
		}
	}

	public void handleIdteTypeCallbacks() {
		for (Map.@NotNull Entry<IdentTableEntry, OnType> entry : idte_type_callbacks.entrySet()) {
			IdentTableEntry idte = entry.getKey();
			if (idte.type != null && // TODO make a stage where this gets set (resolvePotentialTypes)
					idte.type.getAttached() != null)
				entry.getValue().typeDeduced(idte.type.getAttached());
			else
				entry.getValue().noTypeFound();
		}
	}

	public void handleFoundElements() {
		for (@NotNull FoundElement foundElement : foundElements) {
			// TODO As we are using this, didntFind will never fail because
			//  we call doFoundElement manually in resolveIdentIA
			//  As the code matures, maybe this will change and the interface
			//  will be improved, namely calling doFoundElement from here as well
			if (foundElement.didntFind()) {
				foundElement.doNoFoundElement();
			}
		}
	}

	public void handleResolvedVariables() {
		int y = 2;

		for (EvaNode evaNode : generatedClasses.copy()) {
			if (evaNode instanceof final @NotNull EvaContainer evaContainer) {
				Collection<ResolvedVariables> x = resolved_variables.get(evaContainer.getElement());
				for (@NotNull ResolvedVariables resolvedVariables : x) {
					resolvedVariables.handle(evaContainer);
				}
			}
		}
	}

	public void resolveAllVariableTableEntries() {
		@NotNull List<EvaClass> gcs                           = _inj().new_ArrayList__EvaClass();
		boolean                 all_resolve_var_table_entries = false;
		while (!all_resolve_var_table_entries) {
			if (generatedClasses.size() == 0) break;
			for (EvaNode evaNode : generatedClasses.copy()) {
				if (evaNode instanceof final @NotNull EvaClass evaClass) {
					all_resolve_var_table_entries = evaClass.resolve_var_table_entries(this); // TODO use a while loop to get all classes
				}
			}
		}
	}

	public void handleDeferredMemberFunctions() {
		for (@NotNull final DeferredMemberFunction deferredMemberFunction : deferredMemberFunctions) {
			int              y      = 2;
			final OS_Element parent = deferredMemberFunction.getParent();//.getParent().getParent();

			if (parent instanceof ClassStatement) {
				final IInvocation invocation = deferredMemberFunction.getInvocation();

				final DeferredMemberFunctionParentIsClassStatement dmfpic = _inj().new_DeferredMemberFunctionParentIsClassStatement(deferredMemberFunction, invocation, this);
				dmfpic.action();
			} else if (parent instanceof NamespaceStatement) {
//				final ClassStatement classStatement = (ClassStatement) deferredMemberFunction.getParent();
				final IInvocation invocation = deferredMemberFunction.getInvocation();

				final NamespaceInvocation namespaceInvocation;
				if (invocation instanceof ClassInvocation) {
					namespaceInvocation = _inj().new_NamespaceInvocation((NamespaceStatement) parent);
				} else {
					namespaceInvocation = (NamespaceInvocation) invocation;
				}

				namespaceInvocation.resolvePromise().
						then((final @NotNull EvaNamespace result) -> {
							final NamespaceInvocation             x  = namespaceInvocation;
							final @NotNull DeferredMemberFunction z  = deferredMemberFunction;
							int                                   yy = 2;
						});
			}
		}

		for (EvaNode evaNode : generatedClasses) {
			if (evaNode instanceof final @NotNull EvaContainerNC nc) {
				nc.noteDependencies(nc.getDependency()); // TODO is this right?

				for (EvaFunction generatedFunction : nc.functionMap.values()) {
					generatedFunction.noteDependencies(nc.getDependency());
				}
				if (nc instanceof final @NotNull EvaClass evaClass) {

					for (EvaConstructor evaConstructor : evaClass.constructors.values()) {
						evaConstructor.noteDependencies(nc.getDependency());
					}
				}
			}
		}
	}

	public void handleDeferredMembers() {
		for (@NotNull final DeferredMember deferredMember : deferredMembers) {
			if (deferredMember.getParent().isNamespaceStatement()) {
				final @NotNull NamespaceStatement parent = (NamespaceStatement) deferredMember.getParent().element();
				final NamespaceInvocation         nsi    = registerNamespaceInvocation(parent);
				nsi.resolveDeferred().then(new __EvaNamespaceDoneCallback(deferredMember));
			} else if (deferredMember.getParent().element() instanceof ClassStatement) {
				// TODO do something
				final ClassStatement parent = (ClassStatement) deferredMember.getParent().element();
				final String         name   = deferredMember.getVariableStatement().getName();

				// because deferredMember.invocation is null, we must create one here
				final @Nullable ClassInvocation ci = registerClassInvocation(parent, null, new NULL_DeduceTypes2());
				assert ci != null;
				ci.resolvePromise().then(new __EvaClassDoneCallback(name, deferredMember));
			} else
				throw new NotImplementedException();
		}
	}

	private void sanityChecks() {
		for (EvaNode evaNode : generatedClasses) {
			if (evaNode instanceof final @NotNull EvaClass evaClass) {
				sanityChecks(evaClass.functionMap.values());
//				sanityChecks(generatedClass.constructors.values()); // TODO reenable
			} else if (evaNode instanceof final @NotNull EvaNamespace generatedNamespace) {
				sanityChecks(generatedNamespace.functionMap.values());
//				sanityChecks(generatedNamespace.constructors.values());
			}
		}
	}

	public void handleFunctionMapHooks() {
		for (final Map.@NotNull Entry<FunctionDef, Collection<EvaFunction>> entry : functionMap.asMap().entrySet()) {
			for (final IFunctionMapHook functionMapHook : ca.functionMapHooks()) {
				if (functionMapHook.matches(entry.getKey())) {
					functionMapHook.apply(entry.getValue());
				}
			}
		}
	}

	public NamespaceInvocation registerNamespaceInvocation(NamespaceStatement aNamespaceStatement) {
		if (namespaceInvocationMap.containsKey(aNamespaceStatement))
			return namespaceInvocationMap.get(aNamespaceStatement);

		@NotNull NamespaceInvocation nsi = _inj().new_NamespaceInvocation(aNamespaceStatement);
		namespaceInvocationMap.put(aNamespaceStatement, nsi);
		return nsi;
	}

	// helper function. no generics!
	public @Nullable ClassInvocation registerClassInvocation(@NotNull ClassStatement aParent, String aConstructorName, final Supplier<DeduceTypes2> aDeduceTypes2) {
		//@Nullable ClassInvocation ci = _inj().new_ClassInvocation(aParent, aConstructorName, deduceTypes2);
		@Nullable ClassInvocation ci = _inj().new_ClassInvocation(aParent, aConstructorName, aDeduceTypes2); // !! 08/28
		if (ci != null) {
			ci = registerClassInvocation(ci);
		}
		return ci;
	}

	private void sanityChecks(@NotNull Collection<EvaFunction> aGeneratedFunctions) {
		for (@NotNull EvaFunction generatedFunction : aGeneratedFunctions) {
			for (@NotNull IdentTableEntry identTableEntry : generatedFunction.idte_list) {
				switch (identTableEntry.getStatus()) {
					case UNKNOWN:
						assert !identTableEntry.hasResolvedElement();
						LOG.err(String.format("250 UNKNOWN idte %s in %s", identTableEntry, generatedFunction));
						break;
					case KNOWN:
						assert identTableEntry.hasResolvedElement();
						if (identTableEntry.type == null) {
							LOG.err(String.format("258 null type in KNOWN idte %s in %s", identTableEntry, generatedFunction));
						}
						break;
					case UNCHECKED: {


						LOG.err(String.format("255 UNCHECKED idte %s in %s", identTableEntry, generatedFunction));
						break;
					}
				}
				for (@NotNull TypeTableEntry pot_tte : identTableEntry.potentialTypes()) {
					if (pot_tte.getAttached() == null) {
						LOG.err(String.format("267 null potential attached in %s in %s in %s", pot_tte, identTableEntry, generatedFunction));
					}
				}
			}
		}
	}

	public void modulePromise(final OS_Module aModule, final Consumer<DeduceTypes2> con) {
		iWantModules.put(aModule, con);
	}

	public void addActives(@NotNull List<DE3_Active> activesList) {
		_actives.addAll(activesList);
	}

	public @NotNull Multimap<FunctionDef, EvaFunction> _functionMap() {
		return functionMap;
	}

	public <T> Eventual<T> new_Eventual() {
		final Eventual<T> R = new Eventual<>();

		R.register(this);

		return R;
	}

	public void doneWait(final DeduceTypes2Rosetta aRosetta) {
		//throw new UnintendedUseException("24j3 break for impl");
		// README 24/01/03 Honesly, forgot what this was for...
	}

	public Collection<ClassInvocation> classInvocationMultimap_get(final ClassStatement aC) {
		return classInvocationMultimap.get(aC);
	}

	public void classInvocationMultimap_put(final ClassStatement aKlass, final ClassInvocation aClassInvocation) {
		classInvocationMultimap.put(aKlass, aClassInvocation);
	}

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//public class DefaultEventualRegister implements EventualRegister {
	final List<Eventual<?>> _eventuals = new ArrayList<Eventual<?>>();

	//public DefaultEventualRegister() {
	//}

	@Override
	public <P> void register(final Eventual<P> e) {
		_eventuals.add(e);
	}

	@Override
	public void checkFinishEventuals() {
		int y = 0;
		for (Eventual<?> eventual : _eventuals) {
			if (eventual.isResolved()) {
			} else {
				System.err.println("[PipelineLogic::checkEventual] failed for " + eventual.description());
			}
		}
	}

	private static class __EvaClassDoneCallback implements DoneCallback<EvaClass> {
		private final          String         name;
		private final @NotNull DeferredMember deferredMember;

		public __EvaClassDoneCallback(final String aName, final @NotNull DeferredMember aDeferredMember) {
			name           = aName;
			deferredMember = aDeferredMember;
		}

		@Override
		public void onDone(final EvaClass result) {
			final List<EvaContainer.VarTableEntry> vt = result.varTable;
			for (EvaContainer.VarTableEntry gc_vte : vt) {
				if (gc_vte.nameToken.getText().equals(name)) {
					// check connections
					// unify pot. types (prol. shuld be done already -- we don't want to be reporting errors here)
					// call typePromises and externalRefPromisess

					// TODO just getting first element here (without processing of any kind); HACK
					final List<EvaContainer.VarTableEntry.ConnectionPair> connectionPairs = gc_vte.connectionPairs;
					if (connectionPairs.size() > 0) {
						final GenType ty = connectionPairs.get(0).vte.getType().genType;
						assert ty.getResolved() != null;
						gc_vte.varType = ty.getResolved(); // TODO make sure this is right in all cases
						if (deferredMember.typeResolved().isPending())
							deferredMember.typeResolved().resolve(ty);
						break;
					} else {
						NotImplementedException.raise();
					}
				}
			}
		}
	}

	private class __EvaNamespaceDoneCallback implements DoneCallback<EvaNamespace> {
		private final @NotNull DeferredMember deferredMember;

		public __EvaNamespaceDoneCallback(final @NotNull DeferredMember aDeferredMember) {
			deferredMember = aDeferredMember;
		}

		@Override
		public void onDone(final EvaNamespace result) {
			@NotNull Maybe<EvaContainer.VarTableEntry> v_m = result.getVariable(deferredMember.getVariableStatement().getName());

			assert !v_m.isException();

			EvaContainer.VarTableEntry v = v_m.o;

			// TODO varType, potentialTypes and _resolved: which?
			//final OS_Type varType = v.varType;

			assert v != null;
			v.resolve_varType_cb((varType) -> {
				final @NotNull GenType genType = DeducePhase.this._inj().new_GenTypeImpl();
				genType.set(varType);

//								if (deferredMember.getInvocation() instanceof NamespaceInvocation) {
//									((NamespaceInvocation) deferredMember.getInvocation()).resolveDeferred().done(new DoneCallback<EvaNamespace>() {
//										@Override
//										public void onDone(EvaNamespace result) {
//											result;
//										}
//									});
//								}

				if (true) {
					throw new Error();
				} else if (false) {
					deferredMember.externalRefDeferred().resolve(result);
				}
/*
		if (genType.resolved == null) {
			// HACK need to resolve, but this shouldn't be here
			try {
				@NotNull OS_Type rt = DeduceTypes2.resolve_type(null, varType, varType.getTypeName().getContext());
				genType.set(rt);
			} catch (ResolveError aResolveError) {
				aResolveError.printStackTrace();
			}
		}
		deferredMember.typeResolved().resolve(genType);
*/
			});
		}
	}
	//}
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

}
