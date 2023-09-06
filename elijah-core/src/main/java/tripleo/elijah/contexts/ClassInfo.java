/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.contexts;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.NormalTypeName;

/**
 * Created 11/29/21 12:24 AM
 */
public class ClassInfo implements ContextInfo {
	private final @Nullable ClassStatement classStatement;

	private final           ClassInfoType  classInfoType;
	private final @Nullable NormalTypeName typeName;

	public @Nullable ClassStatement getClassStatement() {
		return classStatement;
	}

	public ClassInfo(final ClassStatement aClassStatement, final ClassInfoType aClassInfoType) {
		classStatement = aClassStatement;
		classInfoType  = aClassInfoType;
		typeName       = null;
	}

	public ClassInfo(final NormalTypeName aTypeName, final ClassInfoType aClassInfoType) {
		classStatement = null;
		classInfoType  = aClassInfoType;
		typeName       = aTypeName;
	}

	public ClassInfoType getClassInfoType() {
		return classInfoType;
	}

	public enum ClassInfoType {
		DIRECT, GENERIC, INHERITED
	}

	public @Nullable NormalTypeName getTypeName() {
		return typeName;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
