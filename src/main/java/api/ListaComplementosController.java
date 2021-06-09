package main.java.api;

import java.io.IOException;
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

import main.java.api.models.Complemento;
import main.java.database.DatabaseConnection;
import main.java.database.DatabaseQueries;

/**
 * Servlet implementation class ListaComplementosController
 */
@WebServlet("/complemento/lista")
public class ListaComplementosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaComplementosController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s = request.getParameter("nom");
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.prepareStatement(DatabaseQueries.GET_LLISTA_COMPLEMENTS);
			st.setString(1, "%"+s+"%");
			rs = st.executeQuery();
			List<Complemento> lista = new ArrayList<>();
			while(rs.next()) {
				Complemento c = new Complemento();
				c.setId(rs.getInt(1));
				c.setNom(rs.getString(2));
				c.setPreu(rs.getBigDecimal(3).floatValue());
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
	}

}
