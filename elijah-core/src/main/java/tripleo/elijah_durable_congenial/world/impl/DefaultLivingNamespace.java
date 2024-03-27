package tripleo.elijah_durable_congenial.world.impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.comp.functionality.f291.AmazingPart;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;
import tripleo.elijah_durable_congenial.stages.garish.GarishNamespace;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah_durable_congenial.world.i.LivingNamespace;
import tripleo.elijah_durable_congenial.comp.functionality.f291.AmazingPart;
import tripleo.elijah_durable_congenial.lang.i.NamespaceStatement;
import tripleo.elijah_durable_congenial.stages.garish.GarishNamespace;
import tripleo.elijah_durable_congenial.stages.gen_c.GenerateC;
import tripleo.elijah_durable_congenial.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_congenial.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_congenial.stages.gen_generic.pipeline_impl.GenerateResultSink;

//import static com.ibm.j9ddr.StructureHeader.BlobID.node;

public class DefaultLivingNamespace implements LivingNamespace {
	private final EvaNamespace    node;
	private       GarishNamespace _garish;

	@Contract(pure = true)
	public DefaultLivingNamespace(final EvaNamespace aNode) {
		node = aNode;
	}

	@Override
	public EvaNamespace evaNode() {
		return node;
	}

	@Override
	public void garish(final GenerateC aGenerateC, final GenerateResult gr, final GenerateResultSink aResultSink) {
		getGarish().garish(aGenerateC, gr, aResultSink);
	}

	@Override
	public int getCode() {
		return node.getCode();
	}

	@Override
	public NamespaceStatement getElement() {
		return (NamespaceStatement) node.getElement();
	}

	@Override
	public @NotNull GarishNamespace getGarish() {
		if (_garish == null) {
			_garish = new GarishNamespace(this);
		}
		return _garish;
	}

	@Override
	public void offer(final AmazingPart aAmazingPart) {
		aAmazingPart.reverseOffer(this);
	}
}
