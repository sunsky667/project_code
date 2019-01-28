package com.sunsky.designModel.visitor;

public interface Visitor {

    public void visit(Candy candy);

    public void visit(Wine wine);

    public void visit(Fruit fruit);
}
