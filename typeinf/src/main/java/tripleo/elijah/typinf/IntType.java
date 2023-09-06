package tripleo.elijah.typinf;

public class IntType implements Type {
	@Override
	public boolean equals(Object obj) {
		return obj instanceof IntType || super.equals(obj);
	}

	@Override
	public String toString() {
		return "Int";
	}
}
