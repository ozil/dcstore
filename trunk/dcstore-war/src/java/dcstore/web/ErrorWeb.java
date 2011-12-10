/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dcstore.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

/**
 *
 * @author dcebula
 */
@ManagedBean
@RequestScoped
public class ErrorWeb {

    /** Creates a new instance of ErrorWeb */
    public ErrorWeb() {
    }

    public String getStackTrace() {
        // Get the current JSF context
        FacesContext context = FacesContext.getCurrentInstance();
        Map requestMap = context.getExternalContext().getRequestMap();

        // Fetch the exception
        Throwable ex = (Throwable) requestMap.get("javax.servlet.error.exception");

        // Create a writer for keeping the stacktrace of the exception
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);

        // Fill the stack trace into the write
        fillStackTrace(ex, pw);

        return writer.toString();
    }

    private void fillStackTrace(Throwable ex, PrintWriter pw) {
        if (null == ex) {
            return;
        }

        ex.printStackTrace(pw);

        if (ex instanceof ServletException) {
            Throwable cause = ((ServletException) ex).getRootCause();
            if (null != cause) {
                pw.println("Root Cause:");
                fillStackTrace(cause, pw);
            }
        } else {
            Throwable cause = ex.getCause();

            if (null != cause) {
                pw.println("Cause:");
                fillStackTrace(cause, pw);
            }
        }
    }
}
