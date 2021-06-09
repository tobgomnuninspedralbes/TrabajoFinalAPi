package api;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

import api.models.Menu;
import api.models.Plato;
import database.DatabaseConnection;
import database.DatabaseQueries;

/**
 * Servlet implementation class LlistaMenusController
 */
@WebServlet("/menu/lista")
public class LlistaMenusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LlistaMenusController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.createStatement();
			pst = con.prepareStatement(DatabaseQueries.GET_PLAT_MENU);
			rs = st.executeQuery(DatabaseQueries.GET_LLISTA_MENUS);
			List<Menu> lista = new ArrayList<>();
			while(rs.next()) {
				Menu c = new Menu();
				c.setId(rs.getInt(1));
				c.setDia(rs.getString(2));
				c.setMida(rs.getInt(3));
				c.setPreu(rs.getBigDecimal(4).floatValue());
				
				int pp = rs.getInt(5);
				int sp = rs.getInt(6);
				
				Plato primerPlat = new Plato();
				Plato segonPlat = new Plato();
				pst.setInt(1, pp);
				rs1 = pst.executeQuery();
				primerPlat.setId(rs1.getInt(1));
				primerPlat.setNom(rs1.getString(2));
				String f = rs1.getString(3);
				if(f!=null) {
					File foto = new File(f);
					String b64 = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(foto));
					primerPlat.setFoto(b64);
				}
				c.setPrimerPlat(primerPlat);
				
				pst.setInt(1, sp);
				rs1 = pst.executeQuery();
				segonPlat.setId(rs1.getInt(1));
				segonPlat.setNom(rs1.getString(2));
				f = rs1.getString(3);
				if(f!=null) {
					File foto1 = new File(f);
					String b641 = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(foto1));
					segonPlat.setFoto(b641);
				}
				c.setSegonPlat(segonPlat);
				
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
