/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 8/22/21 16:22
 */
public class ClassHeaderImpl implements tripleo.elijah.lang.i.ClassHeader {
	List<AnnotationClause> annos = new ArrayList<>();
	boolean                extends_;
	TypeNameList           genericPart;
	@NotNull ClassInheritance inh = new ClassInheritanceImpl();
	private  boolean          isConst;
	IdentExpression nameToken;
	ClassTypes      type;

	public ClassHeaderImpl(boolean aExtends, List<AnnotationClause> as) {
		extends_ = aExtends;
		annos    = as;
	}

	@Override
	public List<AnnotationClause> annos() {
		return annos;
	}

	@Override
	public TypeNameList genericPart() {
		return this.genericPart;
	}

	@Override
	public ClassInheritance inheritancePart() {
		return inh;
	}

	@Override
	public IdentExpression nameToken() {
		return nameToken;
	}

	@Override
	public void setConst(boolean aIsConst) {
		isConst = aIsConst;
	}

	@Override
	public void setGenericPart(final TypeNameList aTypeNameList) {
		genericPart = aTypeNameList;
	}

	@Override
	public void setName(final IdentExpression aNameToken) {
		nameToken = aNameToken;
	}

	@Override
	public void setType(ClassTypes ct) {
		type = ct;
	}

	@Override
	public ClassTypes type() {
		return type;
	}
}

//
//
//
