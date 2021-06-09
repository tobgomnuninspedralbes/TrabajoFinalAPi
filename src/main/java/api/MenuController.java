package main.java.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import main.java.api.models.Menu;
import main.java.database.DatabaseConnection;
import main.java.database.DatabaseQueries;

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
		Statement s = null;
		Statement s1 = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		
		con = DatabaseConnection.getConnection();
		try {
			s = con.createStatement();
			s.execute(DatabaseQueries.POST_ITEM);
			s1 = con.createStatement();
			rs = s1.executeQuery(DatabaseQueries.GET_ID_ULTIMO_ITEM);
			rs.next();
			int id = rs.getInt(1);
			st = con.prepareStatement(DatabaseQueries.POST_MENU);
			Menu c = new Gson().fromJson(request.getReader().readLine(), Menu.class);
			st.setInt(1, id);
			st.setString(2, c.getDia());
			st.setInt(3, c.getMida());
			st.setBigDecimal(4, BigDecimal.valueOf(c.getPreu()));
			st.setInt(5, c.getPrimerPlat().getId());
			st.setInt(6, c.getSegonPlat().getId());
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
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.valueOf(request.getParameter("id"));
		Connection con = null;
		PreparedStatement st = null;
		
		con = DatabaseConnection.getConnection();
		
		try {
			st = con.prepareStatement(DatabaseQueries.DELETE_MENU);
			st.setInt(1, id);
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
