package Repo;

import Model.MyException;
import Model.PrgState;

public interface IRepo {
	java.util.List<PrgState> getPrgList();
	void setPrgList(java.util.List<PrgState> prgStates);
	void updatePrgList(java.util.List<PrgState> prgList);
	void log(String message);
	void logPrgStateExec(PrgState state) throws MyException;
	void writeFinishedExecInFile();
    String getLogString(PrgState state);
	void resetPrgStates();
}
