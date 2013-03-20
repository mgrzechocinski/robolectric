package org.robolectric.bytecode;

public class Example {
    public static String method(String value) throws Throwable {
        ClassHandler.Plan plan = RobolectricInternals.methodInvoked("foo/Class/staticMethod(Ljava/lang/String;)Ljava/lang/String;", true, null);
        try {
            if (plan != null) {
                return (String) plan.run(null, new Object[]{value});
            } else {
                return robo$thingy$method(value);
            }
        } catch (Throwable e) {
            throw handleException(e);
        }
    }

    private static RuntimeException handleException(Throwable e) {
        return new RuntimeException(e);
    }

    private static String robo$thingy$method(String value) throws Throwable {
        return null;
    }

    public long hashCode2() {
        if ((__robo_data__ instanceof Example)) {
            try {
                return ((Example) __robo_data__).$$robo$$AClassWithNoDefaultConstructor_0eae_hashCode2();
            } catch (Throwable throwable) {
                throw handleException(throwable);
            }
        } else {
            ClassHandler.Plan plan = RobolectricInternals.methodInvoked("org/robolectric/bytecode/testing/AClassWithNoDefaultConstructor/hashCode2()Ljava/lang/String;", false, null);
            if (plan != null) {
                try {
                    Object result = plan.run(this, new Object[0]);
                    return result == null ? 0 : (Long) result;
                } catch (Throwable throwable) {
                    throw handleException(throwable);
                }
            } else {
                try {
                    return $$robo$$AClassWithNoDefaultConstructor_0eae_hashCode2();
                } catch (Throwable throwable) {
                    throw handleException(throwable);
                }
            }
        }
    }

    private long $$robo$$AClassWithNoDefaultConstructor_0eae_hashCode2() {
        return 0;
    }

    private String name;
    public Object __robo_data__;

}
