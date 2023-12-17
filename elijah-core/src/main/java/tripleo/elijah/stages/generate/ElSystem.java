/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.generate;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.GenerateResultItem;
import tripleo.elijah.stages.gen_generic.Old_GenerateResult;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created 1/8/21 11:02 PM
 */
public class ElSystem {
	public final  Supplier<OutputStrategy> outputStrategyCreator;
	private final Map<EvaFunction, String> gfm_map = new HashMap<EvaFunction, String>();
	private final Compilation              c;
	private final boolean                  verbose;
	private       OutputStrategyC          _os;

	public ElSystem(final boolean aB, final Compilation aC, final Supplier<OutputStrategy> aCreateOutputStratgy) {
		verbose               = aB;
		outputStrategyCreator = aCreateOutputStratgy;
		c                     = aC;
	}

	String getFilenameForNode(final @NotNull EvaNode node,
							  final Old_GenerateResult.TY ty,
							  final @NotNull OutputStrategyC outputStrategyC) {
		final String s;

		if (node instanceof EvaNamespace evaNamespace) {
			s = outputStrategyC.nameForNamespace(evaNamespace, ty);

			logProgress(41, evaNamespace, s);

			for (final EvaFunction gf : evaNamespace.functionMap.values()) {
				final String ss = getFilenameForNode(gf, ty, outputStrategyC);
				gfm_map.put(gf, ss);
			}
		} else if (node instanceof EvaClass evaClass) {
			s = outputStrategyC.nameForClass(evaClass, ty);

			logProgress(48, evaClass, s);

			for (final EvaFunction gf : evaClass.functionMap.values()) {
				final String ss = getFilenameForNode(gf, ty, outputStrategyC);
				gfm_map.put(gf, ss);
			}
		} else if (node instanceof EvaFunction evaFunction) {
			s = outputStrategyC.nameForFunction(evaFunction, ty);

			logProgress(30, evaFunction, s);
		} else if (node instanceof EvaConstructor evaConstructor) {
			s = outputStrategyC.nameForConstructor(evaConstructor, ty);

			logProgress(55, evaConstructor, s);
			//throw new IllegalStateException("Unexpected value: " + node);
		} else {
			logProgress(140, null, null);

			throw new IllegalStateException("Can't be here.");
		}

		return s;
	}

	@Contract(pure = true)
	private void logProgress(final int code, final @NotNull /*@NotNull*/ EvaNode evaNode, final String s) {
		// code:
		//   41:  EvaNamespace
		//   48:  EvaClass
		//   30:  EvaFunction
		//   55:  EvaConstructor
		//   140: not above
		SimplePrintLoggerToRemoveSoon.println_out_2(MessageFormat.format("{0} {1} {2}", code, evaNode.toString(), s));
	}

	public String getFilenameForNode__(final @NotNull GenerateResultItem aGenerateResultItem) {
		return getFilenameForNode(aGenerateResultItem.node(), aGenerateResultItem.__ty(), this._os);
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
