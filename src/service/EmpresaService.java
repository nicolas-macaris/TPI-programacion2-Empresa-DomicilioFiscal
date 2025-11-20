package service;

import config.DatabaseConnection;
import dao.EmpresaDao;
import dao.DomicilioFiscalDao;
import entities.Empresa;
import entities.DomicilioFiscal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EmpresaService implements GenericService<Empresa> {

    private final EmpresaDao empresaDao;
    private final DomicilioFiscalDao domicilioFiscalDao;

    public EmpresaService() {
        this.empresaDao = new EmpresaDao();
        this.domicilioFiscalDao = new DomicilioFiscalDao();
    }

    @Override
    public void insertar(Empresa empresa) throws Exception {
        validarEmpresa(empresa);

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            empresaDao.crear(empresa, conn);

            DomicilioFiscal df = empresa.getDomicilioFiscal();
            if (df != null) {
                domicilioFiscalDao.crearParaEmpresa(df, empresa.getId(), conn);
            }

            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void actualizar(Empresa empresa) throws Exception {
        validarEmpresa(empresa);

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // actualizar empresa
            empresaDao.actualizar(empresa, conn);

            DomicilioFiscal df = empresa.getDomicilioFiscal();
            if (df != null && df.getId() != null) {
                domicilioFiscalDao.actualizar(df, conn);
            }

            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // baja logica de empresa
            empresaDao.eliminarLogico(id, conn);

            domicilioFiscalDao.eliminarLogicoPorEmpresa(id, conn);

            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public Empresa getById(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Empresa e = empresaDao.leerPorId(id, conn);
            if (e != null) {
                DomicilioFiscal df = domicilioFiscalDao.leerPorEmpresaId(id, conn);
                e.setDomicilioFiscal(df);
            }
            return e;
        }
    }

    @Override
    public List<Empresa> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            List<Empresa> empresas = empresaDao.leerTodos(conn);
            for (Empresa e : empresas) {
                DomicilioFiscal df = domicilioFiscalDao.leerPorEmpresaId(e.getId(), conn);
                e.setDomicilioFiscal(df);
            }
            return empresas;
        }
    }

    private void validarEmpresa(Empresa empresa) {
        if (empresa == null) {
            throw new IllegalArgumentException("La empresa no puede ser null");
        }
        if (empresa.getRazonSocial() == null || empresa.getRazonSocial().isBlank()) {
            throw new IllegalArgumentException("La razón social es obligatoria");
        }
        if (empresa.getCuit() == null || empresa.getCuit().isBlank()) {
            throw new IllegalArgumentException("El CUIT es obligatorio");
        }
    }

    public Empresa getByCuit(String cuit) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Empresa e = empresaDao.leerPorCuit(cuit, conn);
            if (e != null) {
                DomicilioFiscal df = domicilioFiscalDao.leerPorEmpresaId(e.getId(), conn);
                e.setDomicilioFiscal(df);
            }
            return e;
        }
    }
}
