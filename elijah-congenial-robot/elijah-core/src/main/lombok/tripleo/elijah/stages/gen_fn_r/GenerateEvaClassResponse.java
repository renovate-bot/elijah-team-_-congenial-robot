package tripleo.elijah.stages.gen_fn_r;

import lombok.Getter;
import tripleo.elijah.util.Eventual;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaClass;

@Getter
public class GenerateEvaClassResponse {
	private final Eventual<EvaClass> evaClassPromise = new Eventual<>();

	public Eventual<EvaClass> getEvaClassPromise() {
		// antilombok
		return evaClassPromise;
	}
}
