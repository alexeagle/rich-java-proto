package example;

/**
 * This class demonstrates a typical helper for a generated protocol buffer class.
 * @author alexeagle@google.com (Alex Eagle)
 */
public class UserHelper {
  private final User delegate;

  // Rather than using static methods, this example "wraps" a protocol buffer class
  public UserHelper(User delegate) {
    this.delegate = delegate;
  }

  // This method will be inlined into the generated User class.
  public boolean IsAwesome() {
    // The "delegate" reference will change to "this" in the inlined code.
    return delegate.getName().equals("Alex");
  }

  // This simulates the generated proto class, without enhancement.
  // Normally, you'd get this class from an initial protoc run.
  private class User {
    public String getName() {
      return "";
    }
  }
}
