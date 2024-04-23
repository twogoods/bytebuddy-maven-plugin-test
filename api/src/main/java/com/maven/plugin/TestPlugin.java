package com.maven.plugin;

import com.maven.test.Module;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.build.Plugin;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.field.FieldList;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.pool.TypePool;
import org.objectweb.asm.ClassVisitor;

import java.io.IOException;

/**
 * @author luhaoshuai@bytedance.com
 * @date 2024/4/23
 */
public class TestPlugin implements Plugin {

    private static final TypeDescription moduleType = new TypeDescription.ForLoadedType(Module.class);


    @Override
    public DynamicType.Builder<?> apply(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassFileLocator classFileLocator) {
        return builder.visit(new Generator());
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public boolean matches(TypeDescription target) {
        boolean isInstrumentationModule = false;
        TypeDefinition instrumentation = target.getSuperClass();
        while (instrumentation != null) {
            if (instrumentation.equals(moduleType)) {
                isInstrumentationModule = true;
                break;
            }
            instrumentation = instrumentation.getSuperClass();
        }
        return isInstrumentationModule;
    }


    class Generator implements AsmVisitorWrapper {

        @Override
        public int mergeWriter(int i) {
            System.out.println("----------- mergeWriter...");
            return 0;
        }

        @Override
        public int mergeReader(int i) {
            System.out.println("----------- mergeReader...");
            return 0;
        }

        @Override
        public ClassVisitor wrap(TypeDescription typeDescription, ClassVisitor classVisitor, Implementation.Context context, TypePool typePool, FieldList<FieldDescription.InDefinedShape> fieldList, MethodList<?> methodList, int i, int i1) {
            System.out.println("----------- Generator wrap...");
            return classVisitor;
        }
    }
}
