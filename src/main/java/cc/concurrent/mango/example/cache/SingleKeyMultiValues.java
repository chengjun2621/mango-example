package cc.concurrent.mango.example.cache;

import cc.concurrent.mango.DriverManagerDataSource;
import cc.concurrent.mango.Mango;

import javax.sql.DataSource;

public class SingleKeyMultiValues {

    public static void main(String[] args) {
        String driverClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/mango_db";
        String username = "root"; // 这里请使用您自己的用户名
        String password = "root"; // 这里请使用您自己的密码
        DataSource ds = new DriverManagerDataSource(driverClassName, url, username, password);
        Mango mango = new Mango(ds, new CacheHandlerImpl()); // 使用数据源和CacheHandlerImpl初始化mango

        SingleKeyMultiValuesDao dao = mango.create(SingleKeyMultiValuesDao.class);
        int uid = 1;
        Message message = newMessage(uid, "hello");
        Message message2 = newMessage(uid, "world");
        Message message3 = newMessage(uid, "boy");
        message.setId(dao.insert(message));
        message2.setId(dao.insert(message2));
        message3.setId(dao.insert(message3));
        System.out.println(dao.getMessages(uid));
        message3.setContent("girl");
        dao.update(message3);
        System.out.println(dao.getMessages(uid));
        dao.delete(uid, message.getId());
        System.out.println(dao.getMessages(uid));
    }

    private static Message newMessage(int uid, String content) {
        Message message = new Message();
        message.setUid(uid);
        message.setContent(content);
        return message;
    }

}
