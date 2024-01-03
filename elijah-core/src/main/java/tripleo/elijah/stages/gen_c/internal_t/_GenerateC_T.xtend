package tripleo.elijah.stages.gen_c.internal_t

import java.util.HashMap
import java.util.Map
import tripleo.elijah.stages.gen_c.DeducedBaseEvaFunction
import tripleo.elijah.stages.gen_c.GenerateC
import tripleo.elijah.stages.gen_c.WhyNotGarish_Class
import tripleo.elijah.stages.gen_c.WhyNotGarish_Constructor
import tripleo.elijah.stages.gen_c.WhyNotGarish_Function
import tripleo.elijah.stages.gen_c.WhyNotGarish_Item
import tripleo.elijah.stages.gen_c.WhyNotGarish_Namespace
import tripleo.elijah.stages.gen_fn.BaseEvaFunction
import tripleo.elijah.stages.gen_fn.EvaClass
import tripleo.elijah.stages.gen_fn.EvaConstructor
import tripleo.elijah.stages.gen_fn.EvaNamespace
import tripleo.elijah.stages.gen_fn.EvaNode
import java.util.Collection
import java.util.ArrayList

abstract class _GenerateC_T {
	protected final Map<EvaNode, WhyNotGarish_Item> a_directory = new HashMap();
	
	def Collection<WhyNotGarish_Item> __directoryValues() { a_directory.values() }
	def Collection<WhyNotGarish_Item> __directoryValuesCopy() { new ArrayList(a_directory.values()) }
	
	def WhyNotGarish_Constructor a_lookup(/*final*/ EvaConstructor aGf) {
	  if (a_directory.containsKey(aGf)) {
		return a_directory.get(aGf) as WhyNotGarish_Constructor;
	  }

	  var ncc1907 = new WhyNotGarish_Constructor(aGf, _this());
	  a_directory.put(aGf, ncc1907);
	  return ncc1907;
	}

	def WhyNotGarish_Function a_lookup(/*final*/ BaseEvaFunction aGf) {
	  if (a_directory.containsKey(aGf)) {
		return a_directory.get(aGf) as WhyNotGarish_Function;
	  }

	  var ncf = new WhyNotGarish_Function(aGf, _this());
	  a_directory.put(aGf, ncf);
	  return ncf;
	}

	def /*WhyNotGarish_Class*/ a_lookup(/*final*/ EvaClass aGc) {
	  if (a_directory.containsKey(aGc)) {
		return  a_directory.get(aGc) as WhyNotGarish_Class;
	  }

	  var ncc = new WhyNotGarish_Class(aGc, _this());
	  a_directory.put(aGc, ncc);
	  return ncc;
	}

	def /*WhyNotGarish_Namespace*/ a_lookup(/*final*/ EvaNamespace en) {
	  if (a_directory.containsKey(en)) {
		return a_directory.get(en) as WhyNotGarish_Namespace;
	  }

	  var ncn = new WhyNotGarish_Namespace(en, _this());
	  a_directory.put(en, ncn);
	  return ncn;
	}

	//abstract 
	def GenerateC _this()

	def WhyNotGarish_Function a_lookup(/*final*/ DeducedBaseEvaFunction aGf) {
	  return a_lookup(aGf.getCarrier() as BaseEvaFunction);
	}
}
