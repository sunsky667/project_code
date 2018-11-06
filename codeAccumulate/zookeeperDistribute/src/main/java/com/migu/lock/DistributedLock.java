package com.migu.lock;

import com.migu.util.DateUtil;
import com.migu.util.WCTime;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 *  成功连接上ZK服务器
 *	获取锁创建锁路径:/disLocks/sub0000000268
 *	检查最小节点过程中，获取子节点中，排在我前面的/disLocks/sub0000000267
 *	监听中，收到情报，排我前面的家伙已挂，我是不是可以出山了？
 *	检查最小节点过程中，在所有子节点中，我果然是老大/disLocks/sub0000000268
 *	获取锁成功，赶紧干活！
 *	==========================================201803282250
 *	真正干活
 *	干完活后，删除本节点：/disLocks/sub0000000268
 *	干完活释放连接
 * @author sunsky
 * 
 * 1.初始化对象
 * 2.获取连接
 * 3.创建主节点
 * 4.获取锁
 */
public class DistributedLock implements Watcher{
	//zk对象
	private ZooKeeper zk = null;
	//当前节点的目录名 通过获取锁的时候初始化selfPath 
	private String selfPath; 
	//前一个节点的目录名
    private String waitPath;

    private static int SESSION_TIMEOUT ;
    private static String ROOT_GROUP_PATH;
    private static String SUB_PATH ;
    private static String CONNECTION_STRING ;

    private static String PIDS_SUB_PATH ;
    private static String PIDS_ROOT_PATH;
    
    static{
    	Properties properties = new Properties();
    	try {
			properties.load(DistributedLock.class.getClassLoader().getResourceAsStream("application.properties"));
			SESSION_TIMEOUT = Integer.parseInt(properties.getProperty("zookeeper.connect.timeout"));
			ROOT_GROUP_PATH = properties.getProperty("zookeeper.root.path");
			SUB_PATH = properties.getProperty("zookeeper.sub.path");
			CONNECTION_STRING = properties.getProperty("zookeeper.connect.url");

            PIDS_ROOT_PATH = properties.getProperty("zookeeper.pidroot.path");
            PIDS_SUB_PATH = properties.getProperty("zookeeper.pidsub.path");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    //确保连接zk成功
    private CountDownLatch connectedSemaphore = new CountDownLatch(1);
    
    /**
     * 创建ZK连接
     * 1.创建具有监听功能的zk连接
     * 2.调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
     * 3.一直等待zk连接创建成功，创建成功的标志是连接上zkServer，并创建监听成功
     * 4.在成功监听到连接成功事件后调用CountDownLatch.countDown()方法，将count置为0，可以往下执行
     */
    public void createConnection() throws IOException, InterruptedException {
    	//创建具有监听功能的zk连接
        zk = new ZooKeeper(CONNECTION_STRING, SESSION_TIMEOUT, this);
        //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
        //一直等待zk连接创建成功，创建成功的标志是连接上zkServer，并创建监听成功，
        //在成功监听到连接成功事件后调用CountDownLatch.countDown()方法，将count置为0，可以往下执行
        connectedSemaphore.await();
    }
    
    /**
     * 创建节点,主要是为了创建主节点，如果主节点存在了，就不做任何操作了
     * @param data 初始数据内容
     * @param needWatch 是否需要监听
     * @return
     */
    public boolean createPath(String data, boolean needWatch) throws KeeperException, InterruptedException {
    	//如果主节点存在了，就不做任何操作了
        if(zk.exists(ROOT_GROUP_PATH, needWatch) == null){
        	String createPath = this.zk.create(ROOT_GROUP_PATH,data.getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        	System.out.println("create path : "+createPath);
        }
        return true;
    }
    
    /**
     * 检查自己是不是最小的节点
     * 若不是最小的节点，会找到本节点的上一个节点
     * @return 是否是最小的节点
     */
    public boolean checkMinPath() throws KeeperException, InterruptedException {
    	//得到根节点下的所有的子节点
        List<String> subNodes = zk.getChildren(ROOT_GROUP_PATH, false);
        //从小到大排序排序所有子节点
        Collections.sort(subNodes);
        
        //得到子目录在排序后的列表中的位置
        int index = subNodes.indexOf(selfPath.substring(ROOT_GROUP_PATH.length()+1));
        
        switch (index){
        	//若子目录不在列表中了，则该节点已经不存在了
            case -1:{
                System.out.println("检查最小节点过程中，本节点已不在了..."+selfPath);
                return false;
            }
            //若子目录在列表中的第一个位置，说明该节点id最小
            case 0:{
                System.out.println("检查最小节点过程中，在所有子节点中，我果然是老大"+selfPath);
                return true;
            }
            //其他情况
            default:{
            	//获取本节点前一个节点的路径
                this.waitPath = ROOT_GROUP_PATH +"/"+ subNodes.get(index - 1);
                System.out.println("检查最小节点过程中，获取子节点中，排在我前面的"+waitPath);
                try{
                	//获取前一个节点路径下的数据
                    zk.getData(waitPath, true, new Stat());
                    //若获取数据时不报异常，则说明前一个节点还存在，返回该节点不是最小节点
                    return false;
                }catch(KeeperException e){
                	//若获取上一个节点出现异常，开始判断上一个节点是否存在
                    if(zk.exists(waitPath,false) == null){
                    	//若不存在，需从新确认本节点是不是最新的节点
                        System.out.println("检查最小节点过程中，子节点中，排在我前面的"+waitPath+"已失踪，幸福来得太突然?");
                        return checkMinPath();
                    }else{
                    	//若节点存在，但获取不到数据，就是其他异常了，抛出即可
                        throw e;
                    }
                }
            }

        }

    }
    
    /**
     * 获取锁成功
     */
    public void getLockSuccess() throws KeeperException, InterruptedException {
        if(zk.exists(this.selfPath,false) == null){
        	//获取成功后发现该节点不存在了，直接返回，不处理
            System.out.println("在获取成功锁后，发现本节点已不在了...");
            return;
        }
        System.out.println("获取锁成功，赶紧干活！");
        
        /********************************************业务逻辑开始*********************************************/
        
        Map<String,String> maps = WCTime.getPidTime();

        for(Map.Entry<String,String> map : maps.entrySet()){

            byte[] data = zk.getData(PIDS_SUB_PATH+map.getKey(), false, null);
            String time = new String(data);

            System.out.println("=================================time"+time);

            try {
                System.out.println(PIDS_SUB_PATH+map.getKey()+" $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ "+time);
                zk.setData(PIDS_SUB_PATH+map.getKey(), DateUtil.getNextTimeMin(time).getBytes(), -1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        /*********************************************业务逻辑结束********************************************/
        System.out.println("干完活后，删除本节点："+selfPath);
        //干完活后删除本临时节点
        zk.delete(this.selfPath, -1);
        //释放连接
        releaseConnection();
    }
    
    /**
     * 获取锁
     * 1.创建临时子节点
     * 2.检查创建的子节点是不是最小的节点
     * 3.若是最小节点，则调用获取锁成功的方法，处理业务逻辑
     * 4.若不是最小的节点，则会在checkMinPath()方法里初始化该节点需要等待的上一个节点
     * 5.然后进入事件监听状态，若删除的节点为等待的上一个节点
     * 6.然后判断该节点是否是最小的节点id
     * 7.若是，调用getLockSuccess()方法处理业务逻辑
     * @return
     */
    public void getLock() throws KeeperException, InterruptedException {
    	//首先创建临时的子节点
        selfPath = zk.create(SUB_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("获取锁创建锁路径:"+selfPath);
        //然后判断创建的该节点是不是最小的节点，若不是，则会在checkMinPath()方法里初始化该节点需要等待的上一个节点
        if(checkMinPath()){
        	//若是就调用成功获取锁的方法，处理业务逻辑
            getLockSuccess();
        }
    }
    
    /**
     * 关闭ZK连接
     */
    public void releaseConnection() {
        if ( this.zk !=null ) {
            try {
                this.zk.close();
            } catch ( InterruptedException e ) {}
        }
        System.out.println("干完活释放连接");
    }

    /**
     * 事件监听
     * 1.监测到成功连接，将CountDownLatch减1
     * 2.监测到本节点的前一个节点删除的话
     * 3.检查本节点是不是最小的节点
     * 4.若是则调用getLockSuccess处理业务逻辑
     */
	@Override
	public void process(WatchedEvent event) {
		if(event == null){
            return;
        }
        Event.KeeperState keeperState = event.getState();
        Event.EventType eventType = event.getType();
        
        if ( Event.KeeperState.SyncConnected == keeperState) {
            if ( Event.EventType.None == eventType ) {
                System.out.println("成功连接上ZK服务器" );
                //在监听到连接服务事件（创建服务完毕后）将CountDownLatch减1，保证后面任务执行
                connectedSemaphore.countDown();
            //监听到删除节点的事件，同时满足删除的节点是本节点的上一个节点
            }else if (event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(waitPath)) {
                System.out.println("监听中，收到情报，排我前面的家伙已挂，我是不是可以出山了？");
                try {
                	//判断是否是最小节点
                    if(checkMinPath()){
                    	//处理业务逻辑
                        getLockSuccess();
                    }
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else if ( Event.KeeperState.Disconnected == keeperState ) {
            System.out.println("监听与ZK服务器断开连接" );
        } else if ( Event.KeeperState.AuthFailed == keeperState ) {
            System.out.println("监听权限检查失败" );
        } else if ( Event.KeeperState.Expired == keeperState ) {
            System.out.println("监听会话失效" );
        }
	}

    public boolean createPath(String Path,String data, boolean needWatch) throws KeeperException, InterruptedException {
        //如果主节点存在了，就不做任何操作了
        if(zk.exists(Path, needWatch) == null){
            String createPath = this.zk.create(Path,data.getBytes(),null ,CreateMode.PERSISTENT);
            System.out.println("create path : "+createPath);
        }
        return true;
    }
    
}
