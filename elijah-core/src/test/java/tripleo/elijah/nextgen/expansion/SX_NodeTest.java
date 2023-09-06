package tripleo.elijah.nextgen.expansion;

import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.Compilation.CompilationAlways;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.model.*;
import tripleo.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah.stages.gen_generic.OutputFileFactory;
import tripleo.elijah.stages.gen_generic.OutputFileFactoryParams;
import tripleo.elijah.test_help.Boilerplate;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public class SX_NodeTest extends TestCase {

	public void testFullText() {
		final Boilerplate b = new Boilerplate();
		b.get();
		final Compilation comp = b.comp;

		final OS_Module mod = comp.moduleBuilder()
				.withFileName("filename.elijah")
				.addToCompilation()
				.build();
		final OutputFileFactoryParams p    = new OutputFileFactoryParams(mod, comp.getCompilationEnclosure());
		//final GenerateFiles           fgen = OutputFileFactory.create(CompilationAlways.defaultPrelude(), p, fileGen);

/*
		final SM_ClassDeclaration node = new SM_ClassDeclaration() {
			@Override
			public @Nullable SM_ClassBody classBody() {
				return null;
			}

			@Override
			public @NotNull SM_ClassInheritance inheritance() {
				return new SM_ClassInheritance() {
					@Override
					public @NotNull List<SM_Name> names() {
						return List_of(new SM_Name() {
							@Override
							public @NotNull String getText() {
								return "Arguments";
							}
						});
					}
				};
			}

			@Override
			public @NotNull SM_Name name() {
				return new SM_Name() {
					@Override
					public @NotNull String getText() {
						return "Main";
					}
				};
			}

			@Override
			public @NotNull SM_ClassSubtype subType() {
				return SM_ClassSubtype.NORMAL;
			}
		};
*/

		//fgen.forNode(node);
	}
}