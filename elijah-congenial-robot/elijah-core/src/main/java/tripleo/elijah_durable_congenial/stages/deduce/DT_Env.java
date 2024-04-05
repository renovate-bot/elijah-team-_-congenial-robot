package tripleo.elijah_durable_congenial.stages.deduce;

import tripleo.elijah_durable_congenial.comp.i.ErrSink;
import tripleo.elijah_durable_congenial.stages.logging.ElLog;
import tripleo.elijah_durable_congenial.comp.i.ErrSink;
import tripleo.elijah_durable_congenial.stages.logging.ElLog;

record DT_Env(ElLog LOG, ErrSink errSink, DeduceCentral central) {
}
