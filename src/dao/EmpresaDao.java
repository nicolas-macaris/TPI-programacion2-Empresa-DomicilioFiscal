package dao;

import entities.Empresa;
import entities.DomicilioFiscal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDao implements GenericDao<Empresa> {

    @Override
    public void crear(Empresa empresa, Connection conn) throws Exception {
        String sql = "INSERT INTO empresa (eliminado, razon_social, cuit, actividad_principal, email) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setBoolean(1, empresa.isEliminado());
            ps.setString(2, empresa.getRazonSocial());
            ps.setString(3, empresa.getCuit());
            ps.setString(4, empresa.getActividadPrincipal());
            ps.setString(5, empresa.getEmail());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                empresa.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public Empresa leerPorId(Long id, Connection conn) throws Exception {
        String sql = "SELECT * FROM empresa WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Empresa e = new Empresa();
                e.setId(rs.getLong("id"));
                e.setEliminado(rs.getBoolean("eliminado"));
                e.setRazonSocial(rs.getString("razon_social"));
                e.setCuit(rs.getString("cuit"));
                e.setActividadPrincipal(rs.getString("actividad_principal"));
                e.setEmail(rs.getString("email"));

                return e;
            }
        }
        return null;
    }

    @Override
    public List<Empresa> leerTodos(Connection conn) throws Exception {
        String sql = "SELECT * FROM empresa WHERE eliminado = 0";
        List<Empresa> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Empresa e = new Empresa();
                e.setId(rs.getLong("id"));
                e.setEliminado(rs.getBoolean("eliminado"));
                e.setRazonSocial(rs.getString("razon_social"));
                e.setCuit(rs.getString("cuit"));
                e.setActividadPrincipal(rs.getString("actividad_principal"));
                e.setEmail(rs.getString("email"));

                lista.add(e);
            }
        }

        return lista;
    }

    @Override
    public void actualizar(Empresa empresa, Connection conn) throws Exception {
        String sql = "UPDATE empresa SET razon_social = ?, cuit = ?, "
                + "actividad_principal = ?, email = ? "
                + "WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, empresa.getRazonSocial());
            ps.setString(2, empresa.getCuit());
            ps.setString(3, empresa.getActividadPrincipal());
            ps.setString(4, empresa.getEmail());
            ps.setLong(5, empresa.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminarLogico(Long id, Connection conn) throws Exception {
        String sql = "UPDATE empresa SET eliminado = 1 WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public Empresa leerPorCuit(String cuit, Connection conn) throws Exception {
        String sql = "SELECT * FROM empresa WHERE cuit = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cuit);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Empresa e = new Empresa();
                e.setId(rs.getLong("id"));
                e.setEliminado(rs.getBoolean("eliminado"));
                e.setRazonSocial(rs.getString("razon_social"));
                e.setCuit(rs.getString("cuit"));
                e.setActividadPrincipal(rs.getString("actividad_principal"));
                e.setEmail(rs.getString("email"));
                return e;
            }
        }
        return null;
    }
}
