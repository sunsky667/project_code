package com.sunsky.designModel.adapter.power;

/**
 * 另一种更简单的方式叫做“类适配器”
 */
public class ClassAdapter extends TV implements TriplePin {
    @Override
    public void electrify(int l, int n, int e) {
        super.electrify(l,n);
    }
}
