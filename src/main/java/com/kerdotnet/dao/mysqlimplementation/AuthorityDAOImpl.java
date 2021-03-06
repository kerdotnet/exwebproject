package com.kerdotnet.dao.mysqlimplementation;

import com.kerdotnet.entity.Authority;
import com.kerdotnet.dao.IAuthorityDAO;
import com.kerdotnet.dao.helpers.AuthorityExtractor;
import com.kerdotnet.dao.helpers.IEnricher;
import com.kerdotnet.exceptions.DAOSystemException;

import java.util.List;

public class AuthorityDAOImpl extends AbstractDAO implements IAuthorityDAO {

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM authority";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM authority WHERE id=?";
    private static final String SQL_INSERT_ONE = "INSERT INTO authority  " +
            " (name, flag_user, flag_administrator) VALUES " +
            " (?,?,?)";
    private static final String SQL_UPDATE_ONE = "UPDATE authority SET " +
            " name = ?, flag_user = ?, flag_administrator = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE_ONE = "DELETE FROM user_authority WHERE id = ?";

    public AuthorityDAOImpl() {
        super();
    }

    @Override
    public Authority findEntity(Integer id) throws DAOSystemException {
        return (Authority) findEntity(SQL_SELECT_BY_ID, id,  new AuthorityExtractor(),
                IEnricher.NULL);
    }

    @Override
    public List<Authority> findAll() throws DAOSystemException {
        return findAll(SQL_SELECT_ALL, new AuthorityExtractor(),
                IEnricher.NULL);
    }
    @Override
    public boolean create(Authority entity) throws DAOSystemException {
        return create(SQL_INSERT_ONE, entity, new AuthorityExtractor());
    }

    @Override
    public boolean update(Authority entity) throws DAOSystemException {
        return update(SQL_UPDATE_ONE, entity, new AuthorityExtractor());
    }

    @Override
    public boolean delete(Authority entity) throws DAOSystemException {
        return delete(SQL_DELETE_ONE, entity);
    }
}
