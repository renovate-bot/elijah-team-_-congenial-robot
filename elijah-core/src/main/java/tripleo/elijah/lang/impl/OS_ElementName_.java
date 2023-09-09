package tripleo.elijah.lang.impl;

import tripleo.elijah.lang.i.OS_ElementName;

public class OS_ElementName_ {
    public static OS_ElementName empty() {
        return new OS_ElementName() {
            @Override
            public String asString() {
                return "";
            }

            @Override
            public boolean sameName(String name) {
                return name.isEmpty();
            }
        };
    }

    public static OS_ElementName ofString(String text) {
        return new OS_ElementName() {
            @Override
            public String asString() {
                return text;
            }

            @Override
            public boolean sameName(String name) {
                return text.equals(name);
            }
        };
    }
}
