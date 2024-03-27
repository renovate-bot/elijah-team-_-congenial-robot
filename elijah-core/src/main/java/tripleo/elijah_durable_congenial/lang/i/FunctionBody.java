package tripleo.elijah_durable_congenial.lang.i;

public interface FunctionBody {
	void addPostCondition(Postcondition aPostcondition);

	void addPreCondition(Precondition aPrecondition);

	boolean getAbstract();

	Scope3 scope3();

	void setAbstract(boolean aAbstract);

	void setScope3(Scope3 aSc);
}
