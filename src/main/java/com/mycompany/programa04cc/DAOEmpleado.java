/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programa04cc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author irvin
 * @param <T>
 */
public class DAOEmpleado implements IDAOGeneral<Empleado, Long> {

    ConexionDB cx = null;
    TransactionDB tdb = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    ResultSet resultado = null;

    @Override
    public Empleado create(Empleado p) {

        cx = ConexionDB.getInstance();

        tdb = new TransactionDB<Empleado>(p) {
            @Override
            public Empleado execute(Connection con) {
                try {
                    String sql = "insert into ejemplo (id, nombre, direccion, telefono)"
                            + "values (?,?,?,?)";

                    pst = con.prepareStatement(sql);
                    pst.setLong(1, p.getClave());
                    pst.setString(2, p.getNombre());
                    pst.setString(3, p.getDireccion());
                    pst.setString(4, p.getTelefono());
                    resultado = pst.executeQuery();
                    pst.executeQuery();
                    
                    return p;

                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return p;

                } finally {
                    try {
                        if (pst != null) {
                            pst.close();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        cx.execute(tdb);                    
        return p;
    }

    @Override
    public boolean delete(Long id) {
        cx = ConexionDB.getInstance();

        tdb = new TransactionDB<Long>(id) {
            @Override
            public Long execute(Connection con) {
                try {
                    String sql = "delete from ejemplo where id = ?";

                    pst = con.prepareStatement(sql);
                    pst.setLong(1, id);
                    int result = pst.executeUpdate();
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, "Registros afectados: {0}", result);
                    return id;

                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return 0L;
                } finally {
                    try {
                        if (pst != null) {
                            pst.close();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

        };
        cx.execute(tdb);

        return true;
    }

    @Override
    public Empleado update(Long id, Empleado p) {
        cx = ConexionDB.getInstance();

        tdb = new TransactionDB<Empleado>(p) {
            @Override
            public Empleado execute(Connection con) {
                try {

                    String sql = "update ejemplo set id = ?, nombre = ?, direccion = ?, telefono = ? where id = ?";

                    pst = con.prepareStatement(sql);
                    pst.setLong(1, p.getClave());
                    pst.setString(2, p.getNombre());
                    pst.setString(3, p.getDireccion());
                    pst.setString(4, p.getTelefono());
                    pst.setLong(5, id);
                    pst.executeUpdate();
                    rs = pst.executeQuery();
                    return (Empleado) rs;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return (Empleado) rs;
                } finally {
                    try {
                        if (pst != null) {
                            pst.close();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }

        };

        cx.execute(tdb);

        return null;
    }

    @Override
    public List<Empleado> findAll() {
        cx = ConexionDB.getInstance();
        List<Empleado> listar = new ArrayList<>();

        tdb = new TransactionDB<Empleado>() {
            @Override
            public Empleado execute(Connection con) {
                try {
                    String sql = "select * from ejemplo ";

                    pst = con.prepareStatement(sql);
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, rs.getString(1));
                        Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, rs.getString(2));
                        Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, rs.getString(3));
                        Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, rs.getString(4));
                        Empleado ejemplo = new Empleado(Long.parseLong(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4));
                        listar.add(ejemplo);
                    }
                    return (Empleado) rs;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return (Empleado) rs;
                } finally {
                    try {
                        if (pst != null) {
                            pst.close();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

        };

        cx.execute(tdb);
        return listar;
    }

    @Override
    public Empleado findByID(Long id) {
        cx = ConexionDB.getInstance();

        tdb = new TransactionDB<Long>(id) {
            @Override
            public Long execute(Connection con) {
                try {
                    String sql = "select * from ejemplo where id =?";

                    pst = con.prepareStatement(sql);
                    pst.setLong(1, id);

                    rs = pst.executeQuery();

                    while (rs.next()) {
                        Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, rs.getString(1));
                        Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, rs.getString(2));
                        Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, rs.getString(3));
                        Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, rs.getString(4));
                    }

                    return 1L;

                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return 0L;
                } finally {
                    try {
                        if (pst != null) {
                            pst.close();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }

        };

        cx.execute(tdb);

        return null;
    }

}
