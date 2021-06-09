package api;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import api.models.Complemento;
import database.DatabaseConnection;
import database.DatabaseQueries;

/**
 * Servlet implementation class ComplementoController
 */
@WebServlet("/complemento")
public class ComplementoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComplementoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int producto = Integer.valueOf(request.getParameter("productoId"));
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.prepareStatement(DatabaseQueries.GET_LLISTA_COMPLEMENTS_PRODUCTE);
			st.setInt(1, producto);
			rs = st.executeQuery();
			List<Complemento> lista = new ArrayList<>();
			while(rs.next()) {
				Complemento c = new Complemento();
				c.setId(rs.getInt(1));
				c.setNom(rs.getString(2));
				c.setPreu(rs.getFloat(3));
				lista.add(c);
			}
			String l = new Gson().toJson(lista);
			response.getWriter().append(l);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement st = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.prepareStatement(DatabaseQueries.POST_COMPLEMENT);
			Complemento c = new Gson().fromJson(request.getReader().readLine(), Complemento.class);
			st.setString(1, c.getNom());
			st.setBigDecimal(2, BigDecimal.valueOf(c.getPreu()));
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
			st = con.prepareStatement(DatabaseQueries.DELETE_COMPLEMENT);
			st.setInt(1, id);
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
