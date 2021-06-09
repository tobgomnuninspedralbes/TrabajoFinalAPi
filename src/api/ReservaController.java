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
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.prepareStatement(DatabaseQueries.POST_RESERVA);
			Reserva c = new Gson().fromJson(request.getReader().readLine(), Reserva.class);
			st.setInt(1, c.getUsuario().getId());
			st.setString(2, c.getEstado());
			st.setString(3, c.getHoraRealizacion());
			st.setString(4, c.getHoraReserva());
			
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

}
