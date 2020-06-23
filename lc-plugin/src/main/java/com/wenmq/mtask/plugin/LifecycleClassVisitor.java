package com.wenmq.mtask.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author gavin
 * @date 2019/2/18
 * lifecycle class visitor
 */
public class LifecycleClassVisitor extends ClassVisitor implements Opcodes {

    private String mClassName;

    public LifecycleClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        //System.out.println("LifecycleClassVisitor : visit -----> started ：" + name);
        this.mClassName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        //System.out.println("LifecycleClassVisitor : visitMethod : " + name);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        //匹配FragmentActivity
        System.out.println("LifecycleClassVisitor : 匹配FragmentActivity ----> ");
        if (isEqualsActivity(this.mClassName)) {
            if ("onCreate".equals(name)) {
                //处理onCreate
                System.out.println("LifecycleClassVisitor : change method ----> ");
                return new LifecycleOnCreateMethodVisitor(mv);
            } else if ("onDestroy".equals(name)) {
                //处理onDestroy
                System.out.println("LifecycleClassVisitor : change method ----> " + name + " onDestroyTime: " + System.currentTimeMillis());
                return new LifecycleOnDestroyMethodVisitor(mv);
            }
        }
        return mv;
    }

    private boolean isEqualsActivity(String p) {
//        if ("androidx/fragment/app/FragmentActivity".equals(p)) return true;
        if ("android/support/v4/app/FragmentActivity".equals(p)) return true;
        if ("com/wenmq/annosample/BaseActivity".equals(p)) return true;
//        if ("androidx/appcompat/app/AppCompatActivity" == p) return true;
        return false;
    }

    @Override
    public void visitEnd() {
        //System.out.println("LifecycleClassVisitor : visit -----> end");
        super.visitEnd();
    }
}
