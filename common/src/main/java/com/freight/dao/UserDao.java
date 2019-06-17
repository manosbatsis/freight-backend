package com.freight.dao;

import com.freight.model.User;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.hibernate.query.Query;

import javax.annotation.Nullable;
import java.util.Optional;

import static com.freight.model.User.Status.ACTIVE;
import static java.util.Objects.requireNonNull;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class UserDao extends BaseDao<User> {

    @AssistedInject
    public UserDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, User.class);
    }

    public Optional<User> getByGuid(final String guid) {
        requireNonNull(guid);
        return getFirst(getByField("guid", guid));
    }

    /**
     * Either email or phone has to be not null
     * @return created User object
     */
    public User createUser(final String guid,
                           @Nullable final String email,
                           @Nullable final Integer phone,
                           final User.Type type) {
        requireNonNull(guid);
        requireNonNull(type);
        getSessionProvider().startTransaction();
        final User user = new User.Builder()
                .guid(guid)
                .status(ACTIVE)
                .email(email)
                .phone(phone)
                .type(type)
                .build();
        getSessionProvider().getSession().persist(user);
        getSessionProvider().commitTransaction();
        return user;
    }

    public void updateUser(final String guid,
                           final String username) {
        requireNonNull(guid);
        final Query query = getSessionProvider().getSession().createQuery(
                "UPDATE " + clazz.getName() + " SET username = :username WHERE guid = :guid");
        query.setParameter("guid", guid);
        query.setParameter("username", username);
        query.executeUpdate();
    }
}
