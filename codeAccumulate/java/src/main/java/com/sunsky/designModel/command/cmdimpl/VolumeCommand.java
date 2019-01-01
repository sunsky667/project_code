package com.sunsky.designModel.command.cmdimpl;

import com.sunsky.designModel.command.Command;
import com.sunsky.designModel.command.Device;

public class VolumeCommand implements Command {

    private Device device;

    public VolumeCommand(Device device) {
        this.device = device;
    }

    @Override
    public void exe() {
        device.volumeUp();
    }

    @Override
    public void unexe() {
        device.volumeDown();
    }

}
