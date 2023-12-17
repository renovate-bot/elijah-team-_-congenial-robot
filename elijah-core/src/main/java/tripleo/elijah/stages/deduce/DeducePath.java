/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.FailCallback;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.LookupResultList;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.RegularTypeName;
import tripleo.elijah.lang.impl.ContextImpl;
import tripleo.elijah.lang.impl.VariableStatementImpl;
import tripleo.elijah.stages.deduce.nextgen.DR_Ident;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.IdentIA;
import tripleo.elijah.stages.instructions.InstructionArgument;
import tripleo.elijah.stages.instructions.IntegerIA;
import tripleo.elijah.stages.instructions.ProcIA;

import java.util.List;

/**
 * Created 7/9/21 6:10 AM
 */
public class DeducePath {
	private final          IdentTableEntry           base;
	private final          MemberContext @NotNull [] contexts;
	private final          OS_Element @NotNull []    elements;  // arrays because they never need to be resized
	private final @NotNull List<InstructionArgument> ias;
	private final          GenType @NotNull []       types;

	public InstructionArgument getIA(int index) {
		return ias.get(index);
	}

	public void setTarget(final @NotNull DeduceElement aTarget) {
		//assert elements[0] == null;
		elements[0] = aTarget.element();
	}

	public int size() {
		return ias.size();
	}

	@Contract(pure = true)
	public DeducePath(IdentTableEntry aIdentTableEntry, @NotNull List<InstructionArgument> aX) {
		final int size = aX.size();
		assert size > 0;

		base = aIdentTableEntry;
		ias  = aX;

		elements = new OS_Element[size];
		types    = new GenTypeImpl[size];
		contexts = new MemberContext[size];
	}

	public @Nullable Context getContext(int aIndex) {
		if (contexts[aIndex] == null) {
			final @Nullable MemberContext memberContext = new MemberContext(this, aIndex, getElement(aIndex));
			contexts[aIndex] = memberContext;
			return memberContext;
		} else
			return contexts[aIndex];

	}

	@Nullable
	public OS_Element getElement(int aIndex) {
		if (elements[aIndex] == null) {
			InstructionArgument  ia2 = getIA(aIndex);
			@Nullable OS_Element el;
			if (ia2 instanceof IntegerIA) {
				el = elementForIndex(aIndex, (IntegerIA) ia2);
			} else if (ia2 instanceof IdentIA) {
				el = elementForIndex(aIndex, (IdentIA) ia2);
			} else if (ia2 instanceof ProcIA) {
				el = elementForIndex((ProcIA) ia2);
			} else
				el = null; // README shouldn't be calling for other subclasses
			elements[aIndex] = el;
			return el;
		} else {
			return elements[aIndex];
		}
	}

	@Nullable
	private OS_Element elementForIndex(final int aIndex, final @NotNull IntegerIA ia2) {
		@Nullable OS_Element        el;
		@NotNull VariableTableEntry vte = ia2.getEntry();
		el = vte.getResolvedElement();
		if (el == null) {
			// never called bc above will NEVER be true due to construction of vte
			vte.elementPromise((el2) -> {
				vte.setStatus(BaseTableEntry.Status.KNOWN,
							  new GenericElementHolderWithIntegerIA(el2,
																	(IntegerIA) ias.get(aIndex)));
			}, null);
		} else {
			// set this to set resolved_elements of remaining entries
			vte.setStatus(BaseTableEntry.Status.KNOWN, //dt2._inj().new_
						  new GenericElementHolderWithIntegerIA(el, (IntegerIA) ias.get(aIndex)));
		}
		return el;
	}

	@Nullable
	private OS_Element elementForIndex(final int aIndex, final @NotNull IdentIA ia2) {
		@Nullable OS_Element el;
		el = null;
		@NotNull IdentTableEntry identTableEntry = ia2.getEntry();
		identTableEntry.onResolvedElement(re -> {
			identTableEntry.setStatus(BaseTableEntry.Status.KNOWN, //_inj().new_
									  new GenericElementHolder(re));
			elements[aIndex] = re;
		});
		if (identTableEntry.hasResolvedElement()) {
			el = identTableEntry.getResolvedElement();
			if (aIndex == 0)
				if (identTableEntry.getResolvedElement() != el)
					identTableEntry.setStatus(BaseTableEntry.Status.KNOWN, //_inj().new_
											  new GenericElementHolder(el));
		} else {
			// TODO 06/19 maybe redundant

			var drIdent = identTableEntry.getDefinedIdent();

			final Compilation c = this.base._deduceTypes2().module.getCompilation();
			if (c.reports().outputOn(Finally.Outs.Out_141)) {
				System.err.println(drIdent);
			}

//			identTableEntry.elementPromise((x)->{}, null);
			identTableEntry.getDeduceElement().resolvedElementPromise().then((x) -> {
				identTableEntry.setStatus(BaseTableEntry.Status.KNOWN, //_inj().new_
										  new GenericElementHolder(x));
			});
		}
		return el;
	}

	@Nullable
	private OS_Element elementForIndex(final @NotNull ProcIA ia2) {
		@Nullable OS_Element          el;
		final @NotNull ProcTableEntry procTableEntry = ia2.getEntry();
		el = procTableEntry.getResolvedElement(); // .expression?
		// TODO no setStatus here?

		var dt2 = procTableEntry._deduceTypes2();

		if (procTableEntry.expression_num instanceof IdentIA identIA) {
			var id = procTableEntry.__gf.getIdent(identIA.getEntry());

			if (el != null) {
				final DR_Ident.ElementUnderstanding understanding = dt2._inj().new_DR_Ident_ElementUnderstanding(el);
				id.u.add(understanding);
			} else {
				for (DR_Ident.Understanding understanding : id.u) {
					if (understanding instanceof DR_Ident.ElementUnderstanding eu) {
						el = eu.getElement();
					}
				}
			}
		}

		//assert el != null;
		if (el == null) {
			if (procTableEntry.expression_num instanceof IdentIA identIA) {
				var ite = (identIA.getEntry());
				System.err.println("139 element not found for " + ite.getIdent().getText());
			}
			//throw new AssertionError();
		}
		return el;
	}

	public void getElementPromise(int aIndex, DoneCallback<OS_Element> aOS_elementDoneCallback, FailCallback<Diagnostic> aDiagnosticFailCallback) {
		getEntry(aIndex).elementPromise(aOS_elementDoneCallback, aDiagnosticFailCallback);
	}

	@Nullable
	public BaseTableEntry getEntry(int aIndex) {
		InstructionArgument ia2 = getIA(aIndex);
		if (ia2 instanceof IntegerIA) {
			@NotNull VariableTableEntry vte = ((IntegerIA) ia2).getEntry();
			return vte;
		} else if (ia2 instanceof IdentIA) {
			@NotNull IdentTableEntry identTableEntry = ((IdentIA) ia2).getEntry();
			return identTableEntry;
		} else if (ia2 instanceof ProcIA) {
			final @NotNull ProcTableEntry procTableEntry = ((ProcIA) ia2).getEntry();
			return procTableEntry;
		}
		return null;
	}

	public interface DeducePathItem {
		//base = aIdentTableEntry;
		//ias  = aX;

		MemberContext context();

		OS_Element element();

		int getIndex();

		InstructionArgument instructionArgument();

		GenType type();

	}

	public @Nullable GenType getType(int aIndex) {
		if (types[aIndex] != null) {
			return types[aIndex];
		}

		InstructionArgument ia2 = getIA(aIndex);
		@Nullable GenType   gt;
		if (ia2 instanceof IntegerIA) {
			@NotNull VariableTableEntry vte = ((IntegerIA) ia2).getEntry();
			gt = vte.getType().genType;
			assert gt != null;
		} else if (ia2 instanceof IdentIA) {
			@NotNull IdentTableEntry identTableEntry = ((IdentIA) ia2).getEntry();
			if (identTableEntry.type != null) {
				gt = identTableEntry.type.genType;
				assert gt != null;
			} else {
				gt = null;
			}
		} else if (ia2 instanceof ProcIA) {
			final @NotNull ProcTableEntry procTableEntry = ((ProcIA) ia2).getEntry();
			gt = null;//procTableEntry.getResolvedElement(); // .expression?
//				assert gt != null;
		} else
			gt = null; // README shouldn't be calling for other subclasses
		types[aIndex] = gt;
		return gt;
	}

	public void injectType(final int index, final GenType aType) {
		types[index] = aType;
	}

	public class DeducePathItemImpl implements DeducePathItem {
		private final int                 index;
		private final InstructionArgument instructionArgument;
		private       MemberContext       context;
		private       OS_Element          element;
		private       GenType             type;

		public DeducePathItemImpl(final InstructionArgument aInstructionArgument, final int aIndex) {
			instructionArgument = aInstructionArgument;
			index               = aIndex;
		}

		@Override
		public MemberContext context() {
			return context;
		}

		@Override
		public OS_Element element() {
			return element;
		}

		@Override
		public int getIndex() {
			return index;
		}

		@Override
		public InstructionArgument instructionArgument() {
			return instructionArgument;
		}

		@Override
		public GenType type() {
			return type;
		}
	}

	class MemberContext extends ContextImpl {

		private final           DeducePath deducePath;
		private final           OS_Element element;
		private final           int        index;
		private final @Nullable GenType    type;

		public MemberContext(DeducePath aDeducePath, int aIndex, OS_Element aElement) {
			assert aIndex >= 0;

			deducePath = aDeducePath;
			index      = aIndex;
			element    = aElement;

			type = deducePath.getType(aIndex);
		}

		@Override
		public @Nullable Context getParent() {
			if (index == 0)
				return element.getContext().getParent();
			return deducePath.getContext(index - 1);
		}

		@Override
		public @Nullable LookupResultList lookup(String name, int level, LookupResultList Result, SearchList alreadySearched, boolean one) {
//			if (index == 0)


			if (type.getResolved() == null) {

				//c = getContext(this.index)
				@Nullable final OS_Element ell = deducePath.getElement(this.index);
				if (ell == null) {
					throw new AssertionError("202 no element found");
				} else {

					if (ell instanceof VariableStatementImpl) {
						VariableStatementImpl variableStatement = (VariableStatementImpl) ell;
						final Context         ctx2              = variableStatement.getParent().getContext();

						String n2 = null;
						if (type.getNonGenericTypeName() != null) {
							final RegularTypeName ngtn = (RegularTypeName) type.getTypeName().getTypeName();
							n2 = ngtn.getName();
						}

						if (n2 != null) {
							return ctx2.lookup(n2, level + 1, Result, alreadySearched, one);
						}
					}
				}
				return null;
			}


			return type.getResolved().getElement().getContext().lookup(name, level, Result, alreadySearched, one);
//			else
//				return null;
		}
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
