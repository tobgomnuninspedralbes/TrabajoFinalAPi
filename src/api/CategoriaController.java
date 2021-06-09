package api;

import java.io.IOException;
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

import api.models.Categoria;
import database.DatabaseConnection;
import database.DatabaseQueries;

/**
 * Servlet implementation class CategoriaController
 */
@WebServlet("/categoria")
public class CategoriaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoriaController() {
        super();
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
		Statement st2 = null;
		ResultSet rs = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.prepareStatement(DatabaseQueries.POST_CATEGORIA);
			st2 = con.createStatement();
			rs = st2.executeQuery(DatabaseQueries.GET_ID_ULTIMA);
			rs.next();
			int lastId = rs.getInt(1);
			Categoria c = new Gson().fromJson(request.getReader().readLine(), Categoria.class);
			st.setString(1, c.getNom());
			
			if(c.getFoto() != null) {
				byte[] decodedImg = Base64.getDecoder()
	                    .decode(c.getFoto().getBytes(StandardCharsets.UTF_8));
				Path destinationFile = Paths.get("./fotos", "cat"+lastId+".jpg");
				Files.write(destinationFile, decodedImg);
	
				st.setString(2, destinationFile.toString());
			} else {
				st.setString(2, null);
			}
			st.execute();
			
			response.getWriter().append("Success");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
				st2.close();
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
			st = con.prepareStatement(DatabaseQueries.DELETE_CATEGORIA);
			st.setInt(1, id);
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
