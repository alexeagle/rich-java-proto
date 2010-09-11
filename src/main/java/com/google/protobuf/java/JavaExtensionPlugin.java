package com.google.protobuf.java;

import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import google.protobuf.compiler.Plugin.CodeGeneratorRequest;
import google.protobuf.compiler.Plugin.CodeGeneratorResponse;
import google.protobuf.compiler.Plugin.CodeGeneratorResponse.File;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Adds code to Java generated protocol buffers.
 * Implements the protoc plugin interface spec, at
 * http://code.google.com/apis/protocolbuffers/docs/reference/cpp/google.protobuf.compiler.plugin.pb.html
 * @author alexeagle@google.com (Alex Eagle)
 */
public class JavaExtensionPlugin {
  public static void main(String[] args) throws IOException {
    CodeGeneratorRequest request = CodeGeneratorRequest.parseFrom(System.in);
    CodeGeneratorResponse response = new CodeGenerator(new JavaSources())
        .generateCode(request);
    response.writeTo(System.out);
  }
}
