/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.typinf;

// Implementation of type inference.
//
// Eli Bendersky [http://eli.thegreenplace.net]
// This code is in the public domain.

import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.NotImplementedException;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * Created 9/3/21 12:40 AM
 */
public class TypInf {
	/**
	 * Unifies variable v with type typ, using subst.
	 * <p>
	 * Returns updated subst or None on failure.
	 *
	 * @param v
	 * @param typ
	 * @param subst
	 * @return
	 */
	static @Nullable HashMap<String, Type> unify_variable(@NotNull TypeVar v, Type typ, @NotNull HashMap<String, Type> subst) {
		if (subst.containsKey(v.name)) {
			return unify(subst.get(v.name), typ, subst);
		} else if ((typ instanceof TypeVar) && subst.containsKey(((TypeVar) typ).name)) {
			return unify(v, subst.get(((TypeVar) typ).name), subst);
		} else if (occurs_check(v, typ, subst)) {
			return null;
		} else {
			// v is not yet in subst and can't simplify x. Extend subst.
			var name = v.name;
			return dict_combine(subst, mapping(name, typ));
		}
	}

	private static @NotNull HashMap<String, Type> dict_combine(@NotNull HashMap<String, Type> a, HashMap<String, Type> b) {
		HashMap<String, Type> r = new HashMap<String, Type>();
		for (Map.Entry<String, Type> entry : a.entrySet()) {
			r.put(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<String, Type> entry : b.entrySet()) {
			r.put(entry.getKey(), entry.getValue());
		}
		return r;
	}

	static <K, V> @NotNull HashMap<K, V> mapping(K k, V v) {
		HashMap<K, V> r = new HashMap<K, V>();
		r.put(k, v);
		return r;
	}

	/**
	 * Unifies all type equations in the sequence eqs.
	 * <p>
	 * Returns a substitution (most general unifier).
	 *
	 * @param eqs
	 * @return
	 */
	public static HashMap<String, Type> unify_all_equations(@NotNull List<TypeEquation> eqs) {
		HashMap<String, Type> subst = new HashMap<>();
		for (TypeEquation eq : eqs) {
			subst = unify(eq.left, eq.right, subst);
			if (subst == null) {
				break;
			}
		}
		return subst;
	}

	/**
	 * Finds the type of the expression given a substitution.
	 * <p>
	 * If rename_types is True, renames all the type vars to be sequential
	 * characters starting from 'a', so that 't5 -> t3' will be renamed to
	 * 'a -> b'. These names are less cluttery and also facilitate testing.
	 * <p>
	 * Note: expr should already be annotated with assign_typenames.
	 *
	 * @param expr
	 * @param subst
	 * @param rename_types
	 * @return
	 */
	public static Type get_expression_type(@NotNull AstNode expr, HashMap<String, Type> subst, boolean rename_types) {
		Type typ = apply_unifier(expr.get_type(), subst);
		if (rename_types) {
			Counter                 namecounter = new Counter();
			HashMap<String, String> namemap     = new HashMap<String, String>();
			rename_type(typ, namemap, namecounter);
		}
		return typ;
	}

	static void rename_type(Type typ, HashMap<String, String> namemap, Counter namecounter) {
		if (typ instanceof TypeVar) {
			if (namemap.containsKey(((TypeVar) typ).name)) {
				((TypeVar) typ).name = namemap.get(((TypeVar) typ).name);
			} else {
				String name = String.format("a%d", namecounter.next());

				namemap.put(((TypeVar) typ).getName(), name);
				namemap.put(name, name);
				((TypeVar) typ).name = namemap.get(((TypeVar) typ).name);
			}
		} else if (typ instanceof FuncType) {
			rename_type(((FuncType) typ).rettype, namemap, namecounter);
			for (Type argtyp : ((FuncType) typ).argtypes) {
				rename_type(argtyp, namemap, namecounter);
			}
		}
	}

	// A symbol table is used to map symbols to types throughout the inference
	// process. Example:
	//
	//  > eight = 8
	//  > nine = 9
	//  > foo a = if a == 0 then eight else nine
	//
	// When inferring the type for 'foo', we already have 'eight' and 'nine' assigned
	// to IntType in the symbol table. Also, inside the definition of 'foo' we have
	// 'a' assigned a TypeVar type (since the type of 'a' is initially unknown).

	// Stages:
	//
	// 1. Visit the AST and assign types to all nodes: known types to constant nodes
	//    and fresh typevars to all other nodes. The types are placed in the _type
	//    attribute of each node.
	// 2. Visit the AST again, this time applying type inference rules to generate
	//    equations between the types. The result is a list of equations, all of
	//    which have to be satisfied.
	// 3. Find the most general unifier (solution) for these equations by using
	//    the classical unification algorithm.

	// Global counter to produce unique type names.
	static Counter _typecounter = new Counter();

	static class Counter {
		int i = 0;

		public int next() {
			int j = i;
			i++;
			return j;
		}
	}

	/**
	 * Creates a fresh typename that will be unique throughout the program.
	 *
	 * @return
	 */
	static String _get_fresh_typename() {
		return String.format("t%d", _typecounter.next());
	}

	/**
	 * This function is useful for determinism in tests.
	 */
	public static void reset_type_counter() {
		_typecounter = new Counter();
	}

	public static void assign_typenames(AstNode node) {
		assign_typenames(node, new HashMap<String, Type>());
	}

	/**
	 * Assign typenames to the given AST subtree and all its children.
	 * <p>
	 * Symtab is the initial symbol table we can query for identifiers found
	 * throughout the subtree. All identifiers in the subtree must be bound either
	 * in symtab or in lambdas contained in the subtree.
	 * <p>
	 * This function doesn't return anything, but it updates the _type property
	 * on the AST nodes it visits.
	 */
	static void assign_typenames(AstNode node, HashMap<String, Type> symtab) throws TypingError {
		if (node instanceof Identifier_AST identifier) {
			// Identifier nodes are treated specially, as they have to refer to
			// previously defined identifiers in the symbol table.
			final String identifier_name = identifier.name;
			if (symtab.containsKey(identifier_name)) {
				node.set_type(symtab.get(identifier_name));
			} else {
				throw new TypingError(String.format("unbound name \"%s\"", identifier_name));
			}
		} else if (node instanceof final LambdaExpr_AST lambdaExpr) {

			lambdaExpr.set_type(new TypeVar(_get_fresh_typename()));
			HashMap<String, Type> local_symtab = new HashMap<>();
			for (String argname : lambdaExpr.argnames) {
				String typename = _get_fresh_typename();
				local_symtab.put(argname, new TypeVar(typename));
			}
			lambdaExpr._arg_types = local_symtab;
			assign_typenames(lambdaExpr.expr, dict_combine(symtab, local_symtab));
		} else if ((node instanceof OpExpr_AST)) {
			node.set_type(new TypeVar(_get_fresh_typename()));
			node.visit_children(c -> assign_typenames(c, symtab));
		} else if ((node instanceof IfExpr_AST)) {
			node.set_type(new TypeVar(_get_fresh_typename()));
			node.visit_children(c -> assign_typenames(c, symtab));
		} else if ((node instanceof AppExpr_AST)) {
			node.set_type(new TypeVar(_get_fresh_typename()));
			node.visit_children(c -> assign_typenames(c, symtab));
		} else if ((node instanceof IntConstant_AST)) {
			node.set_type(new IntType());
		} else if ((node instanceof BoolConstant_AST)) {
			node.set_type(new BoolType());
		} else {
			throw new TypingError(String.format("unknown node %s", node.getClass().getName()));
		}
	}

	/**
	 * Generate type equations from node and place them in type_equations.
	 * <p>
	 * Prior to calling this functions, node and its children already have to
	 * be annotated with _type, by a prior call to assign_typenames.
	 *
	 * @param node
	 */
	public static void generate_equations(AstNode node, List<TypeEquation> type_equations) {
		if ((node instanceof IntConstant_AST)) {
			type_equations.add(new TypeEquation(node.get_type(), new IntType(), node));
		} else if ((node instanceof BoolConstant_AST)) {
			type_equations.add(new TypeEquation(node.get_type(), new BoolType(), node));
		} else if ((node instanceof Identifier_AST)) {
			// Identifier references add no equations.
			//pass
		} else if ((node instanceof final OpExpr_AST opExpr)) {

			node.visit_children(c -> generate_equations(c, type_equations));
			// All op arguments are integers.
			type_equations.add(new TypeEquation(opExpr.left.get_type(), new IntType(), node));
			type_equations.add(new TypeEquation(opExpr.right.get_type(), new IntType(), node));
			// Some ops return boolean, and some return integer.
			if (List_of("!=", "==", ">=", "<=", ">", "<").contains(opExpr.op)) {
				type_equations.add(new TypeEquation(node.get_type(), new BoolType(), node));
			} else {
				type_equations.add(new TypeEquation(node.get_type(), new IntType(), node));
			}
		} else if (node instanceof final AppExpr_AST appExpr) {

			node.visit_children(c -> generate_equations(c, type_equations));
			List<Type> argtypes = appExpr.args.stream().map(name -> appExpr.get_type()).collect(Collectors.toList());

			// An application forces its function's type.
			type_equations.add(new TypeEquation(appExpr.func.get_type(), new FuncType(argtypes, node.get_type()), node));
		} else if (node instanceof final IfExpr_AST ifExpr) {

			ifExpr.visit_children(c -> generate_equations(c, type_equations));
			type_equations.add(new TypeEquation(ifExpr.ifexpr.get_type(), new BoolType(), node));
			type_equations.add(new TypeEquation(ifExpr.get_type(), ifExpr.thenexpr.get_type(), node));
			type_equations.add(new TypeEquation(ifExpr.get_type(), ifExpr.elseexpr.get_type(), node));
		} else if (node instanceof final LambdaExpr_AST lambdaExpr) {

			node.visit_children(c -> generate_equations(c, type_equations));
			List<Type> argtypes = lambdaExpr.argnames.stream().map(name -> lambdaExpr._arg_types.get(name)).collect(Collectors.toList());
			type_equations.add(new TypeEquation(node.get_type(), new FuncType(argtypes, lambdaExpr.expr.get_type()), node));
		} else {
			throw new TypingError(String.format("unknown node %s", node.getClass().getName()));
		}

	}

	/**
	 * Unify two types typ_x and typ_y, with initial subst.
	 * <p>
	 * Returns a subst (map of name->Type) that unifies typ_x and typ_y, or None if
	 * they can't be unified. Pass subst={} if no subst are initially
	 * known. Note that {} means valid (but empty) subst.
	 *
	 * @param typ_x
	 * @param typ_y
	 * @param subst
	 * @return
	 */
	static HashMap<String, Type> unify(Type typ_x, Type typ_y, HashMap<String, Type> subst) {
		if (subst == null) {
			return null;
		} else if (typ_x.equals(typ_y)) {
			return subst;
		} else if ((typ_x instanceof TypeVar)) {
			return unify_variable((TypeVar) typ_x, typ_y, subst);
		} else if ((typ_y instanceof TypeVar)) {
			return unify_variable((TypeVar) typ_y, typ_x, subst);
		} else if ((typ_x instanceof final FuncType funcType_x) && (typ_y instanceof final FuncType funcType_y)) {

			if (funcType_x.argtypes.size() != (funcType_y.argtypes.size())) {
				return null;
			} else {
				subst = unify(funcType_x.rettype, funcType_y.rettype, subst);
				for (int i = 0; i < funcType_x.argtypes.size(); i++) {
					subst = unify(funcType_x.argtypes.get(i), funcType_y.argtypes.get(i), subst);
				}
				return subst;
			}
		} else {
			return null;
		}
	}

	/**
	 * Does the variable v occur anywhere inside typ?
	 * <p>
	 * Variables in typ are looked up in subst and the check is applied
	 * recursively.
	 *
	 * @param v
	 * @param subst
	 * @return
	 */
	static boolean occurs_check(TypeVar v, Object typ, HashMap<String, Type> subst) {
		if (v.equals(typ)) {
			return true;
		} else if (typ instanceof final TypeVar typeVar && subst.containsKey(((TypeVar) typ).name)) {

			return occurs_check(v, subst.get(typeVar.name), subst);
		} else if (typ instanceof final FuncType funcType) {

			if (occurs_check(v, funcType.rettype, subst)) return true;

			for (Type arg : funcType.argtypes) {
				if (occurs_check(v, arg, subst)) return true;
			}
		} else {
			return false;
		}
		return false;
	}

	/**
	 * Applies the unifier subst to typ.
	 * <p>
	 * Returns a type where all occurrences of variables bound in subst
	 * were replaced (recursively); on failure returns None.
	 *
	 * @param typ
	 * @param subst
	 * @return
	 */
	static Type apply_unifier(Type typ, HashMap<String, Type> subst) {
		if (subst == null) {
			return null;
		} else if ((subst.size()) == 0) {
			return typ;
		} else if (typ instanceof BoolType || typ instanceof IntType) {
			return typ;
		} else if (typ instanceof TypeVar) {
			if (subst.containsKey(((TypeVar) typ).name)) return apply_unifier(subst.get(((TypeVar) typ).name), subst);
			else return typ;
		} else if ((typ instanceof FuncType)) {
			List<Type> newargtypes = ((FuncType) typ).argtypes.stream().map(arg -> apply_unifier(arg, subst)).collect(Collectors.toList());
			return new FuncType(newargtypes, apply_unifier(((FuncType) typ).rettype, subst));
		} else {
			return null;
		}
	}

	/**
	 * Show a type assignment for the given subtree, as a table.
	 *
	 * @param node the given subtree
	 * @return Returns a string that shows the assigmnent.
	 */
	String show_type_assignment(AstNode node) {
		List<String> lines = new LinkedList<>();

		show_rec(node, lines);
		return Helpers.String_join("\n", lines);
	}

	void show_rec(AstNode node, @NotNull List<String> lines) {
		lines.add(String.format("%60s %s", node, node.get_type()));
		node.visit_children(node1 -> show_rec(node1, lines));
	}

	Type get_expression_type(AstNode expr, HashMap<String, Type> subst) {
		return get_expression_type(expr, subst, false);
	}

	/**
	 * A type equation between two types: left and right.
	 * <p>
	 * orig_node is the original AST node from which this equation was derived, for
	 * debugging.
	 */
	public record TypeEquation(Type left, Type right, AstNode orig_node) {
		@Override
		public String toString() {
			return String.format("%s :: %s [from %s]", left, right, orig_node);
		}
	}

	/**
	 * Function (n-ary) type.
	 *
	 * <p>Encapsulates a sequence of argument types and a single return type.</p>
	 */
	public record FuncType(List<Type> argtypes, Type rettype) implements Type {
		@Override
		public String toString() {
			if (argtypes.size() == 1) {
				return String.format("(%s -> %s)", argtypes.get(0), rettype);
			} else {
				return String.format("((%s) -> %s)", Helpers.String_join(", ", argtypes.stream().map(t -> t.toString()).collect(Collectors.toList())), rettype);
			}
		}

		//@Override
		public boolean equals__(Object aO) {
			/*
			    def __eq__(self, other):
        			return (type(self) == type(other) and
                		self.rettype == other.rettype and
               	 			all(self.argtypes[i] == other.argtypes[i]
                    			for i in range(len(self.argtypes))))

			*/
			if (this == aO) return true;
			if (aO == null || getClass() != aO.getClass()) return false;
			FuncType funcType = (FuncType) aO;
			return Objects.equals(argtypes, funcType.argtypes) && Objects.equals(rettype, funcType.rettype);
		}
	}

	@Data
	public static class TypeVar implements Type {
		@Getter
		private String name;

		public TypeVar(final String aTypename) {
			name = aTypename;
		}

		public String getName() {
			//	throw new NotImplementedException("should be lombok");
			return this.name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public static class TypingError extends RuntimeException {
		public TypingError(String aS) {
			super(aS);
		}
	}

}

//
//
//
