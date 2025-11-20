package service;

import config.DatabaseConnection;
import dao.DomicilioFiscalDao;
import entities.DomicilioFiscal;

import java.sql.Connection;
import java.util.List;

public class DomicilioFiscalService implements GenericService<DomicilioFiscal> {

    private final DomicilioFiscalDao dao;

    public DomicilioFiscalService() {
        this.dao = new DomicilioFiscalDao();
    }

    @Override
    public void insertar(DomicilioFiscal df) throws Exception {
        throw new UnsupportedOperationException("El domicilio fiscal se inserta desde EmpresaService.");
    }

    @Override
    public void actualizar(DomicilioFiscal df) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            dao.actualizar(df, conn);
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            dao.eliminarLogico(id, conn);
        }
    }

    @Override
    public DomicilioFiscal getById(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return dao.leerPorId(id, conn);
        }
    }

    @Override
    public List<DomicilioFiscal> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return dao.leerTodos(conn);
        }
    }
}
