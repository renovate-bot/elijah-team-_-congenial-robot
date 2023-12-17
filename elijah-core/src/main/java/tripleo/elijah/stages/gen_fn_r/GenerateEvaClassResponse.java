package tripleo.elijah.stages.gen_fn_r;

import lombok.Getter;
import tripleo.elijah.Eventual;
import tripleo.elijah.stages.gen_fn.EvaClass;

@Getter
public class GenerateEvaClassResponse {
	private final Eventual<EvaClass> evaClassPromise = new Eventual<>();

	public Eventual<EvaClass> getEvaClassPromise() {
		return this.evaClassPromise;
	}
}
