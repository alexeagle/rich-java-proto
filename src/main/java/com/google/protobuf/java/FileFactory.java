package com.google.protobuf.java;

import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import google.protobuf.compiler.Plugin.CodeGeneratorResponse.File;
import google.protobuf.compiler.Plugin.CodeGeneratorResponse.File.Builder;

/**
 * @author: alexeagle@google.com (Alex Eagle)
 */
public class FileFactory {
  public Builder createBuilder(FileDescriptorProto fileDescriptorProto) {
    String dir = fileDescriptorProto.getPackage().replaceAll("\\.", "/");
    String filename = fileDescriptorProto.getName()
        .substring(fileDescriptorProto.getName().lastIndexOf("/") + 1)
        .replaceAll(".proto", ".java");
    char first = filename.charAt(0);
    filename = Character.toUpperCase(first) + filename.substring(1);
    return File.newBuilder().setName(dir + "/" + filename);
  }
}
