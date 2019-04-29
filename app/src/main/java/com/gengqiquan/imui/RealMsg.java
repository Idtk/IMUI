package com.gengqiquan.imui;

public class RealMsg implements IimMsg {
    TXMsg txMsg;
    boolean isSelf;

    public RealMsg(TXMsg txMsg, boolean isSelf) {
        this.txMsg = txMsg;
        this.isSelf = isSelf;
    }

    @Override
    public int uiType() {
        if (txMsg.type == TXMsg.Type.TEXT)
            return 1;
        else if (txMsg.type == TXMsg.Type.IMG)
            return 2;
        else
            return 3;
    }


    @Override
    public TXMsg realData() {
        return txMsg;
    }

    @Override
    public boolean isSelf() {
        return isSelf;
    }

}
