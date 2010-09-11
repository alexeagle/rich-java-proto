package com.google.protobuf.java;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: alexeagle@google.com (Alex Eagle)
 */
public class JavaSources {
  //TODO: haw to find the sources?
  private File srcDir = new File("/Users/alexeagle/Projects/richjavaproto/src/main/java");

  public List<CompilationUnit> list() {
    List<File> recursiveListing = new ArrayList<File>();
    findSources(srcDir, recursiveListing);
    List<CompilationUnit> result = new ArrayList<CompilationUnit>();
    for (File javaSrc : recursiveListing) {
      try {
        CompilationUnit compilationUnit = JavaParser.parse(javaSrc);
        result.add(compilationUnit);
      } catch (ParseException e) {
        System.err.println("Couldn't parse " + javaSrc.getAbsolutePath() + ", skipping");
      }
    }
    return result;
  }

  private void findSources(File file, List<File> recursiveListing) {
    if (file.isDirectory()) {
      for (File sub : file.listFiles()) {
        findSources(sub, recursiveListing);
      }
    } else {
      if (file.getName().endsWith(".java")) {
        recursiveListing.add(file);
      }
    }
  }
}
