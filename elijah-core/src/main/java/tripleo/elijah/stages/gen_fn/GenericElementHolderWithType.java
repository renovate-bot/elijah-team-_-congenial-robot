/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
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
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.stages.deduce.DeduceTypes2;

/**
 * Created 11/18/21 8:43 PM
 */
public class GenericElementHolderWithType implements IElementHolder {
	private final          DeduceTypes2 dt2;
	private final @NotNull OS_Element   element;
	private final          OS_Type      type;

	public GenericElementHolderWithType(final @NotNull OS_Element aElement,
										final OS_Type aType,
										final DeduceTypes2 aDeduceTypes2) {
		element = aElement;
		type    = aType;
		dt2     = aDeduceTypes2;
	}

	public DeduceTypes2 getDeduceTypes2() {
		return dt2;
	}

	@Override
	public @NotNull OS_Element getElement() {
		return element;
	}

	public OS_Type getType() {
		return type;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
