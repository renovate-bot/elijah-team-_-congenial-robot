/*
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 * 
 * The contents of this library are released under the LGPL licence v3, 
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 * 
 */
/* Created on Aug 30, 2005 9:01:37 PM
 *
 * $Id$
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.TypeName;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ClassInheritanceImpl implements tripleo.elijah.lang.i.ClassInheritance {
	public @NotNull List<TypeName> tns = new LinkedList<>();

	/**
	 * Do nothing and wait for addAll or add.
	 * Used by ClassBuilder
	 */
	public ClassInheritanceImpl() {
	}

	@Override
	public void add(final TypeName tn) {
		tns.add(tn);
	}

	@Override
	public void addAll(final @NotNull Collection<TypeName> tns) {
		this.tns.addAll(tns);
	}

	@Override
	public List<TypeName> tns() {
		return this.tns;
	}

}

//
//
//
