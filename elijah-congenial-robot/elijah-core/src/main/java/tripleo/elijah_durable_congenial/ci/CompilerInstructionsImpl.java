package tripleo.elijah_durable_congenial.ci;

import antlr.Token;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_congenial.ci.i.CompilerInstructions;
import tripleo.elijah_durable_congenial.lang.i.IExpression;
import tripleo.elijah_durable_congenial.lang.i.StringExpression;
import tripleo.elijah_durable_congenial.util.Helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created 9/6/20 11:20 AM
 */
public class CompilerInstructionsImpl implements CompilerInstructions {
	public @NotNull List<LibraryStatementPart> lsps = new ArrayList<LibraryStatementPart>();
	private         CiIndexingStatement        _idx;
	private         String                     filename;
	private         GenerateStatement          gen;
	private         String                     name;

	public CompilerInstructionsImpl() {
		int y=2;
	}

	@Override
	public void add(final GenerateStatement generateStatement) {
		assert gen == null;
		gen = generateStatement;
	}

	@Override
	public void add(final @NotNull LibraryStatementPart libraryStatementPart) {
		libraryStatementPart.setInstructions(this);
		lsps.add(libraryStatementPart);
	}

	@Override
	@Nullable
	public String genLang() {
		Collection<GenerateStatementImpl.Directive> gens = Collections2.filter(((GenerateStatementImpl) gen).dirs, new Predicate<GenerateStatementImpl.Directive>() {
			@Override
			public boolean apply(GenerateStatementImpl.@Nullable Directive input) {
				assert input != null;
				return input.getName().equals("gen");
			}
		});
		Iterator<GenerateStatementImpl.Directive> gi = gens.iterator();
		if (!gi.hasNext()) return null;
		IExpression lang_raw = gi.next().getExpression();
		assert lang_raw instanceof StringExpression;
		return Helpers.remove_single_quotes_from_string(((StringExpression) lang_raw).getText());
	}

	@Override
	public String getFilename() {
		return filename;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setName(@NotNull Token name) {
		this.name = name.getText();
	}

	@Override
	public List<LibraryStatementPart> lsps() {
		return lsps;
	}

	@Override
	public void setFilename(final String filename) {
		this.filename = filename;
	}

	@Override
	public @NotNull CiIndexingStatement indexingStatement() {
		if (_idx == null)
			_idx = new CiIndexingStatementImpl(this);

		return _idx;
	}

	@Override
	public String toString() {
		return "CompilerInstructionsImpl{" +
				"name='" + name + '\'' +
				", filename='" + filename + '\'' +
				'}';
	}
}
