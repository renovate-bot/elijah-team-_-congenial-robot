package tripleo.elijah.stages.gen_c;

import lombok.EqualsAndHashCode;
import lombok.Getter;
//import org.eclipse.xtend.lib.annotations.EqualsHashCode;

import org.eclipse.xtend.lib.annotations.EqualsHashCode;
import org.jdeferred2.DoneCallback;
import tripleo.elijah.Eventual;

import tripleo.elijah.lang.i.OS_Element;

import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaNode;

import tripleo.elijah.stages.gen_fn.IdentTableEntry;
import tripleo.elijah.stages.gen_fn.ProcTableEntry;

@EqualsAndHashCode
// @EqualsHashCode
//  https://eclipse.dev/Xtext/xtend/documentation/204_activeannotations.html#data-annotation
public final class GR_re_is_FunctionDef implements GRRR, GR_EvaNodeAble {
	@Getter
	private final ProcTableEntry  pte;
	@Getter
	private final EvaClass        cheatClass;
	@Getter
	private final IdentTableEntry ite;
	@Getter
	private final CRI_Ident       cri_ident;
	private final OS_Element      repo_element;
	private final GI_FunctionDef  gi_item;

	private final Eventual<EvaNode> resolvedP = new Eventual<>();

	// TODO too many params make jack feel like a cheater
	public GR_re_is_FunctionDef(final ProcTableEntry aPte,
								final EvaClass a_cheat,
								final IdentTableEntry aIte,
								final CRI_Ident aCRIIdent,
								final GI_FunctionDef aGiItem) {
		this.pte          = aPte;
		this.cheatClass   = a_cheat;
		this.ite          = aIte;
		this.cri_ident    = aCRIIdent;
		this.repo_element = ite.getResolvedElement();
		this.gi_item      = aGiItem;
	}

	@Override
	public void onResolve(final DoneCallback<EvaNode> cb) {
		((GI_FunctionDef) repo_element).resolving(this);
		resolvedP.then(cb);
	}

	@Override
	public void reverseResolving(final Object aObject) {
		assert aObject == this;
		assert aObject instanceof EqualsHashCode; // TODO trigger
		this.gi_item._re_is_FunctionDef(pte, cheatClass, ite, resolvedP);
	}
}
