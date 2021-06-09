package main.java.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import main.java.api.models.Usuario;
import main.java.database.DatabaseConnection;
import main.java.database.DatabaseQueries;

/**
 * Servlet implementation class ListaUsuariosController
 */
@WebServlet("/usuario/lista")
public class ListaUsuariosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaUsuariosController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		Statement stUs = null;
		ResultSet rsUs = null;
		
		con = DatabaseConnection.getConnection();
		try {
			stUs = con.createStatement();
			rsUs = stUs.executeQuery(DatabaseQueries.GET_LLISTA_USUARIS);
			List<Usuario> list = new ArrayList<>();
			while(rsUs.next()) {
				Usuario u = new Usuario();
				u.setId(rsUs.getInt(1));
				u.setTokenId(rsUs.getString(2));
				u.setRefreshToken(rsUs.getString(3));
				u.setSaldo(rsUs.getFloat(4));
				
				list.add(u);
			}
			String l = new Gson().toJson(list);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
