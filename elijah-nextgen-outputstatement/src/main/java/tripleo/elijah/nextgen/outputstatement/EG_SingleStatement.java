/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tripleo.elijah.nextgen.outputstatement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Tripleo Nova
 */
public class EG_SingleStatement implements EG_Statement {
	private final @Nullable EX_Explanation explanation;
	private final           String         text;

	public EG_SingleStatement(final String aText) {
		text        = aText;
		explanation = null;
	}

	public EG_SingleStatement(final String aText, final @Nullable EX_Explanation aExplanation) {
		text        = aText;
		explanation = aExplanation;
	}

	@Override
	public @Nullable EX_Explanation getExplanation() {
		return explanation;
	}

	@Override
	public String getText() {
		return text;
	}

	public @NotNull EG_SingleStatement rule(final String aS, final int aI) {
		return this;
	}

	public @NotNull EG_SingleStatement tag(final String aS, final int aI) {
		return this;
	}
}
