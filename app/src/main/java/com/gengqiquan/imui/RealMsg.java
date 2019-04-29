package com.gengqiquan.imui;

public class RealMsg implements IimMsg {
    TXMsg txMsg;

    public RealMsg(TXMsg txMsg) {
        this.txMsg = txMsg;
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
        return false;
    }

}
