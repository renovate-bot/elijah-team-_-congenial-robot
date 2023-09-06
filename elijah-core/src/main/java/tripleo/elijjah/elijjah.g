header {
  package tripleo.elijjah;
}

{
import java.util.List;
import java.util.ArrayList;
import tripleo.elijah.lang.*;
import tripleo.elijah.lang.builder.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.imports.*;
import tripleo.elijah.lang2.*;
import tripleo.elijah.*;
}

class ElijjahParser extends Parser;
options {
	exportVocab=Elijjah;
//	defaultErrorHandler=false;
	k=2;
//	buildAST = true;
}

tokens {
    AS="as"; CAST_TO="cast_to";
}

{
Qualident xy;
public Out out;
IExpression expr;
Context cur;
Scope3 sco;
}

program
        {ParserClosure pc = out.closure();
         ModuleContext mctx=new ModuleContext(out.module());
         out.module().setContext(mctx);cur=mctx;
         IndexingStatement idx=null;
         OS_Package pkg;}
    : (
		idx=indexingStatement				{out.module().setIndexingStatement(idx);}
	  )?
	  (
	    "package" xy=qualident opt_semi 	{pkg=pc.defaultPackageName(xy);cur=new PackageContext(cur, pkg);pkg.setContext((PackageContext) cur);}
	  | programStatement[/*pc,*/ out.module()] opt_semi
	  )*
	  EOF {out.module().postConstruct();out.FinishModule();}
	;
indexingStatement returns [IndexingStatement idx]
		{ExpressionList el=null;idx=null;IndexingItem item;}
	: "indexing" 				{idx=new IndexingStatement(out.module());}
		(i1:IDENT
		 TOK_COLON 			    
		 el=expressionList		{item=new IndexingItem(i1, el);idx.add(item);})*
	;
constantValue returns [IExpression e]
	 {e=null;}
	:s:STRING_LITERAL {e=new StringExpression(s);}
	|c:CHAR_LITERAL   {e=new CharLitExpression(c);}
	|n:NUM_INT        {e=new NumericExpression(n);}
	|f:NUM_FLOAT      {e=new FloatExpression(f);}
	;
qualident returns [Qualident q]
    {q=new Qualident();IdentExpression r1=null, r2=null;}
	:
     r1=ident {q.append(r1);}
      (d1:DOT r2=ident {q.appendDot(d1); q.append(r2);})*
    ;

class_header [List<AnnotationClause> as] returns [ClassHeader ch]
		{ch=null;boolean extends_=false;IdentExpression class_name=null;TypeNameList tnl=null;}
	: ("extend"			{extends_=true;})?
	  "class"			{ch = new ClassHeader(extends_, as);}
	  class_modifier[ch]
      class_name=ident	{ch.setName(class_name);}
	  ( LBRACK tnl=typeNameList2 RBRACK { ch.setGenericPart(tnl);})?
      (class_inheritance[ch] )?
      ("const"			{ch.setConst(true);})?
	;

class_modifier [ClassHeader ch]
	: ("struct"       	{ch.setType(ClassTypes.STRUCTURE);}
	  |"signature"     	{ch.setType(ClassTypes.SIGNATURE);}
	  |"interface"     	{ch.setType(ClassTypes.INTERFACE);}
	  |"abstract"     	{ch.setType(ClassTypes.ABSTRACT);}
	  |"annotation"   	{ch.setType(ClassTypes.ANNOTATION);}
	  |"exception"    	{ch.setType(ClassTypes.EXCEPTION);}
	  |				   	{ch.setType(ClassTypes.NORMAL);}
	  )
	;

class_inheritance [ClassHeader ch]
	: LPAREN classInheritance_ [ch.inheritancePart()] RPAREN
    | classInheritanceRuby     [ch.inheritancePart()]
	;

classStatement [OS_Element parent, Context cctx, List<AnnotationClause> as] returns [ClassStatement cls]
		{cls=null;ClassContext ctx=null;IdentExpression i1=null;TypeNameList tnl=null;
		ClassHeader ch=null;}
	:
	ch=class_header[as]		{cls = new ClassStatement(parent, cctx);cls.setHeader(ch);}
/*    ("class"				{cls = new ClassStatement(parent, cctx);cls.addAnnotations(as);}
            ("struct"       {cls.setType(ClassTypes.STRUCTURE);}
            |"signature"    {cls.setType(ClassTypes.SIGNATURE);}
            |"abstract"     {cls.setType(ClassTypes.ABSTRACT);}
            |			    {cls.setType(ClassTypes.NORMAL);})?
      i1=ident              {cls.setName(i1);}
	  ( LBRACK tnl=typeNameList2 RBRACK { cls.setGenericPart(tnl);})?
    ((LPAREN classInheritance_ [cls.classInheritance()] RPAREN)
    | classInheritanceRuby [cls.classInheritance()] )?
*/
    LCURLY                  {ctx=cls.getContext();cur=ctx;}
     (classScope[cls]
     |"abstract"         {cls.setType(ClassTypes.ABSTRACT);}
      (invariantStatement[cls.invariantStatement()])?
     )
    RCURLY {cls.postConstruct();cur=ctx.getParent();}
	;
classScope[ClassStatement cr]
        {AccessNotation acs=null;TypeAliasStatement tal=null;BaseFunctionDef fd=null;
        List<AnnotationClause> as=new ArrayList<AnnotationClause>();AnnotationClause a=null;}
    : docstrings[cr]
    ( constructorDef[cr]
    | destructorDef[cr]
    | (a=annotation_clause      {as.add(a);})*
      fd=function_definition[cr, cr.getContext(), as]
    | defFunctionDef[cr.defFuncDef()]
    | varStmt[cr.statementClosure(), cr]
    | "type" IDENT BECOMES IDENT ( BOR IDENT)*
    | tal=typeAlias[cr]     {cr.add(tal);}
    | programStatement[/*cr.XXX(),*/ cr]
    | propertyStatement[cr.prop()]
    | acs=accessNotation {cr.addAccess(acs);}
    )*
    (invariantStatement[cr.invariantStatement()])?
    ;
annotation_clause returns [AnnotationClause a]
		{Qualident q=null;ExpressionList el=null;a=new AnnotationClause();AnnotationPart ap=null;}
	: ANNOT
		(                                       {ap=new AnnotationPart();}
		 q=qualident                            {ap.setClass(q);}
			(LPAREN el=expressionList RPAREN   {ap.setExprs(el);}
			)?                                  {a.add(ap);}
		)+ RBRACK
	;
namespaceStatement [NamespaceStatement cls, List<AnnotationClause> as]
		{AnnotationClause a=null;NamespaceContext ctx=null;IdentExpression i1=null;}
	: {cls.addAnnotations(as);}
    "namespace"
    (  i1=ident  	            {cls.setName(i1);}
    | 				            {cls.setType(NamespaceTypes.MODULE);}
    )?
    LCURLY                      {ctx=new NamespaceContext(cur, cls);cls.setContext(ctx);cur=ctx;}
     namespaceScope[cls]
    RCURLY {cls.postConstruct();cur=ctx.getParent();}
    ;

importStatement[OS_Element el] returns [ImportStatement pc]
	 {pc=null;ImportContext ctx=null;}
    : "from" {pc=new RootedImportStatement(el);ctx=new ImportContext(cur, pc);pc.setContext(ctx);cur=ctx;}
        xy=qualident "import" qualidentList[((RootedImportStatement)pc).importList()] {((RootedImportStatement)pc).importRoot(xy);} opt_semi
    | "import"
        ( (IDENT BECOMES) =>
                {pc=new AssigningImportStatement(el);ctx=new ImportContext(cur, pc);pc.setContext(ctx);cur=ctx;}
            importPart1[(AssigningImportStatement)pc] (COMMA importPart1[(AssigningImportStatement)pc])*
        | (qualident /*DOT*/ LCURLY) =>
                {pc=new QualifiedImportStatement(el);ctx=new ImportContext(cur, pc);pc.setContext(ctx);cur=ctx;}
            importPart2[(QualifiedImportStatement)pc] (COMMA importPart2[(QualifiedImportStatement)pc])*
        |       {pc=new NormalImportStatement(el);ctx=new ImportContext(cur, pc);pc.setContext(ctx);cur=ctx;}
            importPart3[(NormalImportStatement)pc] (COMMA importPart3[(NormalImportStatement)pc])*
        ) opt_semi
    ;
    /*
importStatement2[BaseScope sc]
	 {ImportStatementBuilder ib=new ImportStatementBuilder(); ImportStatement pc=null; QualidentList qil=null;}
    : "from" xy=qualident "import" qil=qualidentList2 {ib.rooted(xy, qil);} opt_semi
    | "import"
        ( (IDENT BECOMES) =>
            importPart1_[ib] (COMMA importPart1_[ib])*
        | (qualident /*DOT* / LCURLY) =>
        |   importPart2_[ib] (COMMA importPart2_[ib])*
        |   importPart3_[ib] (COMMA importPart3_[ib])*
        ) opt_semi
    ;
*/
importPart1 [AssigningImportStatement cr] //current rule
		{IdentExpression i1=null;Qualident q1=null;}
    : i1=ident BECOMES q1=qualident {cr.addAssigningPart(i1,q1);}
    ;
importPart2 [QualifiedImportStatement cr] //current rule
		{Qualident q3;IdentList il=new IdentList();}
    : q3=qualident /*DOT*/ LCURLY il=identList2 { cr.addSelectivePart(q3, il);} RCURLY
    ;
importPart3 [NormalImportStatement cr] //current rule
		{Qualident q2;}
    : q2=qualident {cr.addNormalPart(q2);}
    ;

classInheritance_[ClassInheritance ci]
		{TypeName tn=null;}
	: tn=inhTypeName 			{ci.add(tn);}
      (COMMA tn=inhTypeName 	{ci.add(tn);})*
    ;
classInheritanceRuby[ClassInheritance ci]
    : LT_ classInheritance_[ci]
    ;

docstrings[Documentable sc]:
    ((STRING_LITERAL)=> (s1:STRING_LITERAL {if (sc!=null) sc.addDocString(s1);})+
    |)
    ;

constructorDef[ClassStatement cr]
        {ConstructorDef cd=null;IdentExpression x1=null;FormalArgList fal=null;}
	: ("constructor"|"ctor")
		(x1=ident   {cd=cr.addCtor(x1);}
		|           {cd=cr.addCtor(null);}
		)
		fal=opfal {cd.setFal(fal);}
		sco=scope3[cd] {cd.scope(sco);}
					{cd.postConstruct();}
	;
destructorDef[ClassStatement cr]
        {DestructorDef dd=null;FormalArgList fal=null;}
	: ("destructor"|"dtor") {dd=cr.addDtor();}
		fal=opfal {dd.setFal(fal);}
		sco=scope3[dd] {dd.scope(sco);}
					{dd.postConstruct();}
	;
namespaceScope[NamespaceStatement cr]
        {AccessNotation acs=null;TypeAliasStatement tal=null;BaseFunctionDef fd=null;
        List<AnnotationClause> as=new ArrayList<AnnotationClause>();AnnotationClause a=null;}
    : docstrings[cr]
    (
    	(
    		(a=annotation_clause      {as.add(a);})*
    		fd=function_definition[cr, cr.getContext(), as]
		| varStmt[cr.statementClosure(), cr]
		| tal=typeAlias[cr]						{cr.add(tal);}
		| programStatement[/*cr.XXX(),*/ cr]
		| acs=accessNotation 					{cr.addAccess(acs);}
		)
		opt_semi
	)*
	(invariantStatement[cr.invariantStatement()])?
    ;
scope3[OS_Element parent] returns [Scope3 sc]
		{sc=new Scope3(parent);ClassStatement cls=null;}
    : LCURLY docstrings[sc]
      ((statement[sc.statementClosure(), sc.getParent()]
      | expr=expression {sc.statementWrapper(expr);} //expr.setContext(cur);
      | cls=classStatement[sc.getParent(), cur, null/*annotations*/] {sc.add(cls);}
      | "continue"
      | "break" // opt label?
      | "return" ((expression) => (expr=expression)|)
      | withStatement[sc.getParent()]
      | syntacticBlockScope[sc.getParent()]
      ) opt_semi )*
      RCURLY
    ;
withStatement[OS_Element aParent]
		{WithStatement ws=new WithStatement(aParent);WithContext ctx=null;}
	: "with" varStmt_i[ws.nextVarStmt()] (COMMA varStmt_i[ws.nextVarStmt()])
	                            {ctx=new WithContext(ws, cur);ws.setContext(ctx);cur=ctx;}
       sco=scope3[ws] {ws.scope(sco);}
                                {ws.postConstruct();cur=cur.getParent();}
	;
syntacticBlockScope[OS_Element aParent]
		{SyntacticBlock sb=new SyntacticBlock(aParent);SyntacticBlockContext ctx=null;}
	: 	                            {ctx=new SyntacticBlockContext(sb, cur);sb.setContext(ctx);cur=ctx;}

		sco=scope3[sb] {sb.scope(sco);}
									{sb.postConstruct();cur=cur.getParent();}
	;

functionScope[FunctionDef parent] returns [Scope3 sc]
		{sc=new Scope3(parent);ClassStatement cls=null;}
    : LCURLY docstrings[sc]
//	  (preConditionSegment[sc])? 
      (
        (
            ( statement[sc.statementClosure(), sc.getParent()]
            | expr=expression {sc.statementWrapper(expr);}
            | cls=classStatement[sc.getParent(), cur, null/*annotations*/] {sc.add(cls);}
            | "continue"
            | "break" // opt label?
            | "return" ((expression) => (expr=expression)|)
            )
            opt_semi
        )*
      | "abstract" opt_semi {parent.setAbstract(true);}
      ) 
//	  (postConditionSegment[sc])?
	  RCURLY
    ;
/*
returnExpressionFunctionDefScope [FunctionDefScope sc]
	: "return" 
			(
				(expression) =>  
					(expr=expression) 	{sc.return_expression(expr);}
			|							{sc.return_expression(null);}
			)
	;
*/
preConditionSegment [FunctionBody sc]
		{Precondition p=null;}
	: ("pre"|"requires") LCURLY
	    (p=precondition 					{sc.addPreCondition(p);})*
	  RCURLY
	;
postConditionSegment [FunctionBody sc]
		{Postcondition po=null;}
	: ("post"|"ensures")
		LCURLY (po=postcondition {sc.addPostCondition(po);})* ((RCURLY RCURLY)=> RCURLY)
	;

precondition returns [Precondition prec]
		{prec=new Precondition();IdentExpression id=null;}
	: (id=ident TOK_COLON {prec.id(id);})? expr=expression {prec.expr(expr);}
	;
postcondition returns [Postcondition postc]
		{postc = new Postcondition();IdentExpression id=null;}
	: (id=ident TOK_COLON {postc.id(id);})? expr=expression {postc.expr(expr);}
	;
/*
functionDef[FunctionDef fd]
    	{AnnotationClause a=null;FunctionContext ctx=null;IdentExpression i1=null;TypeName tn=null;FormalArgList fal=null;
    	fd=null;}
    : (a=annotation_clause      {fd.addAnnotation(a);})*
    i1=ident                    {fd.setName(i1);}
    ( "const"                   {fd.set(FunctionModifiers.CONST);}
    | "immutable"               {fd.set(FunctionModifiers.IMMUTABLE);})?
    fal=opfal 					{fd.setFal(fal);}
    (TOK_ARROW tn=typeName2 	{fd.setReturnType(tn);})?
                                {ctx=(FunctionContext)fd.getContext();cur=ctx;}
    sco=functionScope[fd] 		{fd.scope(sco);}
    							{fd.setSpecies(FunctionDef.Species.REG_FUN);fd.postConstruct();cur=ctx.getParent();}
    ;
*/
function_definition [OS_Element parent, Context ctx, List<AnnotationClause> as] returns [BaseFunctionDef fd]
		{fd=null;}
	: fd=def_function_definition[parent, ctx, as]		{fd.setSpecies(BaseFunctionDef.Species.DEF_FUN);}
	| fd=normal_function_definition[parent, ctx, as]	{fd.setSpecies(BaseFunctionDef.Species.REG_FUN);}
	;
normal_function_definition [OS_Element parent, Context ctx, List<AnnotationClause> as] returns [FunctionDef fd]
		{fd=null;FunctionHeader fh=null;FunctionBody fb=null;}
	: fh=function_header	{fd=new FunctionDef(parent, ctx);cur=fd.getContext();}
	  fb=function_body[fd]	{fd.setAnnotations(as);fd.setHeader(fh);fd.setBody(fb);cur=ctx.getParent();}
	;
def_function_definition [OS_Element parent, Context ctx, List<AnnotationClause> as] returns [DefFunctionDef fd]
		{fd=null;FunctionHeader fh=null;IExpression fb=null;}
	: "def"
	  fh=function_header		{fd=new DefFunctionDef(parent, ctx);cur=fd.getContext();}
	  fb=expression (opt_semi)?	{fd.setAnnotations(as);fd.setHeader(fh);fd.setBody(fb);cur=ctx.getParent();} // TODO named docstrings
	;
function_header returns [FunctionHeader fh]
		{fh=new FunctionHeader();IdentExpression i1=null;FormalArgList fal=null;TypeName tn=null;}
	: i1=ident                  {fh.setName(i1);}
      ( "const"                 {fh.setModifier(FunctionModifiers.CONST);}
      | "immutable"             {fh.setModifier(FunctionModifiers.IMMUTABLE);}
      )?
      fal=opfal 				{fh.setFal(fal);}
      (TOK_ARROW tn=typeName2 	{fh.setReturnType(tn);})?
	;
function_body [OS_Element parent] returns [FunctionBody fb]
		{fb=null;}
	: fb=function_body_mandatory[parent]
	| opt_semi		{fb=new FunctionBodyEmpty();}
	;
function_body_mandatory [OS_Element parent] returns [FunctionBody fb]
		{fb=new FunctionBody();Scope3 sc=new Scope3(parent);ClassStatement cls=null;fb.scope3=sc;} // TODO: parent
	: LCURLY docstrings[sc]
      (preConditionSegment[fb])?
		(
		  (
			  ( statement[sc.statementClosure(), sc.getParent()]
			  | expr=expression {sc.statementWrapper(expr);}
			  | cls=classStatement[sc.getParent(), cur, null/*annotations*/] {sc.add(cls);}
			  | "continue"
			  | "break" // opt label?
			  )
			  opt_semi
		  )*
		  ("return" ((expression) => (expr=expression)|) )?
		| "abstract" opt_semi {fb.setAbstract(true);}
		)
  	  (postConditionSegment[fb])?
	  RCURLY
	;

programStatement[/*ProgramClosure pc,*/ OS_Element cont]
		{ImportStatement imp=null;AnnotationClause a=null;List<AnnotationClause> as=new ArrayList<AnnotationClause>();AliasStatement als=null;}
    : imp=importStatement[cont]
	| (a=annotation_clause      {as.add(a);})*
    ( namespaceStatement[new NamespaceStatement(cont, cur), as]
    | classStatement[cont, cur, as] // TODO check if class in class works
    )
    | als=aliasStatement[cont] 			//{cont.add(als);} //[pc.aliasStatement(cont)]
    ;
varStmt[StatementClosure cr, OS_Element aParent]
        {VariableSequence vsq=null;TypeName tn=null;}
    :                   {vsq=cr.varSeq(cur);}
    ( "var"
    | "const"           {vsq.defaultModifiers(TypeModifiers.CONST);}
    | "val"             {vsq.defaultModifiers(TypeModifiers.VAL);}
    )
    ( varStmt_i3[vsq.next()] 
		(COMMA varStmt_i3[vsq.next()])* 
		( TOK_COLON tn=typeName2    {vsq.setTypeName(tn);})?
    )
    ;
varStmt_i[VariableStatement vs]
		{TypeName tn=null;IdentExpression i=null;}
	: i=ident                   {vs.setName(i);}
	( TOK_COLON tn=typeName2    {vs.setTypeName(tn);})?
	( BECOMES expr=expression   {vs.initial(expr);})?
	;
varStmt_i3[VariableStatement vs]
		{IdentExpression i=null;}
	: i=ident                   {vs.setName(i);}
	( BECOMES expr=expression   {vs.initial(expr);})?
	;
typeAlias[OS_Element cont] returns [TypeAliasStatement cr]
		{TypeAliasBuilder tab=new TypeAliasBuilder();cr=null;}
	: typeAlias2[tab]				{tab.setParent(cont);
									 tab.setContext(cur);
									 cr=tab.build();}
	;
typeAlias2[TypeAliasBuilder tab]
		{Qualident q=null;IdentExpression i=null;}
	:
	"type" "alias" i=ident 			{tab.setIdent(i);}
		BECOMES q=qualident 		{tab.setBecomes(q);}
									//{tab.build();}
	;
opfal returns [FormalArgList fal]
		{fal=null;}
	: LPAREN fal=formalArgList RPAREN
	;
formalArgList returns [FormalArgList fal]
		{fal=new FormalArgList();}
	: formalArgList_[fal]
	;
statement[StatementClosure cr, OS_Element aParent]
		{Qualident q=null;ExpressionList o=null;}
	:
	( expr=assignmentExpression {cr.statementWrapper(expr);}
	| ifConditional[cr.ifConditional(aParent, cur)]
	| matchConditional[cr.matchConditional(cur), aParent]
	| caseConditional[cr.caseConditional(cur)]
	| varStmt[cr, aParent]
	| whileLoop[cr]
	| frobeIteration[cr]
	| constructExpression[cr]
	| "yield" expr=expression {cr.yield(expr);}
	) opt_semi
	;
/*
constructExpression2[BaseScope cr] // was BaseFunctionDefScope
		{Qualident q=null;ExpressionList o=null;}
	: "construct" q=qualident
		(LPAREN (o=expressionList)? RPAREN)? // optional empty parens
												{cr.constructExpression(q,o);}
	;
*/
constructExpression[StatementClosure cr]
		{Qualident q=null;ExpressionList o=null;}
	: "construct" q=qualident
		(LPAREN (o=expressionList)? RPAREN)? // optional empty parens
												{cr.constructExpression(q,o);}
	;
/*yieldExpression [BaseScope cr] // was BaseFunctionDefScope
	: "yield" expr=expression 					{cr.yield(expr);}
	;
*/
opt_semi: (SEMI|);
identList2 returns [IdentList ail]
		{IdentExpression s=null;ail=new IdentList();}
	: s=ident {ail.push(s);}
		(COMMA s=ident {ail.push(s);})*
	;
expression returns [IExpression ee]
		{ee=null;}
	: ee=assignmentExpression
	;
aliasStatement[OS_Element cont] returns [AliasStatement pc]
		{IdentExpression i1=null;pc=new AliasStatement(cont);}
	: "alias" i1=ident {pc.setName(i1);} BECOMES xy=qualident {pc.setExpression(xy);}
	;
qualidentList[QualidentList qal]
		{Qualident qid;}
	: qid=qualident {qal.add(qid);} (COMMA qid=qualident {qal.add(qid);})*
	;
qualidentList2 returns [QualidentList qal]
		{Qualident qid;qal=new QualidentList();}
	: qid=qualident {qal.add(qid);} (COMMA qid=qualident {qal.add(qid);})*
	;
ident returns [IdentExpression id]
		{id=null;}
	: r1:IDENT {id=new IdentExpression(r1, cur);}
	;
expressionList returns [ExpressionList el]
		{el = new ExpressionList();}
	: expr=expression {el.next(expr);} (COMMA expr=expression {el.next(expr);})*
	;
invariantStatement[InvariantStatement cr]
        {InvariantStatementPart isp=null;}
	: "invariant"
        (            		{isp = new InvariantStatementPart(cr, i1);}
         (i1:IDENT
         TOK_COLON)?
         expr=expression    {isp.setExpr(expr);})*
    ;
/*
invariantStatement2[ClassScope sc]
        {InvariantStatementPart isp=null;IdentExpression i1=null;}
	: "invariant"
        (            				
         (i1=ident TOK_COLON		
		 |							{i1=null;}
		 )?
         expr=expression    		
		 							{sc.addInvariantStatementPart(i1, expr);})*
    ;
*/
accessNotation returns [AccessNotation acs]
        { TypeNameList tnl=null;acs=new AccessNotation();}
	: "access" (category:STRING_LITERAL (shorthand:IDENT EQUAL)? LCURLY tnl=typeNameList2 RCURLY
	            {acs.setCategory(category);acs.setShortHand(shorthand);acs.setTypeNames(tnl);}
	           |category1:STRING_LITERAL
	            {acs.setCategory(category1);}
	           |(shorthand1:IDENT EQUAL)? LCURLY tnl=typeNameList2 RCURLY
	            {acs.setShortHand(shorthand1);acs.setTypeNames(tnl);}
	           ) opt_semi

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
		|	"is_a"/*^*/ tn=typeName2 //typeSpec[true]
										{ee=new TypeCheckExpression(ee, tn);}
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
	:	INC/*^*/ ee=unaryExpression {ee=new UnaryExpression(ExpressionKind.INCREMENT, ee);}
	|	DEC/*^*/ ee=unaryExpression {ee=new UnaryExpression(ExpressionKind.DECREMENT, ee);}
	|	MINUS/*^*/ /*{#MINUS.setType(UNARY_MINUS);}*/ ee=unaryExpression {ee=new UnaryExpression(ExpressionKind.NEG, ee);}
	|	PLUS/*^*/  /*{#PLUS.setType(UNARY_PLUS);}*/ ee=unaryExpression {ee=new UnaryExpression(ExpressionKind.POS, ee);}
	|	ee=unaryExpressionNotPlusMinus
	;

unaryExpressionNotPlusMinus returns [IExpression ee]
		{ee=null;
		IExpression e3=null;}
	:	BNOT/*^*/ ee=unaryExpression {ee=new UnaryExpression(ExpressionKind.BNOT, ee);}
	|	LNOT/*^*/ ee=unaryExpression {ee=new UnaryExpression(ExpressionKind.LNOT, ee);}
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
//				| "inherit" LPAREN ( expressionList )? RPAREN
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
				(el=expressionList)? 
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

 		(
								{tc=new TypeCastExpression();ee=tc;}
			( AS				{tc.setKind(ExpressionKind.AS_CAST);}
			| CAST_TO			{tc.setKind(ExpressionKind.CAST_TO);})  
			tn=typeName2 		{tc.setTypeName(tn);}
		)?
		
		// look for int.class and int[].class
//	|	builtInType
//		( lbt:LBRACK/*^*/ /*{#lbt.setType(ARRAY_DECLARATOR);}*/ RBRACK/*!*/ )*
//		DOT/*^*/ "class"
	;



dot_expression_or_procedure_call [IExpression e1] returns [IExpression ee]
		{ee=null;ExpressionList el=null;IdentExpression e=null;}
	: e=ident {ee=new DotExpression(e1, e);}

    ( lp2:LPAREN/*^*/ /*{#lp.setType(METHOD_CALL);}*/
      (el=expressionList)?
            {ProcedureCallExpression pce=new ProcedureCallExpression();
            pce.identifier(ee);
            pce.setArgs(el);
            ee=pce;}
     RPAREN/*!*/)?
	;



// the basic element of an expression
primaryExpression returns [IExpression ee]
		{ee=null;FuncExpr ppc=null;IdentExpression e=null;
		ExpressionList el=null;}
		//IExpression e3=null;*/}
	:	ee=ident
//	|	newExpression
	|	ee=constantValue
//	|	"super"
	|	"true"
	|	"false"
	|	"this"
	|	"null"
	|	LPAREN/*!*/ ee=assignmentExpression RPAREN/*!*/ {ee=new SubExpression(ee);}
	|   {ppc=new FuncExpr();} funcExpr[ppc] {ee=ppc;}
	| LBRACK        {ee=new ListExpression();el=new ExpressionList();}
	    el=expressionList  {((ListExpression)ee).setContents(el);}
	  RBRACK
	;
funcExpr[FuncExpr pc] // remove scope to use in `typeName's
		{Scope3 sc = null;TypeName tn=null;FuncExprContext ctx=null;FormalArgList fal=null;}
	:
	( "function"  {	pc.type(TypeModifiers.FUNCTION);	}
	  (fal=opfal {pc.setArgList(fal);})
                              {ctx=new FuncExprContext(cur, pc);pc.setContext(ctx);cur=ctx;}
	  sco=scope3[pc] {pc.scope(sco);}
	  	  ((TOK_ARROW|TOK_COLON) tn=typeName2 {pc.setReturnType(tn);} )?
	| "procedure" {	pc.type(TypeModifiers.PROCEDURE);	}
	  (fal=opfal {pc.setArgList(fal);})
				              {ctx=new FuncExprContext(cur, pc);pc.setContext(ctx);cur=ctx;}
	  sco=scope3[pc] {pc.scope(sco);}
	| 							{sc=new Scope3(pc);}
      LCURLY                  {ctx=new FuncExprContext(cur, pc);pc.setContext(ctx);cur=ctx;}
	   BOR ( fal=opfal {pc.setArgList(fal);} )? BOR
	  (statement[sc.statementClosure(), sc.getParent()]
      | expr=expression {sc.statementWrapper(expr);}
      | classStatement[sc.getParent(), cur, null/*annotations*/]
      )*
      RCURLY 					{pc.scope(sc);}
	
	) {pc.postConstruct();cur=cur.getParent();}
	;



ifConditional[IfConditional ifex]
        {IfConditionalContext ifc_top=null,ifc=null;IfConditional else_=null;}
	: "if" expr=expression {ifex.expr(expr);cur=ifex.getContext();}
	sco=scope3[ifex] {ifex.scope(sco);} {cur=cur.getParent();}
	( ("else" "if")=> elseif_part[ifex.elseif()] )*
	( "else" {else_=ifex.else_();cur=else_.getContext();} sco=scope3[else_] {if(else_!=null) else_.scope(sco);} {cur=cur.getParent();})?
	;
elseif_part[IfConditional ifex]
	: ("elseif" | "else" "if") expr=expression {ifex.expr(expr);cur=ifex.getContext();}
	sco=scope3[ifex] {ifex.scope(sco);} {cur=cur.getParent();}
	;
matchConditional[MatchConditional mc, OS_Element aParent]
		{MatchConditional.MatchArm_TypeMatch mcp1=null;
		 MatchConditional.MatchConditionalPart2 mcp2=null;
		 MatchConditional.MatchConditionalPart3 mcp3=null;
		 TypeName tn=null;
		 IdentExpression i1=null;
		 MatchContext ctx = null;}
    : "match" expr=expression {/*mc.setParent(aParent);*/mc.expr(expr);}
      LCURLY                //{ctx=new MatchContext(cur, mc);mc.setContext(ctx);cur=ctx;}
      ( { mcp1 = mc.typeMatch();} 
      		i1=ident {mcp1.ident(i1);} TOK_COLON tn=typeName2 {mcp1.setTypeName(tn);} sco=scope3[mcp1] {mcp1.scope(sco);}
      | { mcp2 = mc.normal();}
      		expr=expression {mcp2.expr(expr);} sco=scope3[mcp2] {mcp2.scope(sco);}
      | { mcp3 = mc.valNormal();}
      		"val" i1=ident {mcp3.expr(i1);} sco=scope3[mcp3] {mcp3.scope(sco);}
      )+
      RCURLY {mc.postConstruct();}//cur=ctx.getParent();}
    ;
caseConditional[CaseConditional mc]
           {CaseContext ctx = null;IExpression expr1=null;}
    : "case" expr=expression {mc.expr(expr);}
      LCURLY                //{ctx=new CaseContext(cur, mc);mc.setContext(ctx);cur=ctx;}
      ( expr1=expression sco=scope3[mc] {mc.scope(sco, expr1);} )*
      RCURLY {mc.postConstruct();}//cur=ctx.getParent();}
    ;

whileLoop[StatementClosure cr]
	 {Loop loop=cr.loop();LoopContext ctx;}
	:
	( "while"                 {loop.type(LoopTypes.WHILE);}
	  expr=expression         {loop.expr(expr);}
	                            {ctx=new LoopContext(cur, loop);loop.setContext((LoopContext)ctx);cur=ctx;}
	  sco=scope3[loop] {loop.scope(sco);}
	| "do"                    {loop.type(LoopTypes.DO_WHILE);}
	                            {ctx=new LoopContext(cur, loop);loop.setContext((LoopContext)ctx);cur=ctx;}
	  sco=scope3[loop] {loop.scope(sco);}
      "while" expr=expression {loop.expr(expr);}
    )
    ;
frobeIteration[StatementClosure cr]
	 {Loop loop=cr.loop();LoopContext ctx=null;IdentExpression i1=null, i2=null, i3=null;}
	:"iterate"
	                            {ctx=new LoopContext(cur, loop);loop.setContext(ctx);cur=ctx;}
	( "from"                   {loop.type(LoopTypes.FROM_TO_TYPE);}
	  expr=expression          {loop.frompart(expr);}
	  "to" expr=expression     {loop.topart(expr);}
      	("with" i1=ident       {loop.iterName(i1);})?
	| "to"                     {loop.type(LoopTypes.TO_TYPE);}
	  expr=expression          {loop.topart(expr);}
      	("with" i2=ident       {loop.iterName(i2);})?
	|                          {loop.type(LoopTypes.EXPR_TYPE);}
	  expr=expression          {loop.topart(expr);}
      	("with" i3=ident       {loop.iterName(i3);})?
    )
    sco=scope3[loop] {loop.scope(sco);}
    ;

//
// TYPENAMES
//

inhTypeName returns [TypeName tn]
		{tn=null;}
	:
	( tn=typeOfTypeName2
	| tn=normalTypeName2
	)
        {tn.setContext(cur);}
    ;

typeName2 returns [TypeName cr]
		{cr=null;}
	: cr=genericTypeName2
	| cr=typeOfTypeName2
	| cr=normalTypeName2
	| cr=functionTypeName2
	;
genericTypeName2 returns [GenericTypeName tn]
		{tn=new GenericTypeName(cur);TypeName tn2=null;}
	: ("generic"|QUESTION) xy=qualident
		{tn.typeName(xy); tn.set(TypeModifiers.GENERIC);} 
	  (LT_ tn2=typeName2   {tn.setConstraint(tn2);})?
	;
typeOfTypeName2 returns [TypeOfTypeName tn]
		{tn=new TypeOfTypeName(cur);}
	: "typeof" xy=qualident
		{tn.typeOf(xy); tn.set(TypeModifiers.TYPE_OF);}
	;
normalTypeName2 returns [NormalTypeName tn]
		{tn=new RegularTypeName(cur); TypeNameList rtn=null;}
	: regularQualifiers2[tn]
	  xy=qualident          {tn.setName(xy);}
	  (LBRACK rtn=typeNameList2 {tn.addGenericPart(rtn);} RBRACK)?
	  (QUESTION {tn.setNullable();})?
	;
functionTypeName2 returns [FuncTypeName tn]
		{tn=null;}//new FuncTypeName(cur);}
	: tn=functionTypeName2_function
	| tn=functionTypeName2_procedure
	;
functionTypeName2_function returns [FuncTypeName tn]
		{tn=new FuncTypeName(cur); TypeName rtn=null; TypeNameList tnl=null;FormalArgList op=null;}
	: ("function"|"func")                         { tn.type(TypeModifiers.FUNCTION); }
	  (LPAREN 
	  	((typeNameList2)=> tnl=typeNameList2 
		  | op=formalArgList
		)
	   RPAREN
	  )            { if(tnl!=null)tn.argList(tnl); else tn.argList(op); }
	  ((TOK_ARROW|TOK_COLON) rtn=typeName2          { tn.returnValue(rtn);} )?
	;
functionTypeName2_procedure returns [FuncTypeName tn]
		{tn=new FuncTypeName(cur); TypeNameList tnl=null;FormalArgList op=null;}
	: ("procedure"|"proc")                          { tn.type(TypeModifiers.PROCEDURE);	}
	  (LPAREN 
	  	((typeNameList2)=> tnl=typeNameList2 
		  | op=formalArgList
		)
	   RPAREN
	  )            { if(tnl!=null)tn.argList(tnl); else tn.argList(op); }
	;
regularQualifiers2[NormalTypeName fp]
	:
	( "in"            {fp.setIn(true);} // TODO All parameters are in, must mean in out
	| "out"           {fp.setOut(true);})?
	( ("const"        {fp.setConstant(true);}
	   ("ref"		  {fp.setReference(true);})?)
	| "ref"           {fp.setReference(true);}
	)?
	;
typeNameList2 returns [TypeNameList cr]
		{TypeName tn=null;cr=new TypeNameList();}
	: tn=typeName2                  {cr.add(tn);}
	    (COMMA tn=typeName2         {cr.add(tn);})*
	;

//
//
//

defFunctionDef[DefFunctionDef fd]
		{FormalArgList op=null;TypeName tn=null;IdentExpression i1=null;}
	: "def" i1=ident op=opfal
	  ((TOK_COLON|TOK_ARROW) tn=typeName2 {fd.setReturnType(tn);})?
	  BECOMES expr=expression
	   									{fd.setSpecies(FunctionDef.Species.DEF_FUN); fd.setName(i1); fd.setFal(op); fd.setExpr(expr); }
	;
formalArgList_[FormalArgList fal]
	: (formalArgListItem_priv[fal.next()]
	  (COMMA formalArgListItem_priv[fal.next()])*)?
	;
formalArgListItem_priv[FormalArgListItem fali]
		{ TypeName tn=null;IdentExpression i=null; }
	:
		( (regularQualifiers2[(NormalTypeName)fali.typeName()])? // TODO there is a problem here not to mention NPE
		  i=ident  						{ fali.setName(i);	}
		  ( TOK_COLON tn=typeName2  	{ fali.setTypeName(tn); } )?
		)
	;

propertyStatement[PropertyStatement ps]
		{IdentExpression prop_name=null;TypeName tn=null;}
	: ("prop"|"property") prop_name=ident {ps.setName(prop_name);}
			(TOK_COLON|TOK_ARROW) tn=typeName2 {ps.setTypeName(tn);} LCURLY
	("get" (SEMI {ps.addGet();} | sco=scope3[ps] {ps.get_scope(sco);})
	|"set" (SEMI {ps.addSet();} | sco=scope3[ps] {ps.set_scope(sco);})
	)* RCURLY // account for multitude
	;
/*propertyStatement2_abstract[ClassScope cr]
		{PropertyStatementBuilder ps=new PropertyStatementBuilder();IdentExpression prop_name=null;TypeName tn=null;}
	: ("prop"|"property") prop_name=ident {ps.setName(prop_name);}
			(TOK_COLON|TOK_ARROW) tn=typeName2 {ps.setTypeName(tn);} LCURLY
	("get" SEMI {ps.addGet();}
	|"set" SEMI {ps.addSet();}
	)* RCURLY {cr.addProp(ps);}
	;
propertyStatement2[ClassScope cr]
		{PropertyStatementBuilder ps=new PropertyStatementBuilder();IdentExpression prop_name=null;TypeName tn=null;}
	: ("prop"|"property") prop_name=ident {ps.setName(prop_name);}
			(TOK_COLON|TOK_ARROW) tn=typeName2 {ps.setTypeName(tn);} LCURLY
	("get" (SEMI {ps.addGet();} | scope2[ps.get_scope()])
	|"set" (SEMI {ps.addSet();} | scope2[ps.set_scope()])
	)* RCURLY {cr.addProp(ps);} // account for multitude
	;
*/

//----------------------------------------------------------------------------
// The Elijjah scanner
//----------------------------------------------------------------------------
class ElijjahLexer extends Lexer;

options {
	exportVocab=Elijjah;      // call the vocabulary "Java"
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

