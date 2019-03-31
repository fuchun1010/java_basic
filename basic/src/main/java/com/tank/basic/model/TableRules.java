package com.tank.basic.model;

/**
 * @author
 */
public class TableRules {

  public TableListenerModel recognizeModel(final String exp) {

    if ("*".equalsIgnoreCase(exp)) {
      return TableListenerModel.ALL;
    }

    TableListenerModel model = exp.split(",").length > 1 ? TableListenerModel.MULTI : TableListenerModel.SINGLE;

    return model;

  }

}
