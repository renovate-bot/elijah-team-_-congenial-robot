/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_congenial.lang.impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.xlang.LocatableString;
import tripleo.elijah_congenial_durable.model.source2.SM2_AccessNotation;
import tripleo.elijah_durable_congenial.lang.i.*;
import tripleo.elijah_durable_congenial.lang2.ElElementVisitor;
import tripleo.elijjah.ElijjahTokenTypes;

/**
 * Created 9/22/20 1:39 AM
 */
// TODO Does this need to be Element?
public class AccessNotationImpl implements OS_Element, AccessNotation {
	private Token        category;
	private Token        shorthand;
	private TypeNameList tnl;

	@Override
	public Token getCategory() {
		return category;
	}

	@Override
	public Context getContext() {
		throw new NotImplementedException();
	}

	@Override
	public OS_Element getParent() {
		throw new NotImplementedException();
	}

	@Override
	public void setCategory(final @Nullable Token category) {
		if (category == null)
			return;
		assert category.getType() == ElijjahTokenTypes.STRING_LITERAL;
		this.category = category;
	}

	@Override
	public void setShortHand(final @Nullable Token shorthand) {
		if (shorthand == null)
			return;
		assert shorthand.getType() == ElijjahTokenTypes.IDENT;
		this.shorthand = shorthand;
	}

	@Override
	public void setTypeNames(final TypeNameList tnl) {
		this.tnl = tnl;
	}

	@Override
	public void visitGen(@NotNull ElElementVisitor visit) {
		visit.visitAccessNotation(this);
	}

	@Override
	public void serializeTo(@NotNull SmallWriter sw) {
		//SmallWriterX.serializeTo(sw, getSourceModel());
		sw.fieldToken("category", category);
		sw.fieldToken("shorthand", shorthand);
		var tnl1 = sw.createTypeNameList();
		for (TypeName iterable_element : tnl.p()) {
			//dszklmfk;
		}
		sw.fieldTypenameList("typeNames", tnl1);

		throw new NotImplementedException();
	}

	private SM2_AccessNotation getSourceModel() {
		var tnl2 = tnl.p().stream()
				.map((TypeName x) ->x.getSourceModel())
				.toList();
		return new SM2_AccessNotation(LocatableString.of(shorthand),
									  LocatableString.of(category),
									  tnl2);
	}
}

//
//
//
