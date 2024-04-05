package tripleo.elijah

import gumtree.spoon.AstComparator
import gumtree.spoon.builder.SpoonGumTreeBuilder
import gumtree.spoon.diff.Diff
import gumtree.spoon.diff.DiffImpl
import org.junit.Before
import org.junit.Test
import spoon.SpoonModelBuilder
import spoon.compiler.SpoonResource
import spoon.reflect.CtModel
import spoon.reflect.declaration.CtType
import spoon.reflect.factory.Factory
import spoon.support.compiler.VirtualFile
import spoon.support.compiler.jdt.JDTBasedSpoonCompiler
import spoon.testing.utils.ModelUtils.createFactory
import tripleo.elijah_durable_congenial.comp.Finally
import tripleo.elijah_durable_congenial.comp.i.Compilation
import tripleo.elijah_durable_congenial.comp.signal.DeducePipeline_finishedSignal
import tripleo.elijah_durable_congenial.factory.comp.CompilationFactory
import tripleo.elijah_durable_congenial.util.Helpers
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Suppress("PrivatePropertyName")
class TestBasic_fact1_Verification {
    private var REPORTS : Finally? = null
    private lateinit var c: Compilation

    @Before
    fun setUp() {
        val s = "test/basic/fact1/main2"
        c = CompilationFactory.mkCompilationSilent()
        c.feedCmdLine(Helpers.List_of(s, "-sO"))
        this.REPORTS = c.reports()

        assertEquals(true, c.getSignalResult(DeducePipeline_finishedSignal.INSTANCE))
    }

    @Test
    fun testInputs_fact1() {
        val ac = AstComparator()
        val scanner = SpoonGumTreeBuilder()

        val compare: Diff = DiffImpl(
            scanner.treeContext,
            scanner.getTree(m(ac, "a")),
            scanner.getTree(m(ac, "b"))
        )

        val ros = compare.rootOperations
        for (ro in ros) {
            System.err.println("9999-0053 "+ro)
        }


        assertTrue(REPORTS!!.containsInput("test/basic/fact1/fact1.elijah"))
    }

    private fun m(
        ac: AstComparator,
        filename: String
    ): CtType<*>? {
        val content = String(TestBasic_fact1_Verification::class.java.getResourceAsStream(filename)!!.readAllBytes())
        val resource = VirtualFile(content, filename)
        return getCtType(resource)
    }

    fun getCtType(resource: SpoonResource?): CtType<*>? {
        val factory: Factory = createFactory()
        factory.model.setBuildModelIsFinished<CtModel>(false)
        val compiler: SpoonModelBuilder = JDTBasedSpoonCompiler(factory)
        compiler.factory.environment.setLevel("OFF")
        compiler.addInputSource(resource)
        compiler.build()
        if (factory.Type().all.size == 0) {
            return null
        }

        // let's first take the first type.
        val type = factory.Type().all[0]
        // Now, let's ask to the factory the type (which it will set up the
        // corresponding
        // package)
        return factory.Type().get<Any>(type.qualifiedName)
    }

    @Test
    fun testInputs_main2_elijah() {
        assertTrue(REPORTS!!.containsInput("test/basic/fact1/main2/main2.elijah"))
    }

    @Test
    fun testInputs_main2_ez() {
//        assertTrue(REPORTS.containsInput("test/basic/fact1/main2/main2.ez"))
    }

    @Test
    fun testOutputs_main2_Main_h() {
        assertTrue(REPORTS!!.containsCodeOutput("/main2/Main.h"))
    }

    @Test
    fun testOutputs_code2_main2_Main_c() {
        assertTrue(REPORTS!!.containsCodeOutput("/main2/Main.c"))
    }

    /*
401b Writing path: COMP/2408d3e32dc3f2d0d6254141917fa7629c71352a506a0edd17a007d1e3baa781/<date>/sww/modules-sw-writer
401b Writing path: COMP/2408d3e32dc3f2d0d6254141917fa7629c71352a506a0edd17a007d1e3baa781/<date>/
401b Writing path: COMP/2408d3e32dc3f2d0d6254141917fa7629c71352a506a0edd17a007d1e3baa781/<date>/
401b Writing path: COMP/2408d3e32dc3f2d0d6254141917fa7629c71352a506a0edd17a007d1e3baa781/<date>/inputs.txt
401b Writing path: COMP/2408d3e32dc3f2d0d6254141917fa7629c71352a506a0edd17a007d1e3baa781/<date>/buffers.txt
401b Writing path: COMP/2408d3e32dc3f2d0d6254141917fa7629c71352a506a0edd17a007d1e3baa781/<date>/Makefile
401b Writing path: COMP/2408d3e32dc3f2d0d6254141917fa7629c71352a506a0edd17a007d1e3baa781/<date>/logs/(DEDUCE_PHASE)
401b Writing path: COMP/2408d3e32dc3f2d0d6254141917fa7629c71352a506a0edd17a007d1e3baa781/<date>/logs/lib_elijjah~~lib-c~~Prelude.elijjah
401b Writing path: COMP/2408d3e32dc3f2d0d6254141917fa7629c71352a506a0edd17a007d1e3baa781/<date>/logs/test~~basic~~fact1~~fact1.elijah
401b Writing path: COMP/2408d3e32dc3f2d0d6254141917fa7629c71352a506a0edd17a007d1e3baa781/<date>/logs/test~~basic~~fact1~~main2~~main2.elijah
401b Writing path: COMP/2408d3e32dc3f2d0d6254141917fa7629c71352a506a0edd17a007d1e3baa781/<date>/logs/test~~basic~~fact1~~main2~~main2.elijah
401b Writing path: COMP/2408d3e32dc3f2d0d6254141917fa7629c71352a506a0edd17a007d1e3baa781/<date>/logs/test~~basic~~fact1~~main2~~main2.elijah

*/
    /*
import wprust.demo.fact

class Main < Arguments {
main() {
var b1 = 3
val a1 = argument(1)

if a1.isInt() {
b1 = a1.to_int() // intValue()
}

val f1 = factorial(b1)
println(f1)
}
}
*/
    /**
     * **************************************************************************  */
    /*
package wprust.demo.fact

class Main11 {
main() {
const kl = 3
val f1 = factorial(kl)
println(f1)
}
}

//#pragma return_result
namespace / *__MODULE__* / {
		factorial_r(i: u64) -> u64 {
			case i {
				0 { Result = 0 }
				n { Result = n * factorial(n-1) } // _r/_i // also tailcall (not a tailcall, but thanks for the reminder)
			}
		}
		factorial_i(i: u64) -> u64 {
			var acc = 1
			iterate from 2 to i with num {
				acc *= num
			}
			Result = acc
		}

		alias factorial = factorial_r
	}

	*/
    /*
	test/basic/fact1/

	test/basic/fact1/main2


*/
}
