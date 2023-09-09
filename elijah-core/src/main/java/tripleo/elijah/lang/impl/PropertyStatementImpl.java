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
import tripleo.elijah.contexts.PropertyStatementContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah.util.Helpers;

/**
 * Created 8/6/20 4:00 PM
 */
public class PropertyStatementImpl implements PropertyStatement {

	private final @NotNull Context         context;
	private final          OS_Element      parent;
	public                 FunctionDef     get_fn;
	public                 FunctionDef     set_fn;
	private                boolean         _get_is_abstract;
	private                boolean         _set_is_abstract;
	private                AccessNotation  access_note;
	private                El_Category     category;
	private                IdentExpression prop_name;
	private                TypeName        typeName;

	public PropertyStatementImpl(OS_Element parent, Context cur) {
		this.parent  = parent;
		this.context = new PropertyStatementContext(cur, this);
	}

	@Override
	public void addGet() {
		_get_is_abstract = true;
	}

	@Override
	public void addSet() {
		_set_is_abstract = true;
	}

	@NotNull
	FunctionDef createGetFunction() {
		FunctionDef functionDef = new FunctionDefImpl(this, getContext());
		functionDef.setName(Helpers.string_to_ident(String.format("<prop_get %s>", prop_name)));
		functionDef.setSpecies(FunctionDef.Species.PROP_GET);
		functionDef.setReturnType(typeName);
		return functionDef;
	}

	@NotNull
	FunctionDef createSetFunction() {
		FunctionDef functionDef = new FunctionDefImpl(this, getContext());
		functionDef.setName(Helpers.string_to_ident(String.format("<prop_set %s>", prop_name)));
		functionDef.setSpecies(FunctionDef.Species.PROP_SET);
		FormalArgList     fal  = new FormalArgListImpl();
		FormalArgListItem fali = fal.next();
		fali.setName(Helpers.string_to_ident("Value"));
		fali.setTypeName(this.typeName);
		RegularTypeName unitType = new RegularTypeNameImpl();
		unitType.setName(Helpers.string_to_qualident("Unit"));
		functionDef.setReturnType(unitType/* BuiltInTypes.Unit */);
		functionDef.setFal(fal);
		return functionDef;
	}

//	public TypeName typeName() {
//		return tn;
//	}

	/*
	 * public Scope get_scope() { throw new NotImplementedException(); // return
	 * get_fn.scope(); }
	 *
	 * public Scope set_scope() { throw new NotImplementedException(); // return
	 * set_fn.scope(); }
	 */

	@Override
	public FunctionDef get_fn() {
		return get_fn;
	}

	@Override
	public void get_scope(Scope3 sco) {
		get_fn.scope(sco);
	}

	@Override
	public TypeName getTypeName() {
		return typeName;
	}

	@Override
	public FunctionDef set_fn() {
		return set_fn;
	}

	@Override
	public void set_scope(Scope3 sco) {
		set_fn.scope(sco);
	}

	@Override
	public void setName(IdentExpression prop_name) {
		this.prop_name = prop_name;
	}

	@Override
	public void setTypeName(TypeName typeName) {
//		tripleo.elijah.util.Stupidity.println_err_2("** setting TypeName in PropertyStatement to "+typeName);
		this.typeName = typeName;
		this.set_fn   = createSetFunction();
		this.get_fn   = createGetFunction();
	}

	// region ClassItem

	@Override
	public AccessNotation getAccess() {
		return access_note;
	}

	@Override
	public El_Category getCategory() {
		return category;
	}

	@Override
	public void setCategory(El_Category aCategory) {
		category = aCategory;
	}

	@Override
	public void setAccess(AccessNotation aNotation) {
		access_note = aNotation;
	}

	@Override // OS_Element
	public Context getContext() {
		return context;
	}

	@Override // OS_Element
	public OS_Element getParent() {
		return parent;
	}

	// endregion

	@Override
	public @NotNull EN_Name getEnName() {
		if (__n == null) {
			__n = EN_Name_.create(name());
		}
		return __n;
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}

	@Override
	public OS_ElementName name() {
		return OS_ElementName_.ofString(prop_name.getText());
	}

	@Override // OS_Element
	public void visitGen(@NotNull ElElementVisitor visit) {
		visit.visitPropertyStatement(this);
	}

	private EN_Name __n;

}

//
//
//
