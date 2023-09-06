/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.typinf.parser;

import tripleo.elijah.typinf.*;
import tripleo.elijah.typinf.lexer.Lexer;
import tripleo.elijah.typinf.lexer.Token;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * Parser for micro-ML.
 * <p>
 * The only public method here is parse_decl that parses a "decl" from a
 * string. Usage:
 * <p>
 * p = Parser()
 * decl = p.parse_decl(<some micro-ML code>)
 * # decl is now an ast.Decl node
 * <p>
 * parse_decl() can be called multiple times with the same parser to parse
 * multiple decls (state is wiped out between calls).
 * <p>
 * Created 9/3/21 9:50 PM
 */
public class Parser {
	private final Lexer        lexer;
	private       Token        cur_token;
	private final List<String> operators;

	public Parser() {
		List<List<String>> lex_rules = List_of(
				List_of("if", "IF"),
				List_of("then", "THEN"),
				List_of("else", "ELSE"),
				List_of("true", "TRUE"),
				List_of("false", "FALSE"),
				List_of("lambda", "LAMBDA"),
				List_of("\\d+", "INT"),
				List_of("->", "ARROW"),
				List_of("!=", "!="),
				List_of("==", "=="),
				List_of(">=", ">="),
				List_of("<=", "<="),
				List_of("<", "<"),
				List_of(">", ">"),
				List_of("\\+", "+"),
				List_of("\\-", "-"),
				List_of("\\*", "*"),
				List_of("\\(", "("),
				List_of("\\)", ")"),
				List_of("=", "="),
				List_of(",", ","),
				List_of("[a-zA-Z_]\\w*", "ID")
											  );
		this.lexer = new Lexer(lex_rules, true);
		cur_token  = null;
		operators  = /*Set*/List_of("!=", "==", ">=", "<=", "<", ">", "+", "-", "*");
	}

//		static <E> Set<E> Set_of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
//			return new ImmutableCollections.SetN<>(e1, e2, e3, e4, e5,
//					e6, e7, e8, e9);
//		}

	/**
	 * Parse declaration given in text and return an AST node for it.
	 */
	public Decl_AST parse_decl(String text) throws ParseError {
		this.lexer.input(text);
		this._get_next_token();
		Decl_AST decl = this._decl();
		if (this.cur_token.type != null)
			this._error(String.format("Unexpected token \"%s\" (at #%d)",
									  this.cur_token.val, this.cur_token.pos));
		return decl;
	}

	private void _error(String msg) throws ParseError {
		throw new ParseError(msg);
	}

	/**
	 * Advances the parser's internal lexer to the next token.
	 * <p>
	 * This method doesn't return anything; it assigns self.cur_token to the
	 * next token in the input stream.
	 */
	private void _get_next_token() throws ParseError {
		try {
			cur_token = lexer.token();

			if (cur_token == null)
				cur_token = new Token(null, null, 0);
		} catch (tripleo.elijah.typinf.lexer.LexerError e) {
			_error(String.format("Lexer error at position %d: %s", e.pos, e));
		}

	}

	/**
	 * The 'match' primitive of RD parsers.
	 * <p>
	 * * Verifies that the current token is of the given type
	 * * Returns the value of the current token
	 * * Reads in the next token
	 *
	 * @return
	 */
	String _match(String type) throws ParseError {
		if (this.cur_token.type.equals(type)) {
			String val = this.cur_token.val;
			this._get_next_token();
			return val;
		} else {
			this._error(String.format("Unmatched %s (found %s)", type, this.cur_token.type));
		}
		// never reached
		return null;
	}

	Decl_AST _decl() throws ParseError {
		String       name     = this._match("ID");
		List<String> argnames = List_of();

		// If we have arguments, collect them. Only IDs allowed here.;
		while (this.cur_token.type.equals("ID")) {
			argnames.add(this.cur_token.val);
			this._get_next_token();
		}

		this._match("=");
		AstNode expr = this._expr();
		if (len(argnames) > 0) {
			return new Decl_AST(name, new LambdaExpr_AST(argnames, expr));
		} else {
			return new Decl_AST(name, expr);
		}
	}

	private int len(List<String> aList) {
		return aList.size();
	}

	/**
	 * Parse an expr of the form{
	 * <p>
	 * expr op expr;
	 * <p>
	 * We only allow a single operator between expressions. Additional;
	 * operators should be nested using parens, e.g. x + (y * z);
	 *
	 * @return
	 */
	AstNode _expr() throws ParseError {
		AstNode node = this._expr_component();
		if (this.operators.contains(this.cur_token.type)) {
			String op = this.cur_token.type;
			this._get_next_token();
			AstNode rhs = this._expr_component();
			return new OpExpr_AST(op, node, rhs);
		} else {
			return node;
		}
	}

	/**
	 * Parse an expr component (components can be separated by an operator).
	 */
	AstNode _expr_component() throws ParseError {
		Token curtok = this.cur_token;
		if (this.cur_token.type.equals("INT")) {
			this._get_next_token();
			return new IntConstant_AST(curtok.val);
		} else if (List_of("FALSE", "TRUE").contains(this.cur_token.type)) {
			this._get_next_token();
			return new BoolConstant_AST(curtok.val);
		} else if (cur_token.type.equals("ID")) {
			this._get_next_token();
			if (cur_token.type != null && cur_token.type.equals("(")) {
				// ID followed by '(' is function application;
				return _app(curtok.val);
			} else {
				return new Identifier_AST(curtok.val);
			}
		} else if (cur_token.type == "(") {
			_get_next_token();
			AstNode expr = _expr();
			_match(")");
			return expr;
		} else if (this.cur_token.type == "IF") {
			return this._ifexpr();
		} else if (this.cur_token.type == "LAMBDA") {
			return this._lambda();
		} else {
			this._error(String.format("Don't support %s yet", curtok.type));
		}
		throw new IllegalStateException();
	}

	IfExpr_AST _ifexpr() throws ParseError {
		this._match("IF");
		AstNode ifexpr = this._expr();
		this._match("THEN");
		AstNode thenexpr = this._expr();
		this._match("ELSE");
		AstNode elseexpr = this._expr();
		return new IfExpr_AST(ifexpr, thenexpr, elseexpr);
	}

	LambdaExpr_AST _lambda() throws ParseError {
		this._match("LAMBDA");
		List<String> argnames = List_of();

		while (this.cur_token.type.equals("ID")) {
			argnames.add(this.cur_token.val);
			this._get_next_token();
		}
		if (len(argnames) < 1) {
			this._error("Expected non-empty argument list for lambda");
		}
		this._match("ARROW");
		AstNode expr = this._expr();
		return new LambdaExpr_AST(argnames, expr);
	}

	AppExpr_AST _app(String name) throws ParseError {
		this._match("(");
		List<AstNode> args = List_of();
		while (!this.cur_token.type.equals(")")) {
			args.add(this._expr());
			if (this.cur_token.type.equals(",")) {
				this._get_next_token();
			} else if (this.cur_token.type.equals(")")) {
				//pass // the loop will break;
			} else {
				this._error(String.format("Unexpected %s in application", this.cur_token.val));
			}
		}
		this._match(")");
		return new AppExpr_AST(new Identifier_AST(name), args);
	}

}

//
//
//
