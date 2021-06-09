package api;

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

import api.models.Ingreso;
import database.DatabaseConnection;
import database.DatabaseQueries;

/**
 * Servlet implementation class ListaIngresosController
 */
@WebServlet("/ingreso/lista")
public class ListaIngresosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaIngresosController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int usuario = Integer.valueOf(request.getParameter("usuarioId"));
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.prepareStatement(DatabaseQueries.GET_LLISTA_INGRESOS);
			st.setInt(1, usuario);
			rs = st.executeQuery();
			List<Ingreso> lista = new ArrayList<>();
			while(rs.next()) {
				Ingreso c = new Ingreso();
				c.setId(rs.getInt(1));
				c.setUsuarioID(rs.getInt(2));
				c.setImporte(rs.getBigDecimal(3).floatValue());
				c.setFecha(rs.getString(4));
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
