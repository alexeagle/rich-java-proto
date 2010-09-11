package com.google.protobuf.java.example;

/**
 * @author: alexeagle@google.com (Alex Eagle)
 */
public class UserHelper {
  private final User delegate;

  public UserHelper(User delegate) {
    this.delegate = delegate;
  }

  public boolean IsAwesome() {
    return delegate.getName().equals("Alex");
  }

  // This simulates the generated proto class, without enhancement
  private class User {
    public String getName() {
      return "";
    }
  }
}
