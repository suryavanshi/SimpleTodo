package com.manu.simpletodo;

/**
 * Created by Manu on 9/16/2016.
 */

import com.google.common.base.Strings;
import com.orm.SugarRecord;

public class TodoTask extends SugarRecord {

    String taskName;
    String endDate;

    public TodoTask() {

    }

    public TodoTask(String taskName, String endDate) {
        this.taskName = taskName;
        this.endDate = endDate;
    }

}
