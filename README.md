# Swing Toaster
A toaster widget for Swing applications, done right.

Requires Java 11.

## Features
* Per-window toaster messages.  If you're looking for desktop-scoped notifications,
  you should use the standard AWT `TrayIcon.displayMessage()` method.
* Toaster messages can fade away after a configurable delay.
* If multiple toasters are displayed, they are stacked appropriately.
* Looks good in all standard Look and Feels as well as common popular ones (see
  the demo submodule).

## Project Structure
The `toaster` submodule contains the actual library.

The `toaster-demo` submodule contains a simple example of usage.

## Building

```bash
./gradlew clean build --warning-mode all
```

