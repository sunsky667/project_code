package com.sunsky.designModel.command.cmdimpl;

import com.sunsky.designModel.command.Command;
import com.sunsky.designModel.command.Device;

public class ChannelCommand implements Command {

    private Device device;

    public ChannelCommand(Device device) {
        this.device = device;
    }

    @Override
    public void exe() {
        device.channelUp();
    }

    @Override
    public void unexe() {
        device.channelDown();
    }

}
