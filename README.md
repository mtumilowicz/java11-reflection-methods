[![Build Status](https://travis-ci.com/mtumilowicz/java11-reflection-methods.svg?branch=master)](https://travis-ci.com/mtumilowicz/java11-reflection-methods)

# java11-reflection-methods
Using reflection to get information about the methods of a class.

# preface
https://github.com/mtumilowicz/java11-reflection-executables

* `java.lang.reflect.Method` is used to represent method
    * `Class<?> getReturnType()`
* `Class` provides us with four methods to gather information
concerning methods:
    * all methods:
        * `Method[] getMethods()` - returns all public methods 
            of the class or interface (including public methods
            from superclasses and superinterfaces)
            
            algorithm: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Class.html#getMethods()
            
        * `Method[] getDeclaredMethods()` - returns 
            all methods explicitly declared by the class or interface 
            (not inherited)
    * if we know method's name and parameter types:
        * `Method getMethod(String name, Class<?>... parameterTypes)` - 
            you can think of this method as a way of trying to 
            find a method in `getMethods()` by its name and parameters 
            or throwing `NoSuchMethodException` (if the method cannot be found)
        
            **note that argument type has to be exact**
        
            the algorithm of finding method is quite complex:
            more info: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Class.html#getMethod(java.lang.String,java.lang.Class...)
                
            there may be more than one method with matching name and
            parameter types in a class because while the Java language forbids a
            class to declare multiple methods with the same signature but different
            return types, the Java virtual machine does not.
   
        * `Method getDeclaredMethod(String name, Class<?>... parameterTypes)` - 
           you can think of this method as a way of trying to 
           find a method in `getDeclaredMethods()` by its name and parameters 
           or throwing `NoSuchMethodException` (if the method cannot be found)
           
# project description
We will show how to obtain info about declared methods.

Class structure is as simple as it can be:
* parent
    ```
    class Parent implements ParentInterface {
        private long privateParentMethod(String name) {
            return 1;
        }
    
        void packagePrivateParentMethod() {
    
        }
    
        protected String protectedParentMethod(int x1, int x2) {
            return "";
        }
    
        public void publicParentMethod() {
    
        }
        
        public CharSequence override(int count) {
            return "";
        }
    }
    
    interface ParentInterface {
        default void defaultParentInterfaceMethod() {
        }
    }
    ```
* child
    ```
    class Child extends Parent implements ChildInterface {
        private int privateChildMethod(String name) {
            return 1;
        }
        
        void packagePrivateChildMethod() {
            
        }
        
        protected String protectedChildMethod(int x1, int x2) {
            return "";    
        }
        
        public void publicChildMethod() {
            
        }
    
        @Override
        public String override(int count) {
            return "";
        }
    }
    
    interface ChildInterface {
        default void defaultChildInterfaceMethod() {
        }
    }
    ```

All tests are in `MethodReflectionTest` class
* `getMethods`
    ```
    var methods = Child.class.getMethods();
    
    assertThat(methods.length, is(15));
    
    var methodsAsString = Arrays.toString(methods);
    
    // Child
    assertThat(methodsAsString, containsString("public java.lang.String Child.override(int)"));
    assertThat(methodsAsString, containsString("public java.lang.CharSequence Child.override(int)"));
    assertThat(methodsAsString, containsString("public void Child.publicChildMethod()"));
    assertThat(methodsAsString, containsString("public default void ChildInterface.defaultChildInterfaceMethod()"));
    
    // Parent
    assertThat(methodsAsString, containsString("public void Parent.publicParentMethod()"));
    assertThat(methodsAsString, containsString("public default void ParentInterface.defaultParentInterfaceMethod()"));
    
    // Object
    assertThat(methodsAsString, containsString("public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException"));
    assertThat(methodsAsString, containsString("public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException"));
    assertThat(methodsAsString, containsString("public final void java.lang.Object.wait() throws java.lang.InterruptedException"));
    assertThat(methodsAsString, containsString("public boolean java.lang.Object.equals(java.lang.Object)"));
    assertThat(methodsAsString, containsString("public java.lang.String java.lang.Object.toString()"));
    assertThat(methodsAsString, containsString("public native int java.lang.Object.hashCode()"));
    assertThat(methodsAsString, containsString("public final native java.lang.Class java.lang.Object.getClass()"));
    assertThat(methodsAsString, containsString("public final native void java.lang.Object.notify()"));
    assertThat(methodsAsString, containsString("public final native void java.lang.Object.notifyAll()"));
    ```
* `getDeclaredMethods`
    ```
    var methods = Child.class.getDeclaredMethods();
    
    assertThat(methods.length, is(6));
    
    var methodsAsString = Arrays.toString(methods);
    
    assertThat(methodsAsString, containsString("public java.lang.String Child.override(int)"));
    assertThat(methodsAsString, containsString("public java.lang.CharSequence Child.override(int)"));
    assertThat(methodsAsString, containsString("public void Child.publicChildMethod()"));
    assertThat(methodsAsString, containsString("private int Child.privateChildMethod(java.lang.String)"));
    assertThat(methodsAsString, containsString("void Child.packagePrivateChildMethod()"));
    assertThat(methodsAsString, containsString("protected java.lang.String Child.protectedChildMethod(int,int)"));
    ```
* get method by name and params
    * not found - `NoSuchMethodException`
        ```
        @Test(expected = NoSuchMethodException.class)
        public void getMethod_notFound() throws NoSuchMethodException {
            Child.class.getMethod("not exists");
        }
        ```
    * private - `NoSuchMethodException`
        ```
        @Test(expected = NoSuchMethodException.class)
        public void getMethod_private() throws NoSuchMethodException {
            Child.class.getMethod("privateChildMethod", String.class);
        }
        ```
    * public
        ```
        assertThat(Child.class.getMethod("publicChildMethod").toGenericString(),
                is("public void Child.publicChildMethod()"));
        ```
    * from parent
        ```
        assertThat(Child.class.getMethod("publicParentMethod").toGenericString(),
                is("public void Parent.publicParentMethod()"));
        ```
* get declared method by name and params
    * not found - `NoSuchMethodException`
        ```
        @Test(expected = NoSuchMethodException.class)
        public void getDeclaredMethod_notFound() throws NoSuchMethodException {
            Child.class.getDeclaredMethod("not exists");
        }
        ```
    * private
        ```
        assertThat(Child.class.getDeclaredMethod("privateChildMethod", String.class).toGenericString(),
                is("private int Child.privateChildMethod(java.lang.String)"));
        ```
    * argument must be exact
        ```
        @Test(expected = NoSuchMethodException.class)
        public void getMethod_public_byObjectParam() throws NoSuchMethodException {
            assertThat(Child.class.getDeclaredMethod("privateChildMethod", Object.class).toGenericString(),
                    is("private int Child.privateChildMethod(java.lang.String)"));
        }
        ```
    * from parent
        ```
        @Test(expected = NoSuchMethodException.class)
        public void getDeclaredMethod_parent() throws NoSuchMethodException {
            Child.class.getDeclaredMethod("publicParentMethod");
        }
        ```
