import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mtumilowicz on 2019-02-02.
 */
public class MethodReflectionTest {
    
    @Test
    public void getMethods() {
        var methods = Child.class.getMethods();
        
        assertThat(methods.length, is(15));

        var methodsAsString = Arrays.toString(methods);
        
        // Child
        assertThat(methodsAsString, containsString("public java.lang.String Child.override(int)"));
        assertThat(methodsAsString, containsString("public java.lang.CharSequence Child.override(int)"));
        assertThat(methodsAsString, containsString("public void Child.publicChildMethod()"));
        assertThat(methodsAsString, containsString("public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException"));

        // Parent
        assertThat(methodsAsString, containsString("public void Parent.publicParentMethod()"));
        assertThat(methodsAsString, containsString("public default void ParentInterface.defaultParentInterfaceMethod()"));

        // Object
        assertThat(methodsAsString, containsString("public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException"));
        assertThat(methodsAsString, containsString("public final void java.lang.Object.wait() throws java.lang.InterruptedException"));
        assertThat(methodsAsString, containsString("public boolean java.lang.Object.equals(java.lang.Object)"));
        assertThat(methodsAsString, containsString("public java.lang.String java.lang.Object.toString()"));
        assertThat(methodsAsString, containsString("public native int java.lang.Object.hashCode()"));
        assertThat(methodsAsString, containsString("public final native java.lang.Class java.lang.Object.getClass()"));
        assertThat(methodsAsString, containsString("public final native void java.lang.Object.notify()"));
        assertThat(methodsAsString, containsString("public final native void java.lang.Object.notifyAll()"));
        assertThat(methodsAsString, containsString("public default void ChildInterface.defaultChildInterfaceMethod()"));
    }
}
