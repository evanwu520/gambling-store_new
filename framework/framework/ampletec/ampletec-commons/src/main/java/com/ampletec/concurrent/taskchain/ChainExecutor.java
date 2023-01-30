package com.ampletec.concurrent.taskchain;

public interface ChainExecutor<T extends TaskObject> {

	void execute(T t);
}
