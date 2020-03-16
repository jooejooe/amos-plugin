package org.steps.hbase;

import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

/**
 * @program: kettle-sdk-step-plugin
 * @description: ${description}
 * @author: Gou Ding Cheng
 * @create: 2019-09-17 11:28
 **/
public class HbaseStepData extends BaseStepData implements StepDataInterface {
    RowMetaInterface outputRowMeta;

    public HbaseStepData() {
        super();
    }
}