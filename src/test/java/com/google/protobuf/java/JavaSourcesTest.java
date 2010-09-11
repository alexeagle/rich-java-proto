package com.google.protobuf.java;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author: alexeagle@google.com (Alex Eagle)
 */
public class JavaSourcesTest extends TestCase {
  public void testDoesntSpamSystemOut() {
    ByteArrayOutputStream dontSpamMe = new ByteArrayOutputStream();
    System.setOut(new PrintStream(dontSpamMe));
    System.setErr(new PrintStream(dontSpamMe));
    new JavaSources().list();
    assertTrue(dontSpamMe.toString(), dontSpamMe.size() == 0);
  }
}
