package tripleo.elijah.stages.deduce;

import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.stages.logging.ElLog;

record DT_Env(ElLog LOG, ErrSink errSink, DeduceCentral central) {
}
