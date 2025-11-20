package dao;

import entities.DomicilioFiscal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DomicilioFiscalDao implements GenericDao<DomicilioFiscal> {

    public void crearParaEmpresa(DomicilioFiscal df, Long empresaId, Connection conn) throws Exception {
        String sql = "INSERT INTO domicilio_fiscal "
                + "(eliminado, calle, numero, ciudad, provincia, codigo_postal, pais, empresa_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setBoolean(1, df.isEliminado());
            ps.setString(2, df.getCalle());
            if (df.getNumero() != null) {
                ps.setInt(3, df.getNumero());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, df.getCiudad());
            ps.setString(5, df.getProvincia());
            ps.setString(6, df.getCodigoPostal());
            ps.setString(7, df.getPais());
            ps.setLong(8, empresaId);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                df.setId(rs.getLong(1));
            }
        }
    }

    public DomicilioFiscal leerPorEmpresaId(Long empresaId, Connection conn) throws Exception {
        String sql = "SELECT * FROM domicilio_fiscal "
                + "WHERE empresa_id = ? AND eliminado = 0";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, empresaId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToDomicilio(rs);
            }
        }
        return null;
    }

    public void eliminarLogicoPorEmpresa(Long empresaId, Connection conn) throws Exception {
        String sql = "UPDATE domicilio_fiscal SET eliminado = 1 WHERE empresa_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, empresaId);
            ps.executeUpdate();
        }
    }

    @Override
    public void crear(DomicilioFiscal df, Connection conn) throws Exception {
        throw new UnsupportedOperationException(
                "Usar crearParaEmpresa(DomicilioFiscal, Long empresaId, Connection)");
    }

    @Override
    public DomicilioFiscal leerPorId(Long id, Connection conn) throws Exception {
        String sql = "SELECT * FROM domicilio_fiscal WHERE id = ? AND eliminado = 0";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToDomicilio(rs);
            }
        }
        return null;
    }

    @Override
    public List<DomicilioFiscal> leerTodos(Connection conn) throws Exception {
        String sql = "SELECT * FROM domicilio_fiscal WHERE eliminado = 0";
        List<DomicilioFiscal> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapRowToDomicilio(rs));
            }
        }

        return lista;
    }

    @Override
    public void actualizar(DomicilioFiscal df, Connection conn) throws Exception {
        String sql = "UPDATE domicilio_fiscal SET "
                + "calle = ?, numero = ?, ciudad = ?, provincia = ?, "
                + "codigo_postal = ?, pais = ? "
                + "WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, df.getCalle());
            if (df.getNumero() != null) {
                ps.setInt(2, df.getNumero());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setString(3, df.getCiudad());
            ps.setString(4, df.getProvincia());
            ps.setString(5, df.getCodigoPostal());
            ps.setString(6, df.getPais());
            ps.setLong(7, df.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminarLogico(Long id, Connection conn) throws Exception {
        String sql = "UPDATE domicilio_fiscal SET eliminado = 1 WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private DomicilioFiscal mapRowToDomicilio(ResultSet rs) throws SQLException {
        DomicilioFiscal df = new DomicilioFiscal();
        df.setId(rs.getLong("id"));
        df.setEliminado(rs.getBoolean("eliminado"));
        df.setCalle(rs.getString("calle"));

        int numero = rs.getInt("numero");
        if (rs.wasNull()) {
            df.setNumero(null);
        } else {
            df.setNumero(numero);
        }

        df.setCiudad(rs.getString("ciudad"));
        df.setProvincia(rs.getString("provincia"));
        df.setCodigoPostal(rs.getString("codigo_postal"));
        df.setPais(rs.getString("pais"));

        return df;
    }
}
