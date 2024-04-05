package tripleo.vendor.batoull22;

import org.jetbrains.annotations.Contract;

import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

final class EK_Reader1 implements EK_Reader {
	private final EK_ExpertSystem _system;
	Scanner input_;

	@Contract(pure = true)
	EK_Reader1(final EK_ExpertSystem aExpertSystem, final InputStream aStream) {
		_system = aExpertSystem;
		input_  = new Scanner(Objects.requireNonNull(aStream));
	}

	@Override
	public void closefile() {
		input_.close();
	}

	@Override
	public void print() {
		System.out.println("factlist:" + _system.Listfacts);
		System.out.println("rulelist:" + _system.Listrule);
		System.out.println("goal:" + _system.goal);
		System.out.println(" ");
		//System.out.println( c);
		//System.out.println( j);
	}

	@Override
	public void readfile() {
		//Read the line
		while (input_.hasNext()) {
			String a = input_.nextLine();

			_system.proof(a);
		}
		//System.out.println("factlist:"+ Listfacts);
		//System.out.println("goal:"+ goal);
		//System.out.println( "rulelist:"+Listrule);
		//System.out.println( " ");
	}
}
