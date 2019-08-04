package controller;

import com.google.gson.Gson;
import entity.Account;
import entity.ActiveCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ActiveCodeController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Account.class.getName());
    private static Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String code = req.getParameter("code");
            String email = (String) req.getSession().getAttribute("email");
            Account account = ofy().load().type(Account.class).id(email).now();
            if (account != null) {
                if (account.getActiveCodesId().size() != 0) {
                    List<Long> list = account.getActiveCodesId();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) == Long.parseLong(code)) {
                            if (ofy().load().type(ActiveCode.class).id(code).now().getTimeRevoke() > Calendar.getInstance().getTimeInMillis()) {
                                account.setStatus(1);
                                ofy().save().entity(account).now();
                                req.getRequestDispatcher("/admin/home.jsp").forward(req, resp);
                                return;
                            }
                            resp.getWriter().println("Active code revoke");
                        }
                    }
                    resp.getWriter().println("Active code invalid");
                }else {
                    resp.getWriter().println("Please, login again");
                }
            } else {
                resp.getWriter().println("Account dont exist");
            }

        } catch (
                Exception ex) {
            LOGGER.log(Level.SEVERE, String.format("Can not create account. Error: %s", ex.getMessage()));
        }
    }
}
