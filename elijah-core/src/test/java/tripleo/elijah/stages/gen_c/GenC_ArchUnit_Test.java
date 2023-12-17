package tripleo.elijah.stages.gen_c;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@RunWith(ArchUnitRunner.class) // Remove this line for JUnit 5!!
@AnalyzeClasses(packages = "tripleo.elijah")
public class GenC_ArchUnit_Test {

	//@Test
	@ArchTest
	public void xTest(JavaClasses importedClasses) {
		//JavaClasses importedClasses = new ClassFileImporter().importPackages("tripleo.elijah");

		ArchRule rule = noClasses().that() // see next section
				.resideInAPackage("tripleo.elijah.stages.gen_c")
				.should()

				//.onlyDependOnClassesThat()
				//.resideInAPackage("..service..");				.areAnnotatedWith(Service.class)
				//.should()
				//.notBeInnerClasses();

				.dependOnClassesThat()
				.resideInAPackage("tripleo.elijah.stages.gen_fn");

		if (false) {
			rule.check(importedClasses);
		}
	}
}
