package api;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import api.models.Categoria;
import database.DatabaseConnection;
import database.DatabaseQueries;

/**
 * Servlet implementation class ListaCategorias
 */
@WebServlet("/categoria/lista")
public class ListaCategoriasController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaCategoriasController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(DatabaseQueries.GET_LLISTA_CATEGORIES);
			List<Categoria> list = new ArrayList<>();
			while(rs.next()) {
				Categoria c = new Categoria();
				c.setId(rs.getInt(1));
				c.setNom(rs.getString(2));
				File foto = new File(rs.getString(3));
				String b64 = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(foto));
				c.setFoto(b64);
				list.add(c);
			}
			String l = new Gson().toJson(list);
			response.getWriter().append(l);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
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
