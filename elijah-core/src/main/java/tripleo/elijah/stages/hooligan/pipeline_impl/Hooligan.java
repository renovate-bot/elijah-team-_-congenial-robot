package tripleo.elijah.stages.hooligan.pipeline_impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.NotImplementedException;

import java.util.*;

class Hooligan {
	private static final Set<SmallWriter.SW_Ref> insides = new HashSet<>();

	@NotNull SmallWriter1 __modules2(final @NotNull List<OS_Module> aModuleList) {
		var sw = new SmallWriter1();

		for (OS_Module module : aModuleList) {
			module.serializeTo(sw.newWriter(module));
		}

		return sw;
	}

	class SmallWriter1 {
		final         Map<OS_Element, SmallWriter.SW_Ref> objMap1 = new HashMap<>();
		private final Map<OS_Element, SmallWriter>        writers = new LinkedHashMap<>();

		public @NotNull SmallWriter newWriter(final OS_Module aModule) {
			var sw = new SmallWriter2(objMap1);

			writers.put(aModule, sw);

			return sw;
		}

		public @NotNull String getText() {
			var sb3 = new StringBuilder();

			for (Map.Entry<OS_Element, SmallWriter> entry : writers.entrySet()) {
				final OS_Module   module      = (OS_Module) entry.getKey();
				final SmallWriter smallWriter = entry.getValue();

				sb3.append("(MODULE \"%s\"\n\n".formatted(module.getFileName()));
				sb3.append("%s\n\n".formatted(smallWriter.getString()));

				final Collection<SmallWriter.SW_Ref> values = ((SmallWriter2) smallWriter).objMap.values();
				for (SmallWriter.SW_Ref ref : new ArrayList<>(values)) {
					sb3.append("(REFERENCE \"%s\" \"%s\"\n".formatted(ref.name(), ref.get().getClass().getName()));

					final SmallWriter2 writer2 = new SmallWriter2(objMap1);
					ref.get().serializeTo(writer2);

					for (SmallWriter.SW_Ref ref2 : writer2.insides) {
						ref2.get().serializeTo(writer2);
					}

					sb3.append(writer2.sb);
					sb3.append(")\n");
				}
				sb3.append(")\n");
			}

			return sb3.toString();
		}
	}


	class SmallWriter2 implements SmallWriter {
		final         Map<OS_Element, SW_Ref> objMap;// = new HashMap<>();
		private final StringBuilder           sb      = new StringBuilder();
		@NotNull      Set<SW_Ref>             insides = new HashSet<>();

		SmallWriter2(final Map<OS_Element, SW_Ref> aObjMap) {
			objMap = aObjMap;
		}

		@Override
		public void fieldToken(final String aFieldName, final Token aFieldValue) {
			sb.append("(field 'token \"%s\" \"%s\")\n".formatted(aFieldName, aFieldValue));
		}

		@Override
		public void fieldExpression(final String aFieldName, final @NotNull IExpression aFieldValue) {
			sb.append("(field 'expression \"%s\" \"%s\")\n".formatted(aFieldName, aFieldValue.toString()));
		}

		@Override
		public void fieldElement(final String aFieldName, final @NotNull OS_Element aFieldValue) {
			var w5 = new SmallWriter2(objMap);
			aFieldValue.serializeTo(w5);
			sb.append("(field 'element \"%s\" %s)\n".formatted(aFieldName, w5.sb));
		}

		@Override
		public void fieldString(final String aFieldName, final String aFieldValue) {
			sb.append("(field 'string \"%s\" \"%s\")\n".formatted(aFieldName, aFieldValue));
		}

		@Override
		public void fieldInteger(final String aFieldName, final int aFieldValue) {
			sb.append("(field 'integer \"%s\" \"%d\")\n".formatted(aFieldName, aFieldValue));
		}

		@Override
		public <E> void fieldList(final String aFieldName, final @NotNull List<E> aFieldValue) {
			var sb2 = new StringBuilder();

			int i = 1;
			for (E e : aFieldValue) {
				if (!(OS_Element.class.isInstance(e))) {
					sb2.append("(item %d \"%s\")\n".formatted(i++, e.toString()));
				} else {
					var r = this.createRef((OS_Element) e);
//					this.fieldRef("item%d".formatted(i++), r);
					sb2.append("(item %d \"%s\")\n".formatted(i++, r.name()));
				}
			}

			sb.append("(field 'list \"%s\" %s)\n".formatted(aFieldName, sb2.toString()));

			var rs = new ArrayList<>(this.objMap.values());
			var w3 = new SmallWriter2(objMap);
			for (SW_Ref r : rs) {
				if (!Hooligan.insides.contains(r)) {
					w3.sb.append("(REF \"%s\" \"%s\"\n".formatted(r.name(), r.get().getClass().getName()));

					Hooligan.insides.add(r);
					r.get().serializeTo(w3);
					w3.sb.append(")\n");
				}
			}
			sb.append(w3.sb);
		}

		@Override
		public SW_Ref createRef(final OS_Element aFieldValue) {
			if (objMap.containsKey(aFieldValue)) {
				return objMap.get(aFieldValue);
			}

			//aFieldValue.serializeTo(this);
			//
			//sb.append("(field 'string \"%s\" \"%s\")\n".formatted("--ref--", aFieldValue));
			//NotImplementedException.raise();

			final SW_Ref swRef = new SW_Ref() {
				final UUID uuid = UUID.randomUUID();

				@Override
				public String name() {
					return uuid.toString();
				}

				@Override
				public OS_Element get() {
					return aFieldValue;
				}
			};

			objMap.put(aFieldValue, swRef);

			return swRef;
		}

		@Override
		public @Nullable SW_List createList() {
			NotImplementedException.raise();
			return null;
		}

		@Override
		public void fieldIdent(final String aFieldName, final @NotNull IdentExpression aFieldValue) {
			var w4 = new SmallWriter2(objMap);

			aFieldValue.serializeTo(w4);

			sb.append("(field 'ident \"%s\" %s)\n".formatted(aFieldName, w4.sb)); // !!
		}

		@Override
		public @NotNull SW_TypenameList createTypeNameList() {
			NotImplementedException.raise();
			return new SW_TypenameList() {
				private @NotNull List<TypeName> typenames = new LinkedList<>();

				@Override
				public void add(final TypeName el) {
					typenames.add(el);
				}

				@Override
				public List<TypeName> items() {
					return typenames;
				}
			};
		}

		@Override
		public void fieldTypenameList(final String aInheritance, final @NotNull SW_TypenameList aInh) {
			sb.append("(field 'typeNameList \"%s\" ".formatted(aInheritance));
			for (TypeName item : aInh.items()) {
				sb.append("(field 'typeName \"%s\")\n".formatted(item));
			}
			sb.append(")\n");
		}

		@Override
		public void fieldRef(final String aParent, final @NotNull SW_Ref aRef) {
			sb.append("(field 'ref \"%s\" \"%s\")\n".formatted(aParent, aRef.name()));
		}

		@Override
		public @NotNull String getString() {
			return sb.toString();
		}
	}
}
