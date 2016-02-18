package co.ian.kstool;

import io.undertow.Undertow;
import io.undertow.server.*;
import io.undertow.util.Headers;

public class App {
  private static boolean isEmpty(String s) {
    return (s == null || s.isEmpty());
  }

  private static int getPort () {
    String ENV_PORT = System.getenv("PORT");
    String SYS_PORT = System.getProperty("server.port");
    System.out.println("ENV_PORT: " + ENV_PORT);
    System.out.println("SYS_PORT: " + SYS_PORT);
    int port = (!isEmpty(SYS_PORT)) ?
      Integer.parseInt(SYS_PORT) :
      (!isEmpty(ENV_PORT)) ?
        Integer.parseInt(ENV_PORT) :
        8080 ;
    return port;
  }

  private static String getHost () {
    String ENV_HOSTNAME = System.getenv("HOSTNAME");
    String SYS_HOSTNAME = System.getProperty("server.hostname");
    System.out.println("ENV_HOSTNAME: " + ENV_HOSTNAME);
    System.out.println("SYS_HOSTNAME: " + SYS_HOSTNAME);
    String hostname = (!isEmpty(SYS_HOSTNAME)) ?
      SYS_HOSTNAME :
      (!isEmpty(ENV_HOSTNAME)) ?
        ENV_HOSTNAME :
        "0.0.0.0" ;
    return hostname;
  }

  public static void main(final String[] args) {
    int port = getPort();
    String hostname = getHost();
    System.out.println("Listening on port: " + port);
    System.out.println("Listening on hostname: " + hostname);

    Undertow server = Undertow.builder().addHttpListener(port, hostname)
      .setHandler(new HttpHandler() {
        public void handleRequest(final HttpServerExchange exchange) throws Exception {
          exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
          exchange.getResponseSender().send("Hello World");
        }
      }).build();
    server.start();
  }
}
