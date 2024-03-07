package tripleo.elijah.stages.gen_fn_r;

import lombok.Getter;
import tripleo.elijah.Eventual;
import tripleo.elijah.stages.gen_fn.EvaClass;

public class GenerateEvaClassResponse {
	@Getter	private final Eventual<EvaClass> evaClassPromise = new Eventual<>();

//	public Eventual<EvaClass> getEvaClassPromise() {
//		// antilombok
//		return evaClassPromise;
//	}
}
