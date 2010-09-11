package com.google.protobuf.java;

import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import google.protobuf.compiler.Plugin.CodeGeneratorRequest;
import google.protobuf.compiler.Plugin.CodeGeneratorResponse;
import google.protobuf.compiler.Plugin.CodeGeneratorResponse.File;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

/**
 * @author: alexeagle@google.com (Alex Eagle)
 */
public class CodeGenerator {
  private final JavaSources javaSourceFiles;

  public CodeGenerator(JavaSources javaSourceFiles) {
    this.javaSourceFiles = javaSourceFiles;
  }

  public CodeGeneratorResponse generateCode(CodeGeneratorRequest request) {
    CodeGeneratorResponse.Builder response = CodeGeneratorResponse.newBuilder();
    for (FileDescriptorProto fileDescriptorProto : request.getProtoFileList()) {
      File.Builder fileBuilder = new FileFactory().createBuilder(fileDescriptorProto);
      List<CompilationUnit> javaSources = javaSourceFiles.list();
      for (CompilationUnit javaSource : javaSources) {
        javaSource.accept(new MyVisitor(fileBuilder, response), request);
      }
    }
    return response.build();
  }

  private class MyVisitor extends VoidVisitorAdapter<CodeGeneratorRequest> {
    private static final String CLASS_SCOPE = "class_scope";
    private final File.Builder fileBuilder;
    private final CodeGeneratorResponse.Builder responseBuilder;
    private String myPackage;
    private String insertionPoint;
    private VariableDeclaratorId fieldName;

    public MyVisitor(File.Builder fileBuilder, CodeGeneratorResponse.Builder responseBuilder) {
      this.fileBuilder = fileBuilder;
      this.responseBuilder = responseBuilder;
    }

    @Override
    public void visit(PackageDeclaration n, CodeGeneratorRequest arg) {
      myPackage = n.getName().toString();
    }

    @Override
    public void visit(FieldDeclaration field, CodeGeneratorRequest arg) {
      for (FileDescriptorProto fileDescriptorProto : arg.getProtoFileList()) {
        for (DescriptorProto descriptorProto : fileDescriptorProto.getMessageTypeList()) {
          if (descriptorProto.getName().equals(field.getType().toString())) {
            insertionPoint = String.format("%s:%s.%s", CLASS_SCOPE, myPackage, field.getType());
            fieldName = field.getVariables().get(0).getId();
          }
        }
      }
    }

    @Override
    public void visit(MethodDeclaration n, CodeGeneratorRequest arg) {
      if (insertionPoint != null) {
        responseBuilder.addFile(fileBuilder.clone()
            .setInsertionPoint(insertionPoint)
            .setContent(n.toString().replaceAll(fieldName.toString() + "\\.", "")));
      }
    }
  }
}
