package pzinsta.pizzeria.dao.impl;

import org.springframework.stereotype.Repository;
import pzinsta.pizzeria.dao.AccountDAO;
import pzinsta.pizzeria.model.user.Account;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class AccountDAOImpl extends GenericDAOImpl<Account, Long> implements AccountDAO {

    public AccountDAOImpl() {
        super(Account.class);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<Account> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));
        TypedQuery<Account> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList().stream().findFirst();
    }
}
