package com.google.protobuf.java;

import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import google.protobuf.compiler.Plugin.CodeGeneratorRequest;
import google.protobuf.compiler.Plugin.CodeGeneratorResponse;
import google.protobuf.compiler.Plugin.CodeGeneratorResponse.File;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author alexeagle@google.com (Alex Eagle)
 */
public class CodeGeneratorTest extends TestCase {
  public void testInsertsHelperMethods() throws Exception {
    CodeGeneratorRequest request = CodeGeneratorRequest.newBuilder()
        .addFileToGenerate("src/foo.proto")
        .addProtoFile(FileDescriptorProto.newBuilder()
            .setName("src/foo.proto")
            .setPackage("com.google")
            .addMessageType(DescriptorProto.newBuilder()
                .setName("Model"))).build();

    JavaSources sources = new FakeJavaSources(JavaParser.parse(input(
        "package com.google;" +
            "class ModelHelper {" +
            "  private final Model model;" +
            "  public ModelHelper(Model model) {" +
            "    this.model = model;" +
            "  }" +
            "  public boolean IsAwesome() {" +
            "    return model.getCount() > 0;" +
            "  }" +
            "}")));

    CodeGeneratorResponse expectedResponse = CodeGeneratorResponse.newBuilder()
        .addFile(File.newBuilder()
            .setInsertionPoint("class_scope:com.google.Model")
            .setContent("public boolean IsAwesome() {\n    return getCount() > 0;\n}")
            .setName("com/google/Foo.java")).build();

    assertEquals(expectedResponse, new CodeGenerator(sources).generateCode(request));
  }

  private class FakeJavaSources extends JavaSources {
    private final List<CompilationUnit> compilationUnits;

    public FakeJavaSources(CompilationUnit... compilationUnits) {
      super(new java.io.File(""));
      this.compilationUnits = Arrays.asList(compilationUnits);
    }

    @Override
    public List<CompilationUnit> list() {
      return compilationUnits;
    }
  }

  private InputStream input(String javaSrc) {
    return new ByteArrayInputStream(javaSrc.getBytes());
  }
}
