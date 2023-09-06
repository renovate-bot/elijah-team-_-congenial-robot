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
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_ConstantTableEntry;

/**
 * Created 9/10/20 4:47 PM
 */
public class ConstantTableEntry {
	final         int                               index;
	private final String                            name;
	public final  IExpression                       initialValue;
	private       DeduceElement3_ConstantTableEntry _de3;
	public final  TypeTableEntry                    type;

	public ConstantTableEntry(final int index, final String name, final IExpression initialValue, final TypeTableEntry type) {
		this.index        = index;
		this.name         = name;
		this.initialValue = initialValue;
		this.type         = type;
	}

	public @NotNull DeduceElement3_ConstantTableEntry getDeduceElement3() {
		if (_de3 == null) {
			_de3 = new DeduceElement3_ConstantTableEntry(this);
//			_de3.
		}
		return _de3;
	}

	public String getName() {
		return name;
	}

	public TypeTableEntry getTypeTableEntry() {
		return type;
	}

//    public void setName(String name) {
//        this.name = name;
//    }

	@Override
	public @NotNull String toString() {
		return "ConstantTableEntry{" +
				"index=" + index +
				", name='" + name + '\'' +
				", initialValue=" + initialValue +
				", type=" + type +
				'}';
	}
}

//
//
//
