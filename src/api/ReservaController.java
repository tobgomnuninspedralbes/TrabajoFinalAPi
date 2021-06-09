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

import api.models.Complemento;
import api.models.Menu;
import api.models.Producto;
import api.models.Reserva;
import database.DatabaseConnection;
import database.DatabaseQueries;

/**
 * Servlet implementation class ReservaController
 */
@WebServlet("/reserva")
public class ReservaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservaController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		PreparedStatement st3 = null;
		Statement stC = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.prepareStatement(DatabaseQueries.POST_RESERVA);
			Reserva c = new Gson().fromJson(request.getReader().readLine(), Reserva.class);
			st.setInt(1, c.getUsuario().getId());
			st.setString(2, c.getEstado());
			st.setString(3, c.getHoraRealizacion());
			st.setString(4, c.getHoraReserva());
			
			st2 = con.prepareStatement(DatabaseQueries.POST_ITEMS_RESERVA);
			for(Menu m : c.getLlistaMenus()) {
				st2.setInt(1, c.getId());
				st2.setInt(2, m.getId());
			}
			
			stC = con.createStatement();
			st3 = con.prepareStatement(DatabaseQueries.POST_PRODUCTOS_COMPLEMENTOS_RESERVA);
			ResultSet r = null;
			for(Producto p : c.getLlistaProductos()) {
				st2.setInt(1, c.getId());
				st2.setInt(2, p.getId());
				 r = stC.executeQuery(DatabaseQueries.GET_ID_ULTIMA_ITEM_RESERVA);
				 r.next();
				 int lastId = r.getInt(1);
				 for(Complemento comp : p.getComplementos()) {
					 st3.setInt(1, lastId);
					 st3.setInt(2, comp.getId());
					 st3.execute();
				 }
				 st2.execute();
			}
			
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
			st = con.prepareStatement(DatabaseQueries.DELETE_RESERVA);
			st.setInt(1, id);
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
