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

import api.models.Complemento;
import api.models.Item;
import api.models.Menu;
import api.models.Plato;
import api.models.Producto;
import api.models.Reserva;
import api.models.Usuario;
import database.DatabaseConnection;
import database.DatabaseQueries;

/**
 * Servlet implementation class LlistaReservaController
 */
@WebServlet("/reserva/lista/usuario")
public class LlistaReservaUsuariController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LlistaReservaUsuariController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int usuariId = Integer.valueOf(request.getParameter("usuariId"));
		Connection con = null;
		PreparedStatement st = null;
		PreparedStatement stUs = null;
		ResultSet rs = null;
		ResultSet rsUs = null;
		PreparedStatement stIt = null;
		ResultSet rsIt = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.prepareStatement(DatabaseQueries.GET_LLISTA_RESERVES_USUARI);
			st.setInt(1, usuariId);
			rs = st.executeQuery();
			List<Reserva> list = new ArrayList<>();
			while(rs.next()) {
				Reserva c = new Reserva();
				c.setId(rs.getInt(1));
				c.setPreu(rs.getFloat(2));
				stUs = con.prepareStatement(DatabaseQueries.GET_USUARI);
				stUs.setInt(1, rs.getInt(3));
				Usuario u = new Usuario();
				rsUs = stUs.executeQuery();
				u.setId(rsUs.getInt(1));
				u.setTokenId(rsUs.getString(2));
				u.setRefreshToken(rsUs.getString(3));
				u.setSaldo(rsUs.getFloat(4));
				c.setUsuario(u);
				c.setEstado(rs.getString(4));
				c.setHoraRealizacion(rs.getString(5));
				c.setHoraReserva(rs.getString(6));

				stIt = con.prepareStatement(DatabaseQueries.GET_ITEMS_RESERVA);
				stIt.setInt(1, c.getId());
				rsIt = stIt.executeQuery();
				
				List<Item> listItem = new ArrayList<>();
				List<Integer> listIdItemReserva = new ArrayList<>();
				while(rsIt.next()) {
					listIdItemReserva.add(rsIt.getInt(1));
					Item i = new Item();
					i.setId(rsIt.getInt(2));
					i.setTipus(rsIt.getInt(3));
					listItem.add(i);
				}
				List<Menu> listMenus = new ArrayList<>();
				List<Producto> listProductos = new ArrayList<>();
				PreparedStatement pst = null;
				ResultSet rs1 = null;

				for(int i = 0; i < listItem.size();i++) {
					if(listItem.get(i).getTipus() == Item.MENU) {
						stIt = con.prepareStatement(DatabaseQueries.GET_MENU);
						stIt.setInt(1, listItem.get(i).getId());
						rsIt = stIt.executeQuery();
						rsIt.next();
						
						Menu m = new Menu();
						m.setId(rs.getInt(1));
						m.setDia(rs.getString(2));
						m.setMida(rs.getInt(3));
						m.setPreu(rs.getFloat(4));
						
						int pp = rs.getInt(5);
						int sp = rs.getInt(6);
						
						pst = con.prepareStatement(DatabaseQueries.GET_PLAT_MENU);
						
						Plato primerPlat = new Plato();
						Plato segonPlat = new Plato();
						pst.setInt(1, pp);
						rs1 = pst.executeQuery();
						primerPlat.setId(rs1.getInt(1));
						primerPlat.setNom(rs1.getString(2));
						File foto = new File(rs1.getString(3));
						String b64 = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(foto));
						primerPlat.setFoto(b64);
						m.setPrimerPlat(primerPlat);
						
						pst.setInt(1, sp);
						rs1 = pst.executeQuery();
						segonPlat.setId(rs1.getInt(1));
						segonPlat.setNom(rs1.getString(2));
						File foto1 = new File(rs1.getString(3));
						String b641 = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(foto1));
						segonPlat.setFoto(b641);
						m.setSegonPlat(segonPlat);
						
						listMenus.add(m);
						
					} else if(listItem.get(i).getTipus() == Item.PRODUCTE) {
						stIt = con.prepareStatement(DatabaseQueries.GET_PRODUCTE);
						stIt.setInt(1, listItem.get(i).getId());
						rsIt = stIt.executeQuery();
						rsIt.next();
						
						Producto p = new Producto();
						p.setId(rsIt.getInt(1));
						p.setNom(rsIt.getString(2));
						p.setDescripcion(rsIt.getString(3));
						p.setPreu(rsIt.getFloat(4));
						p.setCategoria(rsIt.getInt(5));
						File foto = new File(rsIt.getString(6));
						String b64 = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(foto));
						p.setFoto(b64);
						List<Complemento> listComp = getLlistaComplementosReserva(listIdItemReserva.get(i));
						p.setComplementos(listComp);
						
						listProductos.add(p);
					}
				}
				c.setLlistaMenus(listMenus);
				c.setLlistaProductos(listProductos);
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
	
	protected List<Complemento> getLlistaComplementosReserva(int itemReservaId){
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		con = DatabaseConnection.getConnection();
		try {
			st = con.prepareStatement(DatabaseQueries.GET_COMPLEMENTS_PRODUCTE_RESERVA);
			st.setInt(1, itemReservaId);
			rs = st.executeQuery();
			List<Complemento> list = new ArrayList<>();
			while(rs.next()) {
				Complemento c = new Complemento();
				c.setId(rs.getInt(1));
				c.setNom(rs.getString(2));
				c.setPreu(rs.getBigDecimal(3).floatValue());
				list.add(c);
			}
			return list;
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
		return null;
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
