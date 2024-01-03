package tripleo.elijah.stages.gen_c.internal_t;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import tripleo.elijah.stages.gen_c.DeducedBaseEvaFunction;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_c.WhyNotGarish_Class;
import tripleo.elijah.stages.gen_c.WhyNotGarish_Constructor;
import tripleo.elijah.stages.gen_c.WhyNotGarish_Function;
import tripleo.elijah.stages.gen_c.WhyNotGarish_Item;
import tripleo.elijah.stages.gen_c.WhyNotGarish_Namespace;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaConstructor;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_fn.IEvaFunctionBase;

@SuppressWarnings("all")
public abstract class _GenerateC_T {
  protected final Map<EvaNode, WhyNotGarish_Item> a_directory = new HashMap<EvaNode, WhyNotGarish_Item>();

  public Collection<WhyNotGarish_Item> __directoryValues() {
    return this.a_directory.values();
  }

  public Collection<WhyNotGarish_Item> __directoryValuesCopy() {
    Collection<WhyNotGarish_Item> _values = this.a_directory.values();
    return new ArrayList<WhyNotGarish_Item>(_values);
  }

  public WhyNotGarish_Constructor a_lookup(final EvaConstructor aGf) {
    boolean _containsKey = this.a_directory.containsKey(aGf);
    if (_containsKey) {
      WhyNotGarish_Item _get = this.a_directory.get(aGf);
      return ((WhyNotGarish_Constructor) _get);
    }
    GenerateC __this = this._this();
    WhyNotGarish_Constructor ncc1907 = new WhyNotGarish_Constructor(aGf, __this);
    this.a_directory.put(aGf, ncc1907);
    return ncc1907;
  }

  public WhyNotGarish_Function a_lookup(final BaseEvaFunction aGf) {
    boolean _containsKey = this.a_directory.containsKey(aGf);
    if (_containsKey) {
      WhyNotGarish_Item _get = this.a_directory.get(aGf);
      return ((WhyNotGarish_Function) _get);
    }
    GenerateC __this = this._this();
    WhyNotGarish_Function ncf = new WhyNotGarish_Function(aGf, __this);
    this.a_directory.put(aGf, ncf);
    return ncf;
  }

  public WhyNotGarish_Class a_lookup(final EvaClass aGc) {
    boolean _containsKey = this.a_directory.containsKey(aGc);
    if (_containsKey) {
      WhyNotGarish_Item _get = this.a_directory.get(aGc);
      return ((WhyNotGarish_Class) _get);
    }
    GenerateC __this = this._this();
    WhyNotGarish_Class ncc = new WhyNotGarish_Class(aGc, __this);
    this.a_directory.put(aGc, ncc);
    return ncc;
  }

  public WhyNotGarish_Namespace a_lookup(final EvaNamespace en) {
    boolean _containsKey = this.a_directory.containsKey(en);
    if (_containsKey) {
      WhyNotGarish_Item _get = this.a_directory.get(en);
      return ((WhyNotGarish_Namespace) _get);
    }
    GenerateC __this = this._this();
    WhyNotGarish_Namespace ncn = new WhyNotGarish_Namespace(en, __this);
    this.a_directory.put(en, ncn);
    return ncn;
  }

  public abstract GenerateC _this();

  public WhyNotGarish_Function a_lookup(final DeducedBaseEvaFunction aGf) {
    IEvaFunctionBase _carrier = aGf.getCarrier();
    return this.a_lookup(((BaseEvaFunction) _carrier));
  }
}
