package app.service;

import app.model.dto.CompileResult;

import javax.tools.*;
import java.io.File;
import java.util.List;
import java.util.Locale;

/**
 * @author marsel.maximov
 */
public class CompilerService {

    private final JavaCompiler javaCompiler;
    private final StandardJavaFileManager javaFileManager;

    public CompilerService(JavaCompiler javaCompiler,
                           StandardJavaFileManager javaFileManager
    ) {
        this.javaCompiler = javaCompiler;
        this.javaFileManager = javaFileManager;
    }

    public CompileResult compile(File file) {
        Iterable<? extends JavaFileObject> javaFileObjects = javaFileManager.getJavaFileObjects(file);
        DiagnosticCollector<JavaFileObject> diagnosticCollector2 = new DiagnosticCollector<>();
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, javaFileManager, diagnosticCollector2, null, null, javaFileObjects);

        CompileResult compileResult = new CompileResult();
        compileResult.setResult(task.call());
        compileResult.setErrorMessage(getMessage(diagnosticCollector2.getDiagnostics()));
        return compileResult;
    }

    private String getMessage(List<Diagnostic<? extends JavaFileObject>> diagnostics) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
            stringBuilder
                    .append(diagnostic.getMessage(Locale.getDefault()))
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
