package servlets;

import DAO.DAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        if (req.getParameter("login") != null && req.getParameter("password") != null) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            User user = new User(login, password);
            User user1 = (User) DAO.getObjectByParams(new String[]{"login", "password"},
                    new Object[]{login, password}, User.class);
            if (!user.equals(user1)) {
                DAO.addObject(user);
            }
           req.getRequestDispatcher("auth.html").forward(req,resp);
        }
    }
}
