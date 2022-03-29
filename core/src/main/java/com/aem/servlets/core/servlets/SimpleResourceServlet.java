package com.aem.servlets.core.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.Servlet;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;

@Component(service = { Servlet.class })
@SlingServletResourceTypes(
        resourceTypes="simple/resource",
        methods={HttpConstants.METHOD_GET, HttpConstants.METHOD_POST},
        extensions="txt")
@ServiceDescription("Simple Resource Based Servlet")
public class SimpleResourceServlet extends SlingAllMethodsServlet {

    static String postcontent;

    // 1. Run this command to set the value of postcontent curl -u admin:admin -vs -X POST -H "Content-Type: text/plain" --data "I sent this" http://localhost:4502/content/resource-example.txt
    // This will set the value of postcontent and set it to "I sent this"

    // 2. Run this command curl -u admin:admin -vs http://localhost:4502/content/resource-example.txt and it will return the value of postcontent which was set in (1) and then it will prefix postcontent with "You entered "
    
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String result = "You entered ";
        if (null != postcontent) { 
            result = result + postcontent.trim();
        } else {
            result = result + "nothing";
        }

        response.setContentType("text/plain");
        response.getWriter().write(result);
        response.getWriter().flush();
        response.getWriter().close();

        response.setStatus(SlingHttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        try {
            postcontent = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        response.setStatus(SlingHttpServletResponse.SC_OK);
    }
}
