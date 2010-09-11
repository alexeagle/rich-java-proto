package com.google.protobuf.java;

import google.protobuf.compiler.Plugin.CodeGeneratorRequest;
import google.protobuf.compiler.Plugin.CodeGeneratorResponse;

import java.io.File;
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
    // TODO: we might want to locate classes relative to the proto file
    // TODO: ask Kenton about passing this from the caller of protoc
    JavaSources sources = new JavaSources(new File(System.getProperty("user.dir")));
    CodeGeneratorResponse response = new CodeGenerator(sources)
        .generateCode(request);
    response.writeTo(System.out);
  }
}
