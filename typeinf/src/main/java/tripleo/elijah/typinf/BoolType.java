package tripleo.elijah.typinf;

public class BoolType implements Type {
	@Override
	public boolean equals(Object obj) {
		return obj instanceof BoolType || super.equals(obj);
	}

	@Override
	public String toString() {
		return "Bool";
	}
}
