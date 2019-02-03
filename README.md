# java11-reflection-methods
Gathering basic methods info using reflection.

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
* getMethods
* getDeclaredMethods
* get method by name and params
* get declared method by name and params