package com.sunsky.designModel.command;

import com.sunsky.designModel.command.cmdimpl.ChannelCommand;
import com.sunsky.designModel.command.cmdimpl.SwitchCommand;
import com.sunsky.designModel.command.cmdimpl.VolumeCommand;
import com.sunsky.designModel.command.deviceimpl.Radio;
import com.sunsky.designModel.command.deviceimpl.TV;

/**
 * 命令模式
 * 三个模块，首先得有命令发送方，
 * 接着是被传递的命令本身，
 * 最后就是命令的接收执行方了。
 * @url https://www.javazhiyin.com/24667.html
 *
 *      1.Device接口继承Switchable接口，Switchable接口是定义基本的操作（Switchable接口的实现类,Fan,Bulb未用到），
 *          Device在Switchable接口上扩展了操作
 *      2.Device接口的实现类，Radio和TV，定义了各个方法的功能
 *      3.Command接口，定义了基本操作
 *      4.Command接口的实现类，频道命令，开关命令，声音命令，对应有不同的操作
 *      5.在Controller类里的成员变量是所有的Command的实现类，Command的不同的方法里调用不同的命令的执行方法
 *
 * 命令模式是通过多组命令实现来控制设备，
 * Controller控制器对设备是毫无感知的，
 * Controller里只有命令，Controller控制命令，命令控制设备
 * Controller  --> Command --> Device
 * 相比较与策略模式，系统更加松散，组合更加灵活
 * @see com.sunsky.designModel.stragedy.Client
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("===客户端用【可编程式遥控器】操作电器===");
        Device tv = new TV();
        Device radio = new Radio();
        Controller controller = new Controller();

        //绑定【电视机】的【命令】到【控制器按键】
        controller.bindOKCommand(new SwitchCommand(tv));
        controller.bindVerticalCommand(new ChannelCommand(tv));//上下调台
        controller.bindHorizontalCommand(new VolumeCommand(tv));//左右调音

        controller.buttonOKHold();
        controller.buttonUpClick();
        controller.buttonUpClick();
        controller.buttonDownClick();
        controller.buttonRightClick();

        /*打印输出：
            ===客户端用【可编程式遥控器】操作电器===
            长按OK按键……电视机启动
            单击↑按键……电视机频道+
            单击↑按键……电视机频道+
            单击↓按键……电视机频道-
            单击→按键……电视机音量+
        */

        //绑定【收音机】的【命令】到【控制器按键】
        controller.bindOKCommand(new SwitchCommand(radio));
        controller.bindVerticalCommand(new VolumeCommand(radio));//上下调音
        controller.bindHorizontalCommand(new ChannelCommand(radio));//左右调台

        controller.buttonOKHold();
        controller.buttonUpClick();
        controller.buttonUpClick();
        controller.buttonRightClick();
        controller.buttonDownClick();

        /*打印输出：
            长按OK按键……收音机启动
            单击↑按键……收音机音量+
            单击↑按键……收音机音量+
            单击→按键……收音机调频+
            单击↓按键……收音机音量-
        */

    }

}
