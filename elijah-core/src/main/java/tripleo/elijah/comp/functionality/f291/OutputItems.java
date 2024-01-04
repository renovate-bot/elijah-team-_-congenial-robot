package tripleo.elijah.comp.functionality.f291;

import tripleo.elijah.comp.i.Compilation;
import tripleo.elijah.nextgen.output.NG_OutputItem;
import tripleo.elijah.stages.generate.OutputStrategy;
import tripleo.elijah.stages.generate.OutputStrategyC;
import tripleo.elijah.stages.write_stage.pipeline_impl.NG_OutputRequest;

import java.util.ArrayList;
import java.util.List;

public class OutputItems {
	private final WPIS_GenerateOutputs   WPISGenerateOutputs;
	private final OutputStrategy         osg;
	private final List<NG_OutputRequest> ngOutputRequestList;
	private final List<NG_OutputItem>    ngOutputItemList;
	private final OutputStrategyC        outputStrategyC;
	private       int                    _readyCount;
	private       int                    _addTally;

	public OutputItems(final WPIS_GenerateOutputs aWPISGenerateOutputs) {
		WPISGenerateOutputs = aWPISGenerateOutputs;
		osg                 = WPISGenerateOutputs.__st().sys.outputStrategyCreator.get();
		outputStrategyC     = new OutputStrategyC(osg);
		ngOutputRequestList = new ArrayList<>();
		ngOutputItemList    = new ArrayList<>();
	}

	public void addItem(final NG_OutputItem aOutputItem) {
		ngOutputItemList.add(aOutputItem);

		++_addTally;

		if (_addTally == _readyCount) {
			final Compilation compilation = WPISGenerateOutputs.__st().c;

			final WPIS_GenerateOutputsFinaliation f = new WPIS_GenerateOutputsFinaliation(ngOutputRequestList, outputStrategyC, compilation.getCompilationAccess2());
			WPIS_GenerateOutputsFinalizer._finalizeItems(ngOutputItemList, f);
		}
	}

	public void readyCount(final int aI) {
		this._readyCount = aI;
	}

}
