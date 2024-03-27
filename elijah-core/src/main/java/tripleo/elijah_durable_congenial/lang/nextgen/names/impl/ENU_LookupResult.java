package tripleo.elijah_durable_congenial.lang.nextgen.names.impl;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.lang.i.Context;
import tripleo.elijah_durable_congenial.lang.i.LookupResultList;
import tripleo.elijah_durable_congenial.lang.nextgen.names.i.EN_Understanding;
import tripleo.elijah_durable_congenial.lang.i.Context;
import tripleo.elijah_durable_congenial.lang.i.LookupResultList;

import java.util.List;
import java.util.stream.Collectors;

public class ENU_LookupResult implements EN_Understanding {

	private final int              level;
	private final LookupResultList lrl;
	private final ImmutableList<Context> contexts;

	public ENU_LookupResult(LookupResultList aLrl, int aLevel, ImmutableList<Context> aContexts) {
		this.lrl      = aLrl;
		this.level    = aLevel;
		this.contexts = aContexts;
	}

	public ENU_LookupResult(@NotNull LookupResultList lrl2) {
		this.lrl   = lrl2;
		this.level = -10000;

		final List<Context> collect = lrl2.results().stream().map(lr -> lr.getContext()).collect(Collectors.toList());
		this.contexts = ImmutableList.copyOf(collect);
	}

}
