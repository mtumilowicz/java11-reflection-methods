/**
 * Created by mtumilowicz on 2019-02-02.
 */
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