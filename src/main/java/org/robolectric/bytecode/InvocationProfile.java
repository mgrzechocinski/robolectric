package org.robolectric.bytecode;

import org.objectweb.asm.Type;

import java.util.Arrays;

class InvocationProfile {
    final Class clazz;
    final Class shadowClass;
    final String methodName;
    final boolean isStatic;
    final String[] paramTypes;
    private final int hashCode;

    InvocationProfile(Class clazz, Class shadowClass, String methodName, boolean isStatic, String[] paramTypes) {
        this.clazz = clazz;
        this.shadowClass = shadowClass;
        this.methodName = methodName;
        this.isStatic = isStatic;
        this.paramTypes = paramTypes;

        // calculate hashCode early
        int result = clazz.hashCode();
        result = 31 * result + (shadowClass != null ? shadowClass.hashCode() : 0);
        result = 31 * result + methodName.hashCode();
        result = 31 * result + (this.isStatic ? 1 : 0);
        result = 31 * result + Arrays.hashCode(paramTypes);
        hashCode = result;
    }

    public InvocationProfile(String methodSignature, boolean isStatic, ClassLoader classLoader) {
        int parenStart = methodSignature.indexOf('(');
        int methodStart = methodSignature.lastIndexOf('/', parenStart);
        String className = methodSignature.substring(0, methodStart).replace('/', '.');
        this.clazz = loadClass(classLoader, className);
        this.methodName = methodSignature.substring(methodStart + 1, parenStart);

        Type[] argumentTypes = Type.getArgumentTypes(methodSignature.substring(parenStart));
        this.paramTypes = new String[argumentTypes.length];
        for (int i = 0; i < argumentTypes.length; i++) {
            paramTypes[i] = argumentTypes[i].getClassName();
        }
        this.isStatic = isStatic;
        this.shadowClass = null;
        this.hashCode = 0;
    }

    private Class<?> loadClass(ClassLoader classLoader, String className) {
        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvocationProfile that = (InvocationProfile) o;

        if (isStatic != that.isStatic) return false;
        if (!clazz.equals(that.clazz)) return false;
        if (!methodName.equals(that.methodName)) return false;
        if (!Arrays.equals(paramTypes, that.paramTypes)) return false;
        if (shadowClass != null ? !shadowClass.equals(that.shadowClass) : that.shadowClass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
