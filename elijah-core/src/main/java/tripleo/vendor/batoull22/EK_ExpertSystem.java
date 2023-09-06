package tripleo.vendor.batoull22;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Operation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author batoul
 */
public class EK_ExpertSystem {
	public       EK_Fact                  goal;
	public       Scanner                  input;
	public final List<EK_Fact>            Listfacts = new ArrayList<EK_Fact>();
	public final ArrayList<EK_Production> Listrule  = new ArrayList<>();

	//public static int countBackwardchaining=0;
	//public static int countForwardchaining=0;

	void actualizeMerge(final EK_Production current_production, final @NotNull EK_Merge aEKMerge) {
		Listfacts.add(aEKMerge.result());
		System.out.println("add fact:" + aEKMerge.result());
		Listrule.remove(current_production);
		System.out.println("remove rule: " + current_production);
	}

	public void actualizePush(final @NotNull EK_Push aPush, final EK_Production current_production) {
		Listfacts.add(aPush.resultant());
		System.out.println("add fact: " + aPush.resultant());
		Listrule.remove(current_production);
		System.out.println("remove rule: " + current_production);
	}

	public boolean Backwardchaining() {

		checkBackwardchaining(goal);
		return Listfacts.contains(goal);


	}

	boolean canAcceptMerge(final @NotNull EK_Merge aMerge2) {
		return Listfacts.contains(aMerge2.first())
				&& Listfacts.contains(aMerge2.second())
				&& !Listfacts.contains(aMerge2.result());
	}

	boolean canAcceptPush(final @NotNull EK_Push aPush) {
		return Listfacts.contains(aPush.predicating()) && !Listfacts.contains(aPush.resultant());
	}

	public void checkBackwardchaining(EK_Fact g) {
		for (int i = 0; i < Listrule.size(); i++) {
			final EK_Production ep = Listrule.get(i);

			if (ep.isMerge()) {
				var m = ep.getMerge();

				if (m.result() == g) { // example A.B-->C
					System.out.println("First case income : " + m.result());
					//His right side fact

					final boolean hasFirst  = Listfacts.contains(m.first());
					final boolean hasSecond = Listfacts.contains(m.second());

					if (hasFirst && hasSecond) {
						Listfacts.add(g);
						Listrule.remove(ep);
					} else if (!hasFirst && hasSecond) {
						System.out.println("1 not fact 2 fact");
						checkBackwardchaining(m.first());
					} else if (hasFirst && !hasSecond) {
						System.out.println("1 fact 2 not fact");
						checkBackwardchaining(m.second());
					} else if (!hasFirst && !hasSecond) {
						System.out.println("both not fact");
						checkBackwardchaining(m.first());
						checkBackwardchaining(m.second());
					}
					updateBackwardChaining(ep); // result.st()
				}
			}//end if (ch[4] == g)
			else if (ep.isPush()) {
				final EK_Push push = ep.getPush();

				final EK_Fact resultant   = push.resultant();
				final EK_Fact predicating = push.predicating();

				if (resultant == g) { // example A-->C

					System.out.println("second case income :" + resultant);

					if (Listfacts.contains(predicating)) {
						Listfacts.add(resultant);
						Listrule.remove(ep);
					} else {
						checkBackwardchaining(predicating);
						updateBackwardChaining(ep);
					}
				}
			}
		}//end for

	}// end chekBackwardchaining

	public boolean Forwardchaining() {
		Forwardchaining(goal);
		return Listfacts.contains(goal);
	}

	public void Forwardchaining(final EK_Fact goal) {
		for (int i = 0; i < Listrule.size(); i++) {
			final EK_Production current_production = Listrule.get(i);

			if (current_production.isMerge()) {
				final EK_Merge merge = current_production.getMerge();

				if (canAcceptMerge(merge)) {
					actualizeMerge(current_production, merge);

					if (Listfacts.contains(goal)) break;
					else Forwardchaining(goal);
				}
			} else if (current_production.isPush()) {
				final EK_Push push = current_production.getPush();

				// test
				if (canAcceptPush(push)) {
					// actualize
					actualizePush(push, current_production);

					// recurse
					if (Listfacts.contains(goal)) break;
					else Forwardchaining(goal);
				}
			}
		}
	}

	public @NotNull Operation<EK_Reader> openfile_2() {
		try {
			final InputStream stream = getClass().getResourceAsStream("KB3.txt");
			return Operation.success(new EK_Reader1(this, stream));
		} catch (Exception ex) {
			System.out.println("Error:the input file dose not exist");
			return Operation.failure(ex);
		}
	}

	public void print() {
		System.out.println("factlist:" + Listfacts);
		System.out.println("rulelist:" + Listrule);
		System.out.println("goal:" + goal);
		System.out.println(" ");
		//System.out.println( c);
		//System.out.println( j);
	}

	//Interpretation of input
	public void proof(@NotNull String st) {
		if (st.length() == 1) {
			Listfacts.add(new EK_Fact(st.charAt(0)));
		} else {
			final String proof_str = "proof:";
			if (st.startsWith(proof_str)) {
				final String u = st.substring(proof_str.length());
				goal = new EK_Fact(u.charAt(0));
			} else {
				Listrule.add(new EK_Production(st));
			}
		}
	}

	public void updateBackwardChaining(@NotNull final EK_Production prod) {
		final EK_Merge m = prod.getMerge();

		if (Listfacts.contains(m.first()) && Listfacts.contains(m.second())) {
			if (!Listfacts.contains(m.result())) {// check it does not exist in fact
				Listfacts.add(m.result());
				Listrule.remove(prod);
			}
		}
	}
}
