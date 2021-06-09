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

import api.models.Ingreso;
import database.DatabaseConnection;
import database.DatabaseQueries;

/**
 * Servlet implementation class IngresoController
 */
@WebServlet("/ingreso")
public class IngresoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IngresoController() {
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
		PreparedStatement st2 = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.prepareStatement(DatabaseQueries.POST_INGRES);
			Ingreso c = new Gson().fromJson(request.getReader().readLine(), Ingreso.class);
			st.setInt(1, c.getUsuarioID());
			st.setFloat(2, c.getImporte());
			st.setString(3, c.getFecha());
			
			st2 = con.prepareStatement(DatabaseQueries.UPDATE_SALARI_USUARIO);
			st2.setFloat(1, c.getImporte());
			st2.setInt(2, c.getUsuarioID());
			
			if(!st2.execute()) {
				response.getWriter().append("Success");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
				st2.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
