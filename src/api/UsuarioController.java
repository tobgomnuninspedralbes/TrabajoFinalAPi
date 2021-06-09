package api;

import java.io.IOException;
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

import api.models.TokenUsuario;
import api.models.Usuario;
import database.DatabaseConnection;
import database.DatabaseQueries;

/**
 * Servlet implementation class UsuarioController
 */
@WebServlet("/usuario")
public class UsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement stUs = null;
		ResultSet rsUs = null;
		
		TokenUsuario c = new Gson().fromJson(request.getReader().readLine(), TokenUsuario.class);
		
		con = DatabaseConnection.getConnection();
		try {
			stUs = con.prepareStatement(DatabaseQueries.GET_USUARI_TOKEN);
			stUs.setString(1, c.getId_token());
			rsUs = stUs.executeQuery();
			Usuario u = new Usuario();
			if(rsUs.next()) {
				u.setId(rsUs.getInt(1));
				u.setTokenId(rsUs.getString(2));
				u.setRefreshToken(rsUs.getString(3));
				u.setSaldo(rsUs.getFloat(4));
			} else {
				u = doPost(c);
			}
			
			String l = new Gson().toJson(u);
			response.getWriter().append(l);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rsUs.close();
				stUs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected Usuario doPost(TokenUsuario c) {
		Connection con = null;
		PreparedStatement st = null;
		Statement st2 = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.prepareStatement(DatabaseQueries.POST_USUARI);
			st2 = con.createStatement();
			st.setString(1, c.getId_token());
			st.setString(2, c.getRefresh_token());
			st.setInt(3, 0);
			st.execute();
			ResultSet r = st2.executeQuery(DatabaseQueries.GET_ID_ULTIMA_USUARI);
			r.next();
			int ultId = r.getInt(1);
			Usuario u = new Usuario();
			u.setId(ultId);
			u.setTokenId(c.getId_token());
			u.setRefreshToken(c.getRefresh_token());
			u.setSaldo(0);
			return u;
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
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement st = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.prepareStatement(DatabaseQueries.POST_USUARI);
			Usuario c = new Gson().fromJson(request.getReader().readLine(), Usuario.class);
			st.setString(1, c.getTokenId());
			st.setString(3, c.getRefreshToken());
			st.setFloat(3, c.getSaldo());
			
			if(st.execute()){
				response.getWriter().append("Success");
			}
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
			st = con.prepareStatement(DatabaseQueries.DELETE_USUARI);
			st.setInt(1, id);
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
