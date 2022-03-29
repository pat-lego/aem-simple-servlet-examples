package com.aem.servlets.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class,
property = {
    "sling.servlet.paths=/bin/hello",
    "sling.servlet.methods=POST"
})
public class SimplePostServlet extends SlingAllMethodsServlet {

    // cURL with no name header curl -u admin:admin -vs -X POST http://localhost:4502/bin/hello
    // curl with a name Header curl -H "name: Patrique" -u admin:admin -vs -X POST http://localhost:4502/bin/hello

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String name = request.getHeader("name");

        String result = "Hello ";
        if (null != name && !name.isEmpty()) {
            result = result + name.trim();
        } else {
            result = result + "World";
        }

        response.setContentType("text/plain");
        response.getWriter().write(result);
        response.getWriter().flush();
        response.getWriter().close();
        response.setStatus(SlingHttpServletResponse.SC_OK);
    }
}
