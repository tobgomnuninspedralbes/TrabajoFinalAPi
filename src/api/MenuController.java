package api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import api.models.Menu;
import database.DatabaseConnection;
import database.DatabaseQueries;

/**
 * Servlet implementation class MenuController
 */
@WebServlet("/menu")
public class MenuController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement st = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.prepareStatement(DatabaseQueries.POST_MENU);
			Menu c = new Gson().fromJson(request.getReader().readLine(), Menu.class);
			st.setString(1, c.getDia());
			st.setInt(2, c.getMida());
			st.setFloat(3, c.getPreu());
			st.setInt(4, c.getPrimerPlat().getId());
			st.setInt(5, c.getSegonPlat().getId());
			st.execute();
			response.getWriter().append("Success");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
