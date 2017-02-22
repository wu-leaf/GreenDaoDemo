package com.veyron.www.greendaodemo;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import www.greendao.com.DaoMaster;
import www.greendao.com.DaoSession;
import www.greendao.com.Father;
import www.greendao.com.FatherDao;
import www.greendao.com.Son;
import www.greendao.com.SonDao;

public class MainActivity extends AppCompatActivity {

    //greenDAO提供的帮助类
    private DaoMaster master;
    private DaoSession session;
    private SQLiteDatabase db;
    private SonDao sonDao;
    private FatherDao fatherDao;
    private void openDb(){
        //创建一个名为person.db的数据库
        db = new DaoMaster.DevOpenHelper(MainActivity.this,"person.db",null).getWritableDatabase();
        master = new DaoMaster(db);
        session = master.newSession();
        //获得son表操作数据库类的对象
        sonDao = session.getSonDao();
        //获得father表操作数据库类的对象
        fatherDao = session.getFatherDao();
    }

    //插入数据
    private void addPerson(){
        //下面代码具体得根据数据库表的关系，这里只是示例（son有多个father）
        Son son = new Son();
        son.setName("nate");
        son.setAge(29);
        sonDao.insert(son);

        Father father = new Father();
        father.setName("Tom");
        father.setSon(son);
        fatherDao.insert(father);

        Father father2 = new Father();
        father2.setName("Jane");
        father2.setSon(son);
        fatherDao.insert(father2);

    }

//常用api：
//list() 直接取出数据返回一个list并缓存数据
//listLazy() 不直接取出数据返回一个list有缓存，需手动close
//listLazyUncached() 延迟加载返回一个list不缓存数据，需手动close
//listIterator() 遍历数据，返回一个迭代器

    public void queryAll(){
        //listLazy()懒加载，多个表级联查询使用最佳
        List<Son> list = sonDao.queryBuilder().list();//查询son表中的所有数据，返回集合
        for (Son son: list){
            Log.d("nate","queryAll() called with: "+son);
        }
    }

    //条件查询: Eq 查询
    public void queryEq(){
        Son nate =  sonDao.queryBuilder().where(SonDao.Properties.Name.eq("nate")).unique();
        Log.d("nate","queryEq called with:"+ nate);
    }

    //条件查询: like 查询
    public void queryLike(){
        List data =  sonDao.queryBuilder().where(SonDao.Properties.Name.like("nate%")).list();
        Log.d("nate","queryEq called with:"+ data);
    }

    //条件查询: between 查询
    public void queryBetween(){
        List data =  sonDao.queryBuilder().where(SonDao.Properties.Age.between(16,20)).list();
        Log.d("nate","queryBetween called with:"+ data);
    }

    //条件查询: > 大于 查询
    public void queryGt(){
        List data =  sonDao.queryBuilder().where(SonDao.Properties.Age.gt(16)).list();
        Log.d("nate","queryGt called with:"+ data);
    }

    //条件查询: < 小于 查询
    public void queryLt(){
        List data =  sonDao.queryBuilder().where(SonDao.Properties.Age.lt(16)).list();
        Log.d("nate","queryLt called with:"+ data);
    }

    //条件查询: NotEq 查询
    public void queryNotEq(){
        List data =  sonDao.queryBuilder().where(SonDao.Properties.Age.notEq(16)).list();
        Log.d("nate","queryNotEq called with:"+ data);
    }

    //条件查询: GE 大于等于 查询
    public void queryGE(){
        List data =  sonDao.queryBuilder().where(SonDao.Properties.Age.ge(16)).list();
        Log.d("nate","queryGE called with:"+ data);
    }

    //条件查询: 排序 查询
    public void queryOrder(){
        List data =  sonDao.queryBuilder().where(SonDao.Properties.Name.like("nate%"))
                .orderDesc(SonDao.Properties.Age).list();
        Log.d("nate","queryOrder called with:"+ data);
    }

    //拼装sql语句查询，在api无法满足查询需求的情况下使用
    public void querySQL(){
        List data =  sonDao.queryBuilder()
                .where(new WhereCondition.StringCondition(
                        "FATHER_ID IN" +"{SELECT _ID FROM FATHER WHERE AGE <50}"
                )).list();
        Log.d("nate","querySQL called with:"+ data);
    }

    //多线程查询:有兴趣可以看源码
    public void queryThread(){
        final Query query = sonDao.queryBuilder().build();
        new Thread(){
            @Override
            public void run() {
                List data = query.forCurrentThread().list();
                Log.d("nate","queryThread called with:"+ data);
            }
        };
    }
    //一对多查询：具体得看数据表关系
    public void queryOneToMany(){
        List<Son> sons = sonDao.queryBuilder().list();//查询son表中的所有数据，返回集合
        for (Son son:sons){
            List<Father> fathers = son.getFathers();
            for (Father father:fathers){
                Log.d("nate","queryOneToMany() called with: "+son.getName()+" father:"+father.getName());
            }
        }
    }
    //一对一查询：具体得看数据表关系
    public void queryOneToOne(){
        List<Son> sons = sonDao.queryBuilder().list();//查询son表中的所有数据，返回集合
        for (Son son:sons){
            // Log.d("nate","queryOneToOne() called with: "+son.getFather().getName());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //打开一个数据库
        openDb();

        //向数据库添加person
        addPerson();

        //设置打印sql语句,方便查看
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        //选择具体需要的查询方式测试：如queryOrder
        queryOrder();

    }
}