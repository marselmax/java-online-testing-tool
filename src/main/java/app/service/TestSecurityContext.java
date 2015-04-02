package app.service;

/**
 * @author marsel.maximov
 */
public class TestSecurityContext {

    private static TestSecurityManager testSecurityManager;

    public static void init() {
        testSecurityManager = new TestSecurityManager(Boolean.FALSE);
        System.setSecurityManager(testSecurityManager);
    }

    public static void enable() {
        testSecurityManager.enable();
    }

    public static void disable() {
        testSecurityManager.disable();
    }
}
