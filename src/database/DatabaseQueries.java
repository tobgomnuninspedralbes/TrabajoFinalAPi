package database;

public class DatabaseQueries {
	public static final String GET_LLISTA_CATEGORIES = "SELECT * FROM Categorias";
	public static final String POST_CATEGORIA = "INSERT INTO Categorias (nom, foto) VALUES(?,?)";
	public static final String DELETE_CATEGORIA = "DELETE FROM Categorias WHERE id = ?";
	
	public static final String GET_LLISTA_PRODUCTES = "SELECT * FROM Productos WHERE categoriaId = ?";
	public static final String POST_PRODUCTE = "INSERT INTO Productos(preu, categoriaId, foto, descripcion) VALUES(?, ?, ?, ?)";
	public static final String DELETE_PRODUCTE = "DELETE FROM Productos WHERE id = ?";
	
	public static final String GET_LLISTA_MENUS = "SELECT * FROM Menus";
	public static final String GET_PLAT_MENU = "SELECT * FROM Platos WHERE id = ?";
	public static final String POST_MENU = "INSERT INTO Menus(dia, mida, preu, primerPlatId, segonPlatId) VALUES(?,?,?,?,?)";
	public static final String DELETE_MENU = "DELETE FROM Menus WHERE id = ?";
	
	public static final String GET_LLISTA_USUARIS = "";
	public static final String POST_USUARI = "INSERT INTO Usuarios VALUES(?)";
	public static final String DELETE_USUARI = "DELETE FROM Usuarios WHERE id = ?";

	public static final String GET_LLISTA_INGRESOS = "SELECT * FROM Ingresos WHERE usuarioId = ?";
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
	public static final String GET_TIPUS_ITEM_RESERVA = "SELECT ReservaItems.tipus FROM ReservaItems WHERE ReservaItems.reservaId = ?";
	public static final String GET_LLISTA_ITEMS_RESERVA_PRODUCTES = "SELECT Productos.id, Productos.preu, Productos.categoriaId, Productos.foto, Productos.descripcion "
			+ "FROM ReservaItems, Productos WHERE ReservaItems.reservaId = ? AND ReservaItems.tipus = 1 AND ReservaItems.itemId = Productos.id";
	public static final String GET_LLISTA_ITEMS_RESERVA_MENUS = "SELECT Menus.id, Menus.dia, Menus.mida, Menus.preu, Menus.primerPlatId, Menus.segonPlatId "
			+ "FROM ReservaItems, Menus WHERE ReservaItems.reservaId = ? AND ReservaItems.tipus = 2 AND ReservaItems.itemId = Menus.id";
	public static final String POST_RESERVA = "INSERT INTO Reservas(usuarioId,estado,fechaHoraRealizacion,fechaHoraReserva) VALUES (?,?,?,?)";
	public static final String DELETE_RESERVA = "DELETE FROM Reservas WHERE id = ?";
	
	public static final String BLOQUEJAR_RESERVES = "";
}
