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
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.stages.instructions.IntegerIA;

/**
 * Created 11/27/21 9:01 AM
 */
public class GenericElementHolderWithIntegerIA implements IElementHolder {
	private final @Nullable OS_Element element;
	private final @NotNull  IntegerIA  integerIA;

	public GenericElementHolderWithIntegerIA(final @Nullable OS_Element aElement, final @NotNull IntegerIA aIntegerIA) {
		element   = aElement;
		integerIA = aIntegerIA;
	}

	@Override
	public @Nullable OS_Element getElement() {
		return element;
	}

	public @NotNull IntegerIA getIntegerIA() {
		return integerIA;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
