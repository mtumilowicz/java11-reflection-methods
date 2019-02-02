/**
 * Created by mtumilowicz on 2019-02-02.
 */
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
