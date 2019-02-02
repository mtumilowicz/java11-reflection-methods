# java11-reflection-methods
Gathering basic methods info using reflection.

# preface
https://github.com/mtumilowicz/java11-reflection-executables

* `java.lang.reflect.Method` is used to represent method
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
        
            the algorithm of finding method is quite complex 
            (note that there can be more than one method that meet
            the requirements - Object is a superclass of all classes)  
            more info: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Class.html#getMethod(java.lang.String,java.lang.Class...)
                
            there may be more than one method with matching name and
            parameter types in a class because while the Java language forbids a
            class to declare multiple methods with the same signature but different
            return types, the Java virtual machine does not.
   
        * `Method getDeclaredMethod(String name, Class<?>... parameterTypes)` - 
           you can think of this method as a way of trying to 
           find a method in `getDeclaredMethods()` by its name and parameters 
           or throwing `NoSuchMethodException` (if the method cannot be found)