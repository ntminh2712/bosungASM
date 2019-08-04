package controller;

import com.google.gson.Gson;
import entity.Account;
import entity.ActiveCode;
import entity.JsonResponse;
import until.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class AccountController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Account.class.getName());
    private static Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            String avatar = req.getParameter("avatar");
            Account account = new Account();
            account.setEmail(email);
            account.setLinkAvartar(avatar);
            if (StringUtil.isValidEmailAddress(email)) {
                ActiveCode activeCode = new ActiveCode();
                activeCode.setTimeRevoke(Calendar.getInstance().getTimeInMillis() + 900000);
                activeCode.setCode(generateActivecode());
                ofy().save().entities(activeCode).now();
                Account accountExist = ofy().load().type(Account.class).id(email).now();
                if (accountExist != null) {
                    accountExist.addActiveCode(activeCode.getCode());
                    ofy().save().entity(accountExist).now();
                } else {
                    account.addActiveCode(activeCode.getCode());
                    ofy().save().entity(account).now();
                }
                sendMail("minhntd00562@fpt.edu.vn", account.getEmail(), "this is your active code", activeCode.getCode());
                req.getSession().setAttribute("email", email);
                req.getRequestDispatcher("/admin/active.jsp").forward(req, resp);
            } else {
                resp.getWriter().println("Email invalid !");
                resp.getWriter().println("Email invalid !");
                LOGGER.warning("Email invalid !");
                LOGGER.log(Level.SEVERE, String.format("Email invalid"));
            }
        } catch (Exception ex) {
            LOGGER.warning("Can not create account.");
            LOGGER.warning(ex.getMessage());
        }
    }


    private long generateActivecode() {
        Random rnd = new Random();
        long n = 100000 + rnd.nextInt(900000);
        return n;
    }

    public void sendMail(String sendEmailFrom, String sendMailTo, String messageSubject, long messageText) {
        Properties prop = new Properties();
        Session session = Session.getDefaultInstance(prop, null);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(sendEmailFrom));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(sendMailTo, "Hello,"));
            msg.setSubject(messageSubject);
            msg.setText(messageText + "");
            Transport.send(msg);
            LOGGER.warning("sentMail success.");
        } catch (AddressException e) {
            LOGGER.warning("AddressException");
            e.printStackTrace();
        } catch (MessagingException e) {
            LOGGER.warning("MessagingException");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            LOGGER.warning("UnsupportedEncodingException.");
            e.printStackTrace();
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String avatar = req.getParameter("avatar");

        Account exitsAccount = ofy().load().type(Account.class).id(email).now();
        if (exitsAccount == null || exitsAccount.isDeactive()) {
            LOGGER.warning("Account is not found or has been deleted!.");
            resp.getWriter().println("Account is not found or has been deleted!.");

        }
        String content = StringUtil.convertInputStreamToString(req.getInputStream());
        Account updateArticle = gson.fromJson(content, Account.class);
        exitsAccount.setLinkAvartar(updateArticle.getLinkAvartar());
        ofy().save().entity(updateArticle).now();
        LOGGER.warning("Update success.");
        resp.getWriter().println("Update success.");
    }


}
