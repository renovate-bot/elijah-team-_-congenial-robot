/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tripleo.elijah.nextgen.outputstatement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.small.ES_Item;
import tripleo.small.ES_String;
import tripleo.small.ES_Symbol;

/**
 * @author Tripleo Nova
 */
public class EG_SyntheticStatement implements EG_Statement {

	private final EG_Naming naming;
	private final EX_Rule   rule;
	private final ES_Item   s;
	private       String    text;

	public EG_SyntheticStatement(final EG_Naming aNaming, final ES_Symbol aSymbol, final EX_Rule aRule) {
		naming = aNaming;
		s      = aSymbol;
		rule   = aRule;

		doNaming(naming, s);
	}

	public EG_SyntheticStatement(final EG_Naming aNaming, final String aS, final EX_Rule aRule) {

		naming = aNaming;
		s      = new ES_String(aS);
		rule   = aRule;

		doNaming(naming, s);
	}

	private void doNaming(final @Nullable EG_Naming aNaming, final @NotNull ES_Item aS) {
		if (aNaming == null) return;

		final String ss = aNaming.s;
		final String s1 = aNaming.s1;

		if (ss.equals("include")) {
            assert s1 != null;
            if (s1.equals("local")) {
				final String text1 = ((ES_String) aS).getText();
				text = String.format("#include \"%s\"", text1);
			} else if (s1.equals("system") && ((ES_Symbol) aS).getText().equals("Prelude")) {
				final String text1 = ((ES_Symbol) aS).getText();
				text = String.format("#include \"%s.h\"", text1);
			}
		}
	}

	@Override
	public @Nullable EX_Explanation getExplanation() {
		return null;
	}

	@Override
	public String getText() {
		return text;
	}

	public void setText(final String aS) {
		text = aS;
	}
}
