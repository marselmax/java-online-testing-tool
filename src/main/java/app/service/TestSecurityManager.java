package app.service;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

/**
 * @author marsel.maximov
 */
public class TestSecurityManager extends SecurityManager {

    private static final String EXCEPTION_MESSAGE = "Uploaded class hasn't got permission";
    private final ThreadLocal<Boolean> enabledFlag;

    public TestSecurityManager(Boolean isEnabled) {
        this.enabledFlag = new ThreadLocal<>();
        enabledFlag.set(isEnabled);
    }

    public void enable() {
        enabledFlag.set(Boolean.TRUE);
    }

    public void disable() {
        enabledFlag.set(Boolean.FALSE);
    }

    @Override
    public void checkPermission(Permission perm) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkCreateClassLoader() {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkAccess(Thread t) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkAccess(ThreadGroup g) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkExit(int status) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkExec(String cmd) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkLink(String lib) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkRead(FileDescriptor fd) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkRead(String file) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkRead(String file, Object context) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkWrite(FileDescriptor fd) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkWrite(String file) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkDelete(String file) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkConnect(String host, int port) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkConnect(String host, int port, Object context) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkListen(int port) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkAccept(String host, int port) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkMulticast(InetAddress maddr) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkMulticast(InetAddress maddr, byte ttl) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkPropertiesAccess() {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkPropertyAccess(String key) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public boolean checkTopLevelWindow(Object window) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
        return Boolean.TRUE;
    }

    @Override
    public void checkPrintJobAccess() {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkSystemClipboardAccess() {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkAwtEventQueueAccess() {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkPackageAccess(String pkg) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkPackageDefinition(String pkg) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkSetFactory() {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkMemberAccess(Class<?> clazz, int which) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void checkSecurityAccess(String target) {
        if (isEnabled()) {
            throw new SecurityException(EXCEPTION_MESSAGE);
        }
    }

    private boolean isEnabled() {
        return enabledFlag.get() != null ? enabledFlag.get() : Boolean.FALSE;
    }
}
