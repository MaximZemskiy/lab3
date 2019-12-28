package servlets;

import DAO.DAO;
import model.AuthUser;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        if(req.getParameter("login")!=null && req.getParameter("password")!=null){
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            User user = (User) DAO.getObjectByParams(new String[]{"login", "password"}, new Object[]{login, password}, User.class);
            if(user == null) {
                resp.sendRedirect("reg.html");
            }
            else {
                String hash = UUID.randomUUID().toString();
                AuthUser authUser = new AuthUser(login, hash);
                DAO.addObject(authUser);
                Cookie cookieLogin = new Cookie("login", login);
                Cookie cookieHash = new Cookie("hash_of_session", hash);
                cookieLogin.setMaxAge(12300000);
                cookieHash.setMaxAge(12300000);
                resp.addCookie(cookieLogin);
                resp.addCookie(cookieHash);
                resp.sendRedirect("all_users.jsp");
            }
            DAO.closeOpenedSession();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie[] cookies = req.getCookies();
        if(cookies == null) {
            resp.sendRedirect("auth.html");
            return;
        }
        String login = null;
        String hash = null;
        for (Cookie cookie : cookies){
            if(cookie.getName().equals("login"))
                login = cookie.getValue();
            if(cookie.getName().equals("hash_of_session"))
                hash = cookie.getValue();
        }
        if(login == null || hash == null){
            resp.sendRedirect("auth.html");
        }
        else{
            AuthUser authUser = (AuthUser) DAO.getObjectByParams(new String[]{"login", "hash"},
                    new Object[]{login, hash}, AuthUser.class);
            if(authUser != null){
                resp.sendRedirect("all_users.jsp");
            }
            else{
                resp.sendRedirect("auth.html");
            }
        }
    }
}
