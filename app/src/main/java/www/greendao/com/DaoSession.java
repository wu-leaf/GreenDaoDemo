package www.greendao.com;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import www.greendao.com.Son;
import www.greendao.com.Father;

import www.greendao.com.SonDao;
import www.greendao.com.FatherDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig sonDaoConfig;
    private final DaoConfig fatherDaoConfig;

    private final SonDao sonDao;
    private final FatherDao fatherDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        sonDaoConfig = daoConfigMap.get(SonDao.class).clone();
        sonDaoConfig.initIdentityScope(type);

        fatherDaoConfig = daoConfigMap.get(FatherDao.class).clone();
        fatherDaoConfig.initIdentityScope(type);

        sonDao = new SonDao(sonDaoConfig, this);
        fatherDao = new FatherDao(fatherDaoConfig, this);

        registerDao(Son.class, sonDao);
        registerDao(Father.class, fatherDao);
    }
    
    public void clear() {
        sonDaoConfig.getIdentityScope().clear();
        fatherDaoConfig.getIdentityScope().clear();
    }

    public SonDao getSonDao() {
        return sonDao;
    }

    public FatherDao getFatherDao() {
        return fatherDao;
    }

}
