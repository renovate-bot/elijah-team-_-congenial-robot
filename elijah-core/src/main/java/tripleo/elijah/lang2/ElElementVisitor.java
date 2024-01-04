package tripleo.elijah.lang2;

import tripleo.elijah.contexts.ClassContext;
import tripleo.elijah.lang.i.*;

public interface ElElementVisitor {
	void addClass(ClassStatement klass);

	void addFunctionItem(FunctionItem element);

//	private void addModuleItem(ModuleItem element) ;

//	private void addImport(ImportStatement imp) ;

//	private void addClassItem(ClassItem element) ;

	void addModule(OS_Module module);

	void visitAccessNotation(AccessNotation aAccessNotation);

	void visitAliasStatement(AliasStatement aAliasStatement);

	void visitCaseConditional(CaseConditional caseConditional);

	void visitCaseScope(CaseConditional.CaseScope aCaseScope);

	void visitConstructorDef(ConstructorDef aConstructorDef);

	void visitConstructStatement(ConstructStatement aConstructExpression);

	void visitDefFunction(DefFunctionDef aDefFunctionDef);

	void visitDestructor(DestructorDef aDestructorDef);

	void visitFormalArgListItem(FormalArgListItem aFormalArgListItem);

	void visitFuncExpr(FuncExpr aFuncExpr);

	void visitFunctionDef(FunctionDef aFunctionDef);

	void visitIdentExpression(IdentExpression aIdentExpression);

	void visitIfConditional(IfConditional aIfConditional);

	void visitImportStatment(ImportStatement aImportStatement);

	void visitLoop(Loop aLoop);

	void visitMatchConditional(MatchConditional aMatchConditional);

	void visitMC1(MatchConditional.MC1 mc1);

	void visitNamespaceStatement(NamespaceStatement aNamespaceStatement);

	void visitPropertyStatement(PropertyStatement aPropertyStatement);

	void visitStatementWrapper(StatementWrapper aStatementWrapper);

	void visitSyntacticBlock(SyntacticBlock aSyntacticBlock);

	void visitTypeAlias(TypeAliasStatement aTypeAliasStatement);

	void visitTypeNameElement(ClassContext.OS_TypeNameElement aOS_typeNameElement);

	void visitVariableSequence(VariableSequence aVariableSequence);

	void visitVariableStatement(VariableStatement aVariableStatement);

	void visitWithStatement(WithStatement aWithStatement);

	void visitYield(YieldExpression aYieldExpression);

	// return, continue, next
}

//
//
//
