header {
  package tripleo.elijjah;
}

{
import tripleo.elijah.lang.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.ci.IndexingStatement;
import tripleo.elijah.lang2.*;
import tripleo.elijah.*;
}

//----------------------------------------------------------------------------
// The Ez tokenizer
//----------------------------------------------------------------------------
class EzParser extends Parser;
options {
	exportVocab=Ez;
//	defaultErrorHandler=false;
	k=2;
}

{
IExpression expr;
Context cur=null;
public CompilerInstructions ci = new CompilerInstructions();
}

program
        {GenerateStatement gen=null;}
    : (indexingStatement[ci.indexingStatement()])?
      ("program"|"library"|"shared") i1:IDENT {ci.setName(i1);}
      library_statement
      gen=generate_statement        {ci.add(gen);}
	  "end"
	  EOF
	;
library_statement
		{LibraryStatementPart lsp=null;}
	: ("lib"|"libraries")
		(lsp=library_statement_part {ci.add(lsp);})*
	;
library_statement_part returns [LibraryStatementPart lsp]
		{lsp=new LibraryStatementPart();}
	: (i1:IDENT TOK_COLON                       {lsp.setName(i1);})?
	  dirname:STRING_LITERAL                    {lsp.setDirName(dirname);}
		(LBRACK
		  (i2:IDENT TOK_COLON expr=expression)* {lsp.addDirective(i2, expr);}
		RBRACK)?
	;
generate_statement returns [GenerateStatement gen]
		{gen=new GenerateStatement();}
	: "generate"
		(i1:IDENT TOK_COLON expr=expression   {gen.addDirective(i1, expr);} )*
	;
indexingStatement[IndexingStatement idx]
		{ExpressionList el=null;}
	: "indexing" 
		(i1:IDENT 			{idx.setName(i1);}
		 TOK_COLON 			//{el=new ExpressionList();}
		 el=expressionList2	{idx.setExprs(el);})*
	;
constantValue returns [IExpression e]
	 {e=null;}
	:s:STRING_LITERAL {e=new StringExpression(s);}
	|c:CHAR_LITERAL   {e=new CharLitExpression(c);}
	|n:NUM_INT        {e=new NumericExpression(n);}
	|f:NUM_FLOAT      {e=new FloatExpression(f);}
	;
/*
primitiveExpression  returns [IExpression e]
		{e=null;ExpressionList el=null;}
	: e=constantValue
	| e=variableReference
	| LBRACK        {e=new ListExpression();}//el=new ExpressionList();}
	    el=expressionList2  {((ListExpression)e).setContents(el);}
	  RBRACK
	;
*/
qualident returns [Qualident q]
    {q=new Qualident();IdentExpression r1=null, r2=null;}
	:
     r1=ident /*ident2 */ {q.append(r1);}
      (d1:DOT r2=ident {q.appendDot(d1); q.append(r2);})*
    ;
docstrings[Documentable sc]:
    ((STRING_LITERAL)=> (s1:STRING_LITERAL {if (sc!=null) sc.addDocString(s1);})+
    |)
    ;
/*
opfal[FormalArgList fal]:
	LPAREN formalArgList[fal] RPAREN
	;
opfal2 returns [FormalArgList fal]
		{fal=new FormalArgList();}
	: LPAREN formalArgList[fal] RPAREN
	;
*/
opt_semi: (SEMI|);
identList[IdentList ail]
		{IdentExpression s=null;}
	: s=ident {ail.push(s);}
		(COMMA s=ident {ail.push(s);})*
	;
expression returns [IExpression ee]
		{ee=null;}
	: ee=assignmentExpression
	;
qualidentList[QualidentList qal]
		{Qualident qid;}
	: qid=qualident {qal.add(qid);} (COMMA qid=qualident {qal.add(qid);})*
	;
ident returns [IdentExpression id]
		{id=null;}
	: r1:IDENT {id=new IdentExpression(r1, cur);}
	;
/*
expressionList[ExpressionList el]
	: expr=expression {el.next(expr);} (COMMA expr=expression {el.next(expr);})*
	;
*/
expressionList2 returns [ExpressionList el]
		{el = new ExpressionList();}
	: expr=expression {el.next(expr);} (COMMA expr=expression {el.next(expr);})*
	;
variableReference returns [IExpression ee]
		{ProcedureCallExpression pcx;ExpressionList el=null;ee=null;IdentExpression r1=null, r2=null;}
	: r1=ident  {ee=r1;}
	( DOT r2=ident {ee=new DotExpression(ee, r2);}
	| LBRACK expr=expression RBRACK {ee=new GetItemExpression(ee, expr);}
	| lp:LPAREN	(el=expressionList2)?
      {ProcedureCallExpression pce=new ProcedureCallExpression();
      pce.identifier(ee);
      pce.setArgs(el);
      ee=pce;} RPAREN
	)
	;

// expressions
// Note that most of these expressions follow the pattern
//   thisLevelExpression :
//       nextHigherPrecedenceExpression
//           (OPERATOR nextHigherPrecedenceExpression)*
// which is a standard recursive definition for a parsing an expression.
// The operators in Elijjah have the following precedences:
//    lowest  (13)  = *= /= %= += -= <<= >>= >>>= &= ^= |=
//            (12)  ?:
//            (11)  ||
//            (10)  &&
//            ( 9)  |
//            ( 8)  ^
//            ( 7)  &
//            ( 6)  == !=
//            ( 5)  < <= > >=
//            ( 4)  << >>
//            ( 3)  +(binary) -(binary)
//            ( 2)  * / %
//            ( 1)  ++ -- +(unary) -(unary)  ~  !  (type)
//                  []   () (method call)  . (dot -- identifier qualification)
//
// Note that the above precedence levels map to the rules below...
// Once you have a precedence chart, writing the appropriate rules as below
//   is usually very straightfoward


// assignment expression (level 13)
assignmentExpression returns [IExpression ee]
		{ee=null;IExpression e=null;IExpression e2;ExpressionKind ek=null;}
	:	ee=conditionalExpression
		(

			(	BECOMES/*^*/				{ek= (ExpressionKind.ASSIGNMENT);}
            |   PLUS_ASSIGN/*^*/		    {ek= (ExpressionKind.AUG_PLUS);}
            |   MINUS_ASSIGN/*^*/			{ek= (ExpressionKind.AUG_MINUS);}
            |   STAR_ASSIGN/*^*/			{ek= (ExpressionKind.AUG_MULT);}
            |   DIV_ASSIGN/*^*/				{ek= (ExpressionKind.AUG_DIV);}
            |   MOD_ASSIGN/*^*/				{ek= (ExpressionKind.AUG_MOD);}
            |   SR_ASSIGN/*^*/				{ek= (ExpressionKind.AUG_SR);}
            |   BSR_ASSIGN/*^*/				{ek= (ExpressionKind.AUG_BSR);}
            |   SL_ASSIGN/*^*/			    {ek= (ExpressionKind.AUG_SL);}
            |   BAND_ASSIGN/*^*/			{ek= (ExpressionKind.AUG_BAND);}
            |   BXOR_ASSIGN/*^*/			{ek= (ExpressionKind.AUG_BXOR);}
            |   BOR_ASSIGN/*^*/				{ek= (ExpressionKind.AUG_BOR);}
            )

			e2=assignmentExpression 		{ ee = ExpressionBuilder.build(ee, ek, e2);}
		)?
	;


// conditional test (level 12)
conditionalExpression returns [IExpression ee]
		{ee=null;}
	:	ee=logicalOrExpression
//		( QUESTION/*^*/ assignmentExpression COLON/*!*/ conditionalExpression )? // TODO ignoring this for now
	;


// logical or (||)  (level 11)
logicalOrExpression returns [IExpression ee]
		{ee=null;
		IExpression e3=null;}
	:	ee=logicalAndExpression
		(LOR/*^*/ e3=logicalAndExpression   	{ee = ExpressionBuilder.build(ee, ExpressionKind.LOR, e3);})*
	;


// logical and (&&)  (level 10)
logicalAndExpression returns [IExpression ee]
		{ee=null;IExpression e3=null;}
	:	ee=inclusiveOrExpression
		(LAND/*^*/ e3=inclusiveOrExpression	{ee = ExpressionBuilder.build(ee, ExpressionKind.LAND, e3);})*
	;


// bitwise or non-short-circuiting or (|)  (level 9)
inclusiveOrExpression returns [IExpression ee]
		{ee=null;IExpression e3=null;}
	:	ee=exclusiveOrExpression
		(BOR/*^*/ e3=exclusiveOrExpression		{ee = ExpressionBuilder.build(ee, ExpressionKind.BOR, e3);})*
	;


// exclusive or (^)  (level 8)
exclusiveOrExpression returns [IExpression ee]
		{ee=null;
		IExpression e3=null;}
	:	ee=andExpression
		(BXOR/*^*/ e3=andExpression				{ee = ExpressionBuilder.build(ee, ExpressionKind.BXOR, e3);})*
	;


// bitwise or non-short-circuiting and (&)  (level 7)
andExpression returns [IExpression ee]
		{ee=null;
		IExpression e3=null;}
	:	ee=equalityExpression
		(BAND/*^*/ e3=equalityExpression	{ee = ExpressionBuilder.build(ee, ExpressionKind.BAND, e3);})*
	;


// equality/inequality (==/!=) (level 6)
equalityExpression  returns [IExpression ee]
		{ee=null;
		ExpressionKind e2=null;
		IExpression e3=null;}
	:	ee=relationalExpression
		((NOT_EQUAL/*^*/                   {e2=ExpressionKind.NOT_EQUAL;}
		| EQUAL/*^*/                       {e2=ExpressionKind.EQUAL;}
		) e3=relationalExpression 				{ee = ExpressionBuilder.build(ee, e2, e3);})*
	;


// boolean relational expressions (level 5)
relationalExpression returns [IExpression ee]
		{ee=null;
		ExpressionKind e2=null; // should never be null (below)
		IExpression e3=null;
		TypeName tn=null;}

	:	ee=shiftExpression
		(	(	(	LT_/*^*/            {e2=ExpressionKind.LT_;}
				|	GT/*^*/             {e2=ExpressionKind.GT;}
				|	LE/*^*/             {e2=ExpressionKind.LE;}
				|	GE/*^*/             {e2=ExpressionKind.GE;}
				)
				e3=shiftExpression      {ee=ExpressionBuilder.build(ee,e2,e3);
										ee.setType(new OS_Type(BuiltInTypes.Boolean));}
			)*
		)
	;


// bit shift expressions (level 4)
shiftExpression returns [IExpression ee]
		{ee=null;ExpressionKind e2=null;
		IExpression e3=null;}
	:	ee=additiveExpression
	((SL/*^*/ {e2=ExpressionKind.LSHIFT;}
	 | SR/*^*/ {e2=ExpressionKind.RSHIFT;}
	 | BSR/*^*/ {e2=ExpressionKind.BSHIFTR;}
	 ) e3=additiveExpression				{ee = ExpressionBuilder.build(ee, e2, e3);})*
	;


// binary addition/subtraction (level 3)
additiveExpression returns [IExpression ee]
		{ee=null;ExpressionKind e2=null;
		IExpression e3=null;}
	:	ee=multiplicativeExpression 
	(
		( PLUS/*^*/  {e2=ExpressionKind.ADDITION;}
		| MINUS/*^*/ {e2=ExpressionKind.SUBTRACTION;}) 
		e3=multiplicativeExpression {ee = ExpressionBuilder.build(ee, e2, e3);})*
	;


// multiplication/division/modulo (level 2)
multiplicativeExpression returns [IExpression ee]
		{ee=null;
		IExpression e3=null;ExpressionKind e2=null;}
	:	ee=unaryExpression 
	((STAR/*^*/ {e2=ExpressionKind.MULTIPLY;}
	| DIV/*^*/  {e2=ExpressionKind.DIVIDE;}
	| MOD/*^*/  {e2=ExpressionKind.MODULO;}
	) e3=unaryExpression {ee = ExpressionBuilder.build(ee, e2, e3);})*
	;

unaryExpression returns [IExpression ee]
		{ee=null;
		IExpression e3=null;}
	:	INC/*^*/ ee=unaryExpression {ee.setKind(ExpressionKind.INCREMENT);}
	|	DEC/*^*/ ee=unaryExpression {ee.setKind(ExpressionKind.DECREMENT);}
	|	MINUS/*^*/ /*{#MINUS.setType(UNARY_MINUS);}*/ ee=unaryExpression {ee.setKind(ExpressionKind.NEG);}
	|	PLUS/*^*/  /*{#PLUS.setType(UNARY_PLUS);}*/ ee=unaryExpression {ee.setKind(ExpressionKind.POS);}
	|	ee=unaryExpressionNotPlusMinus
	;

unaryExpressionNotPlusMinus returns [IExpression ee]
		{ee=null;
		IExpression e3=null;}
	:	BNOT/*^*/ ee=unaryExpression {ee.setKind(ExpressionKind.BNOT);}
	|	LNOT/*^*/ ee=unaryExpression {ee.setKind(ExpressionKind.LNOT);}
	|	ee=postfixExpression
	;

// qualified names, array expressions, method invocation, post inc/dec
postfixExpression returns [IExpression ee]
		{ee=null;TypeCastExpression tc=null;TypeName tn=null;
		IExpression e3=null;ExpressionList el=null;}
	:	ee=primaryExpression // start with a primary

		(	// qualified id (id.id.id.id...) -- build the name
			DOT/*^*/ 
				( ee=dot_expression_or_procedure_call[ee]
//				| "this"
//				| "class"
//				| newExpression
//				| "inherit" LPAREN ( expressionList2 )? RPAREN
				)
			// the above line needs a semantic check to make sure "class"
			//   is the _last_ qualifier.

			// allow ClassName[].class
//		|	( lbc:LBRACK/*^*/ /*{#lbc.setType(ARRAY_DECLARATOR);}*/ RBRACK/*!*/ )+
//			DOT/*^*/ "class"

			// an array indexing operation
		|	lb:LBRACK/*^*/ /*{#lb.setType(INDEX_OP);}*/ expr=expression rb:RBRACK/*!*/
			{ee=new GetItemExpression(ee, expr);((GetItemExpression)ee).parens(lb,rb);}
			( BECOMES expr=expression {ee=new SetItemExpression((GetItemExpression)ee, expr);}
			)?

			// method invocation
		|	lp:LPAREN/*^*/ /*{#lp.setType(METHOD_CALL);}*/
				(el=expressionList2)? 
{ProcedureCallExpression pce=new ProcedureCallExpression();
pce.identifier(ee);
pce.setArgs(el);
ee=pce;}
			RPAREN/*!*/
		)*

		// possibly add on a post-increment or post-decrement.
		// allows INC/DEC on too much, but semantics can check
		(	in:INC/*^*/ {ee.setKind(ExpressionKind.POST_INCREMENT);} /*{#in.setType(POST_INC);}*/
	 	|	de:DEC/*^*/ {ee.setKind(ExpressionKind.POST_DECREMENT);} /*{#de.setType(POST_DEC);}*/
		|	// nothing
		)


	;



dot_expression_or_procedure_call [IExpression e1] returns [IExpression ee]
		{ee=null;ExpressionList el=null;IdentExpression e=null;}
	: e=ident {ee=new DotExpression(e1, e);}

    ( lp2:LPAREN/*^*/ /*{#lp.setType(METHOD_CALL);}*/
      (el=expressionList2)?
            {ProcedureCallExpression pce=new ProcedureCallExpression();
            pce.identifier(ee);
            pce.setArgs(el);
            ee=pce;}
     RPAREN/*!*/)?
	;



// the basic element of an expression
primaryExpression returns [IExpression ee]
		{ee=null;FuncExpr ppc=null;IdentExpression e=null;ExpressionList el=null;}
		//IExpression e3=null;*/}
	:	e=ident {ee=e;}
//	|	newExpression
	|	ee=constantValue
//	|	"super"
	|	"true"
	|	"false"
	|	"this"
	|	"null"
	|	LPAREN/*!*/ ee=assignmentExpression RPAREN/*!*/ {ee=new SubExpression(ee);}
	| LBRACK        {ee=new ListExpression();}//el=new ExpressionList();}
	    el=expressionList2  {((ListExpression)ee).setContents(el);}
	  RBRACK
	;

//----------------------------------------------------------------------------
// The Ez scanner
//----------------------------------------------------------------------------
class EzLexer extends Lexer;

options {
	exportVocab=Ez;      // call the vocabulary "Java"
	testLiterals=false;    // don't automatically test for literals
	k=4;                   // four characters of lookahead
//	charVocabulary='\u0003'..'\uFFFF';
	interactive=true;
}



// OPERATORS
QUESTION		:	'?'		;
LPAREN			:	'('		;
RPAREN			:	')'		;
LBRACK			:	'['		;
RBRACK			:	']'		;
LCURLY			:	'{'		;
RCURLY			:	'}'		;
TOK_COLON		:	':'		;
COMMA			:	','		;
//DOT			:	'.'		;
BECOMES			:	'='		;
EQUAL			:	"=="	;
LNOT			:	'!'		;
BNOT			:	'~'		;
NOT_EQUAL		:	"!="	;
DIV				:	'/'		;
DIV_ASSIGN		:	"/="	;
PLUS			:	'+'		;
PLUS_ASSIGN		:	"+="	;
INC				:	"++"	;
TOK_ARROW  		:   "->"	;
MINUS			:	'-' ('>' 		{_ttype=TOK_ARROW;})?;
MINUS_ASSIGN	:	"-="	;
DEC				:	"--"	;
STAR			:	'*'		;
STAR_ASSIGN		:	"*="	;
MOD				:	'%'		;
MOD_ASSIGN		:	"%="	;
SR				:	">>"	;
SR_ASSIGN		:	">>="	;
BSR				:	">>>"	;
BSR_ASSIGN		:	">>>="	;
GE				:	">="	;
GT				:	">"		;
SL				:	"<<"	;
SL_ASSIGN		:	"<<="	;
LE				:	"<="	;
LT_				:	'<'		;
BXOR			:	'^'		;
BXOR_ASSIGN		:	"^="	;
BOR				:	'|'		;
BOR_ASSIGN		:	"|="	;
LOR				:	"||"	;
BAND			:	'&'		;
BAND_ASSIGN		:	"&="	;
LAND			:	"&&"	;
SEMI			:	';'		;
ANNOT           :   "#["    ;

// Whitespace -- ignored
WS	:	(	' '
		|	'\t'
		|	'\f'
		// handle newlines
		|	(	"\r\n"  // Evil DOS
			|	'\r'    // Macintosh
			|	'\n'    // Unix (the right way)
			)
			{ newline(); }
		)
		{ _ttype = Token.SKIP; }
	;

// Single-line comments
SL_COMMENT
	:	"//"
		(~('\n'|'\r'))* ('\n'|'\r'('\n')?)
		{$setType(Token.SKIP); newline();}
	;

// multiple-line comments
ML_COMMENT
	:	"/*"
		(	/*	'\r' '\n' can be matched in one alternative or by matching
				'\r' in one iteration and '\n' in another.  I am trying to
				handle any flavor of newline that comes in, but the language
				that allows both "\r\n" and "\r" and "\n" to all be valid
				newline is ambiguous.  Consequently, the resulting grammar
				must be ambiguous.  I'm shutting this warning off.
			 */
			options {
				generateAmbigWarnings=false;
			}
		:
			{ LA(2)!='/' }? '*'
		|	'\r' '\n'		{newline();}
		|	'\r'			{newline();}
		|	'\n'			{newline();}
		|	~('*'|'\n'|'\r')
		)*
		"*/"
		{$setType(Token.SKIP);}
	;


// character literals
CHAR_LITERAL
	:	'\'' ( ESC | ~'\'' ) '\''
	;

// string literals
STRING_LITERAL
	:	'"' (ESC|~('"'|'\\'))* '"'
	;


// escape sequence -- note that this is protected; it can only be called
//   from another lexer rule -- it will not ever directly return a token to
//   the parser
// There are various ambiguities hushed in this rule.  The optional
// '0'...'9' digit matches should be matched here rather than letting
// them go back to STRING_LITERAL to be matched.  ANTLR does the
// right thing by matching immediately; hence, it's ok to shut off
// the FOLLOW ambig warnings.
protected
ESC
	:	'\\'
		(	'n'
		|	'r'
		|	't'
		|	'b'
		|	'f'
		|	'"'
		|	'\''
		|	'\\'
		|	('u')+ HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
		|	('0'..'3')
			(
				options {
					warnWhenFollowAmbig = false;
				}
			:	('0'..'7')
				(
					options {
						warnWhenFollowAmbig = false;
					}
				:	'0'..'7'
				)?
			)?
		|	('4'..'7')
			(
				options {
					warnWhenFollowAmbig = false;
				}
			:	('0'..'9')
			)?
		)
	;


// hexadecimal digit (again, note it's protected!)
protected
HEX_DIGIT
	:	('0'..'9'|'A'..'F'|'a'..'f')
	;


// a dummy rule to force vocabulary to be all characters (except special
//   ones that ANTLR uses internally (0 to 2)
protected
VOCAB
	:	'\3'..'\377'
	;


// an identifier.  Note that testLiterals is set to true!  This means
// that after we match the rule, we look in the literals table to see
// if it's a literal or really an identifer
IDENT
	options {testLiterals=true;}
	:	('a'..'z'|'A'..'Z'|'_'|'$') ('a'..'z'|'A'..'Z'|'_'|'0'..'9'|'$')*
	;


// a numeric literal
NUM_INT
	{boolean isDecimal=false;}
	:	'.' {_ttype = DOT;}
			(('0'..'9')+ (EXPONENT)? (FLOAT_SUFFIX)? { _ttype = NUM_FLOAT; })?
	|	(	'0' {isDecimal = true;} // special case for just '0'
			(	('x'|'X')
				(											// hex
					// the 'e'|'E' and float suffix stuff look
					// like hex digits, hence the (...)+ doesn't
					// know when to stop: ambig.  ANTLR resolves
					// it correctly by matching immediately.  It
					// is therefor ok to hush warning.
					options {
						warnWhenFollowAmbig=false;
					}
				:	HEX_DIGIT
				)+
			|	('0'..'7')+									// octal
			)?
		|	('1'..'9') ('0'..'9'|'_')*  {isDecimal=true;}		// non-zero decimal
		)
		(	('l'|'L')
		|   //INTLIT_TY
			('u'|'i') ("8"|"16"|"32"|"64"|"size")
		// only check to see if it's a float if looks like decimal so far
		|	{isDecimal}?
			(	'.' ('0'..'9')* (EXPONENT)? (FLOAT_SUFFIX)?
			|	EXPONENT (FLOAT_SUFFIX)?
			|	FLOAT_SUFFIX
			)
			{ _ttype = NUM_FLOAT; }
		)?
	;

/*
INTLIT_TY
	: ('u'|'i') ("8"|"16"|"32"|"64"|"size")
	;
*/

// a couple protected methods to assist in matching floating point numbers
protected
EXPONENT
	:	('e'|'E') ('+'|'-')? ('0'..'9')+
	;


protected
FLOAT_SUFFIX
	:	'f'|'F'|'d'|'D'
	;

