// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
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
        FacesContext context = FacesContext.getCurrentInstance();
        Map requestMap = context.getExternalContext().getRequestMap();
        Throwable ex = (Throwable) requestMap.get("javax.servlet.error.exception");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
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
