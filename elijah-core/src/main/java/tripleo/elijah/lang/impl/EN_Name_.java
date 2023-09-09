package tripleo.elijah.lang.impl;

import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.lang.i.OS_ElementName;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.lang.nextgen.names.i.EN_Type;
import tripleo.elijah.lang.nextgen.names.i.EN_Understanding;
import tripleo.elijah.lang.nextgen.names.i.EN_Usage;

import java.util.LinkedList;
import java.util.List;

public enum EN_Name_ {
    ;

    @Contract(value = "_ -> new", pure = true)
    static @NotNull EN_Name create(@NotNull String name) {
        return new EN_Name() {
            private final @NotNull List<EN_Usage> usages = new LinkedList<>();
            private final @NotNull List<EN_Understanding> understandings = new LinkedList<>();
            private final @NotNull Eventual<EN_Type> typePromise = new Eventual<>();

            @Override
            public String getText() {
                return name;
            }

            @Override
            public Eventual<EN_Type> getType() {
                return typePromise;
            }

            @Override
            public List<EN_Usage> getUsages() {
                return usages;
            }

            @Override
            public List<EN_Understanding> getUnderstandings() {
                return understandings;
            }

            @Override
            public void addUnderstanding(final EN_Understanding u) {
                understandings.add(u);
            }

            @Override
            public boolean hasUnderstanding(@NotNull Class className) {
                return understandings.stream()
                        .anyMatch(className::isInstance);
            }

            @Override
            public void addUsage(EN_Usage aUsage) {
                usages.add(aUsage);
            }
        };
    }

    public static void assertUnderstanding(@NotNull IdentExpression aIdentExpression, final EN_Understanding u) {
        aIdentExpression.getName().addUnderstanding(u);
    }

    public static void assertUnderstanding(@NotNull EN_Name aName, EN_Understanding u) {
        aName.addUnderstanding(u);
    }

    public static EN_Name create(OS_ElementName name) {
        return new EN_Name() {
            private final @NotNull List<EN_Usage> usages = new LinkedList<>();
            private final @NotNull List<EN_Understanding> understandings = new LinkedList<>();
            private final @NotNull Eventual<EN_Type> typePromise = new Eventual<>();

            @Override
            public String getText() {
                return name.asString();
            }

            @Override
            public Eventual<EN_Type> getType() {
                return typePromise;
            }

            @Override
            public List<EN_Usage> getUsages() {
                return usages;
            }

            @Override
            public List<EN_Understanding> getUnderstandings() {
                return understandings;
            }

            @Override
            public void addUnderstanding(final EN_Understanding u) {
                understandings.add(u);
            }

            @Override
            public boolean hasUnderstanding(@NotNull Class className) {
//                for (EN_Understanding und : understandings) {
//                    if (className.isInstance(und)) {
//                        return true;
//                    }
//                }
                return understandings.stream()
                        .anyMatch(className::isInstance);
            }

            @Override
            public void addUsage(EN_Usage aUsage) {
                usages.add(aUsage);
            }
        };
    }
}
