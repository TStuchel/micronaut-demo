package com.dbs.micronaut.demo;

import io.micronaut.runtime.Micronaut;

/**
 * DEVELOPER NOTE: This is the class that starts it all. It doesn't matter what the name of the class is... it doesn't
 * have to be called "Application" (but it's a convenient name to use). It's just a plain Java class with a "main"
 * method (every Java application must have one). The call to the static "Micronaut.run()" method is what actually
 * launches Micronaut (by default an instance of a Netty web server) and officially launches the application. A trick
 * with Micronaut is that it automatically pre-compiles and wires all of the dependency injection prior to runtime,
 * which makes Micronaut start up about 4 times faster than SpringBoot and consume less memory.
 * <p>
 * You can run this file directly, which will start up Spring for real. If you do, you can hit the following endpoint
 * in Postman or your browser... and get a 404 not found, since there aren't *really* any customers...
 * <p>
 * http://localhost:8080/v1/customers/1
 * <p>
 *
 * @see com.dbs.micronaut.demo.customer.CustomerController next!
 */
public class Application {

    // ------------------------------------------------ PUBLIC METHODS -------------------------------------------------

    /**
     * DEVELOPER NOTE: There must always be a main() for a Java application.
     */
    public static void main(String[] args) {

        // Launch Micronaut!
        Micronaut.run(Application.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
}