package com.google.protobuf.java;

import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import google.protobuf.compiler.Plugin.CodeGeneratorResponse.File;
import google.protobuf.compiler.Plugin.CodeGeneratorResponse.File.Builder;
import junit.framework.TestCase;

/**
 * @author: alexeagle@google.com (Alex Eagle)
 */
public class FileFactoryTest extends TestCase {
  public void testShouldUsePackageNameAndProtoFileNameToGuessJavaOutputPath() {
    File file = new FileFactory()
        .createBuilder(FileDescriptorProto.newBuilder()
            .setPackage("com.google")
            .setName("src/com/google/sample.proto").build()).build();
    assertEquals("com/google/Sample.java", file.getName());
  }
}
