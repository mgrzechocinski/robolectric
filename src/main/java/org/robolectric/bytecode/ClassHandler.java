package org.robolectric.bytecode;

public interface ClassHandler {
    void classInitializing(Class clazz);

    Object methodInvoked(Class clazz, String methodName, Object instance, String[] paramTypes, Object[] params) throws Throwable;

    Plan methodInvoked(String signature, boolean isStatic, Class<?> theClass);

    Object intercept(String className, String methodName, Object instance, Object[] paramTypes, Object[] params) throws Throwable;

    // todo: definitely shouldn't live here
    void setStrictI18n(boolean strictI18n);

    public interface Plan {
        Object run(Object instance, Object[] params) throws Exception;
    }
}
