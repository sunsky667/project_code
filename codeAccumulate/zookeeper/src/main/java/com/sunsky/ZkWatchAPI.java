package com.sunsky;

import org.apache.zookeeper.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZkWatchAPI implements Watcher {

    private ZooKeeper zk = null;
    private CountDownLatch connectedSemaphore = new CountDownLatch( 1 );

    /**
     * close zookeeper connection
     */
    public void realseConnection(){
        if(zk != null){
            try {
                zk.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void connectZookeeper(String connectString, int sessionTimeout){
        this.realseConnection();
        try {
            this.zk = new ZooKeeper(connectString,sessionTimeout,this);
            this.connectedSemaphore.await();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * <p>创建zNode节点, String create(path<节点路径>, data[]<节点内容>, List(ACL访问控制列表), CreateMode<zNode创建类型>) </p><br/>
     * <pre>
     *     节点创建类型(CreateMode)
     *     1、PERSISTENT:持久化节点
     *     2、PERSISTENT_SEQUENTIAL:顺序自动编号持久化节点，这种节点会根据当前已存在的节点数自动加 1
     *     3、EPHEMERAL:临时节点客户端,session超时这类节点就会被自动删除
     *     4、EPHEMERAL_SEQUENTIAL:临时自动编号节点
     * </pre>
     * @param path zNode节点路径
     * @param data zNode数据内容
     * @return 创建成功返回true, 反之返回false.
     */
    public boolean createPath(String path,String data){
        try {
            String zkPath = this.zk.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return true;
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (KeeperException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * <p>删除一个zMode节点, void delete(path<节点路径>, stat<数据版本号>)</p><br/>
     * <pre>
     *     说明
     *     1、版本号不一致,无法进行数据删除操作.
     *     2、如果版本号与znode的版本号不一致,将无法删除,是一种乐观加锁机制;如果将版本号设置为-1,不会去检测版本,直接删除.
     * </pre>
     * @param path zNode节点路径
     * @return 删除成功返回true,反之返回false.
     */
    public boolean deletePath(String path){
        try {
            this.zk.delete(path,-1);
            return true;
        }catch (KeeperException keeperException){
            keeperException.printStackTrace();
        }catch (InterruptedException interruptedException){
            interruptedException.printStackTrace();
        }
        return false;
    }

    /**
     * <p>更新指定节点数据内容, Stat setData(path<节点路径>, data[]<节点内容>, stat<数据版本号>)</p>
     * <pre>
     *     设置某个znode上的数据时如果为-1，跳过版本检查
     * </pre>
     * @param path zNode节点路径
     * @param data zNode数据内容
     * @return 更新成功返回true,返回返回false
     */
    public boolean writeData(String path ,String data){
        try {
            Stat stat = this.zk.setData(path,data.getBytes(),-1);
            return true;
        }catch (InterruptedException exception){
            exception.printStackTrace();
        }catch (KeeperException e){
            e.printStackTrace();
        }
        return false;
    }

    public String readData(String path){
        String data = null;
        try {
            byte[] bytes = this.zk.getData(path,false,null);
            data = new String(bytes);
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (KeeperException e){
            e.printStackTrace();
        }
        return data;
    }

    public boolean isExists(String path){
        try {
            Stat stat = this.zk.exists(path,false);
            return (null != stat);
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (KeeperException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getChild(String path){
        try {
            List<String> childs = this.zk.getChildren(path,false);
            if(childs.isEmpty()){
                System.out.println(path + " no childs path !!!");
            }
            return childs;
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (KeeperException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Watcher Server,处理收到的变更
     * @param event
     */
    public void process(WatchedEvent event) {
        System.out.println("收到事件通知：" + event.getState() );
        if ( Event.KeeperState.SyncConnected == event.getState() ) {
            if(Event.EventType.NodeCreated == event.getType()){
                System.out.println("node create");
            } else if(Event.EventType.NodeDataChanged == event.getType()){
                System.out.println("node data changed");
            } else if(Event.EventType.None == event.getType()){
                System.out.println("success connect zookeeper");
                connectedSemaphore.countDown();
            } else if(Event.EventType.NodeDeleted == event.getType()){
                System.out.println("node delete");
            }
        }
    }

    public static void main(String [] args){
        String root = "/nodeRoot";
        String child1 = root + "/nodeChildren1";
        String child2 = root + "/nodeChildren2";

        ZkWatchAPI zkWatchAPI = new ZkWatchAPI();
        ZkWatchAPI zkWatchAPI1 = new ZkWatchAPI();

        zkWatchAPI.connectZookeeper("192.168.80.128:2181",10000);
        zkWatchAPI1.connectZookeeper("192.168.80.128:2181",10000);

        if(!zkWatchAPI.isExists(root)){
            zkWatchAPI.createPath(root,"<父>节点数据");
        }

        String rootData = zkWatchAPI.readData(root);
        System.out.println(rootData);

        if(!zkWatchAPI.isExists(child1)){
            zkWatchAPI.createPath(child1,"<父-子(1)>节点数据");

        }

        if(!zkWatchAPI1.isExists(child2)){
            zkWatchAPI1.createPath(child2,"<父-子(2)>节点数据");
        }

        if(zkWatchAPI1.isExists(child2)){
            zkWatchAPI1.deletePath(child2);
        }

        String childData1 = zkWatchAPI.readData(child1);
        System.out.println(childData1);
        zkWatchAPI.writeData(child1,"<父-子(1)>节点数据 change");
        String childData2 = zkWatchAPI.readData(child1);
        System.out.println(childData2);
    }
}
