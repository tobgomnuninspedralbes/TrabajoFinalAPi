package api;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import api.models.Producto;
import database.DatabaseConnection;
import database.DatabaseQueries;

/**
 * Servlet implementation class ProductoController
 */
@WebServlet("/producto")
public class ProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductoController() {
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
		Statement s = null;
		Statement s1 = null;
		ResultSet r = null;
		
		con = DatabaseConnection.getConnection();
		try {
			s = con.createStatement();
			s.execute(DatabaseQueries.POST_ITEM);
			s1 = con.createStatement();
			r = s1.executeQuery(DatabaseQueries.GET_ID_ULTIMO_ITEM);
			r.next();
			int id = r.getInt(1);
			st = con.prepareStatement(DatabaseQueries.POST_PRODUCTE);
			Producto c = new Gson().fromJson(request.getReader().readLine(), Producto.class);
			st.setInt(1, id);
			st.setString(2, c.getNom());
			st.setString(3, c.getDescripcion());
			st.setInt(4, c.getCategoria());
			st.setBigDecimal(5, BigDecimal.valueOf(c.getPreu()));
			
			if(c.getFoto()!=null) {
				byte[] decodedImg = Base64.getDecoder()
	                    .decode(c.getFoto().getBytes(StandardCharsets.UTF_8));
				Path destinationFile = Paths.get("./fotos", "prod"+id+".jpg");
				Files.write(destinationFile, decodedImg);
				st.setString(6, destinationFile.toString());
			} else {
				st.setString(6, null);
			}
			
			if(st.execute()) {
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
			st = con.prepareStatement(DatabaseQueries.DELETE_PRODUCTE);
			st.setInt(1, id);
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
