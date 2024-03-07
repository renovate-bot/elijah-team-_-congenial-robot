package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.sanaa.ElIntrinsics;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.world.i.LivingFunction;

public class GI_FunctionDef implements GenerateC_Item {
	private final FunctionDef    _e;
	private final GI_Repo        _repo;
	private       LivingFunction _living;
	private       EvaNode        _evaNode;

	public GI_FunctionDef(final FunctionDef aE, final GI_Repo aGIRepo) {
		_e    = aE;
		_repo = aGIRepo;
	}

	void _re_is_FunctionDef(final @Nullable ProcTableEntry pte,
							   final EvaClass a_cheat,
							   final @NotNull IdentTableEntry ite,
							   final Eventual<EvaNode> resolvedP
							  ) {
		final boolean[]         qq        = {false};

		if (pte != null) {
			pte.onFunctionInvocation(fi -> {
				assert fi != null;
				if (fi != null) {
					fi.onGenerated(gen -> {
						assert gen != null;
						qq[0] = true;
						resolvedP.resolve(gen);
					});
				}
			});
		}

		{
			ite.onResolvedType( resolved1->{
				if (resolved1 instanceof EvaFunction) {
					if (!qq[0]) resolvedP.resolve(resolved1);
				} else if (resolved1 instanceof EvaClass) {
					if (!qq[0]) resolvedP.resolve(resolved1);

					// FIXME Bar#quux is not being resolves as a BGF in Hier

//								FunctionInvocation fi = pte.getFunctionInvocation();
//								fi.setClassInvocation();

					int y=2;
				}
			});
		}

		if (!qq[0]) {
			resolvedP.resolve(a_cheat);
		}

		// this is safe to remove
		assert resolvedP.isResolved();
/*
		resolvedP.then(xx -> {
			if (qq[0]) {
				final EvaNode[] resolved2 = new EvaNode[1];
				resolved2[0] = xx;
			}
		});
*/
	}

	@Override
	public EvaNode getEvaNode() {
		return _evaNode;
	}

	@Override
	public void setEvaNode(final EvaNode aEvaNode) {
		if (aEvaNode == null) {
			int y = 3;
			assert false;
		} else {
			ElIntrinsics.checkNotNull(aEvaNode);

			_evaNode = aEvaNode;
			_living  = _repo.generateC._ce().getCompilation().livingRepo().getFunction((BaseEvaFunction) _evaNode);
		}
	}

	@Override
	public void setEvaNode_by(final GR_EvaNodeAble aKotlinEnvy) {
		aKotlinEnvy.onResolve(this::setEvaNode);
	}

	public void resolving(final GRRR aGRReIsFunctionDef) {
		aGRReIsFunctionDef.reverseResolving(this);
	}
}
