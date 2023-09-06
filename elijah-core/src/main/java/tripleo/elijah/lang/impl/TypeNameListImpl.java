/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.util.Helpers;

import java.util.ArrayList;
import java.util.List;

public class TypeNameListImpl implements tripleo.elijah.lang.i.TypeNameList {

	@NotNull List<TypeName> p = new ArrayList<TypeName>();

	@Override
	public void add(final TypeName tn) {
		p.add(tn);
	}

	@Override
	public TypeName get(int index) {
		return p.get(index);
	}

	@Override
	public List<TypeName> p() {
		return p;
	}

	@Override
	public int size() {
		return p.size();
	}

	@Override
	public @NotNull String toString() {
		return Helpers.String_join(", ", Collections2.transform(p, new Function<TypeName, String>() {
			@Nullable
			@Override
			public String apply(@Nullable TypeName input) {
				assert input != null;
				return input.toString();
			}
		}));
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
