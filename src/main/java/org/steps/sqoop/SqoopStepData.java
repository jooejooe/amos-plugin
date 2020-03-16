package org.steps.sqoop;

import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

/**
 * @program: kettle-sdk-step-plugin
 * @description: ${description}
 * @author: Gou Ding Cheng
 * @create: 2019-09-18 17:10
 **/
public class SqoopStepData extends BaseStepData implements StepDataInterface {
    RowMetaInterface outputRowMeta;
    public SqoopStepData() {
        super();
    }
}