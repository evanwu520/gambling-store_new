package com.ampletec.concurrent.taskchain;

public interface TaskObject extends Comparable<TaskObject>, Cloneable {

    /**
     * get object order
     *
     * @return
     */
    int order();

    /**
     * sure the object order is the end of the queue , if true return true, otherwise return false.
     *
     * @return
     */
    boolean isEndOrder();

    /**
     * get group order key
     *
     * @return
     */
    String groupKey();

    /**
     * get clone object
     *
     * @return
     * @throws CloneNotSupportedException
     */
    Object clone() throws CloneNotSupportedException;
}
