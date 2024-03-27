package tripleo.elijah_durable_congenial.lang.i;

public interface ProgramClosure {
	AliasStatement aliasStatement(OS_Element aParent);

	ClassStatement classStatement(OS_Element aParent, Context ctx);

	NamespaceStatement namespaceStatement(OS_Element aParent, Context ctx);
}
