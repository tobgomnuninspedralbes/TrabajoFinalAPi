package database;

import api.models.Reserva;

public class DatabaseQueries {
	public static final String GET_LLISTA_CATEGORIES = "SELECT * FROM Categorias";
	public static final String GET_ID_ULTIMA = "SELECT id FROM Categorias ORDER BY id DESC LIMIT 1";
	public static final String POST_CATEGORIA = "INSERT INTO Categorias (nom, foto) VALUES(?,?)";
	public static final String DELETE_CATEGORIA = "DELETE FROM Categorias WHERE id = ?";
	
	public static final String GET_LLISTA_PRODUCTES = "SELECT * FROM Productos WHERE categoriaId = ?";
	public static final String GET_ID_ULTIMA_PRODUCTE = "SELECT id FROM Productos ORDER BY id DESC LIMIT 1";
	public static final String GET_PRODUCTE = "SELECT * FROM Productos WHERE id = ?";
	public static final String POST_PRODUCTE = "INSERT INTO Productos(nom, descripcion, preu, categoriaId, foto) VALUES(?,?,?, ?, ?, ?)";
	public static final String DELETE_PRODUCTE = "DELETE FROM Productos WHERE id = ?";
	
	public static final String GET_LLISTA_MENUS = "SELECT * FROM Menus";
	public static final String GET_MENU = "SELECT * FROM Menus WHERE id = ?";
	public static final String GET_PLAT_MENU = "SELECT * FROM Platos WHERE id = ?";
	public static final String POST_MENU = "INSERT INTO Menus(dia, mida, preu, primerPlatId, segonPlatId) VALUES(?,?,?,?,?)";
	public static final String DELETE_MENU = "DELETE FROM Menus WHERE id = ?";
	
	public static final String GET_LLISTA_USUARIS = "SELECT * FROM Usuarios";
	public static final String GET_USUARI_TOKEN = "SELECT * FROM Usuarios WHERE id_token = ?";
	public static final String GET_USUARI = "SELECT * FROM Usuarios WHERE id = ?";
	public static final String GET_ID_ULTIMA_USUARI = "SELECT id FROM Usuarios ORDER BY id DESC LIMIT 1";
	public static final String POST_USUARI = "INSERT INTO Usuarios (id_token,refresh_token, saldo) VALUES(?,?,?)";
	public static final String DELETE_USUARI = "DELETE FROM Usuarios WHERE id = ?";

	public static final String GET_LLISTA_INGRESOS = "SELECT * FROM Ingresos WHERE usuarioId = ?";
	public static final String UPDATE_SALARI_USUARIO = "UPDATE Usuarios SET saldo = saldo + ? WHERE id = ?";
	public static final String POST_INGRES = "INSERT INTO Ingresos(usuarioId,importe,fecha) VALUES(?,?,?)";
	public static final String DELETE_INGRES = "DELETE FROM Ingresos WHERE id = ?";
	
	public static final String GET_LLISTA_COMPLEMENTS = "SELECT Complementos.id, Complementos.nom, Complementos.preu FROM Complementos WHERE nom LIKE ?";
	public static final String GET_LLISTA_COMPLEMENTS_PRODUCTE = "SELECT * FROM Complementos "
			+ "INNER JOIN ProductoComplementos "
			+ "ON ProductosComplementos.complementoId = Complementos.id "
			+ "WHERE ProductosComplementos.productoId = ?";
	public static final String POST_COMPLEMENT = "INSERT INTO Complementos(nom,preu) VALUES(?,?)";
	public static final String DELETE_COMPLEMENT = "DELETE FROM Complementos WHERE id = ?";
	
	public static final String GET_LLISTA_RESERVES_AVUI = "SELECT * FROM Reservas WHERE DATE(Reservas.fechaHoraReserva) = CURDATE()";
	public static final String GET_LLISTA_RESERVES_USUARI = "SELECT * FROM Reservas WHERE usuarioId = ?";
	public static final String GET_ITEMS_RESERVA = "SELECT ReservaItem.id, ReservaItem.itemId, ReservaItem.tipus FROM ReservaItem WHERE ReservaItem.reservaId = ?";
	public static final String GET_COMPLEMENTS_PRODUCTE_RESERVA = "SELECT Complementos.id, Complementos.nom, Complementos.preu FROM ReservaItemComplemento "
			+ "INNER JOIN Complementos ON ReservaItemComplemento.complementoId = Complementos.id WHERE reservaItemId = ?";
	
	public static final String POST_RESERVA = "INSERT INTO Reservas(usuarioId,estado,fechaHoraRealizacion,fechaHoraReserva) VALUES (?,?,?,?)";
	public static final String DELETE_RESERVA = "DELETE FROM Reservas WHERE id = ?";
	public static final String BLOQUEJAR_RESERVAS = "UPDATE FROM Reservas SET estado = "+ Reserva.BLOQUEJADA + " WHERE DATE(Reservas.fechaHoraReserva) = CURDATE()";
}
