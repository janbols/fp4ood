package be.guterfluss.transaction;

import be.guterfluss.user.User;

import java.io.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static scala.actors.threadpool.Executors.newCachedThreadPool;

public class TransactionUtils {

    public static <T> T withTransaction(Supplier<T> s) {
        setupDataInfrastructure();
        T result;
        try {
            result = s.get();
            completeTransaction();
        } catch (Exception condition) {
            rollbackTransaction();
            throw condition;
        } finally {
            cleanUp();
        }
        return result;
    }

    public static <T> T retrying(Supplier<T> s) {
        RuntimeException lastException = null;
        for (int retries = 3; retries > 0; retries--) {
            try {
                return s.get();
            } catch (RuntimeException e) {
                lastException = e;
                if (isRollback(e)) continue;
                else throw e;
            }
        }
        throw lastException;
    }

    private static boolean isRollback(Exception e) {
        return e.toString().length() % 2 == 0;
    }

    private static void cleanUp() {

    }

    private static void rollbackTransaction() {

    }

    private static void completeTransaction() {

    }

    private static void setupDataInfrastructure() {

    }

    static void client() {
        newCachedThreadPool().submit(
                () -> out.println(
                        "inside " + currentThread().getName()
                )
        );

        newCachedThreadPool().submit(
                () -> withThreadName("myThread",
                        () -> out.println(
                                "inside " + currentThread().getName()
                        )
                )
        );

    }

    static <T> T withThreadName(String threadName, Supplier<T> action) {
        String oldThreadName = currentThread().getName();
        currentThread().setName(threadName);
        try {
            return action.get();
        } finally {
            currentThread().setName(oldThreadName);
        }

    }

    static void withThreadName(String threadName, Runnable action) {
        String oldThreadName = currentThread().getName();
        currentThread().setName(threadName);
        try {
            action.run();
        } finally {
            currentThread().setName(oldThreadName);
        }

    }

    static <T> T doAs(User userInfo, Supplier<T> action) {
//        def currentContext = SecurityContextHolder.context
//        try {
//            def newAuth = new UsernamePasswordAuthenticationToken(userInfo, userInfo?.password, userInfo?.authorities)
//            SecurityContextHolder.context = createEmptyContext().with {ctx ->
//                    ctx.authentication = newAuth
//                ctx
//            }
        return action.get();
//        } finally {
//            SecurityContextHolder.setContext(currentContext)
//        }
    }

    static <T> T quietly(Supplier<T> action) {
        try {
            return action.get();
        } catch (Throwable all) {
            return null;
        }
    }

    static <T> T logAndRethrow(Supplier<T> action) {
        try {
            return action.get();
        } catch (Throwable all) {
            if (all instanceof InterruptedException) {
            } else {
            }
            throw all;
        }
    }

    /**
     * Helper method to create a new Reader for a stream and then
     * passes it into the closure.  The reader (and this stream) is closed after
     * the closure returns.
     *
     * @param in      a stream
     * @return the value returned by the closure
     * @throws IOException if an IOException occurs.
     * @see java.io.InputStreamReader
     * @since 1.5.2
     */
    public static <T> T withReader(InputStream in, Function<java.io.Reader, T> f) throws IOException {
        return withReader(new InputStreamReader(in), f);
    }

    public static <T, U extends Closeable> T withCloseable(U self, Function<U, T> action) throws IOException {
        try {
            T result = action.apply(self);

            Closeable temp = self;
            self = null;
            temp.close();

            return result;
        } finally {
            closeWithWarning(self);
        }
    }

    public static <T> T withWriter(Writer writer, Function<Writer, T> f) throws IOException {
        try {
            T result = f.apply(writer);

            try {
                writer.flush();
            } catch (IOException e) {
                // try to continue even in case of error
            }
            Writer temp = writer;
            writer = null;
            temp.close();
            return result;
        } finally {
            closeWithWarning(writer);
        }
    }

    private static void closeWithWarning(Closeable writer) {

    }
}
