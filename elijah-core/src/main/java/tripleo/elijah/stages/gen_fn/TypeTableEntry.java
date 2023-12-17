/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.types.OS_UnitType;
import tripleo.elijah.lang.types.OS_UserType;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.DeduceTypes2;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

/**
 * Created 9/12/20 10:26 PM
 */
public class TypeTableEntry {
	public final    IExpression     __debug_expression;
	@NotNull
	public final    Type            lifetime;
	public @NotNull GenType         genType = new GenTypeImpl();
	@Nullable
	public final    TableEntryIV    tableEntry;
	final           int             index;
	private         BaseEvaFunction __gf;
	private         DeduceTypes2    _dt2;
	@Nullable
	private         OS_Type         attached;

	public void _fix_table(final DeduceTypes2 aDeduceTypes2, final @NotNull BaseEvaFunction aEvaFunction) {
		_dt2 = aDeduceTypes2;
		__gf = aEvaFunction;
	}

	public TypeTableEntry(final int index,
						  @NotNull final Type lifetime,
						  @Nullable final OS_Type aAttached,
						  final IExpression expression,
						  @Nullable final TableEntryIV aTableEntryIV) {
		this.index    = index;
		this.lifetime = lifetime;
		if (aAttached == null || (aAttached.getType() == OS_Type.Type.USER && aAttached.getTypeName() == null)) {
			attached = null;
			// do nothing with genType
		} else {
			// README if specified as `Unit' in source, save some energy... (not strictly necessary, but we'll see)
			if (aAttached instanceof OS_UserType ut) {
				if (ut.getTypeName() instanceof RegularTypeName rtn) {
					if (rtn.getName().equals("Unit")) {
						attached = new OS_UnitType();
					}
				}
			} else {
				attached = aAttached;
			}
			if (attached != null)
				_settingAttached(attached);
		}
		this.__debug_expression = expression;
		this.tableEntry         = aTableEntryIV;
	}

	public TypeTableEntry(final int index,
						  @NotNull final Type lifetime,
						  @Nullable final GenType aGenType,
						  final IExpression expression,
						  @Nullable final TableEntryIV aTableEntryIV) {
		this.index    = index;
		this.lifetime = lifetime;

		this.genType.copy(aGenType);
		this.attached = this.genType.getResolved(); // !!

		this.__debug_expression = expression;
		this.tableEntry         = aTableEntryIV;
	}

	public void genTypeCI(ClassInvocation aClsinv) {
		genType.setCi(aClsinv);
	}

	@Deprecated
	private void _settingAttached(@NotNull OS_Type aAttached) {
		switch (aAttached.getType()) {
		case USER:
			if (genType.getTypeName() != null) {
				final TypeName typeName = aAttached.getTypeName();
				if (!(typeName instanceof GenericTypeName))
					genType.setNonGenericTypeName(typeName);
			} else
				genType.setTypeName(aAttached)/* .getTypeName() */;
			break;
		case USER_CLASS:
//			ClassStatement c = attached.getClassOf();
			genType.setResolved(aAttached); // c
			break;
		case UNIT_TYPE:
			genType.setResolved(aAttached);
		case BUILT_IN:
			if (genType.getTypeName() != null)
				genType.setResolved(aAttached);
			else
				genType.setTypeName(aAttached);
			break;
		case FUNCTION:
			assert genType.getResolved() == null || genType.getResolved() == aAttached
					|| /* HACK */ aAttached.getType() == OS_Type.Type.FUNCTION;
			genType.setResolved(aAttached);
			break;
		case FUNC_EXPR:
			assert genType.getResolved() == null || genType.getResolved() == aAttached;// || /*HACK*/
			// aAttached.getType() ==
			// OS_Type.Type.FUNCTION;
			genType.setResolved(aAttached);
			break;
		default:
//			throw new NotImplementedException();
			SimplePrintLoggerToRemoveSoon.println_err_2("73 " + aAttached);
			break;
		}
	}

	public @Nullable OS_Type getAttached() {
		return attached;
	}

	public void setAttached(GenType aGenType) {
		genType.copy(aGenType);

		if (genType instanceof ForwardingGenType)
			((ForwardingGenType) genType).unsparkled();

		setAttached(genType.getResolved());
	}

	public int getIndex() {
		return index;
	}

	@Override
	@NotNull
	public String toString() {
		return "TypeTableEntry{" + "index=" + index + ", lifetime=" + lifetime + ", attached=" + attached
				+ ", expression=" + __debug_expression + '}';
	}

	public void resolve(EvaNode aResolved) {
		genType.setNode(aResolved);
	}

	public EvaNode resolved() {
		return genType.getNode();
	}

	public boolean isResolved() {
		return genType.getNode() != null;
	}

	public enum Type {
		SPECIFIED, TRANSIENT
	}

	public void setAttached(@Nullable OS_Type aAttached) {
		attached = aAttached;
		if (aAttached != null) {
			_settingAttached(aAttached);
		}
	}
}

//
//
//
